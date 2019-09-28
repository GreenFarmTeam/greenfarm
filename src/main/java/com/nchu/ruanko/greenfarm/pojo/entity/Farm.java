package com.nchu.ruanko.greenfarm.pojo.entity;

import java.util.Date;

/**
 * 对应数据库的表：gf_tb_farm
 */
public class Farm {

    private String farmUid;
    private Float farmPrice;
    private String farmUnit;
    private Integer farmYear;
    private Integer farmArea;
    private String farmDescription;
    private String farmLongitude;
    private String farmLatitude;
    private Date farmUpDate;
    private Business business;
    private FarmType type;

    public String getFarmUid() {
        return farmUid;
    }

    public void setFarmUid(String farmUid) {
        this.farmUid = farmUid;
    }

    public Float getFarmPrice() {
        return farmPrice;
    }

    public void setFarmPrice(Float farmPrice) {
        this.farmPrice = farmPrice;
    }

    public String getFarmUnit() {
        return farmUnit;
    }

    public void setFarmUnit(String farmUnit) {
        this.farmUnit = farmUnit;
    }

    public Integer getFarmYear() {
        return farmYear;
    }

    public void setFarmYear(Integer farmYear) {
        this.farmYear = farmYear;
    }

    public Integer getFarmArea() {
        return farmArea;
    }

    public void setFarmArea(Integer farmArea) {
        this.farmArea = farmArea;
    }

    public String getFarmDescription() {
        return farmDescription;
    }

    public void setFarmDescription(String farmDescription) {
        this.farmDescription = farmDescription;
    }

    public String getFarmLongitude() {
        return farmLongitude;
    }

    public void setFarmLongitude(String farmLongitude) {
        this.farmLongitude = farmLongitude;
    }

    public String getFarmLatitude() {
        return farmLatitude;
    }

    public void setFarmLatitude(String farmLatitude) {
        this.farmLatitude = farmLatitude;
    }

    public Date getFarmUpDate() {
        return farmUpDate;
    }

    public void setFarmUpDate(Date farmUpDate) {
        this.farmUpDate = farmUpDate;
    }

    public Business getBusiness() {
        return business;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }

    public FarmType getType() {
        return type;
    }

    public void setType(FarmType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Farm{" +
                "farmUid='" + farmUid + '\'' +
                ", farmPrice=" + farmPrice +
                ", farmUnit='" + farmUnit + '\'' +
                ", farmYear=" + farmYear +
                ", farmArea=" + farmArea +
                ", farmDescription='" + farmDescription + '\'' +
                ", farmLongitude='" + farmLongitude + '\'' +
                ", farmLatitude='" + farmLatitude + '\'' +
                ", farmUpDate=" + farmUpDate +
                ", business=" + business +
                ", type=" + type +
                '}';
    }

}