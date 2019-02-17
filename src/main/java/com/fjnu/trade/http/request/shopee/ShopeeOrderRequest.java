package com.fjnu.trade.http.request.shopee;

import com.fjnu.trade.model.shopee.ShopeeOrderInfo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;

/**
 * Created by luochunchen on 2019/1/24.
 */
public interface ShopeeOrderRequest {

    /**
     * 根据订单id查询
     *
     * @param orderSn
     * @return
     */
    @GET("shopee/order/{orderSn}")
    Call<ShopeeOrderInfo> getByOrderSn(@Path("orderSn") String orderSn);

    /**
     * 根据店铺id、订单状态、订单时间查询数据
     *
     * @param shopId
     * @param orderStatus
     * @param beginTime
     * @param endTime
     * @return
     */
    @GET("shopee/order/shop/{shopId}/")
    Call<List<ShopeeOrderInfo>> getAllByShopInfoAndParameters(@Path("shopId") int shopId,
                                                              @Query("orderStatus") String orderStatus,
                                                              @Query("beginTime") String beginTime,
                                                              @Query("endTime") String endTime);

    /**
     * 根据订单状态、订单时间查询数据
     *
     * @param orderStatus
     * @param beginTime
     * @param endTime
     * @return
     */
    @GET("shopee/order/")
    Call<List<ShopeeOrderInfo>> getAllByParameters(@Query("orderStatus") String orderStatus,
                                                   @Query("beginTime") String beginTime,
                                                   @Query("endTime") String endTime);


    /**
     * 更新订单出货状态
     *
     * @param orderSn    订单sn
     * @param isDelivery true-已出货 / false-未出货
     * @return
     */
    @PUT("shopee/order/{orderSn}/update_delivery_status")
    Call<String> updateDeliveryStatus(@Path("orderSn") String orderSn, @Query("IsDelivery") boolean isDelivery);

    /**
     * 更新订单(公司内部)备注
     *
     * @param orderSn 订单sn
     * @param remark
     * @return
     */
    @PUT("shopee/order/{orderSn}/update_remark")
    Call<String> updateRemark(@Path("orderSn") String orderSn, @Query("remark") String remark);

    /**
     * 更新订单(公司内部)备注
     *
     * @param orderSn              订单sn
     * @param overseasExpressPrice
     * @return
     */
    @PUT("shopee/order/{orderSn}/update_overseas_express_price")
    Call<String> updateOverseasExpressPrice(@Path("orderSn") String orderSn, @Query("OverseasExpressPrice") float overseasExpressPrice);
}
