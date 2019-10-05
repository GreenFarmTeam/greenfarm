package com.nchu.ruanko.greenfarm.pojo.vo;

import com.github.pagehelper.PageInfo;
import com.nchu.ruanko.greenfarm.pojo.entity.Farm;

import java.util.List;

public class BusinessFarmPageVO {
    private PageInfo<Farm> pageInfo;
    List<BusinessFarmVO> businessFarmVOList;

    public PageInfo<Farm> getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo<Farm> pageInfo) {
        this.pageInfo = pageInfo;
    }

    public List<BusinessFarmVO> getBusinessFarmVOList() {
        return businessFarmVOList;
    }

    public void setBusinessFarmVOList(List<BusinessFarmVO> businessFarmVOList) {
        this.businessFarmVOList = businessFarmVOList;
    }
}
