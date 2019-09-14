package com.nchu.ruanko.greenfarm.pojo.entity;

import java.util.Date;

/**
 * 对应数据库的表 gf_tb_user
 *
 * @author Yuan Yueshun
 */
public class User {

    private String userUid;
    private String userUsername;
    private String userPassword;
    private String userNickname;
    private String userPhone;
    private String userMail;
    private String userIdcard;
    private String userRealname;
    private String userPhoto;
    private Date userTime;
    private Integer userIsBusiness;

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    public String getUserUsername() {
        return userUsername;
    }

    public void setUserUsername(String userUsername) {
        this.userUsername = userUsername;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public String getUserIdcard() {
        return userIdcard;
    }

    public void setUserIdcard(String userIdcard) {
        this.userIdcard = userIdcard;
    }

    public String getUserRealname() {
        return userRealname;
    }

    public void setUserRealname(String userRealname) {
        this.userRealname = userRealname;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public Date getUserTime() {
        return userTime;
    }

    public void setUserTime(Date userTime) {
        this.userTime = userTime;
    }

    public Integer getUserIsBusiness() {
        return userIsBusiness;
    }

    public void setUserIsBusiness(Integer userIsBusiness) {
        this.userIsBusiness = userIsBusiness;
    }

    @Override
    public String toString() {
        return "User{" +
                "userUid='" + userUid + '\'' +
                ", userUsername='" + userUsername + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userNickname='" + userNickname + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", userMail='" + userMail + '\'' +
                ", userIdcard='" + userIdcard + '\'' +
                ", userRealname='" + userRealname + '\'' +
                ", userPhoto='" + userPhoto + '\'' +
                ", userTime=" + userTime +
                ", userIsBusiness=" + userIsBusiness +
                '}';
    }

}