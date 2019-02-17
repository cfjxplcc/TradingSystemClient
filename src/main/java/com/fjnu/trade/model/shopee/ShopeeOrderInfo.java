package com.fjnu.trade.model.shopee;

import com.fjnu.trade.model.ExchangeRate;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by luochunchen on 2018/12/12.
 */
public class ShopeeOrderInfo {

    private String orderSn;
    private ShopeeShopInfo shopeeShopInfo;
    private Set<ShopeeOrderItemsInfo> shopeeOrderItemsInfoSet = new HashSet<>();
    private Set<ShopeePurchaseOrderInfo> shopeePurchaseOrderInfoSet = new HashSet<>();
    private ExchangeRate exchangeRate;
    private boolean cashOnDelivery;
    private String trackingNo;
    private Timestamp dateToShip;
    private String recipientName;
    private String recipientPhone;
    private String recipientCountry;
    private String recipientZipCode;
    private String recipientFullAddress;
    private float estimatedShippingFee;
    private float actualShippingCost;
    private float totalAmount;
    private float escrowAmount;
    private String orderStatus;
    private String shippingCarrier;
    private String paymentMethod;
    private String messageToSeller;
    private String note;
    private String noteUpdateTime;
    private Timestamp createTime;
    private Timestamp updateTime;
    private Timestamp payTime;

    private float overseasExpressPrice;

    private boolean delivery;

    private String remarks;

    public ShopeeOrderInfo() {
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String ordrSn) {
        this.orderSn = ordrSn;
    }

    public ShopeeShopInfo getShopeeShopInfo() {
        return shopeeShopInfo;
    }

    public void setShopeeShopInfo(ShopeeShopInfo shopeeShopInfo) {
        this.shopeeShopInfo = shopeeShopInfo;
    }

    public Set<ShopeeOrderItemsInfo> getShopeeOrderItemsInfoSet() {
        return shopeeOrderItemsInfoSet;
    }

    public void setShopeeOrderItemsInfoSet(Set<ShopeeOrderItemsInfo> shopeeOrderItemsInfoSet) {
        this.shopeeOrderItemsInfoSet = shopeeOrderItemsInfoSet;
    }

    public Set<ShopeePurchaseOrderInfo> getShopeePurchaseOrderInfoSet() {
        return shopeePurchaseOrderInfoSet;
    }

    public void setShopeePurchaseOrderInfoSet(Set<ShopeePurchaseOrderInfo> shopeePurchaseOrderInfoSet) {
        this.shopeePurchaseOrderInfoSet = shopeePurchaseOrderInfoSet;
    }

    public ExchangeRate getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(ExchangeRate exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public boolean isCashOnDelivery() {
        return cashOnDelivery;
    }

    public void setCashOnDelivery(boolean cashOnDelivery) {
        this.cashOnDelivery = cashOnDelivery;
    }

    public String getTrackingNo() {
        return trackingNo;
    }

    public void setTrackingNo(String trackingNo) {
        this.trackingNo = trackingNo;
    }

    public Timestamp getDateToShip() {
        return dateToShip;
    }

    public void setDateToShip(Timestamp dateToShip) {
        this.dateToShip = dateToShip;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getRecipientPhone() {
        return recipientPhone;
    }

    public void setRecipientPhone(String recipientPhone) {
        this.recipientPhone = recipientPhone;
    }

    public String getRecipientCountry() {
        return recipientCountry;
    }

    public void setRecipientCountry(String recipientCountry) {
        this.recipientCountry = recipientCountry;
    }

    public String getRecipientZipCode() {
        return recipientZipCode;
    }

    public void setRecipientZipCode(String recipientZipCode) {
        this.recipientZipCode = recipientZipCode;
    }

    public String getRecipientFullAddress() {
        return recipientFullAddress;
    }

    public void setRecipientFullAddress(String recipientFullAddress) {
        this.recipientFullAddress = recipientFullAddress;
    }

    public float getEstimatedShippingFee() {
        return estimatedShippingFee;
    }

    public void setEstimatedShippingFee(float estimatedShippingFee) {
        this.estimatedShippingFee = estimatedShippingFee;
    }

    public float getActualShippingCost() {
        return actualShippingCost;
    }

    public void setActualShippingCost(float actualShippingCost) {
        this.actualShippingCost = actualShippingCost;
    }

    public float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public float getEscrowAmount() {
        return escrowAmount;
    }

    public void setEscrowAmount(float escrowAmount) {
        this.escrowAmount = escrowAmount;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getShippingCarrier() {
        return shippingCarrier;
    }

    public void setShippingCarrier(String shippingCarrier) {
        this.shippingCarrier = shippingCarrier;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getMessageToSeller() {
        return messageToSeller;
    }

    public void setMessageToSeller(String messageToSeller) {
        this.messageToSeller = messageToSeller;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getNoteUpdateTime() {
        return noteUpdateTime;
    }

    public void setNoteUpdateTime(String noteUpdateTime) {
        this.noteUpdateTime = noteUpdateTime;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public Timestamp getPayTime() {
        return payTime;
    }

    public void setPayTime(Timestamp payTime) {
        this.payTime = payTime;
    }

    public float getOverseasExpressPrice() {
        return overseasExpressPrice;
    }

    public void setOverseasExpressPrice(float overseasExpressPrice) {
        this.overseasExpressPrice = overseasExpressPrice;
    }

    public boolean isDelivery() {
        return delivery;
    }

    public void setDelivery(boolean delivery) {
        this.delivery = delivery;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
