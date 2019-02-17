package com.fjnu.trade.view.modules.shopee.order;

import com.fjnu.trade.model.shopee.ShopeeOrderItemsInfo;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class ShopeeOrderItemsInfoTableModel extends AbstractTableModel {

    private final String[] columnNames = {
            "uuid", "itemId", "itemName", "itemSku",
            "variationId", "variationName", "variationSku", "购买数量",
            "原价(外币)", "折扣价(外币)"};

    private List<ShopeeOrderItemsInfo> data;

    public ShopeeOrderItemsInfoTableModel(List<ShopeeOrderItemsInfo> data) {
        this.data = data;
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int colIndex) {
        ShopeeOrderItemsInfo shopeeOrderItemsInfo = data.get(rowIndex);
        switch (colIndex) {
            case 0:
                return shopeeOrderItemsInfo.getId();
            case 1:
                return shopeeOrderItemsInfo.getItemId();
            case 2:
                return shopeeOrderItemsInfo.getItemName();
            case 3:
                return shopeeOrderItemsInfo.getItemSku();
            case 4:
                return shopeeOrderItemsInfo.getVariationId();
            case 5:
                return shopeeOrderItemsInfo.getVariationName();
            case 6:
                return shopeeOrderItemsInfo.getVariationSku();
            case 7:
                return shopeeOrderItemsInfo.getVariationQuantityPurchased();
            case 8:
                return shopeeOrderItemsInfo.getVariationOriginalPrice();
            case 9:
                return shopeeOrderItemsInfo.getVariationDiscountedPrice();
            default:
                return "";
        }
    }
}
