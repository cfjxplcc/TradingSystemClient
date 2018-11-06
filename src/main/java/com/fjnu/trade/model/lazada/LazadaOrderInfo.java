package com.fjnu.trade.model.lazada;

import com.fjnu.trade.model.ExchangeRate;

import java.sql.Timestamp;

/**
 * Lazada平台订单信息表
 * <br>
 * Created by LCC on 2018/3/12.
 */
//Entity
//Table(name = "tb_lazada_order_info")
public class LazadaOrderInfo {


    //    @Id
//    @GeneratedValue(generator = "uuid")
//    @GenericGenerator(name = "uuid", strategy = "uuid2")
//    @Column(name = "ID", length = 36)
    private String id;

    //    @ManyToOne
//    @JoinColumn(name = "SHOP_ID", nullable = false)
    private LazadaShopInfo lazadaShopInfo;

    //    @ManyToOne
//    @JoinColumn(name = "EXCHANGE_RATE_ID")
//    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ExchangeRate exchangeRate;

    //    @Column(name = "LAZADA_ORDER_ID", nullable = false)
    private Long lazadaOrderId;

    //    @Column(name = "ORDER_NUMBER", nullable = false)
    private Long orderNumber;

    //    @Column(name = "PRICE", nullable = false)
    private Float price;

    //    @Column(name = "CREATED_TIME", nullable = false)
    private java.sql.Timestamp createTime;

    //    @Column(name = "LAST_UPDATE_TIME")
//    @JsonInclude(JsonInclude.Include.NON_NULL)
    private java.sql.Timestamp lastUpdateTime;

    //    @Column(name = "ORDER_STATUS", nullable = false)
    private String orderStatus;

    //    @Column(name = "CUSTOMER_FIRST_NAME")
    private String customerFirstNmae;

    //    @Column(name = "CUSTOMER_LAST_NAME")
    private String customerLastName;

    //    @Column(name = "PAYMENT_METHOD")
    private String paymentMethod;

    //    @Column(name = "LAZADA_ORDER_REMARKS")
    private String lazadaOrderRemarks;

    //    @Column(name = "REMARKS")
    private String remarks;

    //    @Column(name = "ADDRESS_SHIPPING_FIRST_NAME")
    private String addressShippingFirstName;

    //    @Column(name = "ADDRESS_SHIPPING_LAST_NAME")
    private String addressShippingLastName;

    //    @Column(name = "ADDRESS_SHIPPING_PHONE_1")
    private String addressShippingPhone1;

    //    @Column(name = "ADDRESS_SHIPPING_PHONE_2")
    private String addressShippingPhone2;

    //    @Column(name = "ADDRESS_SHIPPING_ADDRESS_1")
    private String addressShippingAddress1;

    //    @Column(name = "ADDRESS_SHIPPING_ADDRESS_2")
    private String addressShippingAddress2;

    //    @Column(name = "ADDRESS_SHIPPING_CUSTOMER_EMAIL")
    private String addressShippingCustomerEmail;

    //    @Column(name = "ADDRESS_SHIPPING_CITY")
    private String addressShippingCity;

    //    @Column(name = "ADDRESS_SHIPPING_WARD")
    private String addressShippingWard;

    //    @Column(name = "ADDRESS_SHIPPING_REGION")
    private String addressShippingRegion;

    //    @Column(name = "ADDRESS_SHIPPING_POST_CODE")
    private String addressShippingPostCode;

    //    @Column(name = "ADDRESS_SHIPPING_COUNTRY")
    private String addressShippingCountry;

    //    @Column(name = "SHIPMENT_PROVIDERS_NAME")
    private String shipmentProvidersName;

    //    @Column(name = "OVERSEAS_EXPRESS_NUMBER")
    private String overseasExpressNumber;

    //    @Column(name = "OVERSEAS_EXPRESS_PRICE")
//    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Float overseasExpressPrice;

    //    @Column(name = "IS_DELIVERY")
    private boolean delivery = false;

    public LazadaOrderInfo() {
        overseasExpressPrice = 0.0f;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LazadaShopInfo getLazadaShopInfo() {
        return lazadaShopInfo;
    }

    public void setLazadaShopInfo(LazadaShopInfo lazadaShopInfo) {
        this.lazadaShopInfo = lazadaShopInfo;
    }

    public ExchangeRate getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(ExchangeRate exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public Long getLazadaOrderId() {
        return lazadaOrderId;
    }

    public void setLazadaOrderId(Long lazadaOrderId) {
        this.lazadaOrderId = lazadaOrderId;
    }

    public Long getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Long orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Timestamp lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getCustomerFirstNmae() {
        return customerFirstNmae;
    }

    public void setCustomerFirstNmae(String customerFirstNmae) {
        this.customerFirstNmae = customerFirstNmae;
    }

    public String getCustomerLastName() {
        return customerLastName;
    }

    public void setCustomerLastName(String customerLastName) {
        this.customerLastName = customerLastName;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getLazadaOrderRemarks() {
        return lazadaOrderRemarks;
    }

    public void setLazadaOrderRemarks(String lazadaOrderRemarks) {
        this.lazadaOrderRemarks = lazadaOrderRemarks;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getAddressShippingFirstName() {
        return addressShippingFirstName;
    }

    public void setAddressShippingFirstName(String addressShippingFirstName) {
        this.addressShippingFirstName = addressShippingFirstName;
    }

    public String getAddressShippingLastName() {
        return addressShippingLastName;
    }

    public void setAddressShippingLastName(String addressShippingLastName) {
        this.addressShippingLastName = addressShippingLastName;
    }

    public String getAddressShippingPhone1() {
        return addressShippingPhone1;
    }

    public void setAddressShippingPhone1(String addressShippingPhone1) {
        this.addressShippingPhone1 = addressShippingPhone1;
    }

    public String getAddressShippingPhone2() {
        return addressShippingPhone2;
    }

    public void setAddressShippingPhone2(String addressShippingPhone2) {
        this.addressShippingPhone2 = addressShippingPhone2;
    }

    public String getAddressShippingAddress1() {
        return addressShippingAddress1;
    }

    public void setAddressShippingAddress1(String addressShippingAddress1) {
        this.addressShippingAddress1 = addressShippingAddress1;
    }

    public String getAddressShippingAddress2() {
        return addressShippingAddress2;
    }

    public void setAddressShippingAddress2(String addressShippingAddress2) {
        this.addressShippingAddress2 = addressShippingAddress2;
    }

    public String getAddressShippingCustomerEmail() {
        return addressShippingCustomerEmail;
    }

    public void setAddressShippingCustomerEmail(String addressShippingCustomerEmail) {
        this.addressShippingCustomerEmail = addressShippingCustomerEmail;
    }

    public String getAddressShippingCity() {
        return addressShippingCity;
    }

    public void setAddressShippingCity(String addressShippingCity) {
        this.addressShippingCity = addressShippingCity;
    }

    public String getAddressShippingWard() {
        return addressShippingWard;
    }

    public void setAddressShippingWard(String addressShippingWard) {
        this.addressShippingWard = addressShippingWard;
    }

    public String getAddressShippingRegion() {
        return addressShippingRegion;
    }

    public void setAddressShippingRegion(String addressShippingRegion) {
        this.addressShippingRegion = addressShippingRegion;
    }

    public String getAddressShippingPostCode() {
        return addressShippingPostCode;
    }

    public void setAddressShippingPostCode(String addressShippingPostCode) {
        this.addressShippingPostCode = addressShippingPostCode;
    }

    public String getAddressShippingCountry() {
        return addressShippingCountry;
    }

    public void setAddressShippingCountry(String addressShippingCountry) {
        this.addressShippingCountry = addressShippingCountry;
    }

    public String getShipmentProvidersName() {
        return shipmentProvidersName;
    }

    public void setShipmentProvidersName(String shipmentProvidersName) {
        this.shipmentProvidersName = shipmentProvidersName;
    }

    public String getOverseasExpressNumber() {
        return overseasExpressNumber;
    }

    public void setOverseasExpressNumber(String overseasExpressNumber) {
        this.overseasExpressNumber = overseasExpressNumber;
    }

    public Float getOverseasExpressPrice() {
        return overseasExpressPrice;
    }

    public void setOverseasExpressPrice(Float overseasExpressPrice) {
        this.overseasExpressPrice = overseasExpressPrice;
    }

    public boolean isDelivery() {
        return delivery;
    }

    public void setDelivery(boolean delivery) {
        this.delivery = delivery;
    }
}
