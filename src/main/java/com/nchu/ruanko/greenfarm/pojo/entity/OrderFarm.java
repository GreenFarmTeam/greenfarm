package com.nchu.ruanko.greenfarm.pojo.entity;

import java.util.Date;

public class OrderFarm {
    private String orderFarmUid;
    private User orderFarmUser;
    private Date orderFarmTime;
    private Farm orderFarmFarm;
    private String orderFarmPhone;
    private String orderFarmName;
    private String orderFarmRemark;
    private Integer orderFarmState;

    public Integer getOrderFarmState() {
        return orderFarmState;
    }

    public void setOrderFarmState(Integer orderFarmState) {
        this.orderFarmState = orderFarmState;
    }

    public String getOrderFarmUid() {
        return orderFarmUid;
    }

    public void setOrderFarmUid(String orderFarmUid) {
        this.orderFarmUid = orderFarmUid;
    }

    public User getOrderFarmUser() {
        return orderFarmUser;
    }

    public void setOrderFarmUser(User orderFarmUser) {
        this.orderFarmUser = orderFarmUser;
    }

    public Date getOrderFarmTime() {
        return orderFarmTime;
    }

    public void setOrderFarmTime(Date orderFarmTime) {
        this.orderFarmTime = orderFarmTime;
    }

    public Farm getOrderFarmFarm() {
        return orderFarmFarm;
    }

    public void setOrderFarmFarm(Farm orderFarmFarm) {
        this.orderFarmFarm = orderFarmFarm;
    }

    public String getOrderFarmPhone() {
        return orderFarmPhone;
    }

    public void setOrderFarmPhone(String orderFarmPhone) {
        this.orderFarmPhone = orderFarmPhone;
    }

    public String getOrderFarmName() {
        return orderFarmName;
    }

    public void setOrderFarmName(String orderFarmName) {
        this.orderFarmName = orderFarmName;
    }

    public String getOrderFarmRemark() {
        return orderFarmRemark;
    }

    public void setOrderFarmRemark(String orderFarmRemark) {
        this.orderFarmRemark = orderFarmRemark;
    }

    @Override
    public String toString() {
        return "OrderFarm{" +
                "orderFarmUid='" + orderFarmUid + '\'' +
                ", orderFarmUser=" + orderFarmUser +
                ", orderFarmTime=" + orderFarmTime +
                ", orderFarmFarm=" + orderFarmFarm +
                ", orderFarmPhone='" + orderFarmPhone + '\'' +
                ", orderFarmName='" + orderFarmName + '\'' +
                ", orderFarmRemark='" + orderFarmRemark + '\'' +
                ", orderFarmState=" + orderFarmState +
                '}';
    }
}
