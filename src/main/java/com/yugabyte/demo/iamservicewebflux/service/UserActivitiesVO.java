package com.yugabyte.demo.iamservicewebflux.service;

import java.sql.Timestamp;
import java.util.Objects;

import com.yugabyte.demo.iamservicewebflux.domain.UserAudit;

public final class UserActivitiesVO {
    private String action;
    private String description;
    private Timestamp transactionTime;
    private String device;
    private String clientIp;
    private String location;

    public static UserActivitiesVO from(UserAudit o){
        UserActivitiesVO vo = new UserActivitiesVO();
        vo.action = o.getAction();
        vo.clientIp = o.getClientIp();
        vo.description = o.getDescription();
        vo.device = o.getDevice();
        vo.location = o.getLocation();
        vo.transactionTime = o.getTransactionTime();
        return vo;
    }

    public void init(UserAudit o){
        this.action = o.getAction();
        this.clientIp = o.getClientIp();
        this.description = o.getDescription();
        this.device = o.getDevice();
        this.location = o.getLocation();
        this.transactionTime = o.getTransactionTime();
    }

    public String getAction() {
        return action;
    }

    public void setAction(
            String action) {
        this.action = action;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(Timestamp transactionTime) {
        this.transactionTime = transactionTime;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserActivitiesVO that = (UserActivitiesVO) o;
        return Objects.equals(action, that.action)
                && Objects.equals(description, that.description)
                && Objects.equals(transactionTime, that.transactionTime)
                && Objects.equals(device, that.device)
                && Objects.equals(clientIp, that.clientIp)
                && Objects.equals(location, that.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                action,
                description,
                transactionTime,
                device,
                clientIp,
                location);
    }

    @Override
    public String toString() {
        return "UserActivitiesVO{"
                + "action='" + action + '\''
                + ", description='" + description + '\''
                + ", transactionTime=" + transactionTime
                + ", device='" + device + '\''
                + ", clientIp='" + clientIp + '\''
                + ", location='" + location + '\''
                + '}';
    }
}