package com.lazada.platform.bean;

/**
 * Created by LCC on 2018/6/3.
 */
public enum DeliveryType {
    Dropship("dropship"),
    Pickup("pickup"),
    Warehourse("send_to_warehouse");

    private String type;

    DeliveryType(String deliveryType) {
        this.type = deliveryType;
    }

    public String getType() {
        return this.type;
    }
}
