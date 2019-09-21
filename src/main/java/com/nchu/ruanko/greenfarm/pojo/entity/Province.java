package com.nchu.ruanko.greenfarm.pojo.entity;

/**
 * 省份
 *
 * 该实体也可以作为封装 市、县 来使用
 *
 * 对应数据库的表 gf_tb_province
 */
public class Province {

    private Integer provinceId;
    private String provinceName;

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    @Override
    public String toString() {
        return "Province{" +
                "provinceId=" + provinceId +
                ", provinceName='" + provinceName + '\'' +
                '}';
    }

}