package com.nchu.ruanko.greenfarm.pojo.vo;

import com.github.pagehelper.PageInfo;
import com.nchu.ruanko.greenfarm.pojo.entity.Farm;

import java.util.List;

public class AdminFarmPageVO {
    private PageInfo<Farm> pageInfo;
    List<AdminFarmVO> adminFarmVOList;

    public PageInfo<Farm> getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo<Farm> pageInfo) {
        this.pageInfo = pageInfo;
    }

    public List<AdminFarmVO> getAdminFarmVOList() {
        return adminFarmVOList;
    }

    public void setAdminFarmVOList(List<AdminFarmVO> adminFarmVOList) {
        this.adminFarmVOList = adminFarmVOList;
    }

    @Override
    public String toString() {
        return "AdminFarmPageVO{" +
                "pageInfo=" + pageInfo +
                ", adminFarmVOList=" + adminFarmVOList +
                '}';
    }
}
