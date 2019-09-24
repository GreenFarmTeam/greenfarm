package com.nchu.ruanko.greenfarm.pojo.entity;

import java.util.Date;

/**
 * 对应数据库表：gf_tb_product_review
 */
public class ProductReview {

    private String productReviewUid;
    private Date productReviewSubmitTime;
    private Date productReviewReviewTime;
    private Integer productReviewResult;
    private String productReviewReason;
    private Product product;

    public String getProductReviewUid() {
        return productReviewUid;
    }

    public void setProductReviewUid(String productReviewUid) {
        this.productReviewUid = productReviewUid;
    }

    public Date getProductReviewSubmitTime() {
        return productReviewSubmitTime;
    }

    public void setProductReviewSubmitTime(Date productReviewSubmitTime) {
        this.productReviewSubmitTime = productReviewSubmitTime;
    }

    public Date getProductReviewReviewTime() {
        return productReviewReviewTime;
    }

    public void setProductReviewReviewTime(Date productReviewReviewTime) {
        this.productReviewReviewTime = productReviewReviewTime;
    }

    public Integer getProductReviewResult() {
        return productReviewResult;
    }

    public void setProductReviewResult(Integer productReviewResult) {
        this.productReviewResult = productReviewResult;
    }

    public String getProductReviewReason() {
        return productReviewReason;
    }

    public void setProductReviewReason(String productReviewReason) {
        this.productReviewReason = productReviewReason;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "ProductReview{" +
                "productReviewUid='" + productReviewUid + '\'' +
                ", productReviewSubmitTime=" + productReviewSubmitTime +
                ", productReviewReviewTime=" + productReviewReviewTime +
                ", productReviewResult=" + productReviewResult +
                ", productReviewReason='" + productReviewReason + '\'' +
                ", product=" + product +
                '}';
    }

}