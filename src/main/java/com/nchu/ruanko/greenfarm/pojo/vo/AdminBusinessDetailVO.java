package com.nchu.ruanko.greenfarm.pojo.vo;

import com.nchu.ruanko.greenfarm.pojo.entity.Business;
import com.nchu.ruanko.greenfarm.pojo.entity.BusinessScope;
import java.util.List;

public class AdminBusinessDetailVO {

    private Business business;

    private List<BusinessScope> businessScopeList;

    public Business getBusiness() {
        return business;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }

    public List<BusinessScope> getBusinessScopeList() {
        return businessScopeList;
    }

    public void setBusinessScopeList(List<BusinessScope> businessScopeList) {
        this.businessScopeList = businessScopeList;
    }

    @Override
    public String toString() {
        return "AdminBusinessDetailVO{" +
                "business=" + business +
                ", businessScopeList=" + businessScopeList +
                '}';
    }

}