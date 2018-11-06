package com.fjnu.trade.http.request;

import com.fjnu.trade.model.PersonInfo;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

/**
 * Created by LCC on 2018/4/5.
 */
public interface PersonInfoRequest {

    @GET("login")
    Call<PersonInfo> login(@Query("loginName") String loginName, @Query("password") String password);

    @GET("personinfo")
    Call<List<PersonInfo>> getAll();

    @POST("personinfo")
    Call<String> save(@Body PersonInfo personInfo);

    @GET("personinfo/{id}")
    Call<PersonInfo> getById(@Path("id") String id);

    @PUT("personinfo/{id}")
    Call<String> update(@Path("id") String id, @Body PersonInfo personInfo);

    @DELETE("personinfo/{id}")
    Call<String> delete(@Path("id") String id);

}
