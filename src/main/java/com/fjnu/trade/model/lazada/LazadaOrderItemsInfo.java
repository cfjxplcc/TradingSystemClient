package com.fjnu.trade.model.lazada;

import java.sql.Timestamp;

/**
 * Created by LCC on 2018/3/14.
 */
//@Entity
//@Table(name = "tb_lazada_order_items_info")
public class LazadaOrderItemsInfo {

    //    @Id
//    @GeneratedValue(generator = "uuid")
//    @GenericGenerator(name = "uuid", strategy = "uuid2")
//    @Column(name = "ID", length = 36)
    private String id;

    //    @ManyToOne
//    @JoinColumn(name = "ORDER_ID")
    private LazadaOrderInfo lazadaOrderInfo;

    //    @Column(name = "Lazada_ORDER_ITEM_ID")
    private Long lazadaOrderItemId;

    //    @Column(name = "NAME")
    private String name;

    //    @Column(name = "SKU")
    private String sku;

    //    @Column(name = "SHIPPING_TYPE")
    private String shippingType;

    //    @Column(name = "ITEM_PRICE")
    private Float itemPrice;

    //    @Column(name = "PAID_PRICE")
    private Float paidPrice;

    //    @Column(name = "CURRENCY")
    private String currency;

    //    @Column(name = "TAX_AMOUNT")
    private Float taxAmount;

    //    @Column(name = "SHIPPING_AMOUNT")
    private Float shippingAmount;

    //    @Column(name = "SHIPPING_SERVICE_COST")
    private Float shippingServiceCost;

    //    @Column(name = "VOUCHER_AMOUNT")
    private Float voucherAmount;

    //    @Column(name = "STATUS")
    private String status;

    //    @Column(name = "CREATED_TIME")
    private java.sql.Timestamp createdTime;

    //    @Column(name = "UPDATED_TIME")
//    @JsonInclude(JsonInclude.Include.NON_NULL)
    private java.sql.Timestamp updatedTime;

    //    @Column(name = "REMARKS")
    private String remarks;

    //    @Column(name = "ORDER_ITEM_URL")
    private String orderItemUrl;

    public LazadaOrderItemsInfo() {

    }

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

    public Long getLazadaOrderItemId() {
        return lazadaOrderItemId;
    }

    public void setLazadaOrderItemId(Long lazadaOrderItemId) {
        this.lazadaOrderItemId = lazadaOrderItemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getShippingType() {
        return shippingType;
    }

    public void setShippingType(String shippingType) {
        this.shippingType = shippingType;
    }

    public Float getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(Float itemPrice) {
        this.itemPrice = itemPrice;
    }

    public Float getPaidPrice() {
        return paidPrice;
    }

    public void setPaidPrice(Float paidPrice) {
        this.paidPrice = paidPrice;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Float getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(Float taxAmount) {
        this.taxAmount = taxAmount;
    }

    public Float getShippingAmount() {
        return shippingAmount;
    }

    public void setShippingAmount(Float shippingAmount) {
        this.shippingAmount = shippingAmount;
    }

    public Float getShippingServiceCost() {
        return shippingServiceCost;
    }

    public void setShippingServiceCost(Float shippingServiceCost) {
        this.shippingServiceCost = shippingServiceCost;
    }

    public Float getVoucherAmount() {
        return voucherAmount;
    }

    public void setVoucherAmount(Float voucherAmount) {
        this.voucherAmount = voucherAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }

    public Timestamp getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Timestamp updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getOrderItemUrl() {
        return orderItemUrl;
    }

    public void setOrderItemUrl(String orderItemUrl) {
        this.orderItemUrl = orderItemUrl;
    }
}
