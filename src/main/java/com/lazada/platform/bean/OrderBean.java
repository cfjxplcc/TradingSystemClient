package com.lazada.platform.bean;

import java.util.List;

/**
 * Created by LCC on 2018/6/2.
 */
public class OrderBean {

    /**
     * voucher_platform : 0
     * voucher : 0
     * order_number : 202774420893279
     * voucher_seller : 0
     * created_at : 2018-05-28 13:17:47 +0800
     * voucher_code :
     * gift_option : false
     * customer_last_name :
     * updated_at : 2018-06-01 21:01:15 +0800
     * promised_shipping_times :
     * price : 30.00
     * national_registration_number :
     * payment_method : MOLPAY_SEVENELEVEN_OTC
     * customer_first_name : L******j
     * shipping_fee : 0
     * items_count : 1
     * delivery_info :
     * statuses : ["shipped"]
     * address_billing : {"country":"Malaysia","address3":"S******r","address2":"","city":"Kajang","address1":"2**************************************g","phone2":"","last_name":"","phone":"60*********20","customer_email":"","post_code":"43000","address5":"4***0","address4":"K****g","first_name":"Logaraaj"}
     * extra_attributes : {"TaxInvoiceRequested":false}
     * order_id : 202774420893279
     * gift_message :
     * remarks :
     * address_shipping : {"country":"Malaysia","address3":"S******r","address2":"","city":"Kajang","address1":"2**************************************g","phone2":"","last_name":"","phone":"60*********20","customer_email":"","post_code":"43000","address5":"4***0","address4":"K****g","first_name":"Logaraaj"}
     */

    private int voucher_platform;
    private int voucher;
    private long order_number;
    private int voucher_seller;
    private String created_at;
    private String voucher_code;
    private boolean gift_option;
    private String customer_last_name;
    private String updated_at;
    private String promised_shipping_times;
    private String price;
    private String national_registration_number;
    private String payment_method;
    private String customer_first_name;
    private int shipping_fee;
    private int items_count;
    private String delivery_info;
    private AddressBillingBean address_billing;
    private String extra_attributes;
    private long order_id;
    private String gift_message;
    private String remarks;
    private AddressShippingBean address_shipping;
    private List<String> statuses;

    public int getVoucher_platform() {
        return voucher_platform;
    }

    public void setVoucher_platform(int voucher_platform) {
        this.voucher_platform = voucher_platform;
    }

    public int getVoucher() {
        return voucher;
    }

    public void setVoucher(int voucher) {
        this.voucher = voucher;
    }

    public long getOrder_number() {
        return order_number;
    }

    public void setOrder_number(long order_number) {
        this.order_number = order_number;
    }

    public int getVoucher_seller() {
        return voucher_seller;
    }

    public void setVoucher_seller(int voucher_seller) {
        this.voucher_seller = voucher_seller;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getVoucher_code() {
        return voucher_code;
    }

    public void setVoucher_code(String voucher_code) {
        this.voucher_code = voucher_code;
    }

    public boolean isGift_option() {
        return gift_option;
    }

    public void setGift_option(boolean gift_option) {
        this.gift_option = gift_option;
    }

    public String getCustomer_last_name() {
        return customer_last_name;
    }

    public void setCustomer_last_name(String customer_last_name) {
        this.customer_last_name = customer_last_name;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getPromised_shipping_times() {
        return promised_shipping_times;
    }

    public void setPromised_shipping_times(String promised_shipping_times) {
        this.promised_shipping_times = promised_shipping_times;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNational_registration_number() {
        return national_registration_number;
    }

    public void setNational_registration_number(String national_registration_number) {
        this.national_registration_number = national_registration_number;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    public String getCustomer_first_name() {
        return customer_first_name;
    }

    public void setCustomer_first_name(String customer_first_name) {
        this.customer_first_name = customer_first_name;
    }

    public int getShipping_fee() {
        return shipping_fee;
    }

    public void setShipping_fee(int shipping_fee) {
        this.shipping_fee = shipping_fee;
    }

    public int getItems_count() {
        return items_count;
    }

    public void setItems_count(int items_count) {
        this.items_count = items_count;
    }

    public String getDelivery_info() {
        return delivery_info;
    }

    public void setDelivery_info(String delivery_info) {
        this.delivery_info = delivery_info;
    }

    public AddressBillingBean getAddress_billing() {
        return address_billing;
    }

    public void setAddress_billing(AddressBillingBean address_billing) {
        this.address_billing = address_billing;
    }

    public String getExtra_attributes() {
        return extra_attributes;
    }

    public void setExtra_attributes(String extra_attributes) {
        this.extra_attributes = extra_attributes;
    }

    public long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(long order_id) {
        this.order_id = order_id;
    }

    public String getGift_message() {
        return gift_message;
    }

    public void setGift_message(String gift_message) {
        this.gift_message = gift_message;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public AddressShippingBean getAddress_shipping() {
        return address_shipping;
    }

    public void setAddress_shipping(AddressShippingBean address_shipping) {
        this.address_shipping = address_shipping;
    }

    public List<String> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<String> statuses) {
        this.statuses = statuses;
    }

    public static class AddressBillingBean {
        /**
         * country : Malaysia
         * address3 : S******r
         * address2 :
         * city : Kajang
         * address1 : 2**************************************g
         * phone2 :
         * last_name :
         * phone : 60*********20
         * customer_email :
         * post_code : 43000
         * address5 : 4***0
         * address4 : K****g
         * first_name : Logaraaj
         */

        private String country;
        private String address3;
        private String address2;
        private String city;
        private String address1;
        private String phone2;
        private String last_name;
        private String phone;
        private String customer_email;
        private String post_code;
        private String address5;
        private String address4;
        private String first_name;

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getAddress3() {
            return address3;
        }

        public void setAddress3(String address3) {
            this.address3 = address3;
        }

        public String getAddress2() {
            return address2;
        }

        public void setAddress2(String address2) {
            this.address2 = address2;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getAddress1() {
            return address1;
        }

        public void setAddress1(String address1) {
            this.address1 = address1;
        }

        public String getPhone2() {
            return phone2;
        }

        public void setPhone2(String phone2) {
            this.phone2 = phone2;
        }

        public String getLast_name() {
            return last_name;
        }

        public void setLast_name(String last_name) {
            this.last_name = last_name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getCustomer_email() {
            return customer_email;
        }

        public void setCustomer_email(String customer_email) {
            this.customer_email = customer_email;
        }

        public String getPost_code() {
            return post_code;
        }

        public void setPost_code(String post_code) {
            this.post_code = post_code;
        }

        public String getAddress5() {
            return address5;
        }

        public void setAddress5(String address5) {
            this.address5 = address5;
        }

        public String getAddress4() {
            return address4;
        }

        public void setAddress4(String address4) {
            this.address4 = address4;
        }

        public String getFirst_name() {
            return first_name;
        }

        public void setFirst_name(String first_name) {
            this.first_name = first_name;
        }
    }

    public static class AddressShippingBean {
        /**
         * country : Malaysia
         * address3 : S******r
         * address2 :
         * city : Kajang
         * address1 : 2**************************************g
         * phone2 :
         * last_name :
         * phone : 60*********20
         * customer_email :
         * post_code : 43000
         * address5 : 4***0
         * address4 : K****g
         * first_name : Logaraaj
         */

        private String country;
        private String address3;
        private String address2;
        private String city;
        private String address1;
        private String phone2;
        private String last_name;
        private String phone;
        private String customer_email;
        private String post_code;
        private String address5;
        private String address4;
        private String first_name;

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getAddress3() {
            return address3;
        }

        public void setAddress3(String address3) {
            this.address3 = address3;
        }

        public String getAddress2() {
            return address2;
        }

        public void setAddress2(String address2) {
            this.address2 = address2;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getAddress1() {
            return address1;
        }

        public void setAddress1(String address1) {
            this.address1 = address1;
        }

        public String getPhone2() {
            return phone2;
        }

        public void setPhone2(String phone2) {
            this.phone2 = phone2;
        }

        public String getLast_name() {
            return last_name;
        }

        public void setLast_name(String last_name) {
            this.last_name = last_name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getCustomer_email() {
            return customer_email;
        }

        public void setCustomer_email(String customer_email) {
            this.customer_email = customer_email;
        }

        public String getPost_code() {
            return post_code;
        }

        public void setPost_code(String post_code) {
            this.post_code = post_code;
        }

        public String getAddress5() {
            return address5;
        }

        public void setAddress5(String address5) {
            this.address5 = address5;
        }

        public String getAddress4() {
            return address4;
        }

        public void setAddress4(String address4) {
            this.address4 = address4;
        }

        public String getFirst_name() {
            return first_name;
        }

        public void setFirst_name(String first_name) {
            this.first_name = first_name;
        }
    }

    public enum Status {
        Pending("pending"),
        Canceled("canceled"),
        RTS("ready_to_ship"),
        Delivered("delivered"),
        Returned("returned"),
        Shipped("shipped"),
        Failed("failed");

        private String status;

        Status(String status) {
            this.status = status;
        }

        public String getStatus() {
            return this.status;
        }
    }
}
