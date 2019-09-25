package com.nchu.ruanko.greenfarm.service;

import com.nchu.ruanko.greenfarm.pojo.entity.Product;
import com.nchu.ruanko.greenfarm.pojo.entity.ProductImage;
import com.nchu.ruanko.greenfarm.pojo.vo.*;

import java.util.List;

public interface ProductService {

    void businessAddProduct(Product product, ProductImage productMainImage, List<ProductImage> productOtherImages, String productTypeUid, String businessUid);

    BusinessProductReviewPageVO businessListProductReviewRecords(String businessUid, int pageNum, int pageSize, int navigationSize);

    AdminProductReviewPageVO adminListProductReview(int pageNum, int pageSize, int navigationSize);

    BusinessProductPageVO businessListProducts(String businessUid, int pageNum, int pageSize, int navigationSize);

    BusinessProductVO businessGetProductByProductUID(String productUid);

    AdminProductPageVO adminListProducts(int pageNum, int pageSize, int navigationSize);

    AdminProductVO adminGetProductByProductUID(String productUid);

    AdminProductPageVO adminListDownProducts(int pageNum, int pageSize, int navigationSize);

    void adminAgreeProductReview(String productReviewUid);

    void adminDisagreeProductReview(String reason, String productReviewUid);

    void businessClearNoPassProductReview(String businessUid);

    void adminDownProduct(String productUid);

    void adminUpProduct(String productUid);

    void businessDownProduct(String productUid);

    void businessUpProduct(String productUid);

}