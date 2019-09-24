package com.nchu.ruanko.greenfarm.service;

import com.nchu.ruanko.greenfarm.pojo.entity.Product;
import com.nchu.ruanko.greenfarm.pojo.entity.ProductImage;
import com.nchu.ruanko.greenfarm.pojo.vo.BusinessProductReviewPageVO;
import java.util.List;

public interface ProductService {

    void addProduct(Product product, ProductImage productMainImage, List<ProductImage> productOtherImages, String productTypeUid, String businessUid);

    BusinessProductReviewPageVO listBusinessProductReviewRecords(String businessUid, int pageNum, int pageSize, int navigationSize);

}
