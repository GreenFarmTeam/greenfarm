package com.nchu.ruanko.greenfarm.pojo.entity;

/**
 * 对应数据库表 gf_tb_product_type
 *
 * @author Yuan Yueshun
 */
public class ProductType {

    private String typeUid;
    private String typeName;
    private String typeDesc;

    public String getTypeUid() {
        return typeUid;
    }

    public void setTypeUid(String typeUid) {
        this.typeUid = typeUid;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeDesc() {
        return typeDesc;
    }

    public void setTypeDesc(String typeDesc) {
        this.typeDesc = typeDesc;
    }

    @Override
    public String toString() {
        return "ProductType{" +
                "typeUid='" + typeUid + '\'' +
                ", typeName='" + typeName + '\'' +
                ", typeDesc='" + typeDesc + '\'' +
                '}';
    }

}