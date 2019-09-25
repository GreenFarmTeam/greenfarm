package com.nchu.ruanko.greenfarm.pojo.vo;

import com.nchu.ruanko.greenfarm.pojo.entity.Product;
import com.nchu.ruanko.greenfarm.pojo.entity.ProductImage;
import java.util.List;

public class BusinessProductVO {

    private Product product;
    private ProductImage mainImage;
    private List<ProductImage> otherImages;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
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
        return "BusinessProductVO{" +
                "product=" + product +
                ", mainImage=" + mainImage +
                ", otherImages=" + otherImages +
                '}';
    }

}