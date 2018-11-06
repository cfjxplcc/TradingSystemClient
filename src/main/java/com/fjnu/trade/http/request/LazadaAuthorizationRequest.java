package com.fjnu.trade.http.request;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by LCC on 2018/6/12.
 */
public interface LazadaAuthorizationRequest {

    @GET("authorization/getUrl")
    Call<String> getAuthorizationUrl();

}
