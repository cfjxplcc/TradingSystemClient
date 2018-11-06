package com.fjnu.trade.model.lazada;

import java.sql.Timestamp;

/**
 * Created by LCC on 2018/3/14.
 */
//Entity
//Table(name = "tb_purchase_order_info")
public class PurchaseOrderInfo {

    //Id
    //GeneratedValue(generator = "uuid")
    //GenericGenerator(name = "uuid", strategy = "uuid2")
    //Column(name = "ID", length = 36)
    private String id;

    //ManyToOne
    //JoinColumn(name = "ORDER_ID")
    private LazadaOrderInfo lazadaOrderInfo;

    //ManyToOne
    //JoinColumn(name = "ORDER_ITEM_ID")
    private LazadaOrderItemsInfo lazadaOrderItemsInfo;

    //    @Column(name = "THIRD_PARTY_ORDER_ID")
    private String thirdPartyOrderId;

    //Column(name = "DESCRIPTION")
    private String description;

    //Column(name = "TOTAL_PRICE")
    private Float totalPrice;

    //Column(name = "ITEM_TOTAL_MUNBER")
    private int itemTotalMunber;

    //Column(name = "ORDER_URL")
    private String orderUrl;

    //Column(name = "WEIGHT")
    private Float weight;

    //Column(name = "DATE")
    private Timestamp date;

    //Column(name = "ORDER_EXPRESS_NUMBER")
    private String orderExpressNumber;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LazadaOrderInfo getLazadaOrderInfo() {
        return lazadaOrderInfo;
    }

    public void setLazadaOrderInfo(LazadaOrderInfo lazadaOrderInfo) {
        this.lazadaOrderInfo = lazadaOrderInfo;
    }

    public LazadaOrderItemsInfo getLazadaOrderItemsInfo() {
        return lazadaOrderItemsInfo;
    }

    public void setLazadaOrderItemsInfo(LazadaOrderItemsInfo lazadaOrderItemsInfo) {
        this.lazadaOrderItemsInfo = lazadaOrderItemsInfo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThirdPartyOrderId() {
        return thirdPartyOrderId;
    }

    public void setThirdPartyOrderId(String thirdPartyOrderId) {
        this.thirdPartyOrderId = thirdPartyOrderId;
    }

    public Float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getItemTotalMunber() {
        return itemTotalMunber;
    }

    public void setItemTotalMunber(int itemTotalMunber) {
        this.itemTotalMunber = itemTotalMunber;
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
