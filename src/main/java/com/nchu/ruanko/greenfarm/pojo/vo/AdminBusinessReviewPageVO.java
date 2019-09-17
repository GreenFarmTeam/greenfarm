package com.nchu.ruanko.greenfarm.pojo.vo;

import com.github.pagehelper.PageInfo;
import com.nchu.ruanko.greenfarm.pojo.entity.BusinessReview;
import java.util.List;

/**
 * “审核记录”的 VO
 *
 * @author Yuan Yueshun
 */
public class AdminBusinessReviewPageVO {

    private PageInfo<BusinessReview> pageInfo;
    private List<BusinessReview> businessReviewList;

    public PageInfo<BusinessReview> getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo<BusinessReview> pageInfo) {
        this.pageInfo = pageInfo;
    }

    public List<BusinessReview> getBusinessReviewList() {
        return businessReviewList;
    }

    public void setBusinessReviewList(List<BusinessReview> businessReviewList) {
        this.businessReviewList = businessReviewList;
    }

    @Override
    public String toString() {
        return "AdminBusinessReviewPageVO{" +
                "pageInfo=" + pageInfo +
                ", businessReviewList=" + businessReviewList +
                '}';
    }

}