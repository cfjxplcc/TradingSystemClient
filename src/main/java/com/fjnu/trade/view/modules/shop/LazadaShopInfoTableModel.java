package com.fjnu.trade.view.modules.shop;

import com.fjnu.common.utils.DateUtils;
import com.fjnu.trade.model.lazada.LazadaShopInfo;
import org.apache.http.util.TextUtils;

import javax.swing.table.AbstractTableModel;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by LCC on 2018/4/9.
 */
public class LazadaShopInfoTableModel extends AbstractTableModel {

    private final String[] columnNames = {
            "id", "店铺名称", "Email", "国家码",
            "Seller ID", "最后授权时间", "token生效时间(分钟)", "是否需要重新授权"};

    private List<LazadaShopInfo> data;

    public LazadaShopInfoTableModel(List<LazadaShopInfo> data) {
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
        LazadaShopInfo lazadaShopInfo = data.get(rowIndex);
        switch (colIndex) {
            case 0:
                return lazadaShopInfo.getId();
            case 1:
                return lazadaShopInfo.getShopName();
            case 2:
                return lazadaShopInfo.getEmail();
            case 3:
                return lazadaShopInfo.getCountryCode();
            case 4:
                return lazadaShopInfo.getShortCode();
            case 5:
                return DateUtils.dateToStr(lazadaShopInfo.getLastTokenUpdateTime(), "yyyy-MM-dd HH:mm:ss");
            case 6:
                return lazadaShopInfo.getExpireeIn();
            case 7:
                return tokenDeadlineHint(rowIndex);
            default:
                return "";
        }
    }

    private String tokenDeadlineHint(int rowIndex) {
        LazadaShopInfo lazadaShopInfo = data.get(rowIndex);

        if (TextUtils.isEmpty(lazadaShopInfo.getRefreshToken())) {
            return "<html><strong color='red'>未授权</strong>";
        }

        long currentTime = System.currentTimeMillis();
        Calendar now = Calendar.getInstance();
        now.setTime(new Date(currentTime));
        long lastTokenUpdateTime = DateUtils.timeToDate(lazadaShopInfo.getLastTokenUpdateTime()).getTime();
        int exprireIn = lazadaShopInfo.getExpireeIn();
        Calendar tokenUpdateTime = Calendar.getInstance();
        tokenUpdateTime.setTime(new Date(lastTokenUpdateTime));

        if ((now.getTimeInMillis() - tokenUpdateTime.getTimeInMillis()) / 1000 < exprireIn) {
            return "token未过期";
        } else {
            return "<html><strong color='red'>token已过期</strong>";
        }
    }
}
