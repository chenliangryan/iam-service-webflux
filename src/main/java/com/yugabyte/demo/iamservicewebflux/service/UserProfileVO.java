package com.yugabyte.demo.iamservicewebflux.service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.yugabyte.demo.iamservicewebflux.domain.UserProfile;

public class UserProfileVO {

    private Integer userProfileId;
    private String firstName;
    private String lastName;
    private String gender;
    private Date birthday;
    private String email;
    private String emailVerified;
    private Timestamp createTime;
    private Timestamp lastAccessTime;
    private String salutation;
    private List<ServiceAccountVO> serviceAccount = new ArrayList<>(0);
    private List<UserActivitiesVO> UserActivities = new ArrayList<>(0);

    public Integer getUserProfileId() {
        return userProfileId;
    }

    public void setUserProfileId(
            Integer userProfileId) {
        this.userProfileId = userProfileId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(
            String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(
            String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(
            String gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(
            Date birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(
            String email) {
        this.email = email;
    }

    public String getEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(
            String emailVerified) {
        this.emailVerified = emailVerified;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(
            Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getLastAccessTime() {
        return lastAccessTime;
    }

    public void setLastAccessTime(
            Timestamp lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }

    public String getSalutation() {
        return salutation;
    }

    public void setSalutation(
            String salutation) {
        this.salutation = salutation;
    }

    public List<ServiceAccountVO> getServiceAccount() {
        return serviceAccount;
    }

    public void setServiceAccount(
            List<ServiceAccountVO> serviceAccount) {
        this.serviceAccount = serviceAccount;
    }

    public List<UserActivitiesVO> getUserActivities() {
        return UserActivities;
    }

    public void setUserActivities(
            List<UserActivitiesVO> userActivities) {
        UserActivities = userActivities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserProfileVO that = (UserProfileVO) o;
        return userProfileId.equals(that.userProfileId)
                && Objects.equals(firstName, that.firstName)
                && Objects.equals(lastName, that.lastName)
                && Objects.equals(gender, that.gender)
                && Objects.equals(birthday, that.birthday)
                && Objects.equals(email, that.email)
                && Objects.equals(emailVerified, that.emailVerified)
                && Objects.equals(createTime, that.createTime)
                && Objects.equals(lastAccessTime, that.lastAccessTime)
                && Objects.equals(salutation, that.salutation)
                && Objects.equals(serviceAccount, that.serviceAccount)
                && Objects.equals(UserActivities, that.UserActivities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                userProfileId,
                firstName,
                lastName,
                gender,
                birthday,
                email,
                emailVerified,
                createTime,
                lastAccessTime,
                salutation,
                serviceAccount,
                UserActivities);
    }

    public void init(UserProfile o) {
        this.birthday = o.getBirthday();
        this.createTime = o.getCreateTime();
        this.email = o.getEmail();
        this.userProfileId = o.getUserProfileId();
        this.emailVerified = o.getEmailVerified();
        this.firstName = o.getFirstName();
        this.lastName = o.getLastName();
        this.gender = o.getGender();
        this.lastAccessTime = o.getLastAccessTime();
        this.salutation = o.getSalutation();
    }

    public static UserProfileVO from(UserProfile o) {
        UserProfileVO vo = new UserProfileVO();
        vo.init(o);
        return vo;
    }

    @Override
    public String toString() {
        return "UserProfileVO{"
                + "userProfileId=" + userProfileId
                + ", firstName='" + firstName + '\''
                + ", lastName='" + lastName + '\''
                + ", gender='" + gender + '\''
                + ", birthday=" + birthday
                + ", email='" + email + '\''
                + ", emailVerified='" + emailVerified + '\''
                + ", createTime=" + createTime
                + ", lastAccessTime=" + lastAccessTime
                + ", salutation='" + salutation + '\''
                + ", serviceAccount=" + serviceAccount
                + ", UserActivities=" + UserActivities
                + '}';
    }
}