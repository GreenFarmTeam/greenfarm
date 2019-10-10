package com.nchu.ruanko.greenfarm.pojo.vo;

import com.nchu.ruanko.greenfarm.pojo.entity.Address;
import com.nchu.ruanko.greenfarm.pojo.entity.Business;
import com.nchu.ruanko.greenfarm.pojo.entity.User;

import java.util.List;

public class AdminMemberVO {

    private User member;
    private List<Address> addressList;

    public User getMember() {
        return member;
    }

    public void setMember(User member) {
        this.member = member;
    }

    public List<Address> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<Address> addressList) {
        this.addressList = addressList;
    }

    @Override
    public String toString() {
        return "AdminMemberVO{" +
                "member=" + member +
                ", addressList=" + addressList +
                '}';
    }
}
