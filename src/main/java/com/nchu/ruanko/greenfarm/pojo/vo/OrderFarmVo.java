package com.nchu.ruanko.greenfarm.pojo.vo;

import com.nchu.ruanko.greenfarm.pojo.entity.FarmImage;
import com.nchu.ruanko.greenfarm.pojo.entity.OrderFarm;

public class OrderFarmVo {
    private OrderFarm orderFarm;
    private FarmImage mainFarmImage;

    public OrderFarm getOrderFarm() {
        return orderFarm;
    }

    public void setOrderFarm(OrderFarm orderFarm) {
        this.orderFarm = orderFarm;
    }

    public FarmImage getMainFarmImage() {
        return mainFarmImage;
    }

    public void setMainFarmImage(FarmImage mainFarmImage) {
        this.mainFarmImage = mainFarmImage;
    }

    @Override
    public String toString() {
        return "OrderFarmVo{" +
                "orderFarm=" + orderFarm +
                ", mainFarmImage=" + mainFarmImage +
                '}';
    }
}
