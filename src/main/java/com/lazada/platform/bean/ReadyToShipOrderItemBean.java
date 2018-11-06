package com.lazada.platform.bean;

/**
 * Created by LCC on 2018/6/3.
 */
public class ReadyToShipOrderItemBean {

    /**
     * order_item_id : 123456
     * purchase_order_id : 456789
     * purchase_order_number : ABC-123456
     */

    private String order_item_id;
    private String purchase_order_id;
    private String purchase_order_number;

    public String getOrder_item_id() {
        return order_item_id;
    }

    public void setOrder_item_id(String order_item_id) {
        this.order_item_id = order_item_id;
    }

    public String getPurchase_order_id() {
        return purchase_order_id;
    }

    public void setPurchase_order_id(String purchase_order_id) {
        this.purchase_order_id = purchase_order_id;
    }

    public String getPurchase_order_number() {
        return purchase_order_number;
    }

    public void setPurchase_order_number(String purchase_order_number) {
        this.purchase_order_number = purchase_order_number;
    }
}
