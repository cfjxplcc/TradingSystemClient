package com.lazada.platform.response;


import com.lazada.platform.bean.OrderBean;

import java.util.List;

/**
 * Created by LCC on 2018/6/2.
 */
public class GetOrdersResponse extends BaseLazadaResponse {

    private DataBean data;

    public static class DataBean {
        private int count;
        private List<OrderBean> orders;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public List<OrderBean> getOrders() {
            return orders;
        }

        public void setOrders(List<OrderBean> orders) {
            this.orders = orders;
        }
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }
}
