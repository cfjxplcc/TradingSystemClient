package com.lazada.platform.response;


import com.lazada.platform.bean.PackedByMarketplaceOrderItemBean;

import java.util.List;

/**
 * Created by LCC on 2018/6/3.
 */
public class SetStatusToPackedByMarketplaceResponse extends BaseLazadaResponse {

    /**
     * data : {"order_items":[{"order_item_id":203019891286539,"tracking_number":"LZDCB00094640894","shipment_provider":"LGS-FM40","package_id":"OP06192011999277"}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<PackedByMarketplaceOrderItemBean> order_items;

        public List<PackedByMarketplaceOrderItemBean> getOrder_items() {
            return order_items;
        }

        public void setOrder_items(List<PackedByMarketplaceOrderItemBean> order_items) {
            this.order_items = order_items;
        }
    }
}
