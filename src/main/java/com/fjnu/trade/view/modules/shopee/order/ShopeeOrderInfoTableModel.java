package com.fjnu.trade.view.modules.shopee.order;

import com.fjnu.common.utils.DateUtils;
import com.fjnu.trade.bean.shopee.ShopeeOrderStatus;
import com.fjnu.trade.model.shopee.ShopeeOrderInfo;

import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * Created by LCC on 2018/4/12.
 */
public class ShopeeOrderInfoTableModel extends AbstractTableModel {

    private final String[] columnNames = {
            "行数", "Shopee店铺名", "订单Sn", "订单状态",
            "订单生成日期", "订单出货截止日期", "订单总金额(外币)", "订单进帐金额(外币)",
            "买家留言", "备注(平台)", "Shopee平台快递编号"
    };

    private List<ShopeeOrderInfo> data;

    public ShopeeOrderInfoTableModel(List<ShopeeOrderInfo> data) {
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
        ShopeeOrderInfo shopeeOrderInfo = data.get(rowIndex);
        switch (colIndex) {
            case 0:
                return rowIndex + 1;
            case 1:
                return shopeeOrderInfo.getShopeeShopInfo().getShopName();
            case 2:
                return shopeeOrderInfo.getOrderSn();
            case 3:
                return orderStatusHint(shopeeOrderInfo.getOrderStatus());
            case 4:
                return DateUtils.dateToStr(shopeeOrderInfo.getCreateTime(), "yyyy-MM-dd HH:mm:ss");
            case 5:
                return DateUtils.dateToStr(shopeeOrderInfo.getDateToShip(), "yyyy-MM-dd");
            case 6:
                return String.format("%.2f", shopeeOrderInfo.getTotalAmount());
            case 7:
                return String.format("%.2f", shopeeOrderInfo.getEscrowAmount());
            case 8:
                return shopeeOrderInfo.getMessageToSeller();
            case 9:
                return shopeeOrderInfo.getNote();
            case 10:
                return shopeeOrderInfo.getTrackingNo();
            default:
                return "";
        }
    }

    private String orderStatusHint(String orderStatus) {
        if (ShopeeOrderStatus.Cancelled.getStatus().equals(orderStatus)
                || ShopeeOrderStatus.InCancel.getStatus().equals(orderStatus)) {
            return "<html><strong color='red'>" + orderStatus + "</strong>";
        } else {
            return orderStatus;
        }
    }
}
