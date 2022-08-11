package com.yugabyte.demo.iamservicewebflux.repository;

import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import com.yugabyte.demo.iamservicewebflux.domain.UserCredential;

@Repository
public interface UserCredentialsRepository extends ReactiveCrudRepository<UserCredential, String> {
    @Modifying
    @Query("UPDATE user_credentials SET password_hash = :newPasswordHash WHERE user_id = :userId AND password_hash = :oldPasswordHash")
    Mono<Boolean> updatePasswordHash(String userId, String oldPasswordHash, String newPasswordHash);

    @Query("SELECT profile_id FROM user_credentials WHERE user_id = :userId AND password_hash = :passwordHash")
    Mono<Integer> authenticate(String userId, String passwordHash);

    @Query("SELECT profile_id FROM user_credentials WHERE user_id = :userId")
    Mono<Integer> findByUserId(String userId);
}