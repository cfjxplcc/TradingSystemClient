package com.lazada.platform.response;


import com.lazada.platform.bean.OrderItemBean;

import java.util.List;

/**
 * Created by LCC on 2018/6/3.
 */
public class GetOrderItemsResponse extends BaseLazadaResponse {

    private List<OrderItemBean> data;

    public List<OrderItemBean> getData() {
        return data;
    }

    public void setData(List<OrderItemBean> data) {
        this.data = data;
    }

}
