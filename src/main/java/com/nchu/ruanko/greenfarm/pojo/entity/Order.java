package com.nchu.ruanko.greenfarm.pojo.entity;

import java.util.Date;

public class Order {
    private String orderUid;
    private Date orderCtime;
    private Date orderPtime;
    private String orderRname;
    private String orderRaddress;
    private String orderRphone;
    private Integer orderState;
    private Float orderSum;
    private User Member;

    public String getOrderUid() {
        return orderUid;
    }

    public void setOrderUid(String orderUid) {
        this.orderUid = orderUid;
    }

    public Date getOrderCtime() {
        return orderCtime;
    }

    public void setOrderCtime(Date orderCtime) {
        this.orderCtime = orderCtime;
    }

    public Date getOrderPtime() {
        return orderPtime;
    }

    public void setOrderPtime(Date orderPtime) {
        this.orderPtime = orderPtime;
    }

    public String getOrderRname() {
        return orderRname;
    }

    public void setOrderRname(String orderRname) {
        this.orderRname = orderRname;
    }

    public String getOrderRaddress() {
        return orderRaddress;
    }

    public void setOrderRaddress(String orderRaddress) {
        this.orderRaddress = orderRaddress;
    }

    public String getOrderRphone() {
        return orderRphone;
    }

    public void setOrderRphone(String orderRphone) {
        this.orderRphone = orderRphone;
    }

    public Integer getOrderState() {
        return orderState;
    }

    public void setOrderState(Integer orderState) {
        this.orderState = orderState;
    }

    public Float getOrderSum() {
        return orderSum;
    }

    public void setOrderSum(Float orderSum) {
        this.orderSum = orderSum;
    }

    public User getMember() {
        return Member;
    }

    public void setMember(User member) {
        Member = member;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderUid='" + orderUid + '\'' +
                ", orderCtime=" + orderCtime +
                ", orderPtime=" + orderPtime +
                ", orderRname='" + orderRname + '\'' +
                ", orderRaddress='" + orderRaddress + '\'' +
                ", orderRphone='" + orderRphone + '\'' +
                ", orderState=" + orderState +
                ", orderSum=" + orderSum +
                ", Member=" + Member +
                '}';
    }
}
