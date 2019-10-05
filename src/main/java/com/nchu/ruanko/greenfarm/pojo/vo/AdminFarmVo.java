package com.nchu.ruanko.greenfarm.pojo.vo;

import com.nchu.ruanko.greenfarm.pojo.entity.*;

import java.util.List;

public class AdminFarmVo {

    private Farm farm;
    private FarmReview review;
    private FarmImage mainImage;
    private List<FarmImage> otherImages;
    private FarmType farmType;

    public FarmType getFarmType() {
        return farmType;
    }

    public void setFarmType(FarmType farmType) {
        this.farmType = farmType;
    }

    public Farm getFarm() {
        return farm;
    }

    public void setFarm(Farm farm) {
        this.farm = farm;
    }

    public FarmReview getReview() {
        return review;
    }

    public void setReview(FarmReview review) {
        this.review = review;
    }

    public FarmImage getMainImage() {
        return mainImage;
    }

    public void setMainImage(FarmImage mainImage) {
        this.mainImage = mainImage;
    }

    public List<FarmImage> getOtherImages() {
        return otherImages;
    }

    public void setOtherImages(List<FarmImage> otherImages) {
        this.otherImages = otherImages;
    }

    @Override
    public String toString() {
        return "AdminFarmVo{" +
                "farm=" + farm +
                ", review=" + review +
                ", mainImage=" + mainImage +
                ", otherImages=" + otherImages +
                '}';
    }
}
