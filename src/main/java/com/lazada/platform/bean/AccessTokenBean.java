package com.lazada.platform.bean;


import com.lazada.platform.response.BaseLazadaResponse;

import java.util.List;

/**
 * Created by LCC on 2018/6/1.
 */
public class AccessTokenBean extends BaseLazadaResponse {

    /**
     * access_token : 50000701302rzr181e3703zhTjoSduTHfGhxiMrTDfGLj3eVTol0mssdomdagp
     * country : cb
     * refresh_token : 50001700f02BDE1b427c13nr9f5IxfMUiWvkBDaPekUvfneyHamuWDgzdmWLEr
     * account_platform : seller_center
     * refresh_expires_in : 1209600
     * country_user_info : [{"country":"sg","user_id":"100027341","seller_id":"100015339","short_code":"SG10XF3"},{"country":"th","user_id":"100033870","seller_id":"100023397","short_code":"TH11H2O"},{"country":"ph","user_id":"100020236","seller_id":"100014320","short_code":"PH10XJ3"},{"country":"id","user_id":"100051229","seller_id":"100045560","short_code":"ID12PTP"},{"country":"my","user_id":"100034641","seller_id":"100028626","short_code":"MY11G00"}]
     * expires_in : 604800
     * account : 18020753014@163.com
     * code : 0
     * request_id : 0b86d54915277607120946169
     */

    private String access_token;
    private String country;
    private String refresh_token;
    private String account_platform;
    private int refresh_expires_in;
    private int expires_in;
    private String account;
    private String request_id;
    private List<CountryUserInfoBean> country_user_info;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getAccount_platform() {
        return account_platform;
    }

    public void setAccount_platform(String account_platform) {
        this.account_platform = account_platform;
    }

    public int getRefresh_expires_in() {
        return refresh_expires_in;
    }

    public void setRefresh_expires_in(int refresh_expires_in) {
        this.refresh_expires_in = refresh_expires_in;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public List<CountryUserInfoBean> getCountry_user_info() {
        return country_user_info;
    }

    public void setCountry_user_info(List<CountryUserInfoBean> country_user_info) {
        this.country_user_info = country_user_info;
    }

    public static class CountryUserInfoBean {
        /**
         * country : sg
         * user_id : 100027341
         * seller_id : 100015339
         * short_code : SG10XF3
         */

        private String country;
        private String user_id;
        private String seller_id;
        private String short_code;

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getSeller_id() {
            return seller_id;
        }

        public void setSeller_id(String seller_id) {
            this.seller_id = seller_id;
        }

        public String getShort_code() {
            return short_code;
        }

        public void setShort_code(String short_code) {
            this.short_code = short_code;
        }
    }
}
