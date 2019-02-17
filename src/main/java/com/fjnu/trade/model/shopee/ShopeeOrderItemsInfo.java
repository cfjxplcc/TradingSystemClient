package com.fjnu.trade.model.shopee;

/**
 * Created by luochunchen on 2018/12/13.
 */
public class ShopeeOrderItemsInfo {

    private String id;
    private ShopeeOrderInfo shopeeOrderInfo;
    private long itemId;
    private String itemName;
    private String itemSku;
    private long variationId;
    private String variationName;
    private String variationSku;
    private int variationQuantityPurchased;
    private float variationOriginalPrice;
    private float variationDiscountedPrice;
    private boolean wholesale;

    public ShopeeOrderItemsInfo() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ShopeeOrderInfo getShopeeOrderInfo() {
        return shopeeOrderInfo;
    }

    public void setShopeeOrderInfo(ShopeeOrderInfo shopeeOrderInfo) {
        this.shopeeOrderInfo = shopeeOrderInfo;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemSku() {
        return itemSku;
    }

    public void setItemSku(String itemSku) {
        this.itemSku = itemSku;
    }

    public long getVariationId() {
        return variationId;
    }

    public void setVariationId(long variationId) {
        this.variationId = variationId;
    }

    public String getVariationName() {
        return variationName;
    }

    public void setVariationName(String variationName) {
        this.variationName = variationName;
    }

    public String getVariationSku() {
        return variationSku;
    }

    public void setVariationSku(String variationSku) {
        this.variationSku = variationSku;
    }

    public int getVariationQuantityPurchased() {
        return variationQuantityPurchased;
    }

    public void setVariationQuantityPurchased(int variationQuantityPurchased) {
        this.variationQuantityPurchased = variationQuantityPurchased;
    }

    public float getVariationOriginalPrice() {
        return variationOriginalPrice;
    }

    public void setVariationOriginalPrice(float variationOriginalPrice) {
        this.variationOriginalPrice = variationOriginalPrice;
    }

    public float getVariationDiscountedPrice() {
        return variationDiscountedPrice;
    }

    public void setVariationDiscountedPrice(float variationDiscountedPrice) {
        this.variationDiscountedPrice = variationDiscountedPrice;
    }

    public boolean isWholesale() {
        return wholesale;
    }

    public void setWholesale(boolean wholesale) {
        this.wholesale = wholesale;
    }
}
