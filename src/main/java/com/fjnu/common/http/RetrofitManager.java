package com.fjnu.common.http;

import com.fjnu.common.Constants;
import com.fjnu.common.http.factory.EmptyJsonLenientConverterFactory;
import com.fjnu.common.http.factory.NobodyConverterFactory;
import com.fjnu.trade.module.ServerConnectInfoManager;
import com.google.gson.*;
import okhttp3.OkHttpClient;
import org.apache.http.util.TextUtils;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.lang.reflect.Type;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;

/**
 * Created by LCC on 2018/4/3.
 */
public class RetrofitManager {

    private Retrofit retrofit;

    private String baseUrl;

    private RetrofitManager() {
        initServerUrl();
        initRetrofit();
    }

    public static final RetrofitManager getInstance() {
        return InnerHolder.mInstance;
    }

    private void initRetrofit() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(Constants.CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(Constants.READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(Constants.WRITE_TIMEOUT, TimeUnit.SECONDS)
                .build();

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(java.sql.Timestamp.class, new JsonDeserializer<java.sql.Timestamp>() {
                    public java.sql.Timestamp deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                        return new java.sql.Timestamp(json.getAsJsonPrimitive().getAsLong());
                    }
                })
                .registerTypeAdapter(java.sql.Date.class, new JsonDeserializer<java.sql.Date>() {
                    public java.sql.Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                        return new java.sql.Date(json.getAsJsonPrimitive().getAsLong());
                    }
                })
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(NobodyConverterFactory.create())
                .addConverterFactory(new EmptyJsonLenientConverterFactory(GsonConverterFactory.create(gson)))
//                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    private void initServerUrl() {
        ServerConnectInfoManager.ServerConnectInfo serverConnectInfo
                = ServerConnectInfoManager.getInstance().getServerConnectInfo();
        String serverAddress;
        switch (serverConnectInfo.getServerConnectWay()) {
            case WAN_WAY:
                serverAddress = serverConnectInfo.getServerWANAddress();
                break;
            case LAN_WAY:
            default:
                serverAddress = serverConnectInfo.getServerLANAddress();
                break;
        }
        if (TextUtils.isEmpty(serverAddress)) {
            serverAddress = Constants.SERVER_DEFAULT_IP;
        }
        baseUrl = "http://" + serverAddress + "/trading_system_server/";
        System.out.println("baseUrl:" + baseUrl);
    }

    /**
     * 获取自定义Retrofit对象
     *
     * @param connectTimeout 单位：秒
     * @param readTimeout    单位：秒
     * @param writeTimeout   单位：秒
     * @return
     */
    public Retrofit getNewRetorfit(long connectTimeout, long readTimeout, long writeTimeout) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(connectTimeout, TimeUnit.SECONDS)
                .readTimeout(readTimeout, TimeUnit.SECONDS)
                .writeTimeout(writeTimeout, TimeUnit.SECONDS)
                .build();

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(java.sql.Timestamp.class, (JsonDeserializer<Timestamp>) (json, typeOfT, context) -> new Timestamp(json.getAsJsonPrimitive().getAsLong()))
                .registerTypeAdapter(java.sql.Date.class, (JsonDeserializer<Date>) (json, typeOfT, context) -> new Date(json.getAsJsonPrimitive().getAsLong()))
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(NobodyConverterFactory.create())
                .addConverterFactory(new EmptyJsonLenientConverterFactory(GsonConverterFactory.create(gson)))
                .client(okHttpClient)
                .build();
        return retrofit;
    }

    public void resetBaseUrl(String baseUrl) {
        if (TextUtils.isEmpty(baseUrl)) {
            return;
        }
        this.baseUrl = baseUrl;
        System.out.println("current url = " + baseUrl);
        retrofit = retrofit.newBuilder().baseUrl(baseUrl).build();
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    private static final class InnerHolder {
        private static final RetrofitManager mInstance = new RetrofitManager();
    }

}
