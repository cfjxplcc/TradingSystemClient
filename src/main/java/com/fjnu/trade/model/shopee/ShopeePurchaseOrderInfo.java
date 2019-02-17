package com.fjnu.trade.model.shopee;

import java.sql.Timestamp;

/**
 * Created by luochunchen on 2018/12/13.
 */
public class ShopeePurchaseOrderInfo {

    private String id;
    private ShopeeOrderInfo shopeeOrderInfo;
    private ShopeeOrderItemsInfo shopeeOrderItemsInfo;
    private String thirdPartyOrderId;
    private String description;
    private Float totalPrice;
    private int itemTotalNumber;
    private String orderUrl;
    private Float weight;
    private Timestamp date;
    private String orderExpressNumber;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ShopeeOrderInfo getShopeeOrderInfo() {
        return shopeeOrderInfo;
    }

    public void setShopeeOrderInfo(ShopeeOrderInfo shopeeOrderInfo) {
        this.shopeeOrderInfo = shopeeOrderInfo;
    }

    public ShopeeOrderItemsInfo getShopeeOrderItemsInfo() {
        return shopeeOrderItemsInfo;
    }

    public void setShopeeOrderItemsInfo(ShopeeOrderItemsInfo shopeeOrderItemsInfo) {
        this.shopeeOrderItemsInfo = shopeeOrderItemsInfo;
    }

    public String getThirdPartyOrderId() {
        return thirdPartyOrderId;
    }

    public void setThirdPartyOrderId(String thirdPartyOrderId) {
        this.thirdPartyOrderId = thirdPartyOrderId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getItemTotalNumber() {
        return itemTotalNumber;
    }

    public void setItemTotalNumber(int itemTotalNumber) {
        this.itemTotalNumber = itemTotalNumber;
    }

    public String getOrderUrl() {
        return orderUrl;
    }

    public void setOrderUrl(String orderUrl) {
        this.orderUrl = orderUrl;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getOrderExpressNumber() {
        return orderExpressNumber;
    }

    public void setOrderExpressNumber(String orderExpressNumber) {
        this.orderExpressNumber = orderExpressNumber;
    }
}
