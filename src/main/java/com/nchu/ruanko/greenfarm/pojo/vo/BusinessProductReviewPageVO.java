package com.nchu.ruanko.greenfarm.pojo.vo;

import com.github.pagehelper.PageInfo;
import com.nchu.ruanko.greenfarm.pojo.entity.Product;
import java.util.List;

public class BusinessProductReviewPageVO {

    private PageInfo<Product> pageInfo;
    private List<BusinessProductReviewVO> businessProductReviewVOList;

    public PageInfo<Product> getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo<Product> pageInfo) {
        this.pageInfo = pageInfo;
    }

    public List<BusinessProductReviewVO> getBusinessProductReviewVOList() {
        return businessProductReviewVOList;
    }

    public void setBusinessProductReviewVOList(List<BusinessProductReviewVO> businessProductReviewVOList) {
        this.businessProductReviewVOList = businessProductReviewVOList;
    }

    @Override
    public String toString() {
        return "BusinessProductReviewPageVO{" +
                "pageInfo=" + pageInfo +
                ", businessProductReviewVOList=" + businessProductReviewVOList +
                '}';
    }

}
