package com.nchu.ruanko.greenfarm.pojo.vo;

import com.github.pagehelper.PageInfo;
import com.nchu.ruanko.greenfarm.pojo.entity.Farm;
import java.util.List;

public class BusinessFarmReviewPageVO {

    private PageInfo<Farm> pageInfo;
    private List<BusinessFarmReviewVO> businessFarmReviewVOList;

    public PageInfo<Farm> getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo<Farm> pageInfo) {
        this.pageInfo = pageInfo;
    }

    public List<BusinessFarmReviewVO> getBusinessFarmReviewVOList() {
        return businessFarmReviewVOList;
    }

    public void setBusinessFarmReviewVOList(List<BusinessFarmReviewVO> businessFarmReviewVOList) {
        this.businessFarmReviewVOList = businessFarmReviewVOList;
    }

    @Override
    public String toString() {
        return "BusinessFarmReviewPageVO{" +
                "pageInfo=" + pageInfo +
                ", businessFarmReviewVOList=" + businessFarmReviewVOList +
                '}';
    }

}