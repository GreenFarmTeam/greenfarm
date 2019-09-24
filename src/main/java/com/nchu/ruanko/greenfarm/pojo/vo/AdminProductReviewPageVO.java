package com.nchu.ruanko.greenfarm.pojo.vo;

import com.github.pagehelper.PageInfo;
import com.nchu.ruanko.greenfarm.pojo.entity.ProductReview;
import java.util.List;

public class AdminProductReviewPageVO {

    private PageInfo<ProductReview> pageInfo;
    private List<AdminProductReviewVO> adminProductReviewVOList;

    public PageInfo<ProductReview> getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo<ProductReview> pageInfo) {
        this.pageInfo = pageInfo;
    }

    public List<AdminProductReviewVO> getAdminProductReviewVOList() {
        return adminProductReviewVOList;
    }

    public void setAdminProductReviewVOList(List<AdminProductReviewVO> adminProductReviewVOList) {
        this.adminProductReviewVOList = adminProductReviewVOList;
    }

    @Override
    public String toString() {
        return "AdminProductReviewPageVO{" +
                "pageInfo=" + pageInfo +
                ", adminProductReviewVOList=" + adminProductReviewVOList +
                '}';
    }

}