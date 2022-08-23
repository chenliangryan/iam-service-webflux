package com.yugabyte.demo.iamservicewebflux;

/* Ref: https://github.com/pgjdbc/r2dbc-postgresql/issues/120#issuecomment-765471935 */

import io.r2dbc.spi.Connection;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryMetadata;
import io.r2dbc.spi.ConnectionFactoryOptions;
import io.r2dbc.spi.ConnectionFactoryProvider;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;

/**
 * An implementation of {@link ConnectionFactory} for creating failover connections to delegated
 * {@link ConnectionFactory}s.
 * <p> Usage:
 * <p> 1. Create file in resources directory (on classpath):
 * META-INF/services/io.r2dbc.spi.ConnectionFactoryProvider with the following line:<p>
 * com.yugabyte.demo.iamservicewebflux.FailoverConnectionFactoryProvider
 * <p> 2. Update spring.r2dbc.url - add failover driver before last driver (for example:
 * postgresql):<p> url:
 * r2dbc:pool:failover:postgresql://host1[:port1],host2[:port2]/database[?connectTimeout=PT5S]
 *
 * @author Anton Kotov
 */
public class FailoverConnectionFactoryProvider implements ConnectionFactoryProvider {

    /*
     * Driver option value.
     */
    public static final String FAILOVER_DRIVER = "failover";
    public static final Duration DEFAULT_CONNECT_TIMEOUT = Duration.ofSeconds(15);

    private static final String COLON = ":";
    private static final String COMMA = ",";

    @Override
    public ConnectionFactory create(ConnectionFactoryOptions connectionFactoryOptions) {

        String protocol = connectionFactoryOptions.getRequiredValue(ConnectionFactoryOptions.PROTOCOL)
                .toString();
        if (protocol.trim()
                .length() == 0) {
            throw new IllegalArgumentException(String.format("Protocol %s is not valid.", protocol));
        }
        String[] protocols = protocol.split(COLON, 2);
        String driverDelegate = protocols[0];

        // when protocol does NOT contain COLON, the length becomes 1
        String protocolDelegate = protocols.length == 2 ? protocols[1] : "";

        String host = connectionFactoryOptions.getRequiredValue(ConnectionFactoryOptions.HOST)
                .toString();
        if (host.trim()
                .length() == 0) {
            throw new IllegalArgumentException(String.format("Host %s is not valid.", host));
        }

        List<ConnectionFactory> connectionFactories = new ArrayList<>();

        String[] hosts = host.trim()
                .split(COMMA);
        for (String hostDelegate : hosts) {

            String[] hostAndPort = hostDelegate.split(COLON, 2);
            hostDelegate = hostAndPort[0];

            // when hostAndPort does NOT contain COLON, the length becomes 1
            Integer portDelegate = hostAndPort.length == 2 ? Integer.parseInt(hostAndPort[1]) : null;

            ConnectionFactoryOptions.Builder newOptions = ConnectionFactoryOptions.builder()
                    .from(connectionFactoryOptions)
                    .option(ConnectionFactoryOptions.DRIVER, driverDelegate)
                    .option(ConnectionFactoryOptions.PROTOCOL, protocolDelegate)
                    .option(ConnectionFactoryOptions.HOST, hostDelegate);

            if (portDelegate != null) {
                newOptions.option(ConnectionFactoryOptions.PORT, portDelegate);
            }

            // Run discovery again to find the actual connection factory
            ConnectionFactory connectionFactory = ConnectionFactories.find(newOptions.build());
            if (connectionFactory == null) {
                throw new IllegalArgumentException(
                        String.format("Could not find delegating driver [%s]", driverDelegate));
            }

            connectionFactories.add(connectionFactory);
        }

        if (connectionFactories.isEmpty()) {
            throw new IllegalArgumentException(String.format("Host %s is not valid.", host));
        }
        if (connectionFactories.size() == 1) {
            return connectionFactories.get(0);
        }
        return FailoverConnectionFactory.newBuilder()
                .connectTimeout(
                        Optional.ofNullable(
                                        toDuration(connectionFactoryOptions.getValue(ConnectionFactoryOptions.CONNECT_TIMEOUT)))
                                .orElse(DEFAULT_CONNECT_TIMEOUT)
                )
                .addConnectionFactories(connectionFactories)
                .build();
    }

    @Override
    public boolean supports(ConnectionFactoryOptions connectionFactoryOptions) {
        Objects.requireNonNull(connectionFactoryOptions, "connectionFactoryOptions must not be null");

        String driver = Objects.requireNonNull(
                        connectionFactoryOptions.getValue(ConnectionFactoryOptions.DRIVER))
                .toString();
        return driver != null && driver.equals(FAILOVER_DRIVER);
    }

    @Override
    public String getDriver() {
        return FAILOVER_DRIVER;
    }

    private static Duration toDuration(Object value) {
        if (value == null) {
            return null;
        }

        if (value instanceof Duration) {
            return ((Duration) value);
        }

        if (value instanceof String) {
            return Duration.parse(value.toString());
        }

        throw new IllegalArgumentException(
                String.format("Cannot convert value %s into Duration", value));
    }

    public static class FailoverConnectionFactory implements ConnectionFactory {

        private static final Logger logger = LoggerFactory.getLogger(FailoverConnectionFactory.class);

        private final List<ConnectionFactory> connectionFactories;
        private final AtomicInteger currentConnectionFactoryIndex = new AtomicInteger(0);
        private final Duration connectTimeout;
        private final Mono<Connection> create;

        public static Builder newBuilder() {
            return new Builder();
        }

        public Builder toBuilder() {
            return newBuilder()
                    .connectTimeout(this.connectTimeout)
                    .addConnectionFactories(this.connectionFactories);
        }

        @Override
        public Mono<Connection> create() {
            return create;
        }

        @Override
        public ConnectionFactoryMetadata getMetadata() {
            return connectionFactories.get(0)
                    .getMetadata();
        }

        private FailoverConnectionFactory(List<ConnectionFactory> connectionFactories,
                                          Duration connectTimeout) {
            int connectionFactoriesSize = Objects.requireNonNull(connectionFactories,
                            "connectionFactories must not be null")
                    .size();
            if (connectionFactoriesSize < 2) {
                throw new IllegalArgumentException(
                        String.format("connectionFactories size must not be less than 2: [%s]",
                                connectionFactoriesSize));
            }
            this.connectionFactories = connectionFactories;
            Objects.requireNonNull(connectTimeout, "connectTimeout must not be null");
            if (connectTimeout.isNegative()) {
                throw new IllegalArgumentException("connectTimeout must not be negative");
            }
            this.connectTimeout = connectTimeout;

            int numRetries = this.connectionFactories.size() - 1;

            this.create = Mono.defer(this::createConnection)
                    .retry(numRetries);
        }

        private Mono<Connection> createConnection() {
            int index = currentConnectionFactoryIndex.get();
            logger.info("Trying to create connection... [index: {}]", index);

            ConnectionHolder holder = new ConnectionHolder();

            return Mono.from(this.connectionFactories.get(index)
                            .create())
                    .cast(Connection.class)
                    .doOnNext(holder::setConnection)
                    .timeout(connectTimeout)
                    .doOnNext(
                            connection -> logger.info("Connection has been successfully created [index: {}]", index))
                    .doOnError(throwable -> {
                        holder.dispose();
                        currentConnectionFactoryIndex.compareAndSet(index,
                                (index + 1) % this.connectionFactories.size());
                        logger.info("Failed to create connection [index: {}]", index, throwable);
                    });
        }

        // package-private for testing
        List<ConnectionFactory> getConnectionFactories() {
            return Collections.unmodifiableList(connectionFactories);
        }

        // package-private for testing
        Duration getConnectTimeout() {
            return connectTimeout;
        }

        public static class Builder {

            private final List<ConnectionFactory> connectionFactories = new ArrayList<>();
            private Duration connectTimeout = DEFAULT_CONNECT_TIMEOUT;

            public Builder connectTimeout(Duration connectTimeout) {
                Objects.requireNonNull(connectTimeout, "connectTimeout must not be null");
                if (connectTimeout.isNegative()) {
                    throw new IllegalArgumentException("connectTimeout must not be negative");
                }
                this.connectTimeout = connectTimeout;
                return this;
            }

            public Builder addConnectionFactory(ConnectionFactory connectionFactory) {
                connectionFactories.add(
                        Objects.requireNonNull(connectionFactory, "connectionFactory must not be null"));
                return this;
            }

            public Builder addConnectionFactories(Collection<ConnectionFactory> connectionFactories) {
                Objects.requireNonNull(connectionFactories, "connectionFactories must not be null")
                        .forEach(this::addConnectionFactory);
                return this;
            }

            public FailoverConnectionFactory build() {
                if (connectionFactories.size() < 2) {
                    throw new IllegalArgumentException(
                            String.format("connectionFactories size must not be less than 2: [%s]",
                                    connectionFactories.size()));
                }
                return new FailoverConnectionFactory(connectionFactories, connectTimeout);
            }
        }

        private static final class ConnectionHolder implements Disposable {

            private final AtomicBoolean disposed = new AtomicBoolean();
            private final AtomicReference<Connection> connection = new AtomicReference<>();

            public void setConnection(Connection connection) {
                if (!this.connection.compareAndSet(null, connection)) {
                    throw new IllegalStateException("Holder already contains connection");
                }
                if (isDisposed()) {
                    closeConnection();
                }
            }

            @Override
            public void dispose() {
                if (disposed.compareAndSet(false, true)) {
                    closeConnection();
                }
            }

            @Override
            public boolean isDisposed() {
                return disposed.get();
            }

            private void closeConnection() {
                Connection connection = this.connection.getAndSet(null);
                if (connection != null) {
                    connection.close();
                }
            }
        }
    }
}
