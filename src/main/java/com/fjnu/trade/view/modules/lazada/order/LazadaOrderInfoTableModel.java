package com.fjnu.trade.view.modules.lazada.order;

import com.fjnu.common.utils.CalculateUtils;
import com.fjnu.common.utils.DateUtils;
import com.fjnu.trade.model.lazada.LazadaOrderInfo;
import com.lazada.platform.bean.OrderBean;

import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * Created by LCC on 2018/4/12.
 */
public class LazadaOrderInfoTableModel extends AbstractTableModel {

    private final String[] columnNames = {
            "行数", "订单id", "lazada店铺名", "Lazada平台订单ID",
            "订单状态", "订单生成日期", "订单总收入金额(人民币)", "采购金额(人民币)",
            "海外运费金额", "利润(人民币)", "订单物品信息", "采购单号",
            "采购快递单号", "lazada平台订单备注", "公司内部备注"
    };

    private List<LazadaOrderInfo> data;

    public LazadaOrderInfoTableModel(List<LazadaOrderInfo> data) {
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
        LazadaOrderInfo lazadaOrderInfo = data.get(rowIndex);
        switch (colIndex) {
            case 0:
                return rowIndex + 1;
            case 1:
                return lazadaOrderInfo.getId();
            case 2:
                return lazadaOrderInfo.getLazadaShopInfo().getShopName();
            case 3:
                return lazadaOrderInfo.getLazadaOrderId();
            case 4:
                return orderStatusHint(lazadaOrderInfo.getOrderStatus());
            case 5:
                return DateUtils.dateToStr(lazadaOrderInfo.getCreateTime(), "yyyy-MM-dd HH:mm:ss");
            case 6:
                return CalculateUtils.convertPriceToRMB(lazadaOrderInfo.getPrice(),
                        lazadaOrderInfo.getExchangeRate().getExchangeRate());
            case 7:
                return "";
            case 8:
                return lazadaOrderInfo.getOverseasExpressPrice();
            case 9:
                return "";
            case 10:
                return "";
            case 11:
                return "";
            case 12:
                return "";
            case 13:
                return lazadaOrderInfo.getLazadaOrderRemarks();
            case 14:
                return lazadaOrderInfo.getRemarks();
            default:
                return "";
        }
    }

    private String orderStatusHint(String orderStatus) {
        if (OrderBean.Status.Canceled.getStatus().equals(orderStatus)) {
            return "<html><strong color='red'>" + orderStatus + "</strong>";
        } else {
            return orderStatus;
        }
    }
}
