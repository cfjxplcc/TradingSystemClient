package com.lazada.platform.bean;

/**
 * Created by LCC on 2018/6/3.
 */
public class PackedByMarketplaceOrderItemBean {

    /**
     * order_item_id : 203019891286539
     * tracking_number : LZDCB00094640894
     * shipment_provider : LGS-FM40
     * package_id : OP06192011999277
     */

    private long order_item_id;
    private String tracking_number;
    private String shipment_provider;
    private String package_id;

    public long getOrder_item_id() {
        return order_item_id;
    }

    public void setOrder_item_id(long order_item_id) {
        this.order_item_id = order_item_id;
    }

    public String getTracking_number() {
        return tracking_number;
    }

    public void setTracking_number(String tracking_number) {
        this.tracking_number = tracking_number;
    }

    public String getShipment_provider() {
        return shipment_provider;
    }

    public void setShipment_provider(String shipment_provider) {
        this.shipment_provider = shipment_provider;
    }

    public String getPackage_id() {
        return package_id;
    }

    public void setPackage_id(String package_id) {
        this.package_id = package_id;
    }
}
