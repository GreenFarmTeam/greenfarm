package com.nchu.ruanko.greenfarm.pojo.entity;

import java.util.Date;

/**
 * 对应数据表 gf_tb_business_review
 *
 * @author Yuan Yueshun
 */
public class BusinessReview {

    private String reviewUid;
    private Date reviewSubmitTime;
    private Date reviewTime;
    private Integer reviewResult;
    private String reviewReason;
    private Business business;

    public String getReviewUid() {
        return reviewUid;
    }

    public void setReviewUid(String reviewUid) {
        this.reviewUid = reviewUid;
    }

    public Date getReviewSubmitTime() {
        return reviewSubmitTime;
    }

    public void setReviewSubmitTime(Date reviewSubmitTime) {
        this.reviewSubmitTime = reviewSubmitTime;
    }

    public Date getReviewTime() {
        return reviewTime;
    }

    public void setReviewTime(Date reviewTime) {
        this.reviewTime = reviewTime;
    }

    public Integer getReviewResult() {
        return reviewResult;
    }

    public void setReviewResult(Integer reviewResult) {
        this.reviewResult = reviewResult;
    }

    public String getReviewReason() {
        return reviewReason;
    }

    public void setReviewReason(String reviewReason) {
        this.reviewReason = reviewReason;
    }

    public Business getBusiness() {
        return business;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }

    @Override
    public String toString() {
        return "BusinessReview{" +
                "reviewUid='" + reviewUid + '\'' +
                ", reviewSubmitTime=" + reviewSubmitTime +
                ", reviewTime=" + reviewTime +
                ", reviewResult=" + reviewResult +
                ", reviewReason='" + reviewReason + '\'' +
                ", business=" + business +
                '}';
    }
}