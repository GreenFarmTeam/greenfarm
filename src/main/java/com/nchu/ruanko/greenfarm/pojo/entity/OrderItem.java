package com.nchu.ruanko.greenfarm.pojo.entity;

public class OrderItem {

    private String itemUid;
    private String itemPrice;
    private Integer itemCount;
    private Float itemSum;
    private Order order;
    private Product product;

    public String getItemUid() {
        return itemUid;
    }

    public void setItemUid(String itemUid) {
        this.itemUid = itemUid;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public Integer getItemCount() {
        return itemCount;
    }

    public void setItemCount(Integer itemCount) {
        this.itemCount = itemCount;
    }

    public Float getItemSum() {
        return itemSum;
    }

    public void setItemSum(Float itemSum) {
        this.itemSum = itemSum;
    }


    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "itemUid='" + itemUid + '\'' +
                ", itemPrice='" + itemPrice + '\'' +
                ", itemCount='" + itemCount + '\'' +
                ", itemSum=" + itemSum +
                ", order=" + order +
                ", product=" + product +
                '}';
    }
}
