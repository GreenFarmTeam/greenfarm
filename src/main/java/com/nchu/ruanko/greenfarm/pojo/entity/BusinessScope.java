package com.nchu.ruanko.greenfarm.pojo.entity;

/**
 * 对应数据库表 gf_tb_scope
 *
 * @author Yuan Yueshun
 */
public class BusinessScope {

    private String businessScopeUid;
    private Business business;
    private ProductType productType;

    public String getBusinessScopeUid() {
        return businessScopeUid;
    }

    public void setBusinessScopeUid(String businessScopeUid) {
        this.businessScopeUid = businessScopeUid;
    }

    public Business getBusiness() {
        return business;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    @Override
    public String toString() {
        return "BusinessScope{" +
                "businessScopeUid='" + businessScopeUid + '\'' +
                ", business=" + business +
                ", productType=" + productType +
                '}';
    }

}