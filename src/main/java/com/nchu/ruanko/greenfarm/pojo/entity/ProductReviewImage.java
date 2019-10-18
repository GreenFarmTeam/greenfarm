package com.nchu.ruanko.greenfarm.pojo.entity;

public class ProductReviewImage {
    private String productReviewImageUid;
    private String productReviewImagePath;
    private Order  productReviewImageOrder;

    public String getProductReviewImageUid() {
        return productReviewImageUid;
    }

    public void setProductReviewImageUid(String productReviewImageUid) {
        this.productReviewImageUid = productReviewImageUid;
    }

    public String getProductReviewImagePath() {
        return productReviewImagePath;
    }

    public void setProductReviewImagePath(String productReviewImagePath) {
        this.productReviewImagePath = productReviewImagePath;
    }

    public Order getProductReviewImageOrder() {
        return productReviewImageOrder;
    }

    public void setProductReviewImageOrder(Order productReviewImageOrder) {
        this.productReviewImageOrder = productReviewImageOrder;
    }

    @Override
    public String toString() {
        return "ProductReviewImage{" +
                "productReviewImageUid='" + productReviewImageUid + '\'' +
                ", productReviewImagePath='" + productReviewImagePath + '\'' +
                ", productReviewImageOrder=" + productReviewImageOrder +
                '}';
    }
}
