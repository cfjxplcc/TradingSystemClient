package com.fjnu.trade.http.request;

import com.fjnu.trade.model.lazada.LazadaOrderItemsInfo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.List;

/**
 * Created by LCC on 2018/4/19.
 */
public interface LazadaOrderItemsInfoRequest {

    @GET("lazadaorderitemsinfo/lazadaorderinfo/{id}")
    Call<List<LazadaOrderItemsInfo>> getByLazadaOrderInfo(@Path("id") String id);

}
