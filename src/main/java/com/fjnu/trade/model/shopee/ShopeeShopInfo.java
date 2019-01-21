package com.fjnu.trade.model.shopee;

/**
 * Created by luochunchen on 2018/12/12.
 */
public class ShopeeShopInfo {

    private int shopId;
    private String shopName;
    private String countryCode;
    private boolean authorizationFlag;

    public ShopeeShopInfo() {
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public boolean isAuthorizationFlag() {
        return authorizationFlag;
    }

    public void setAuthorizationFlag(boolean authorizationFlag) {
        this.authorizationFlag = authorizationFlag;
    }
}
