package com.nchu.ruanko.greenfarm.pojo.vo;

import com.github.pagehelper.PageInfo;
import com.nchu.ruanko.greenfarm.pojo.entity.Farm;

import java.util.List;

public class AdminFarmPageVO {
    private PageInfo<Farm> pageInfo;
    List<AdminFarmVo> adminFarmVOList;

    public PageInfo<Farm> getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo<Farm> pageInfo) {
        this.pageInfo = pageInfo;
    }

    public List<AdminFarmVo> getAdminFarmVOList() {
        return adminFarmVOList;
    }

    public void setAdminFarmVOList(List<AdminFarmVo> adminFarmVOList) {
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
