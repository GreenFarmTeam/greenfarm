package com.nchu.ruanko.greenfarm.pojo.entity;

/**
 * 对应数据库的表：gf_tb_farm_image
 */
public class FarmImage {

    private String farmImageUid;
    private String farmImagePath;
    private Integer farmImageIsMainImage;
    private Farm farm;

    public String getFarmImageUid() {
        return farmImageUid;
    }

    public void setFarmImageUid(String farmImageUid) {
        this.farmImageUid = farmImageUid;
    }

    public String getFarmImagePath() {
        return farmImagePath;
    }

    public void setFarmImagePath(String farmImagePath) {
        this.farmImagePath = farmImagePath;
    }

    public Integer getFarmImageIsMainImage() {
        return farmImageIsMainImage;
    }

    public void setFarmImageIsMainImage(Integer farmImageIsMainImage) {
        this.farmImageIsMainImage = farmImageIsMainImage;
    }

    public Farm getFarm() {
        return farm;
    }

    public void setFarm(Farm farm) {
        this.farm = farm;
    }

    @Override
    public String toString() {
        return "FarmImage{" +
                "farmImageUid='" + farmImageUid + '\'' +
                ", farmImagePath='" + farmImagePath + '\'' +
                ", farmImageIsMainImage='" + farmImageIsMainImage + '\'' +
                ", farm=" + farm +
                '}';
    }

}