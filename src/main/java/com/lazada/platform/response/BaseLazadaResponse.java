package com.lazada.platform.response;

/**
 * Created by LCC on 2018/6/1.
 */
public class BaseLazadaResponse {

    /**
     * code : MISSING_PARAMETER
     * type : ISV
     * message : missing required parameter: access_token
     * request_id : 0ba2887315172940728551014
     */

    private String code;
    private String type;
    private String message;
    private String request_id;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    /**
     * 判断请求是否成功
     *
     * @return
     */
    public boolean isSuccess() {
        return code.equals("0");
    }
}
