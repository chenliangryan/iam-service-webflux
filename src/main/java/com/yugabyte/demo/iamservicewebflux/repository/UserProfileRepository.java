package com.yugabyte.demo.iamservicewebflux.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.yugabyte.demo.iamservicewebflux.domain.UserProfile;

@Repository
public interface UserProfileRepository extends ReactiveCrudRepository<UserProfile, Integer> {


}