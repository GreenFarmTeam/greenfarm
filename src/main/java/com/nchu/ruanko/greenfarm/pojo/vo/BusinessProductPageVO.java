package com.nchu.ruanko.greenfarm.pojo.vo;

import com.github.pagehelper.PageInfo;
import com.nchu.ruanko.greenfarm.pojo.entity.Product;
import java.util.List;

public class BusinessProductPageVO {

    private PageInfo<Product> pageInfo;
    private List<BusinessProductVO> businessProductVOList;

    public PageInfo<Product> getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo<Product> pageInfo) {
        this.pageInfo = pageInfo;
    }

    public List<BusinessProductVO> getBusinessProductVOList() {
        return businessProductVOList;
    }

    public void setBusinessProductVOList(List<BusinessProductVO> businessProductVOList) {
        this.businessProductVOList = businessProductVOList;
    }

    @Override
    public String toString() {
        return "BusinessProductPageVO{" +
                "pageInfo=" + pageInfo +
                ", businessProductVOList=" + businessProductVOList +
                '}';
    }

}