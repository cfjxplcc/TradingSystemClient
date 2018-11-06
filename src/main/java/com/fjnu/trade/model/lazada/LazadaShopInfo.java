package com.fjnu.trade.model.lazada;

import java.sql.Timestamp;

/**
 * Lazada平台店铺信息表
 * <br>
 * Created by LCC on 2018/3/12.
 */
//@Entity
//@Table(name = "tb_lazada_shop_info")
public class LazadaShopInfo {

    //    @Id
//    @GeneratedValue(generator = "uuid")
//    @GenericGenerator(name = "uuid", strategy = "uuid2")
//    @Column(name = "ID", length = 36)
    private String id;

    //    @Column(name = "EMAIL", length = 50, nullable = false)
    private String email;

    //    @Column(name = "SHOP_NAME", length = 50)
    private String shopName;

    //    @Column(name = "COUNTRY_CODE", length = 50, nullable = false)
    private String countryCode;

    //    @Column(name = "API_URL", length = 100)
    private String apiUrl;

    //    @Column(name = "ACCESS_TOKEN", length = 200)
    private String accessToken;

    //    @Column(name = "EXPIREE_IN")
    private int expireeIn;

    //    @Column(name = "REFRESH_TOKEN", length = 200)
    private String refreshToken;

    //    @Column(name = "REFRESH_EXPIREE_IN")
    private int refreshExpireeIn;

    //    @Column(name = "ACCOUNT_PLATFORM", length = 100)
    private String accountPlatform;

    //    @Column(name = "LAST_TOKEN_UPDATE_TIME")
//    @JsonInclude(JsonInclude.Include.NON_NULL)
    private java.sql.Timestamp lastTokenUpdateTime;

    //    @Column(name = "USER_ID", length = 100)
    private String userId;

    //    @Column(name = "SELLER_ID", length = 100)
    private String sellerId;

    //    @Column(name = "SHORT_CODE", length = 100)
    private String shortCode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public int getExpireeIn() {
        return expireeIn;
    }

    public void setExpireeIn(int expireeIn) {
        this.expireeIn = expireeIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public int getRefreshExpireeIn() {
        return refreshExpireeIn;
    }

    public void setRefreshExpireeIn(int refreshExpireeIn) {
        this.refreshExpireeIn = refreshExpireeIn;
    }

    public String getAccountPlatform() {
        return accountPlatform;
    }

    public void setAccountPlatform(String accountPlatform) {
        this.accountPlatform = accountPlatform;
    }

    public Timestamp getLastTokenUpdateTime() {
        return lastTokenUpdateTime;
    }

    public void setLastTokenUpdateTime(Timestamp lastTokenUpdateTime) {
        this.lastTokenUpdateTime = lastTokenUpdateTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }
}

