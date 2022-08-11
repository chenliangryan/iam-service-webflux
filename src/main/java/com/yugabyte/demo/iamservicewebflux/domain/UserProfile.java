package com.yugabyte.demo.iamservicewebflux.domain;

import java.sql.Date;
import java.sql.Timestamp;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("user_profile")
public class UserProfile {
    @Id
    @Column(value = "id")
    private Integer userProfileId;

    public Integer getUserProfileId() {
        return userProfileId;
    }

    public void setUserProfileId(Integer userProfileId) {
        this.userProfileId = userProfileId;
    }

    @Column(value = "first_name")
    private String firstName;

    public String getFirstName () {
        return firstName;
    }

    public void setFirstName (String firstName) {
        this.firstName  = firstName;
    }

    @Column(value = "last_name")
    private String lastName;

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(value = "gender")
    String gender;

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Column(value = "birthday")
    Date birthday;

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Column(value = "mobile_no")
    private String mobileNo;

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    @Column(value = "email")
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(value = "email_verified")
    private String emailVerified;

    public String getEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(String emailVerified) {
        this.emailVerified = emailVerified;
    }

    @Column(value = "create_time")
    private Timestamp createTime;

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Column(value = "last_access_time")
    private Timestamp lastAccessTime;

    public Timestamp getLastAccessTime () {
        return lastAccessTime;
    }

    public void setLastAccessTime (Timestamp lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }

    @Column(value = "salutation")
    private String salutation;

    public String getSalutation() {
        return salutation;
    }

    public void setSalutation(String salutation) {
        this.salutation = salutation;
    }
}