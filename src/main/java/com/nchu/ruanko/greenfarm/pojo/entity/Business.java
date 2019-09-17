package com.nchu.ruanko.greenfarm.pojo.entity;

/**
 * 对应数据库表 gf_tb_business
 *
 * 与其有关联的表 gf_tb_user、gf_tb_scope、gf_tb_review
 *
 * @author Yuan Yueshun
 */
public class Business {

    private String businessUid;
    private String businessShopName;
    private String businessShopDescription;
    private Integer businessShopState;
    private String businessIdcardFront;
    private String businessIdcardBehind;
    private User user;

    public String getBusinessUid() {
        return businessUid;
    }

    public void setBusinessUid(String businessUid) {
        this.businessUid = businessUid;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Business{" +
                "businessUid='" + businessUid + '\'' +
                ", businessShopName='" + businessShopName + '\'' +
                ", businessShopDescription='" + businessShopDescription + '\'' +
                ", businessShopState=" + businessShopState +
                ", businessIdcardFront='" + businessIdcardFront + '\'' +
                ", businessIdcardBehind='" + businessIdcardBehind + '\'' +
                ", user=" + user +
                '}';
    }

}