package com.nchu.ruanko.greenfarm.pojo.vo;

import com.github.pagehelper.PageInfo;
import com.nchu.ruanko.greenfarm.pojo.entity.Product;

import java.util.List;

public class MemberProductPageVo {
    private PageInfo<Product> pageInfo;
    private List<MemberProductVo> memberProductVoList;

    public PageInfo<Product> getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo<Product> pageInfo) {
        this.pageInfo = pageInfo;
    }

    public List<MemberProductVo> getMemberProductVoList() {
        return memberProductVoList;
    }

    public void setMemberProductVoList(List<MemberProductVo> memberProductVoList) {
        this.memberProductVoList = memberProductVoList;
    }

    @Override
    public String toString() {
        return "MemberProductPageVo{" +
                "pageInfo=" + pageInfo +
                ", memberProductVoList=" + memberProductVoList +
                '}';
    }
}
