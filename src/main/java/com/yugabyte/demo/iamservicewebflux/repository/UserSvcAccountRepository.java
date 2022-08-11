package com.yugabyte.demo.iamservicewebflux.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import com.yugabyte.demo.iamservicewebflux.domain.UserSvcAccount;

@Repository
public interface UserSvcAccountRepository extends ReactiveCrudRepository<UserSvcAccount, Long> {

    @Query("SELECT * FROM user_svc_account where user_id = :userId")
    Flux<UserSvcAccount> findServiceAccountsForUser(String userId);

}