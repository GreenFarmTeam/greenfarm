package com.nchu.ruanko.greenfarm.pojo.entity;

/**
 * 对应数据库表：gf_tb_product_image
 */
public class ProductImage {

    private String productImageUid;
    private String productImagePath;
    private Integer productImageIsMainImage;
    private Product product;

    public String getProductImageUid() {
        return productImageUid;
    }

    public void setProductImageUid(String productImageUid) {
        this.productImageUid = productImageUid;
    }

    public String getProductImagePath() {
        return productImagePath;
    }

    public void setProductImagePath(String productImagePath) {
        this.productImagePath = productImagePath;
    }

    public Integer getProductImageIsMainImage() {
        return productImageIsMainImage;
    }

    public void setProductImageIsMainImage(Integer productImageIsMainImage) {
        this.productImageIsMainImage = productImageIsMainImage;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "ProductImage{" +
                "productImageUid='" + productImageUid + '\'' +
                ", productImagePath='" + productImagePath + '\'' +
                ", productImageIsMainImage=" + productImageIsMainImage +
                ", product=" + product +
                '}';
    }

}