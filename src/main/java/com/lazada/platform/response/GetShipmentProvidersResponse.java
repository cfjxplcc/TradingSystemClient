package com.lazada.platform.response;


import com.lazada.platform.bean.ShipmentProviderBean;

import java.util.List;

/**
 * Created by LCC on 2018/6/3.
 */
public class GetShipmentProvidersResponse extends BaseLazadaResponse {

    /**
     * {"data":{"shipment_providers":[{"name":"LGS-FM40","cod":1,"is_default":0,"api_integration":0}]},"code":"0","request_id":"0b8fda7b15280098678954853"}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<ShipmentProviderBean> shipment_providers;

        public List<ShipmentProviderBean> getShipment_providers() {
            return shipment_providers;
        }

        public void setShipment_providers(List<ShipmentProviderBean> shipment_providers) {
            this.shipment_providers = shipment_providers;
        }
    }
}
