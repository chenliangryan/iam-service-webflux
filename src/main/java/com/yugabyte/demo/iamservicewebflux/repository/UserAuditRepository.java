package com.yugabyte.demo.iamservicewebflux.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import com.yugabyte.demo.iamservicewebflux.domain.UserAudit;

@Repository
public interface UserAuditRepository extends ReactiveCrudRepository<UserProfileRepository, Long> {

    @Query("SELECT * FROM user_audit WHERE user_id = :userId AND action = 'login' ORDER BY transaction_time DESC LIMIT :count")
    Flux<UserAudit> recentLogins(String userId, Integer count);

}