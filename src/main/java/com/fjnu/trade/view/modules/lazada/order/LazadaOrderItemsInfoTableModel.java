package com.fjnu.trade.view.modules.lazada.order;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.fjnu.common.utils.CalculateUtils;
import com.fjnu.trade.model.lazada.LazadaOrderItemsInfo;

public class LazadaOrderItemsInfoTableModel extends AbstractTableModel {

    private final String[] columnNames = {
            "id", "Lazada平台订单详细ID", "sku", "产品描述",
            "商品价格", "PaidPrice", "产品快照地址", "备注"};

    private List<LazadaOrderItemsInfo> data;

    public LazadaOrderItemsInfoTableModel(List<LazadaOrderItemsInfo> data) {
        this.data = data;
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col].toString();
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
        LazadaOrderItemsInfo lazadaOrderItemsInfo = data.get(rowIndex);
        switch (colIndex) {
            case 0:
                return lazadaOrderItemsInfo.getId();
            case 1:
                return lazadaOrderItemsInfo.getLazadaOrderItemId();
            case 2:
                return lazadaOrderItemsInfo.getSku();
            case 3:
                return lazadaOrderItemsInfo.getName();
            case 4:
                return CalculateUtils.convertPriceToRMB(lazadaOrderItemsInfo.getItemPrice(),
                        lazadaOrderItemsInfo.getLazadaOrderInfo().getExchangeRate().getExchangeRate());
            case 5:
                return CalculateUtils.convertPriceToRMB(lazadaOrderItemsInfo.getPaidPrice(),
                        lazadaOrderItemsInfo.getLazadaOrderInfo().getExchangeRate().getExchangeRate());
            case 6:
                return lazadaOrderItemsInfo.getOrderItemUrl();
            case 7:
                return lazadaOrderItemsInfo.getRemarks();
            default:
                return "";
        }
    }

}
