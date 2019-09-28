package com.nchu.ruanko.greenfarm.pojo.entity;

import java.util.Date;

/**
 * 对应数据库的表：gf_tb_farm_review
 */
public class FarmReview {

    private String farmReviewUid;
    private Date farmReviewSubmitTime;
    private Date farmReviewReviewTime;
    private Integer farmReviewResult;
    private String farmReviewReason;
    private Farm farm;

    public String getFarmReviewUid() {
        return farmReviewUid;
    }

    public void setFarmReviewUid(String farmReviewUid) {
        this.farmReviewUid = farmReviewUid;
    }

    public Date getFarmReviewSubmitTime() {
        return farmReviewSubmitTime;
    }

    public void setFarmReviewSubmitTime(Date farmReviewSubmitTime) {
        this.farmReviewSubmitTime = farmReviewSubmitTime;
    }

    public Date getFarmReviewReviewTime() {
        return farmReviewReviewTime;
    }

    public void setFarmReviewReviewTime(Date farmReviewReviewTime) {
        this.farmReviewReviewTime = farmReviewReviewTime;
    }

    public Integer getFarmReviewResult() {
        return farmReviewResult;
    }

    public void setFarmReviewResult(Integer farmReviewResult) {
        this.farmReviewResult = farmReviewResult;
    }

    public String getFarmReviewReason() {
        return farmReviewReason;
    }

    public void setFarmReviewReason(String farmReviewReason) {
        this.farmReviewReason = farmReviewReason;
    }

    public Farm getFarm() {
        return farm;
    }

    public void setFarm(Farm farm) {
        this.farm = farm;
    }

    @Override
    public String toString() {
        return "FarmReview{" +
                "farmReviewUid='" + farmReviewUid + '\'' +
                ", farmReviewSubmitTime=" + farmReviewSubmitTime +
                ", farmReviewReviewTime=" + farmReviewReviewTime +
                ", farmReviewResult=" + farmReviewResult +
                ", farmReviewReason='" + farmReviewReason + '\'' +
                ", farm=" + farm +
                '}';
    }

}