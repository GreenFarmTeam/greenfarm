package com.nchu.ruanko.greenfarm.pojo.entity;

/**
 * 对应数据库表：gf_tb_address
 */
public class Address {

    private String addressUid;
    private String addressName;
    private String addressDetail;
    private String addressProvince;
    private String addressCity;
    private String addressDistrict;
    private String addressPhone;
    private User user;

    public String getAddressUid() {
        return addressUid;
    }

    public void setAddressUid(String addressUid) {
        this.addressUid = addressUid;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    public String getAddressProvince() {
        return addressProvince;
    }

    public void setAddressProvince(String addressProvince) {
        this.addressProvince = addressProvince;
    }

    public String getAddressCity() {
        return addressCity;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

    public String getAddressDistrict() {
        return addressDistrict;
    }

    public void setAddressDistrict(String addressDistrict) {
        this.addressDistrict = addressDistrict;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAddressPhone() {
        return addressPhone;
    }

    public void setAddressPhone(String addressPhone) {
        this.addressPhone = addressPhone;
    }

    @Override
    public String toString() {
        return "Address{" +
                "addressUid='" + addressUid + '\'' +
                ", addressName='" + addressName + '\'' +
                ", addressDetail='" + addressDetail + '\'' +
                ", addressProvince='" + addressProvince + '\'' +
                ", addressCity='" + addressCity + '\'' +
                ", addressDistrict='" + addressDistrict + '\'' +
                ", addressPhone='" + addressPhone + '\'' +
                ", user=" + user +
                '}';
    }
}