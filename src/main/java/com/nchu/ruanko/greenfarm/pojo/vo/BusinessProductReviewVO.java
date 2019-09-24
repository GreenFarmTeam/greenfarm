package com.nchu.ruanko.greenfarm.pojo.vo;

import com.nchu.ruanko.greenfarm.pojo.entity.Product;
import com.nchu.ruanko.greenfarm.pojo.entity.ProductImage;
import com.nchu.ruanko.greenfarm.pojo.entity.ProductReview;
import java.util.List;

/**
 * VO
 */
public class BusinessProductReviewVO {

    private Product product;
    private ProductImage productMainImage;
    private List<ProductImage> productOtherImageList;
    // private List<ProductImage> productImageList;
    private ProductReview productReview;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ProductImage getProductMainImage() {
        return productMainImage;
    }

    public void setProductMainImage(ProductImage productMainImage) {
        this.productMainImage = productMainImage;
    }

    public List<ProductImage> getProductOtherImageList() {
        return productOtherImageList;
    }

    public void setProductOtherImageList(List<ProductImage> productOtherImageList) {
        this.productOtherImageList = productOtherImageList;
    }

    public ProductReview getProductReview() {
        return productReview;
    }

    public void setProductReview(ProductReview productReview) {
        this.productReview = productReview;
    }

    @Override
    public String toString() {
        return "BusinessProductReviewVO{" +
                "product=" + product +
                ", productMainImage=" + productMainImage +
                ", productOtherImageList=" + productOtherImageList +
                ", productReview=" + productReview +
                '}';
    }

}