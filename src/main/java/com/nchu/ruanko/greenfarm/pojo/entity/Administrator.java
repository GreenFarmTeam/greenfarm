package com.nchu.ruanko.greenfarm.pojo.entity;

/**
 * 对应数据库的表 gf_tb_administrator
 *
 * @author Yuan Yueshun
 */
public class Administrator {

    private String adminUsername;
    private String adminPassword;
    private String adminPhone;
    private String adminMail;

    public String getAdminUsername() {
        return adminUsername;
    }

    public void setAdminUsername(String adminUsername) {
        this.adminUsername = adminUsername;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    public String getAdminPhone() {
        return adminPhone;
    }

    public void setAdminPhone(String adminPhone) {
        this.adminPhone = adminPhone;
    }

    public String getAdminMail() {
        return adminMail;
    }

    public void setAdminMail(String adminMail) {
        this.adminMail = adminMail;
    }

    @Override
    public String toString() {
        return "Administrator{" +
                "adminUsername='" + adminUsername + '\'' +
                ", adminPassword='" + adminPassword + '\'' +
                ", adminPhone='" + adminPhone + '\'' +
                ", adminMail='" + adminMail + '\'' +
                '}';
    }

}