package com.nchu.ruanko.greenfarm.pojo.vo;

import com.github.pagehelper.PageInfo;
import com.nchu.ruanko.greenfarm.pojo.entity.Order;

import java.util.List;

public class BusinessOrderPageVo {
    private List<BusinessOrderVo> businessOrderVoList;
    private PageInfo<Order> pageInfo;

    public List<BusinessOrderVo> getBusinessOrderVoList() {
        return businessOrderVoList;
    }

    public void setBusinessOrderVoList(List<BusinessOrderVo> businessOrderVoList) {
        this.businessOrderVoList = businessOrderVoList;
    }

    public PageInfo<Order> getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo<Order> pageInfo) {
        this.pageInfo = pageInfo;
    }

    @Override
    public String toString() {
        return "BusinessOrderPageVo{" +
                "businessOrderVoList=" + businessOrderVoList +
                ", pageInfo=" + pageInfo +
                '}';
    }
}
