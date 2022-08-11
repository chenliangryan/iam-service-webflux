package com.yugabyte.demo.iamservicewebflux.domain;

import java.sql.Timestamp;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("user_svc_account")
public class UserSvcAccount {
    @Id
    @Column(value = "account_id")
    Long accountId;

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    @Column(value = "user_id")
    String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Column(value = "create_time")
    Timestamp createTime;

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Column(value = "last_access_time")
    Timestamp lastAccessTime;

    public Timestamp getLastAccessTime() {
        return lastAccessTime;
    }

    public void setLastAccessTime(Timestamp lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }

    @Column(value = "user_verified")
    String userVerified;

    public String getUserVerified() {
        return userVerified;
    }

    public void setUserVerified(String userVerified) {
        this.userVerified = userVerified;
    }

    @Column(value = "svc_name")
    String svcName;

    public String getSvcName() {
        return svcName;
    }

    public void setSvcName(String svcName) {
        this.svcName = svcName;
    }

    @Column(value = "svc_description")
    String svcDescription;

    public String getSvcDescription() {
        return svcDescription;
    }

    public void setSvcDescription(String svcDescription) {
        this.svcDescription = svcDescription;
    }
}