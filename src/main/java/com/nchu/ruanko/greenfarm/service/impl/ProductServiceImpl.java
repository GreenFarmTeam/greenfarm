package com.nchu.ruanko.greenfarm.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nchu.ruanko.greenfarm.constant.FileConstant;
import com.nchu.ruanko.greenfarm.dao.ProductDAO;
import com.nchu.ruanko.greenfarm.dao.ProductImageDAO;
import com.nchu.ruanko.greenfarm.dao.ProductReviewDAO;
import com.nchu.ruanko.greenfarm.pojo.entity.Product;
import com.nchu.ruanko.greenfarm.pojo.entity.ProductImage;
import com.nchu.ruanko.greenfarm.pojo.entity.ProductReview;
import com.nchu.ruanko.greenfarm.pojo.vo.*;
import com.nchu.ruanko.greenfarm.service.ProductService;
import com.nchu.ruanko.greenfarm.util.string.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDAO productDAO;

    @Autowired
    private ProductImageDAO productImageDAO;

    @Autowired
    private ProductReviewDAO productReviewDAO;

    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 商家上架商品
     *
     * @param product
     * @param productMainImage
     * @param productOtherImages
     * @param productTypeUid
     * @param businessUid
     */
    @Override
    public void businessAddProduct(Product product, ProductImage productMainImage, List<ProductImage> productOtherImages, String productTypeUid, String businessUid) {
        /* 商品基本信息 */
        String productUid = StringUtils.createUUID();
        product.setProductUid(productUid);
        // 未通过审核前，“商品库存”为 0
        product.setProductStock(0);
        // 未通过审核前，“商品是否推荐”为 NULL
        product.setProductIsRecommend(null);
        // 未通过审核前，“商品上架时间”为 NULL
        product.setProductUpDate(null);
        productDAO.insertProductWithProductTypeUIDAndBusinessUID(product, productTypeUid, businessUid);

        /* 商品图片 */
        if (productMainImage != null) {
            productMainImage.setProductImageIsMainImage(1);
            productImageDAO.insertProductImageWithProductUID(productMainImage, productUid);
        }
        if (productOtherImages.size() != 0) {
            for (ProductImage image : productOtherImages) {
                image.setProductImageIsMainImage(0);
                productImageDAO.insertProductImageWithProductUID(image, productUid);
            }
        }

        /* 商品审核信息 */
        ProductReview productReview = new ProductReview();
        productReview.setProductReviewUid(StringUtils.createUUID());
        productReview.setProductReviewSubmitTime(new Date(System.currentTimeMillis()));
        // 这三项“未审核”前均为 NULL
        productReview.setProductReviewReviewTime(null);
        productReview.setProductReviewResult(null);
        productReview.setProductReviewReason(null);
        productReviewDAO.insertProductReviewWithProductUID(productReview, productUid);
    }

    /**
     * 商家获取当前商家所有的商品审核记录
     *
     * @param businessUid uid
     * @param pageNum 页码
     * @param pageSize 每页显示数
     * @param navigationSize 导航页书
     * @return BusinessProductReviewPageVO
     */
    @Override
    public BusinessProductReviewPageVO businessListProductReviewRecords(String businessUid, int pageNum, int pageSize, int navigationSize) {
        BusinessProductReviewPageVO vo = new BusinessProductReviewPageVO();

        PageHelper.startPage(pageNum, pageSize);
        List<Product> productList = productDAO.listProductsByBusinessUID(businessUid);
        PageInfo<Product> pageInfo = new PageInfo<>(productList, navigationSize);

        List<BusinessProductReviewVO> businessProductReviewVOList = new ArrayList<>();
        for (Product product : productList) {
            BusinessProductReviewVO reviewVO = new BusinessProductReviewVO();
            reviewVO.setProduct(product);
            reviewVO.setProductMainImage(productImageDAO.getProductMainImageByProductUID(product.getProductUid()));
            reviewVO.setProductOtherImageList(productImageDAO.listProductOtherImagesByProductUID(product.getProductUid()));
            reviewVO.setProductReview(productReviewDAO.getProductReviewByProductUID(product.getProductUid()));
            businessProductReviewVOList.add(reviewVO);
        }

        vo.setPageInfo(pageInfo);
        vo.setBusinessProductReviewVOList(businessProductReviewVOList);
        return vo;
    }

    /**
     * 管理员获取所有未审核的商品
     *
     * @param pageNum 页码
     * @param pageSize 每页显示数
     * @param navigationSize 导航页书
     * @return AdminProductReviewPageVO
     */
    @Override
    public AdminProductReviewPageVO adminListProductReview(int pageNum, int pageSize, int navigationSize) {
        AdminProductReviewPageVO vo = new AdminProductReviewPageVO();

        PageHelper.startPage(pageNum, pageSize);
        List<ProductReview> productReviewList = productReviewDAO.listUnreviewedProductReviewsOrderBySubmitTimeDesc();
        PageInfo<ProductReview> pageInfo = new PageInfo<>(productReviewList, navigationSize);

        List<AdminProductReviewVO> adminProductReviewVOList = new ArrayList<>();
        for (ProductReview productReview : productReviewList) {
            AdminProductReviewVO reviewVO = new AdminProductReviewVO();
            String productUid = productReview.getProduct().getProductUid();
            reviewVO.setProductReview(productReview);
            reviewVO.setProduct(productReview.getProduct());
            reviewVO.setProductMainImage(productImageDAO.getProductMainImageByProductUID(productUid));
            reviewVO.setProductOtherImageList(productImageDAO.listProductOtherImagesByProductUID(productUid));
            adminProductReviewVOList.add(reviewVO);
        }

        vo.setPageInfo(pageInfo);
        vo.setAdminProductReviewVOList(adminProductReviewVOList);

        return vo;
    }

    /**
     * 管理员同意上架商品
     *
     * @param productReviewUid uid
     */
    @Override
    public void adminAgreeProductReview(String productReviewUid) {
        String productUid = productReviewDAO.getProductUIDByProductReviewUID(productReviewUid);
        Date date = new Date(System.currentTimeMillis());
        productReviewDAO.updateProductReviewReviewTimeByProductReviewUID(date, productReviewUid);
        productDAO.updateProductUpDateByProductUID(date, productUid);
        productReviewDAO.updateProductReviewResultAndProductReviewReasonByProductReviewUID(1, null, productReviewUid);
        productDAO.updateProductIsRecommendByProductUID(0, productUid);
    }

    /**
     * 管理员驳回上架商品申请
     *
     * @param reason
     * @param productReviewUid
     */
    @Override
    public void adminDisagreeProductReview(String reason, String productReviewUid) {
        String productUid = productReviewDAO.getProductUIDByProductReviewUID(productReviewUid);

        productReviewDAO.updateProductReviewReviewTimeByProductReviewUID(new Date(System.currentTimeMillis()), productReviewUid);
        productReviewDAO.updateProductReviewResultAndProductReviewReasonByProductReviewUID(0, reason, productReviewUid);
        productDAO.updateProductIsRecommendByProductUID(null, productUid);
        try {
            productDAO.updateProductUpDateByProductUID(dateFormat.parse("1000-01-01 00:00:00"), productUid);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * 商家删除未通过审核的商品的信息
     *
     * @param businessUid
     */
    @Override
    public void businessClearNoPassProductReview(String businessUid) {
        List<String> productUidList = productDAO.listNoPassReviewProductUIDs();
        if (productUidList.size() != 0) {
            for (String productUid : productUidList) {
                productReviewDAO.deleteProductReviewByProductUID(productUid);
                List<String> imagePathList = productImageDAO.listProductImagesPathByProductUID(productUid);
                for (String imagePath : imagePathList) {
                    File file = new File(FileConstant.FILE_UPLOAD_PATH + "\\" + imagePath.substring(imagePath.lastIndexOf("/") + 1));
                    if (file.exists() && file.isFile()) {
                        file.delete();
                    }
                }
                productImageDAO.deleteProductImageByProductUID(productUid);
                productDAO.deleteProductByProductUID(productUid);
            }
        }
    }


    /**
     * 待讨论
     *
     * @param businessUid
     * @param pageNum
     * @param pageSize
     * @param navigationSize
     * @return
     */
    @Override
    public BusinessProductPageVO businessListProducts(String businessUid, int pageNum, int pageSize, int navigationSize) {
        BusinessProductPageVO vo = new BusinessProductPageVO();

        PageHelper.startPage(pageNum, pageSize);
        List<Product> productList = productDAO.listPassReviewProductsByBusinessUID(businessUid);
        PageInfo<Product> pageInfo = new PageInfo<>(productList, navigationSize);

        List<BusinessProductVO> businessProductVOList = new ArrayList<>();

        for (Product product : productList) {
            BusinessProductVO productVO = new BusinessProductVO();
            productVO.setProduct(product);
            productVO.setMainImage(productImageDAO.getProductMainImageByProductUID(product.getProductUid()));
            productVO.setOtherImages(productImageDAO.listProductOtherImagesByProductUID(product.getProductUid()));
            businessProductVOList.add(productVO);
        }

        vo.setPageInfo(pageInfo);
        vo.setBusinessProductVOList(businessProductVOList);

        return vo;
    }

    /**
     * 商家获取某一成功上架商品的详细信息
     *
     * @param productUid
     * @return
     */
    @Override
    public BusinessProductVO businessGetProductByProductUID(String productUid) {
        BusinessProductVO vo = new BusinessProductVO();
        vo.setProduct(productDAO.getProductByProductUID(productUid));
        vo.setMainImage(productImageDAO.getProductMainImageByProductUID(productUid));
        vo.setOtherImages(productImageDAO.listProductOtherImagesByProductUID(productUid));
        return vo;
    }

}