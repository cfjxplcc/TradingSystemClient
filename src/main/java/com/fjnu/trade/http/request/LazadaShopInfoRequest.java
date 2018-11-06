package com.fjnu.trade.http.request;

import com.fjnu.trade.model.lazada.LazadaShopInfo;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

/**
 * Created by LCC on 2018/4/9.
 */
public interface LazadaShopInfoRequest {

    @GET("lazadashopinfo")
    Call<List<LazadaShopInfo>> getAll();

    @POST("lazadashopinfo/")
    Call<String> save(@Body LazadaShopInfo lazadaShopInfo);

    @GET("lazadashopinfo/{id}")
    Call<LazadaShopInfo> getById(@Path("id") String id);

    @PUT("lazadashopinfo/{id}")
    Call<String> update(@Path("id") String id, @Body LazadaShopInfo lazadaShopInfo);

    @DELETE("lazadashopinfo/{id}")
    Call<String> delete(@Path("id") String id);

}
