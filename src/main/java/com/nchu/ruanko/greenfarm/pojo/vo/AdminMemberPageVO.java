package com.nchu.ruanko.greenfarm.pojo.vo;

import com.github.pagehelper.PageInfo;
import com.nchu.ruanko.greenfarm.pojo.entity.User;
import java.util.List;

public class AdminMemberPageVO {

    private PageInfo<User> pageInfo;
    private List<AdminMemberVO> adminMemberVOList;

    public PageInfo<User> getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo<User> pageInfo) {
        this.pageInfo = pageInfo;
    }

    public List<AdminMemberVO> getAdminMemberVOList() {
        return adminMemberVOList;
    }

    public void setAdminMemberVOList(List<AdminMemberVO> adminMemberVOList) {
        this.adminMemberVOList = adminMemberVOList;
    }

    @Override
    public String toString() {
        return "AdminMemberPageVO{" +
                "pageInfo=" + pageInfo +
                ", adminMemberVOList=" + adminMemberVOList +
                '}';
    }

}