package com.fjnu.trade.view.modules.lazada.order;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.fjnu.common.utils.DateUtils;
import com.fjnu.trade.model.lazada.PurchaseOrderInfo;

public class LazadaPurchaseOrderInfoTableModel extends AbstractTableModel {

    private final String[] columnNames = {"id", "关联订单", "关联sku", "采购物品描述",
            "采购金额(含运费)", "采购物品数量", "采购订单快照地址", "商品重量",
            "采购日期", "快递单号"};

    private List<PurchaseOrderInfo> data;

    public LazadaPurchaseOrderInfoTableModel(List<PurchaseOrderInfo> data) {
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
        PurchaseOrderInfo purchaseOrderInfo = data.get(rowIndex);
        switch (colIndex) {
            case 0:
                return purchaseOrderInfo.getId();
            case 1:
                return purchaseOrderInfo.getLazadaOrderItemsInfo().getLazadaOrderItemId();
            case 2:
                return purchaseOrderInfo.getLazadaOrderItemsInfo().getSku();
            case 3:
                return purchaseOrderInfo.getDescription();
            case 4:
                return purchaseOrderInfo.getTotalPrice();
            case 5:
                return purchaseOrderInfo.getItemTotalMunber();
            case 6:
                return purchaseOrderInfo.getOrderUrl();
            case 7:
                return purchaseOrderInfo.getWeight();
            case 8:
                return DateUtils.dateToStr(purchaseOrderInfo.getDate(), "yyyy-MM-dd");
            case 9:
                return purchaseOrderInfo.getOrderExpressNumber();
            default:
                return "";
        }
    }

}
