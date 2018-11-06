package com.lazada.platform.response;


import com.lazada.platform.bean.ReadyToShipOrderItemBean;

import java.util.List;

/**
 * Created by LCC on 2018/6/3.
 */
public class SetStatusToReadyToShipResponse extends BaseLazadaResponse {

    /**
     * data : {"order_items":[{"order_item_id":"123456","purchase_order_id":"456789","purchase_order_number":"ABC-123456"},{"order_item_id":"123456","purchase_order_id":"456789","purchase_order_number":"ABC-123456"}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<ReadyToShipOrderItemBean> order_items;

        public List<ReadyToShipOrderItemBean> getOrder_items() {
            return order_items;
        }

        public void setOrder_items(List<ReadyToShipOrderItemBean> order_items) {
            this.order_items = order_items;
        }
    }
}
