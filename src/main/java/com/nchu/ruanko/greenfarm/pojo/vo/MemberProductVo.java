package com.nchu.ruanko.greenfarm.pojo.vo;

import com.nchu.ruanko.greenfarm.pojo.entity.Product;
import com.nchu.ruanko.greenfarm.pojo.entity.ProductImage;
import com.nchu.ruanko.greenfarm.pojo.entity.ProductReview;

import java.util.List;

public class MemberProductVo {
    private Product product;
    private ProductReview review;
    private ProductImage mainImage;
    private List<ProductImage> otherImages;


    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ProductReview getReview() {
        return review;
    }

    public void setReview(ProductReview review) {
        this.review = review;
    }

    public ProductImage getMainImage() {

        return mainImage;
    }

    public void setMainImage(ProductImage mainImage) {
        this.mainImage = mainImage;
    }

    public List<ProductImage> getOtherImages() {
        return otherImages;
    }

    public void setOtherImages(List<ProductImage> otherImages) {
        this.otherImages = otherImages;
    }

    @Override
    public String toString() {
        return "MemberProductVo{" +
                "product=" + product +
                ", review=" + review +
                ", mainImage=" + mainImage +
                ", otherImages=" + otherImages +
                '}';
    }
}
