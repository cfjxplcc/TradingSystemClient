package com.fjnu.trade.http.request;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by LCC on 2018/4/3.
 */
public interface ClientConnectRequest {

    @GET("clientconnect/")
    Call<ResponseBody> connect();

}
