package com.lazada.platform.response;


import com.lazada.platform.bean.OrderBean;

/**
 * Created by LCC on 2018/6/2.
 */
public class GetOrderResponse extends BaseLazadaResponse {

    /**
     * data : {"voucher":"0.00","customer_first_name":"First Name","order_number":"300034416","created_at":"2014-10-15 18:36:05 +0800","voucher_code":"3432","gift_option":"0","shipping_fee":"0.00","branch_number":"2222","tax_code":"1234","customer_last_name":"last_name","promised_shipping_times":"2017-03-24 16:09:22","items_count":"1","updated_at":"2014-10-15 18:36:05 +0800","price":"99.00","delivery_info":"1","statuses":"delivered","address_billing":{"country":"Singapore","address3":"address3","address2":"address2","city":"Singapore-Central","address1":"22 leonie hill road, #13-01","phone2":"24***22","last_name":"Last Name","phone":"81***8","customer_email":"hello@sellercenter.net","post_code":"239195","address5":"address5","address4":"address4","first_name":"First Name"},"national_registration_number":"1123","extra_attributes":"{\"TaxInvoiceRequested\":\"true\"}","order_id":"16090","gift_message":"Gift","payment_method":"COD","remarks":"remarks","address_shipping":{"country":"Singapore","address3":"address3","address2":"address2","city":"Singapore-Central","address1":"318 tanglin road, phoenix park, #01-59","phone2":"1******2","last_name":"Last Name","phone":"94236248","customer_email":"hello@sellercenter.net","post_code":"247979","address5":"1******2","address4":"address4","first_name":"First Name"}}
     */

    private OrderBean data;

    public OrderBean getData() {
        return data;
    }

    public void setData(OrderBean data) {
        this.data = data;
    }
}
