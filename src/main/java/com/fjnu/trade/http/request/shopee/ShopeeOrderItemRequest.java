package com.fjnu.trade.http.request.shopee;

import com.fjnu.trade.model.shopee.ShopeeOrderItemsInfo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.List;

/**
 * Created by luochunchen on 2019/2/15.
 */
public interface ShopeeOrderItemRequest {

    @GET("shopee/order_item/order/{orderSn}/")
    Call<List<ShopeeOrderItemsInfo>> getByShopeeOrderInfo(@Path("orderSn") String orderSn);
}
