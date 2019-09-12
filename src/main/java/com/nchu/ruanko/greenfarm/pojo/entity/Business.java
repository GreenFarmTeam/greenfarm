package com.nchu.ruanko.greenfarm.pojo.entity;

/**
 * 对应数据库的表 gf_tb_user
 *
 * @author Yuan Yueshu
 */
public class Business {

    private String businessUid;
    private User user;
    private String businessIdcardFront;
    private String businessIdcardBehind;
    private String businessShopName;
    private String businessShopDescription;
    private Integer businessShopState;

    public String getBusinessUid() {
        return businessUid;
    }

    public void setBusinessUid(String businessUid) {
        this.businessUid = businessUid;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getBusinessIdcardFront() {
        return businessIdcardFront;
    }

    public void setBusinessIdcardFront(String businessIdcardFront) {
        this.businessIdcardFront = businessIdcardFront;
    }

    public String getBusinessIdcardBehind() {
        return businessIdcardBehind;
    }

    public void setBusinessIdcardBehind(String businessIdcardBehind) {
        this.businessIdcardBehind = businessIdcardBehind;
    }

    public String getBusinessShopName() {
        return businessShopName;
    }

    public void setBusinessShopName(String businessShopName) {
        this.businessShopName = businessShopName;
    }

    public String getBusinessShopDescription() {
        return businessShopDescription;
    }

    public void setBusinessShopDescription(String businessShopDescription) {
        this.businessShopDescription = businessShopDescription;
    }

    public Integer getBusinessShopState() {
        return businessShopState;
    }

    public void setBusinessShopState(Integer businessShopState) {
        this.businessShopState = businessShopState;
    }

    @Override
    public String toString() {
        return "Business{" +
                "businessUid='" + businessUid + '\'' +
                ", user=" + user +
                ", businessIdcardFront='" + businessIdcardFront + '\'' +
                ", businessIdcardBehind='" + businessIdcardBehind + '\'' +
                ", businessShopName='" + businessShopName + '\'' +
                ", businessShopDescription='" + businessShopDescription + '\'' +
                ", businessShopState=" + businessShopState +
                '}';
    }

}