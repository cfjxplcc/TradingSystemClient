package com.fjnu.trade.http.request.lazada;

import com.fjnu.trade.model.lazada.LazadaOrderInfo;
import com.lazada.platform.bean.ShipmentProviderBean;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

/**
 * Created by LCC on 2018/4/9.
 */
public interface LazadaOrderRequest {

    @GET("lazadaorderinfo/{id}")
    Call<LazadaOrderInfo> getById(@Path("id") String id);

    @GET("lazadaorderinfo")
    Call<List<LazadaOrderInfo>> getAll();

    @GET("lazadaorderinfo/")
    Call<List<LazadaOrderInfo>> getAllByLazadaShopInfo(@Query("lazadaShopInfoId") String lazadaShopInfoId,
                                                       @Query("orderStatus") String orderStatus,
                                                       @Query("beginTime") String beginTime,
                                                       @Query("endTime") String endTime);

    @POST("lazadaorderinfo/")
    Call<String> save(@Body LazadaOrderInfo lazadaOrderInfo);

    @PUT("lazadaorderinfo/{id}")
    Call<String> update(@Path("id") String id, @Body LazadaOrderInfo lazadaOrderInfo);

    @GET("lazadaorderinfo/findByPurchaseOrderInfoExpressIsNull")
    Call<List<LazadaOrderInfo>> getAllByPurchaseOrderInfoExpressIsNull();

    @GET("lazadaorderinfo/getByPurchaseOrderInfoThirdPartyOrderId/{id}")
    Call<List<LazadaOrderInfo>> getAllByPurchaseOrderInfoThirdPartyOrderId(@Path("id") String id);

    @GET("lazadaorderinfo/getShipmentProviderByLazadaShopInfo/{id}")
    Call<List<ShipmentProviderBean>> getShipmentProviderByLazadaShopInfo(@Path("id") String id);

    @PUT("lazadaorderinfo/{id}/changeOrderStatusToReadyToShip/")
    Call<String> changeOrderStatusToReadyToShip(@Path("id") String id, @Body String shippingProvider);

    @PUT("lazadaorderinfo/{id}/updateOverseasExpressPrice/")
    Call<String> updateOverseasExpressPrice(@Path("id") String id, @Query("OverseasExpressPrice") float overseasExpressPrice);

    @GET("lazadaorderinfo/getOrderDeliveryStatusIsFalse")
    Call<List<LazadaOrderInfo>> getOrderDeliveryStatusIsFalse();

    @GET("lazadaorderinfo/getByOrderExpressNumber")
    Call<List<LazadaOrderInfo>> getByOrderExpressNumber(@Query("OrderExpressNumber") String orderExpressNumber);

    @PUT("lazadaorderinfo/{id}/updateLazadaOrderInfoIsDelivery")
    Call<String> updateLazadaOrderInfoIsDelivery(@Path("id") String id, @Query("IsDelivery") boolean isDelivery);

    @GET("lazadaorderinfo/getByEmail")
    Call<List<LazadaOrderInfo>> getByEmail(@Query("email") String email,
                                           @Query("orderStatus") String orderStatus,
                                           @Query("beginTime") String beginTime,
                                           @Query("endTime") String endTime);

    /**
     * 手动同步Lazada数据
     *
     * @param beginTime
     * @param endTime
     * @return
     */
    @PUT("lazadaorderinfo/manualSyncLazadaOrderInfo")
    Call<String> manualSyncLazadaOrderInfo(@Query("beginTime") String beginTime, @Query("endTime") String endTime);

    @PUT("lazadaorderinfo/{id}/updateLazadaOrderInfoRemark")
    Call<String> updateLazadaOrderInfoRemark(@Path("id") String id, @Query("remark") String remark);

    @PUT("lazada/order/{id}/sync")
    Call<LazadaOrderInfo> syncLazadaOrderInfo(@Path("id") String id);

    @GET("lazada/order/get_by_order_number")
    Call<List<LazadaOrderInfo>> getByOrderNumber(@Query("order_number") long orderNumber);

    @GET("lazada/order/get_by_order_item_sku_and_delivery_is_false")
    Call<List<LazadaOrderInfo>> getByItemSkuAndDeliveryIsFalse(@Query("sku") String sku);
}
