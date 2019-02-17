package com.fjnu.trade.http.request.shopee;

import com.fjnu.trade.model.shopee.ShopeePurchaseOrderInfo;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

/**
 * Created by luochunchen on 2019/2/14.
 */
public interface ShopeePurchaseRequest {

    @GET("shopee/purchase/order/{orderSn}")
    Call<List<ShopeePurchaseOrderInfo>> getByShopeeOrderInfo(@Path("orderSn") String orderSn);

    @POST("shopee/purchase/")
    Call<String> save(@Body ShopeePurchaseOrderInfo shopeePurchaseOrderInfo);

    @PUT("shopee/purchase/{id}")
    Call<String> update(@Path("id") String id, @Body ShopeePurchaseOrderInfo shopeePurchaseOrderInfo);

    @DELETE("shopee/purchase/{id}")
    Call<String> delete(@Path("id") String id);
}
