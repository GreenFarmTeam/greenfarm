package com.nchu.ruanko.greenfarm.pojo.vo;

import com.github.pagehelper.PageInfo;
import com.nchu.ruanko.greenfarm.pojo.entity.Business;
import java.util.List;

public class AdminBusinessLegalPageVO {

    private PageInfo<Business> pageInfo;
    private List<Business> businessList;

    public PageInfo<Business> getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo<Business> pageInfo) {
        this.pageInfo = pageInfo;
    }

    public List<Business> getBusinessList() {
        return businessList;
    }

    public void setBusinessList(List<Business> businessList) {
        this.businessList = businessList;
    }

    @Override
    public String toString() {
        return "AdminBusinessLegalPageVO{" +
                "pageInfo=" + pageInfo +
                ", businessList=" + businessList +
                '}';
    }

}
