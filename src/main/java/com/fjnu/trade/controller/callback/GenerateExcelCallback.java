package com.fjnu.trade.controller.callback;

/**
 * Created by LCC on 2017/11/2.
 */
public interface GenerateExcelCallback {

    void onStart();

    void onProcessing(int percent, String msg);

    void onError(Exception e);

    void onFinish(String resultStr);

}
