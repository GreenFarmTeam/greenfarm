package com.nchu.ruanko.greenfarm.pojo.vo;

import com.github.pagehelper.PageInfo;
import com.nchu.ruanko.greenfarm.pojo.entity.Farm;

import java.util.List;

public class MemberFarmPageVo {
    private PageInfo<Farm> pageInfo;
    List<MemberFarmVo> memberFarmVOList;

    public PageInfo<Farm> getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo<Farm> pageInfo) {
        this.pageInfo = pageInfo;
    }

    public List<MemberFarmVo> getMemberFarmVOList() {
        return memberFarmVOList;
    }

    public void setMemberFarmVOList(List<MemberFarmVo> memberFarmVOList) {
        this.memberFarmVOList = memberFarmVOList;
    }

    @Override
    public String toString() {
        return "MemberFarmPageVo{" +
                "pageInfo=" + pageInfo +
                ", memberFarmVOList=" + memberFarmVOList +
                '}';
    }
}
