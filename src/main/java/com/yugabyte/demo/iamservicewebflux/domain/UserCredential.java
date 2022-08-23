package com.yugabyte.demo.iamservicewebflux.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("user_credentials")
public class UserCredential {

    @Id
    @Column(value = "user_id")
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Column(value = "password_hash")
    private String passwordHash;

    public void setUserPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    @Column(value = "profile_id")
    private Integer userProfileId;

    public Integer getUserProfileId() {
        return userProfileId;
    }

    public void setUserProfileId(Integer userProfileId) {
        this.userProfileId = userProfileId;
    }
}