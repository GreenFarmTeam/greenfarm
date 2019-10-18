package com.nchu.ruanko.greenfarm.pojo.entity;

public class FarmReviewImage {
    private String farmReviewImageUid;

    private Farm farmReviewImageOrderFarm;
    private String farmReviewImagePath;

    public String getFarmReviewImageUid() {
        return farmReviewImageUid;
    }

    public void setFarmReviewImageUid(String farmReviewImageUid) {
        this.farmReviewImageUid = farmReviewImageUid;
    }

    public Farm getFarmReviewImageOrderFarm() {
        return farmReviewImageOrderFarm;
    }

    public void setFarmReviewImageOrderFarm(Farm farmReviewImageOrderFarm) {
        this.farmReviewImageOrderFarm = farmReviewImageOrderFarm;
    }

    public String getFarmReviewImagePath() {
        return farmReviewImagePath;
    }

    public void setFarmReviewImagePath(String farmReviewImagePath) {
        this.farmReviewImagePath = farmReviewImagePath;
    }

    @Override
    public String toString() {
        return "FarmReviewImage{" +
                "farmReviewImageUid='" + farmReviewImageUid + '\'' +
                ", farmReviewImageOrderFarm=" + farmReviewImageOrderFarm +
                ", farmReviewImagePath='" + farmReviewImagePath + '\'' +
                '}';
    }
}
