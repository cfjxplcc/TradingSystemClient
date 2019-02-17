package com.fjnu.trade.http.request.shopee;

import com.fjnu.trade.model.shopee.ShopeeShopInfo;
import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

/**
 * Created by luochunchen on 2019/1/18.
 */
public interface ShopeeShopRequest {

    /**
     * 获取所有shopee店铺信息
     *
     * @return
     */
    @GET("shopee/shop/shop_info_all")
    Call<List<ShopeeShopInfo>> getShopeeShopInfoAll();

    /**
     * 获取shopee授权url
     *
     * @return
     */
    @GET("shopee/shop/authorization/getUrl")
    Call<String> getShopeeAuthorizationUrl();

    /**
     * 获取shopee撤销授权url
     *
     * @return
     */
    @GET("shopee/shop/cancel_authorization/getUrl")
    Call<String> getShopeeCancelAuthorizationUrl();
}
