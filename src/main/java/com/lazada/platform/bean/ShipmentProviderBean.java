package com.lazada.platform.bean;

/**
 * Created by LCC on 2018/6/3.
 */
public class ShipmentProviderBean {

    /**
     * tracking_code_example : 1200234789012
     * enabled_delivery_options : []
     * name : NinjaVan API
     * cod : 1
     * tracking_code_validation_regex : /^[0-9]{12}[-]{0,1}[0-9]{0,2}$/i
     * is_default : 1
     * tracking_url : https://sofp.lazada.sg
     * api_integration : 1
     */

    private String tracking_code_example;
    private String enabled_delivery_options;
    private String name;
    private String cod;
    private String tracking_code_validation_regex;
    private String is_default;
    private String tracking_url;
    private String api_integration;

    public String getTracking_code_example() {
        return tracking_code_example;
    }

    public void setTracking_code_example(String tracking_code_example) {
        this.tracking_code_example = tracking_code_example;
    }

    public String getEnabled_delivery_options() {
        return enabled_delivery_options;
    }

    public void setEnabled_delivery_options(String enabled_delivery_options) {
        this.enabled_delivery_options = enabled_delivery_options;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public String getTracking_code_validation_regex() {
        return tracking_code_validation_regex;
    }

    public void setTracking_code_validation_regex(String tracking_code_validation_regex) {
        this.tracking_code_validation_regex = tracking_code_validation_regex;
    }

    public String getIs_default() {
        return is_default;
    }

    public void setIs_default(String is_default) {
        this.is_default = is_default;
    }

    public String getTracking_url() {
        return tracking_url;
    }

    public void setTracking_url(String tracking_url) {
        this.tracking_url = tracking_url;
    }

    public String getApi_integration() {
        return api_integration;
    }

    public void setApi_integration(String api_integration) {
        this.api_integration = api_integration;
    }
}
