package com.nchu.ruanko.greenfarm.pojo.vo;

import com.github.pagehelper.PageInfo;
import com.nchu.ruanko.greenfarm.pojo.entity.OrderFarm;

import java.util.List;

public class OrderFarmPageVo {
    private List<OrderFarmVo> orderFarmVoList;
    private PageInfo<OrderFarm> orderFarmPageInfo;

    public List<OrderFarmVo> getOrderFarmVoList() {
        return orderFarmVoList;
    }

    public void setOrderFarmVoList(List<OrderFarmVo> orderFarmVoList) {
        this.orderFarmVoList = orderFarmVoList;
    }

    public PageInfo<OrderFarm> getOrderFarmPageInfo() {
        return orderFarmPageInfo;
    }

    public void setOrderFarmPageInfo(PageInfo<OrderFarm> orderFarmPageInfo) {
        this.orderFarmPageInfo = orderFarmPageInfo;
    }

    @Override
    public String toString() {
        return "OrderFarmPageVo{" +
                "orderFarmVoList=" + orderFarmVoList +
                ", orderFarmPageInfo=" + orderFarmPageInfo +
                '}';
    }
}
