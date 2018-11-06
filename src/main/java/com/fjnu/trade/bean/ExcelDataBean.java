package com.fjnu.trade.bean;

import com.lazada.platform.bean.OrderBean;
import com.lazada.platform.bean.OrderItemBean;

import java.util.Date;
import java.util.List;

/**
 * Created by LCC on 2017/11/3.
 */
public class ExcelDataBean {

/*    public final static String[] EXCEL_LABEL_TITLES = {"订单日期"
            , "订单号","Status", "Price", "CustomerFirstName", "CustomerLastName", "Remarks", "AddressBilling"
            , "FirstName", "LastName", "Phone", "Phone2", "Address1", "Address2", "Address3", "Address4", "Address5"
            , "City", "Ward", "Region", "PostCode", "Country", "OrderItem", "OrderItemId", "Name", "Sku", "ItemPrice"
            , "PaidPrice", "Currency", "WalletCredits", "TaxAmount", "ShippingAmount", "ShippingServiceCost"};*/

    public final static String[] EXCEL_LABEL_TITLES = {"订单日期"
            , "订单号", "Status", "Price(外币)", "汇率(-1表示获取汇率失败)", "汇率日期", "订单人民币（根据汇率计算）", "Currency", "Name", "Sku", "采购成本", "采购单号", "快递单号"
            , "产品重量（g）", "发往深圳国内快递单号", "发货日期", "国内运费", "国际运费", "交易是否成功", "利润"};

    private String createAt;
    private Long orderNumber;
    private Float price;
    private String customerFirstName;
    private String customerLastName;
    private String remarks;
    private OrderBean.AddressBillingBean addressBilling;
    private String[] statuses;
    private String promisedShippingTime;

    private List<OrderItemBean> orderItems;

    public ExcelDataBean() {
    }

    public ExcelDataBean(OrderBean order, List<OrderItemBean> orderItems) {
        createAt = order.getCreated_at();
        orderNumber = order.getOrder_number();
        price = Float.valueOf(order.getPrice().replace(",", ""));
        customerFirstName = order.getCustomer_first_name();
        customerLastName = order.getCustomer_last_name();
        remarks = order.getRemarks();
        addressBilling = order.getAddress_billing();
        statuses = order.getStatuses().toArray(new String[]{});
        promisedShippingTime = order.getPromised_shipping_times();
        this.orderItems = orderItems;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
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

    public String getCustomerFirstName() {
        return customerFirstName;
    }

    public void setCustomerFirstName(String customerFirstName) {
        this.customerFirstName = customerFirstName;
    }

    public String getCustomerLastName() {
        return customerLastName;
    }

    public void setCustomerLastName(String customerLastName) {
        this.customerLastName = customerLastName;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public OrderBean.AddressBillingBean getAddressBilling() {
        return addressBilling;
    }

    public void setAddressBilling(OrderBean.AddressBillingBean addressBilling) {
        this.addressBilling = addressBilling;
    }

    public String[] getStatuses() {
        return statuses;
    }

    public void setStatuses(String[] statuses) {
        this.statuses = statuses;
    }

    public String getPromisedShippingTime() {
        return promisedShippingTime;
    }

    public void setPromisedShippingTime(String promisedShippingTime) {
        this.promisedShippingTime = promisedShippingTime;
    }

    public List<OrderItemBean> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemBean> orderItems) {
        this.orderItems = orderItems;
    }
}
