package com.fjnu.trade.view.modules.shopee.shop;

import com.fjnu.trade.model.shopee.ShopeeShopInfo;

import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * Created by LCC on 2018/4/9.
 */
public class ShopeeShopInfoTableModel extends AbstractTableModel {

    private final String[] columnNames = {
            "ShopId", "店铺名称", "国家码", "是否授权"};

    private List<ShopeeShopInfo> data;

    public ShopeeShopInfoTableModel(List<ShopeeShopInfo> data) {
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
        ShopeeShopInfo shopeeShopInfo = data.get(rowIndex);
        switch (colIndex) {
            case 0:
                return shopeeShopInfo.getShopId();
            case 1:
                return shopeeShopInfo.getShopName();
            case 2:
                return shopeeShopInfo.getCountryCode();
            case 3:
                return shopeeShopInfo.isAuthorizationFlag() ? "已授权" : "<html><strong color='red'>未授权</strong>";
            default:
                return "";
        }
    }
}
