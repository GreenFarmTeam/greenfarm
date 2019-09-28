package com.nchu.ruanko.greenfarm.pojo.entity;

/**
 * 对应数据库的表：gf_tb_farm_land_type
 */
public class FarmType {

    private String farmTypeUid;
    private String farmTypeName;

    public String getFarmTypeUid() {
        return farmTypeUid;
    }

    public void setFarmTypeUid(String farmTypeUid) {
        this.farmTypeUid = farmTypeUid;
    }

    public String getFarmTypeName() {
        return farmTypeName;
    }

    public void setFarmTypeName(String farmTypeName) {
        this.farmTypeName = farmTypeName;
    }

    @Override
    public String toString() {
        return "FarmType{" +
                "farmTypeUid='" + farmTypeUid + '\'' +
                ", farmTypeName='" + farmTypeName + '\'' +
                '}';
    }

}