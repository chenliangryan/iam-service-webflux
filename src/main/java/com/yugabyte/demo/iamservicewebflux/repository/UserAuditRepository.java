package com.yugabyte.demo.iamservicewebflux.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import com.yugabyte.demo.iamservicewebflux.domain.UserAudit;

@Repository
public interface UserAuditRepository extends ReactiveCrudRepository<UserAudit, Long> {

    @Query("SELECT id, account_id, user_id, action, description, transaction_time, device, client_ip, location " +
            "FROM user_audit WHERE user_id = :userId AND action = 'login' ORDER BY transaction_time DESC LIMIT :count")
    Flux<UserAudit> recentLogins(String userId, Integer count);

}