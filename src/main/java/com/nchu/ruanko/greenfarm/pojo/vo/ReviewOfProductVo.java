package com.nchu.ruanko.greenfarm.pojo.vo;

import com.nchu.ruanko.greenfarm.pojo.entity.OrderRate;
import com.nchu.ruanko.greenfarm.pojo.entity.ProductReviewImage;

import java.util.List;

public class ReviewOfProductVo {
    private List<ProductReviewImage> productReviewImages;
    private OrderRate orderRate;

    public List<ProductReviewImage> getProductReviewImages() {
        return productReviewImages;
    }

    public void setProductReviewImages(List<ProductReviewImage> productReviewImages) {
        this.productReviewImages = productReviewImages;
    }

    public OrderRate getOrderRate() {
        return orderRate;
    }

    public void setOrderRate(OrderRate orderRate) {
        this.orderRate = orderRate;
    }

    @Override
    public String toString() {
        return "ReviewOfProductVo{" +
                "productReviewImages=" + productReviewImages +
                ", orderRate=" + orderRate +
                '}';
    }
}
