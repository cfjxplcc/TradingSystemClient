package com.fjnu.trade.bean.shopee;

/**
 * Created by LCC on 2018/9/12.
 */
public enum ShopeeOrderStatus {
    Unpaid("UNPAID"),
    ReadyToShip("READY_TO_SHIP"),
    RetryShip("RETRY_SHIP"),
    Shipped("SHIPPED"),
    ToConfirmReceive("TO_CONFIRM_RECEIVE"),
    InCancel("IN_CANCEL"),
    Cancelled("CANCELLED"),
    ToReturn("TO_RETURN"),
    Completed("COMPLETED");

    private String status;

    ShopeeOrderStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
