package com.fjnu.trade.model;

import java.math.BigDecimal;

/**
 * 汇率表
 * <br>
 * Created by LCC on 2018/3/12.
 */
//Entity
//Table(name = "tb_exchange_rate")
public class ExchangeRate {

    //Id
    //GeneratedValue(generator = "uuid")
    //GenericGenerator(name = "uuid", strategy = "uuid2")
    //Column(name = "ID", length = 36)
    private String id;

    //Column(name = "COUNTRY_CODE", length = 50, nullable = false)
    private String countryCode;

    //Column(name = "DATE")
    private java.sql.Date date;

    //Column(name = "EXCHANGE_RATE", precision = 16, scale = 8)
    private BigDecimal exchangeRate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public java.sql.Date getDate() {
        return date;
    }

    public void setDate(java.sql.Date date) {
        this.date = date;
    }

    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }
}
