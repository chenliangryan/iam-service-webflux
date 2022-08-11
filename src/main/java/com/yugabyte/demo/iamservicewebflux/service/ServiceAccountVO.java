package com.yugabyte.demo.iamservicewebflux.service;

import java.sql.Timestamp;
import java.util.Objects;

import com.yugabyte.demo.iamservicewebflux.domain.UserSvcAccount;

public final class ServiceAccountVO {

    private Long accountId;

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Timestamp getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Timestamp creationTime) {
        this.creationTime = creationTime;
    }

    public Timestamp getLastAccessTime() {
        return lastAccessTime;
    }

    public void setLastAccessTime(Timestamp lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }

    public String getUserVerified() {
        return userVerified;
    }

    public void setUserVerified(String userVerified) {
        this.userVerified = userVerified;
    }

    public String getSvcName() {
        return svcName;
    }

    public void setSvcName(String svcName) {
        this.svcName = svcName;
    }

    public String getSvcDescription() {
        return svcDescription;
    }

    public void setSvcDescription(String svcDescription) {
        this.svcDescription = svcDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ServiceAccountVO that = (ServiceAccountVO) o;
        return accountId.equals(that.accountId)
                && userId.equals(that.userId)
                && creationTime.equals(that.creationTime)
                && Objects.equals(lastAccessTime, that.lastAccessTime)
                && userVerified.equals(that.userVerified)
                && svcName.equals(that.svcName)
                && svcDescription.equals(that.svcDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                accountId,
                userId,
                creationTime,
                lastAccessTime,
                userVerified,
                svcName,
                svcDescription);
    }

    private String userId;
    private Timestamp creationTime;
    private Timestamp lastAccessTime;
    private String userVerified;
    private String svcName;
    private String svcDescription;

    public void init(UserSvcAccount o){
        this.accountId = o.getAccountId();
        this.userId = o.getUserId();
        this.creationTime = o.getCreateTime();
        this.lastAccessTime = o.getLastAccessTime();
        this.userVerified = o.getUserVerified();
        this.svcName = o.getSvcName();
        this.svcDescription = o.getSvcDescription();
    }

    public static ServiceAccountVO from(UserSvcAccount o) {
        ServiceAccountVO vo = new ServiceAccountVO();
        vo.init(o);
        return vo;
    }

    @Override
    public String toString() {
        return "ServiceAccountVO{"
                + "accountId=" + accountId
                + ", userId='" + userId + '\''
                + ", creationTime=" + creationTime
                + ", lastAccessTime=" + lastAccessTime
                + ", userVerified='" + userVerified + '\''
                + ", svcName='" + svcName + '\''
                + ", svcDescription='" + svcDescription + '\''
                + '}';
    }
}