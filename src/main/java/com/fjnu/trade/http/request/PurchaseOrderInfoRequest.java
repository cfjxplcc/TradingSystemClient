package com.fjnu.trade.http.request;

import com.fjnu.trade.model.lazada.PurchaseOrderInfo;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

/**
 * Created by LCC on 2018/4/22.
 */
public interface PurchaseOrderInfoRequest {

    @GET("purchaseorderinfo/lazadaorderinfo/{id}")
    Call<List<PurchaseOrderInfo>> getByLazadaOrderInfo(@Path("id") String id);

    @POST("purchaseorderinfo/")
    Call<String> save(@Body PurchaseOrderInfo purchaseOrderInfo);

    @PUT("purchaseorderinfo/{id}")
    Call<String> update(@Path("id") String id, @Body PurchaseOrderInfo purchaseOrderInfo);

    @DELETE("purchaseorderinfo/{id}")
    Call<String> delete(@Path("id") String id);

    @GET("purchaseorderinfo/lazadashopinfo/{id}")
    Call<List<PurchaseOrderInfo>> getByLazadaShopInfoAndCreateTime(@Path("id") String lazadaShopInfoId,
                                                                   @Query("beginTime") String beginTime,
                                                                   @Query("endTime") String endTime);

}
