package com.nchu.ruanko.greenfarm.pojo.entity;

import java.util.Date;

/**
 * 对应数据库表：gf_tb_product
 */
public class Product {

    private String productUid;
    private String productName;
    private Float productPrice;
    private String productUnit;
    private String productDescription;
    private Date productUpDate;
    private Integer productStock;
    private Integer productIsRecommend;
    private ProductType productType;
    private Business business;

    public String getProductUid() {
        return productUid;
    }

    public void setProductUid(String productUid) {
        this.productUid = productUid;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Float getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Float productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductUnit() {
        return productUnit;
    }

    public void setProductUnit(String productUnit) {
        this.productUnit = productUnit;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public Date getProductUpDate() {
        return productUpDate;
    }

    public void setProductUpDate(Date productUpDate) {
        this.productUpDate = productUpDate;
    }

    public Integer getProductStock() {
        return productStock;
    }

    public void setProductStock(Integer productStock) {
        this.productStock = productStock;
    }

    public Integer getProductIsRecommend() {
        return productIsRecommend;
    }

    public void setProductIsRecommend(Integer productIsRecommend) {
        this.productIsRecommend = productIsRecommend;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public Business getBusiness() {
        return business;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productUid='" + productUid + '\'' +
                ", productName='" + productName + '\'' +
                ", productPrice=" + productPrice +
                ", productUnit='" + productUnit + '\'' +
                ", productDescription='" + productDescription + '\'' +
                ", productUpDate=" + productUpDate +
                ", productStock=" + productStock +
                ", productIsRecommend=" + productIsRecommend +
                ", productType=" + productType +
                ", business=" + business +
                '}';
    }

}