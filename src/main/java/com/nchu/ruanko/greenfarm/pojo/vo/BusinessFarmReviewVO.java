package com.nchu.ruanko.greenfarm.pojo.vo;

import com.nchu.ruanko.greenfarm.pojo.entity.Farm;
import com.nchu.ruanko.greenfarm.pojo.entity.FarmImage;
import com.nchu.ruanko.greenfarm.pojo.entity.FarmReview;
import java.util.List;

public class BusinessFarmReviewVO {

    private Farm farm;
    private FarmImage mainImage;
    private List<FarmImage> otherImageList;
    private FarmReview farmReview;

    public Farm getFarm() {
        return farm;
    }

    public void setFarm(Farm farm) {
        this.farm = farm;
    }

    public FarmImage getMainImage() {
        return mainImage;
    }

    public void setMainImage(FarmImage mainImage) {
        this.mainImage = mainImage;
    }

    public List<FarmImage> getOtherImageList() {
        return otherImageList;
    }

    public void setOtherImageList(List<FarmImage> otherImageList) {
        this.otherImageList = otherImageList;
    }

    public FarmReview getFarmReview() {
        return farmReview;
    }

    public void setFarmReview(FarmReview farmReview) {
        this.farmReview = farmReview;
    }

    @Override
    public String toString() {
        return "BusinessFarmReviewVO{" +
                "farm=" + farm +
                ", mainImage=" + mainImage +
                ", otherImageList=" + otherImageList +
                ", farmReview=" + farmReview +
                '}';
    }

}