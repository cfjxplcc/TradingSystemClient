package com.fjnu.trade.view.modules.summary;

import com.fjnu.common.http.RetrofitManager;
import com.fjnu.common.utils.CalculateUtils;
import com.fjnu.common.utils.DesktopBrowseUtils;
import com.fjnu.trade.http.request.lazada.LazadaDataAnalysisRequest;
import com.fjnu.trade.http.request.lazada.LazadaOrderRequest;
import com.fjnu.trade.http.request.lazada.LazadaShopRequest;
import com.fjnu.trade.http.request.PurchaseOrderInfoRequest;
import com.fjnu.trade.model.ExchangeRate;
import com.fjnu.trade.model.lazada.LazadaOrderInfo;
import com.fjnu.trade.model.lazada.LazadaShopInfo;
import com.fjnu.trade.model.lazada.PurchaseOrderInfo;
import com.fjnu.trade.view.common.calendar.CalendarJDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Method;
import java.net.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by LCC on 2018/7/23.
 */
public class LazadaShopMonthlySummaryJPanel extends JPanel {

    private JComboBox cbLazadaShop;
    private JComboBox cbYear;
    private JComboBox cbMonth;
    private JButton btnGetStatisticalData;
    private JTextField tfTotalOrderNumber;
    private JTextField tfPendingNumber;
    private JTextField tfCanceledNumber;
    private JTextField tfRTSNumber;
    private JTextField tfDeliveredNumber;
    private JTextField tfReturnedNumber;
    private JTextField tfShippedNumber;
    private JTextField tfFailedNumber;
    private JTextField tfTotalIncome;
    private JTextField tfTotalCostOfSales;
    private JTextField tfTotalProfit;
    private JTextField tfTotalProfitRate;
    private JLabel lbTotalCostOfSales;
    private JLabel lbTotalProfit;
    private JTextField tfBeginTime;
    private JTextField tfEndTime;
    private JButton btnSynchronize;
    private JTextField tfExportExcelBeginTime;
    private JTextField tfExportExcelEndTime;
    private JButton btnExportExcel;

    private long beginTime = System.currentTimeMillis();
    private long endTime = System.currentTimeMillis();

    private List<LazadaShopInfo> lazadaShopInfoList = new ArrayList<>();
    private List<LazadaOrderInfo> lazadaOrderInfoList = new ArrayList<>();
    private List<PurchaseOrderInfo> purchaseOrderInfoList = new ArrayList<>();

    public LazadaShopMonthlySummaryJPanel() {
        initView();
        initClickEvent();
        initData();
    }

    public void initView() {
        setLayout(new BorderLayout(0, 0));

        JPanel panel = new JPanel();
        panel.setBorder(new LineBorder(new Color(0, 0, 0)));
        panel.setForeground(Color.LIGHT_GRAY);
        panel.setPreferredSize(new Dimension(1000, 160));
        add(panel, BorderLayout.NORTH);
        panel.setLayout(null);

        JLabel lblNewLabel = new JLabel("店铺：");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setBounds(26, 95, 40, 15);
        panel.add(lblNewLabel);

        cbLazadaShop = new JComboBox();
        cbLazadaShop.setBounds(69, 92, 150, 21);
        panel.add(cbLazadaShop);
        cbLazadaShop.addItem("正在获取店铺信息");

        JLabel label = new JLabel("年份");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBounds(272, 75, 40, 15);
        panel.add(label);

        cbYear = new JComboBox();
        cbYear.setBounds(313, 72, 80, 21);
        panel.add(cbYear);

        JLabel label_1 = new JLabel("月份");
        label_1.setHorizontalAlignment(SwingConstants.CENTER);
        label_1.setBounds(431, 75, 40, 15);
        panel.add(label_1);

        cbMonth = new JComboBox();
        cbMonth.setBounds(481, 72, 80, 21);
        panel.add(cbMonth);

        btnGetStatisticalData = new JButton("获取统计信息");
        btnGetStatisticalData.setBounds(647, 71, 114, 23);
        panel.add(btnGetStatisticalData);

        JSeparator separator = new JSeparator();
        separator.setForeground(Color.LIGHT_GRAY);
        separator.setBounds(0, 55, 1000, 1);
        panel.add(separator);

        JLabel lbllazada = new JLabel("手动同步Lazada平台数据");
        lbllazada.setHorizontalAlignment(SwingConstants.CENTER);
        lbllazada.setFont(new Font("宋体", Font.PLAIN, 15));
        lbllazada.setBounds(38, 15, 203, 30);
        panel.add(lbllazada);

        tfBeginTime = new JTextField(8);
        tfBeginTime.setEditable(false);
        tfBeginTime.setBounds(389, 20, 70, 21);
        panel.add(tfBeginTime);

        JLabel label_6 = new JLabel("起始时间：");
        label_6.setHorizontalAlignment(SwingConstants.CENTER);
        label_6.setBounds(313, 23, 66, 15);
        panel.add(label_6);

        JLabel label_8 = new JLabel("结束时间：");
        label_8.setHorizontalAlignment(SwingConstants.CENTER);
        label_8.setBounds(475, 23, 75, 15);
        panel.add(label_8);

        tfEndTime = new JTextField(8);
        tfEndTime.setEditable(false);
        tfEndTime.setBounds(555, 20, 70, 21);
        panel.add(tfEndTime);

        btnSynchronize = new JButton("同步数据");
        btnSynchronize.setBounds(668, 19, 93, 23);
        panel.add(btnSynchronize);

        JLabel label_11 = new JLabel("起始时间：");
        label_11.setHorizontalAlignment(SwingConstants.CENTER);
        label_11.setBounds(272, 119, 66, 15);
        panel.add(label_11);

        tfExportExcelBeginTime = new JTextField(8);
        tfExportExcelBeginTime.setEditable(false);
        tfExportExcelBeginTime.setBounds(348, 116, 70, 21);
        panel.add(tfExportExcelBeginTime);

        JLabel label_12 = new JLabel("结束时间：");
        label_12.setHorizontalAlignment(SwingConstants.CENTER);
        label_12.setBounds(434, 119, 75, 15);
        panel.add(label_12);

        tfExportExcelEndTime = new JTextField(8);
        tfExportExcelEndTime.setEditable(false);
        tfExportExcelEndTime.setBounds(514, 116, 70, 21);
        panel.add(tfExportExcelEndTime);

        btnExportExcel = new JButton("导出Excel");
        btnExportExcel.setEnabled(false);
        btnExportExcel.setBounds(647, 115, 114, 23);
        panel.add(btnExportExcel);

        JPanel panel_1 = new JPanel();
        add(panel_1, BorderLayout.CENTER);
        panel_1.setLayout(null);

        JLabel label_2 = new JLabel("汇总统计信息");
        label_2.setFont(new Font("宋体", Font.PLAIN, 17));
        label_2.setHorizontalAlignment(SwingConstants.CENTER);
        label_2.setBounds(420, 20, 120, 30);
        panel_1.add(label_2);

        JLabel label_3 = new JLabel("总订单数：");
        label_3.setHorizontalAlignment(SwingConstants.CENTER);
        label_3.setBounds(29, 73, 66, 15);
        panel_1.add(label_3);

        tfTotalOrderNumber = new JTextField();
        tfTotalOrderNumber.setEditable(false);
        tfTotalOrderNumber.setBounds(92, 70, 66, 21);
        panel_1.add(tfTotalOrderNumber);
        tfTotalOrderNumber.setColumns(10);

        JLabel lblPending = new JLabel("Pending数：");
        lblPending.setHorizontalAlignment(SwingConstants.CENTER);
        lblPending.setBounds(200, 73, 75, 15);
        panel_1.add(lblPending);

        JLabel lblCanceled = new JLabel("Canceled数：");
        lblCanceled.setHorizontalAlignment(SwingConstants.CENTER);
        lblCanceled.setBounds(385, 73, 80, 15);
        panel_1.add(lblCanceled);

        JLabel lblRts = new JLabel("RTS数：");
        lblRts.setHorizontalAlignment(SwingConstants.CENTER);
        lblRts.setBounds(588, 73, 58, 15);
        panel_1.add(lblRts);

        JLabel lblDelivered = new JLabel("Delivered数：");
        lblDelivered.setHorizontalAlignment(SwingConstants.CENTER);
        lblDelivered.setBounds(779, 73, 88, 15);
        panel_1.add(lblDelivered);

        tfPendingNumber = new JTextField();
        tfPendingNumber.setEditable(false);
        tfPendingNumber.setColumns(10);
        tfPendingNumber.setBounds(274, 70, 66, 21);
        panel_1.add(tfPendingNumber);

        tfCanceledNumber = new JTextField();
        tfCanceledNumber.setEditable(false);
        tfCanceledNumber.setColumns(10);
        tfCanceledNumber.setBounds(466, 70, 66, 21);
        panel_1.add(tfCanceledNumber);

        tfRTSNumber = new JTextField();
        tfRTSNumber.setEditable(false);
        tfRTSNumber.setColumns(10);
        tfRTSNumber.setBounds(645, 70, 66, 21);
        panel_1.add(tfRTSNumber);

        tfDeliveredNumber = new JTextField();
        tfDeliveredNumber.setEditable(false);
        tfDeliveredNumber.setColumns(10);
        tfDeliveredNumber.setBounds(863, 70, 66, 21);
        panel_1.add(tfDeliveredNumber);

        JLabel lblReturned = new JLabel("Returned数：");
        lblReturned.setHorizontalAlignment(SwingConstants.CENTER);
        lblReturned.setBounds(196, 121, 80, 15);
        panel_1.add(lblReturned);

        tfReturnedNumber = new JTextField();
        tfReturnedNumber.setEditable(false);
        tfReturnedNumber.setColumns(10);
        tfReturnedNumber.setBounds(274, 118, 66, 21);
        panel_1.add(tfReturnedNumber);

        JLabel lblShipped = new JLabel("Shipped数：");
        lblShipped.setHorizontalAlignment(SwingConstants.CENTER);
        lblShipped.setBounds(385, 121, 80, 15);
        panel_1.add(lblShipped);

        tfShippedNumber = new JTextField();
        tfShippedNumber.setEditable(false);
        tfShippedNumber.setColumns(10);
        tfShippedNumber.setBounds(466, 118, 66, 21);
        panel_1.add(tfShippedNumber);

        JLabel lblFailed = new JLabel("Failed数：");
        lblFailed.setHorizontalAlignment(SwingConstants.CENTER);
        lblFailed.setBounds(576, 121, 70, 15);
        panel_1.add(lblFailed);

        tfFailedNumber = new JTextField();
        tfFailedNumber.setEditable(false);
        tfFailedNumber.setColumns(10);
        tfFailedNumber.setBounds(645, 118, 66, 21);
        panel_1.add(tfFailedNumber);

        JLabel label_4 = new JLabel("当月总收入金额：");
        label_4.setHorizontalAlignment(SwingConstants.CENTER);
        label_4.setBounds(39, 207, 111, 15);
        panel_1.add(label_4);

        tfTotalIncome = new JTextField();
        tfTotalIncome.setEditable(false);
        tfTotalIncome.setBounds(160, 204, 88, 21);
        panel_1.add(tfTotalIncome);
        tfTotalIncome.setColumns(10);

        JLabel label_5 = new JLabel("当月销售成本：");
        label_5.setHorizontalAlignment(SwingConstants.CENTER);
        label_5.setBounds(357, 207, 101, 15);
        panel_1.add(label_5);

        tfTotalCostOfSales = new JTextField();
        tfTotalCostOfSales.setEditable(false);
        tfTotalCostOfSales.setColumns(10);
        tfTotalCostOfSales.setBounds(456, 204, 66, 21);
        panel_1.add(tfTotalCostOfSales);

        lbTotalCostOfSales = new JLabel("=  采购成本（0.0）+ 国际运费（0.0）");
        lbTotalCostOfSales.setBounds(532, 207, 300, 15);
        panel_1.add(lbTotalCostOfSales);

        JLabel label_7 = new JLabel("当月利润：");
        label_7.setHorizontalAlignment(SwingConstants.CENTER);
        label_7.setBounds(41, 260, 66, 15);
        panel_1.add(label_7);

        tfTotalProfit = new JTextField();
        tfTotalProfit.setEditable(false);
        tfTotalProfit.setColumns(10);
        tfTotalProfit.setBounds(117, 257, 66, 21);
        panel_1.add(tfTotalProfit);

        lbTotalProfit = new JLabel("=  订单收入（0.00）* 系数（0.85）- 销售成本（0.00）");
        lbTotalProfit.setBounds(196, 260, 370, 15);
        panel_1.add(lbTotalProfit);

        JLabel label_9 = new JLabel("当月利润率：");
        label_9.setHorizontalAlignment(SwingConstants.CENTER);
        label_9.setBounds(642, 260, 80, 15);
        panel_1.add(label_9);

        tfTotalProfitRate = new JTextField();
        tfTotalProfitRate.setEditable(false);
        tfTotalProfitRate.setColumns(10);
        tfTotalProfitRate.setBounds(732, 257, 66, 21);
        panel_1.add(tfTotalProfitRate);

        JLabel label_10 = new JLabel("= 利润 / 销售成本");
        label_10.setBounds(811, 260, 123, 15);
        panel_1.add(label_10);
    }

    public void initData() {
        initDateJComboBox();
        tfBeginTime.setText(getTodayString());
        tfEndTime.setText(getTodayString());
        tfExportExcelBeginTime.setText(getTodayString());
        tfExportExcelEndTime.setText(getTodayString());
        getLazadaShopInfoFromServer();
    }

    public void initClickEvent() {
        btnGetStatisticalData.addActionListener(e -> getLazadaOrderInfoFromServer());
        tfBeginTime.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                CalendarJDialog calendarJDialog = new CalendarJDialog(tfBeginTime, "yyyy-MM-dd");
                calendarJDialog.setVisible(true);
            }
        });
        tfEndTime.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                CalendarJDialog calendarJDialog = new CalendarJDialog(tfEndTime, "yyyy-MM-dd");
                calendarJDialog.setVisible(true);
            }
        });
        tfExportExcelBeginTime.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                CalendarJDialog calendarJDialog = new CalendarJDialog(tfExportExcelBeginTime, "yyyy-MM-dd");
                calendarJDialog.setVisible(true);
            }
        });
        tfExportExcelEndTime.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                CalendarJDialog calendarJDialog = new CalendarJDialog(tfExportExcelEndTime, "yyyy-MM-dd");
                calendarJDialog.setVisible(true);
            }
        });
        btnSynchronize.addActionListener(e -> {
            String beginDate = tfBeginTime.getText().replaceAll("-", "");
            String endDate = tfEndTime.getText().replaceAll("-", "");

            if (Integer.valueOf(beginDate) < 20180501) {
                JOptionPane.showMessageDialog(LazadaShopMonthlySummaryJPanel.this,
                        "起始时间不能在2018年5月份之前",
                        "WARNING_MESSAGE",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (Integer.valueOf(beginDate) > Integer.valueOf(endDate)) {
                JOptionPane.showMessageDialog(LazadaShopMonthlySummaryJPanel.this,
                        "起始时间不能小于结束时间",
                        "WARNING_MESSAGE",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            manualSyncLazadaOrderInfo();
        });
        btnExportExcel.addActionListener(e -> {
            String beginDate = tfExportExcelBeginTime.getText().replaceAll("-", "");
            String endDate = tfExportExcelEndTime.getText().replaceAll("-", "");

            if (Integer.valueOf(beginDate) < 20180501) {
                JOptionPane.showMessageDialog(LazadaShopMonthlySummaryJPanel.this,
                        "起始时间不能在2018年5月份之前",
                        "WARNING_MESSAGE",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (Integer.valueOf(beginDate) > Integer.valueOf(endDate)) {
                JOptionPane.showMessageDialog(LazadaShopMonthlySummaryJPanel.this,
                        "起始时间不能小于结束时间",
                        "WARNING_MESSAGE",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            openGenerateExcelUrl();
        });
    }

    /***
     * 计算汇总信息
     */
    private void calculateSummaryInformation() {
        if (lazadaOrderInfoList.isEmpty()) {
            clearCalculateSummaryInformation();
        }

        tfTotalOrderNumber.setText(String.valueOf(lazadaOrderInfoList.size()));
        int pendingNumber = 0, canceleNumber = 0, rtsNumber = 0, deliveredNumber = 0, returnedNumber = 0, shippedNumber = 0, failedNumber = 0;
        float totalPrice = 0f, totalOverseasExpressPrice = 0f, purchaseTotalPrice = 0f;

        for (LazadaOrderInfo lazadaOrderInfo : lazadaOrderInfoList) {
            String orderStatus = lazadaOrderInfo.getOrderStatus();
            switch (orderStatus) {
                case "pending":
                    pendingNumber++;
                    break;
                case "canceled":
                    canceleNumber++;
                    break;
                case "ready_to_ship":
                    rtsNumber++;
                    break;
                case "delivered":
                    deliveredNumber++;
                    totalPrice += lazadaOrderInfo.getPrice();
                    break;
                case "returned":
                    returnedNumber++;
                    break;
                case "shipped":
                    shippedNumber++;
                    break;
                case "failed":
                    failedNumber++;
                    break;
                default:
                    break;
            }
            totalOverseasExpressPrice += lazadaOrderInfo.getOverseasExpressPrice();
        }

        tfPendingNumber.setText(String.valueOf(pendingNumber));
        tfCanceledNumber.setText(String.valueOf(canceleNumber));
        tfRTSNumber.setText(String.valueOf(rtsNumber));
        tfDeliveredNumber.setText(String.valueOf(deliveredNumber));
        tfReturnedNumber.setText(String.valueOf(returnedNumber));
        tfShippedNumber.setText(String.valueOf(shippedNumber));
        tfFailedNumber.setText(String.valueOf(failedNumber));

        for (PurchaseOrderInfo purchaseOrderInfo : purchaseOrderInfoList) {
            purchaseTotalPrice += purchaseOrderInfo.getTotalPrice();
        }

        ExchangeRate exchangeRate = lazadaOrderInfoList.get(0).getExchangeRate();
        float totalIncome = CalculateUtils.convertPriceToRMB(totalPrice, exchangeRate.getExchangeRate());
        float sellingCost = totalOverseasExpressPrice + purchaseTotalPrice;

        tfTotalIncome.setText(String.format("%.2f", totalIncome));
        tfTotalCostOfSales.setText(String.format("%.2f", sellingCost));
        lbTotalCostOfSales.setText(String.format("=  采购成本（%.2f）+ 国际运费（%.2f）", purchaseTotalPrice, totalOverseasExpressPrice));
        tfTotalProfit.setText(String.format(("%.2f"), (CalculateUtils.multiply(totalIncome, 0.85f) - sellingCost)));
        lbTotalProfit.setText(String.format("=  订单收入（%.2f）* 系数（0.85）- 销售成本（%.2f）", totalIncome, sellingCost));

        float profit = Float.valueOf(tfTotalProfit.getText());
        float costOfSales = Float.valueOf(tfTotalCostOfSales.getText());
        String profitRate = "";
        if (costOfSales != 0f) {
            profitRate = String.format("%d%%", (int) (CalculateUtils.divide(profit, costOfSales) * 100));
        }
        tfTotalProfitRate.setText(profitRate);
    }

    private void clearCalculateSummaryInformation() {
        tfTotalOrderNumber.setText("");
        tfPendingNumber.setText("");
        tfCanceledNumber.setText("");
        tfRTSNumber.setText("");
        tfDeliveredNumber.setText("");
        tfReturnedNumber.setText("");
        tfShippedNumber.setText("");
        tfFailedNumber.setText("");
        tfTotalIncome.setText("");
        tfTotalCostOfSales.setText("");
        lbTotalCostOfSales.setText("=  采购成本（0.0）+ 国际运费（0.0）");
        tfTotalProfit.setText("");
        lbTotalProfit.setText("=  订单收入（0.00）* 系数（0.85）- 销售成本（0.00）");
        tfTotalProfitRate.setText("");
    }

    /**
     * 初始化年、月选项
     */
    private void initDateJComboBox() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
        String date = simpleDateFormat.format(new Date());

        int year = Integer.valueOf(date.split("-")[0]);
        int month = Integer.valueOf(date.split("-")[1]);

        // 年份选择器数据
        for (int i = 2018; i <= year; i++) {
            cbYear.addItem(i);
        }
        cbYear.setSelectedItem(year);

        // 月份选择器数据
        for (int i = 1; i <= 12; i++) {
            cbMonth.addItem(i);
        }
        cbMonth.setSelectedItem(month);
    }

    /**
     * @param isSuccessful
     */
    private void initShopItem(boolean isSuccessful) {
        cbLazadaShop.removeAllItems();
        if (isSuccessful) {
            for (LazadaShopInfo lazadaShopInfo : lazadaShopInfoList) {
                cbLazadaShop.addItem(lazadaShopInfo.getShopName());
            }
        } else {
            cbLazadaShop.addItem("获取失败");
        }
        setAllComponentEnable(isSuccessful);
    }

    private void getLazadaShopInfoFromServer() {
        setAllComponentEnable(false);

        LazadaShopRequest request = RetrofitManager.getInstance().getRetrofit().create(LazadaShopRequest.class);
        Call<List<LazadaShopInfo>> call = request.getAll();
        call.enqueue(new Callback<List<LazadaShopInfo>>() {
            @Override
            public void onResponse(Call<List<LazadaShopInfo>> call, Response<List<LazadaShopInfo>> response) {
                if (response.code() == 200) {
                    lazadaShopInfoList.clear();
                    lazadaShopInfoList.addAll(response.body());
                    initShopItem(true);
                } else {
                    JOptionPane.showMessageDialog(null, "服务器异常,请关闭该界面重试", "WARNING_MESSAGE",
                            JOptionPane.WARNING_MESSAGE);
                    initShopItem(false);
                }
            }

            @Override
            public void onFailure(Call<List<LazadaShopInfo>> call, Throwable throwable) {
                JOptionPane.showMessageDialog(null, "发送请求失败,请关闭该界面重试:" + throwable.toString(), "WARNING_MESSAGE",
                        JOptionPane.WARNING_MESSAGE);
                initShopItem(false);
            }
        });
    }

    private void getPurchaseOrderInfoFromServer() {
        setAllComponentEnable(false);
        String lazadaShopInfoId = lazadaShopInfoList.get(cbLazadaShop.getSelectedIndex()).getId();

        PurchaseOrderInfoRequest request = RetrofitManager.getInstance().getRetrofit()
                .create(PurchaseOrderInfoRequest.class);
        Call<List<PurchaseOrderInfo>> call = request.getByLazadaShopInfoAndCreateTime(lazadaShopInfoId, String.valueOf(beginTime), String.valueOf(endTime));
        call.enqueue(new Callback<List<PurchaseOrderInfo>>() {
            @Override
            public void onResponse(Call<List<PurchaseOrderInfo>> call, Response<List<PurchaseOrderInfo>> response) {
                setAllComponentEnable(true);
                if (response.code() == 200) {
                    purchaseOrderInfoList.clear();
                    purchaseOrderInfoList.addAll(response.body());
                    calculateSummaryInformation();
                } else {
                    JOptionPane.showMessageDialog(null, "服务器异常", "WARNING_MESSAGE", JOptionPane.WARNING_MESSAGE);
                }
            }

            @Override
            public void onFailure(Call<List<PurchaseOrderInfo>> call, Throwable throwable) {
                setAllComponentEnable(true);
                JOptionPane.showMessageDialog(null, "发送请求失败:" + throwable.toString(), "WARNING_MESSAGE",
                        JOptionPane.WARNING_MESSAGE);
                throwable.printStackTrace();
            }
        });
    }

    private void manualSyncLazadaOrderInfo() {
        setAllComponentEnable(false);
        String beginTime = tfBeginTime.getText() + " 00:00:00";
        String endTime = tfEndTime.getText() + " 24:00:00";

        LazadaOrderRequest request = RetrofitManager.getInstance().getNewRetorfit(20, 120, 120)
                .create(LazadaOrderRequest.class);
        Call<String> call = request.manualSyncLazadaOrderInfo(String.valueOf(beginTime), String.valueOf(endTime));
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                setAllComponentEnable(true);
                if (response.code() == 200 && "Call api successful".equals(response.body())) {
                    JOptionPane.showMessageDialog(LazadaShopMonthlySummaryJPanel.this,
                            "同步数据成功",
                            "INFORMATION_MESSAGE",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "服务器异常", "WARNING_MESSAGE", JOptionPane.WARNING_MESSAGE);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                setAllComponentEnable(true);
                if (throwable instanceof SocketTimeoutException) {
                    JOptionPane.showMessageDialog(null, "超出响应时间:" + throwable.toString(), "WARNING_MESSAGE",
                            JOptionPane.WARNING_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "发送请求失败:" + throwable.toString(), "WARNING_MESSAGE",
                            JOptionPane.WARNING_MESSAGE);
                }
                throwable.printStackTrace();
            }
        });
    }

    private void getLazadaOrderInfoFromServer() {
        setAllComponentEnable(false);
        LazadaOrderRequest request = RetrofitManager.getInstance().getRetrofit().create(LazadaOrderRequest.class);
        String lazadaShopInfoId = lazadaShopInfoList.get(cbLazadaShop.getSelectedIndex()).getId();
        String orderStatus = "";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        try {
            int year = (int) cbYear.getSelectedItem();
            int month = (int) cbMonth.getSelectedItem();
            beginTime = simpleDateFormat.parse(year + "-" + month + "-01" + " 00:00:00").getTime();
            System.out.println("beginTime:" + year + "-" + month + "-01" + " 00:00:00");
            if (month == 12) {
                year++;
                month = 1;
            } else {
                month++;
            }
            endTime = simpleDateFormat.parse(year + "-" + month + "-01" + " 00:00:00").getTime();
            System.out.println("endTime:" + year + "-" + month + "-01" + " 00:00:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Call<List<LazadaOrderInfo>> call = request.getAllByLazadaShopInfo(lazadaShopInfoId, orderStatus,
                String.valueOf(beginTime), String.valueOf(endTime));
        call.enqueue(new Callback<List<LazadaOrderInfo>>() {
            @Override
            public void onResponse(Call<List<LazadaOrderInfo>> call, Response<List<LazadaOrderInfo>> response) {
                if (response.code() == 200) {
                    lazadaOrderInfoList.clear();
                    lazadaOrderInfoList.addAll(response.body());
                    if (lazadaOrderInfoList.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "无数据", "INFORMATION_MESSAGE",
                                JOptionPane.INFORMATION_MESSAGE);
                        setAllComponentEnable(true);
                    } else {
                        getPurchaseOrderInfoFromServer();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "服务器异常", "WARNING_MESSAGE", JOptionPane.WARNING_MESSAGE);
                }
            }

            @Override
            public void onFailure(Call<List<LazadaOrderInfo>> call, Throwable throwable) {
                setAllComponentEnable(true);
                throwable.printStackTrace();
                JOptionPane.showMessageDialog(null, "发送请求失败:" + throwable.toString(), "WARNING_MESSAGE",
                        JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    /**
     * 打开生成excel的url
     */
    private void openGenerateExcelUrl() {
        String lazadaShopInfoId = lazadaShopInfoList.get(cbLazadaShop.getSelectedIndex()).getId();
        String beginTime = tfExportExcelBeginTime.getText() + " 00:00:00";
        String endTime = tfExportExcelEndTime.getText() + " 24:00:00";

        try {
            Method method = LazadaDataAnalysisRequest.class.getDeclaredMethod("generateExcel", String.class, String.class, String.class);
            GET get = method.getAnnotation(GET.class);
            //拼接URL
            String exportUrl = RetrofitManager.getInstance().getBaseUrl() + get.value() + "?shopId=" + lazadaShopInfoId
                    + "&beginTime=" + beginTime + "&endTime=" + endTime;
            URL url = new URL(exportUrl);
            URI uri = new URI(url.getProtocol(), url.getAuthority(), url.getPath(), url.getQuery(), null);
            System.out.println("url:" + uri.toString());
            DesktopBrowseUtils.useDesktopBrowseOpenUrl(uri.toString());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "打开生成Excel网页失败:" + e.toString(), "WARNING_MESSAGE",
                    JOptionPane.WARNING_MESSAGE);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void setAllComponentEnable(boolean enable) {
        cbLazadaShop.setEnabled(enable);
        cbYear.setEnabled(enable);
        cbMonth.setEnabled(enable);
        btnGetStatisticalData.setEnabled(enable);
        tfEndTime.setEnabled(enable);
        tfBeginTime.setEnabled(enable);
        btnSynchronize.setEnabled(enable);
        btnExportExcel.setEnabled(enable);
    }

    private String getTodayString() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(new Date());
    }
}
