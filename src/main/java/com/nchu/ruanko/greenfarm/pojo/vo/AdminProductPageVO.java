package com.nchu.ruanko.greenfarm.pojo.vo;

import com.github.pagehelper.PageInfo;
import com.nchu.ruanko.greenfarm.pojo.entity.Product;
import java.util.List;

public class AdminProductPageVO {

    private PageInfo<Product> pageInfo;
    List<AdminProductVO> adminProductVOList;

    public PageInfo<Product> getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo<Product> pageInfo) {
        this.pageInfo = pageInfo;
    }

    public List<AdminProductVO> getAdminProductVOList() {
        return adminProductVOList;
    }

    public void setAdminProductVOList(List<AdminProductVO> adminProductVOList) {
        this.adminProductVOList = adminProductVOList;
    }

    @Override
    public String toString() {
        return "AdminProductPageVO{" +
                "pageInfo=" + pageInfo +
                ", adminProductVOList=" + adminProductVOList +
                '}';
    }

}