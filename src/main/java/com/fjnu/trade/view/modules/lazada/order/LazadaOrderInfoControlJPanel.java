package com.fjnu.trade.view.modules.lazada.order;

import com.fjnu.common.http.RetrofitManager;
import com.fjnu.trade.http.request.lazada.LazadaOrderRequest;
import com.fjnu.trade.http.request.lazada.LazadaShopRequest;
import com.fjnu.trade.model.lazada.LazadaOrderInfo;
import com.fjnu.trade.model.lazada.LazadaShopInfo;
import com.fjnu.trade.view.common.calendar.CalendarJDialog;
import com.lazada.platform.bean.OrderBean;
import org.apache.http.util.TextUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LazadaOrderInfoControlJPanel extends JPanel {

    private static final String ORDER_STATUS_ALL = "ALL";

    private JTextField tfBeginTime;
    private JTextField tfEndTime;
    private JComboBox<String> cbLazadaShop;
    private JComboBox<String> cbOrderStatus;
    private JButton btnDataQuery;
    private JButton btnQueryExpressInNull;
    private JTable table;
    private JTextField tfThirdPartyOrderId;
    private JButton btnQueryDataByThirdPartyOrderId;
    private JButton btnQueryOrderExpressNumber;
    private JButton btnQueryOrderDeliveryStatusIsFalse;
    private JCheckBox checkboxFindByEmail;
    private JTextField tfOrderExpressNumber;
    private JTextField tfOrderNumberOrSku;
    private JButton btnSku;
    private JButton btnQueryOrderNumber;

    private LazadaOrderInfoTableModel tableModel;

    private LazadaOrderInfoDetailJFrame lazadaOrderInfoDetailJFrame;

    private List<LazadaShopInfo> lazadaShopInfoList = new ArrayList<>();
    private List<LazadaOrderInfo> lazadaOrderInfoList = new ArrayList<>();

    /**
     * Create the panel.
     */
    public LazadaOrderInfoControlJPanel() {
        initView();
        initData();
        initClickEvent();
    }

    private void initView() {
        setLayout(new BorderLayout(0, 0));

        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(1000, 120));
        add(panel, BorderLayout.NORTH);
        panel.setLayout(null);

        JLabel lblNewLabel = new JLabel("店铺：");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setBounds(20, 10, 40, 15);
        panel.add(lblNewLabel);

        cbLazadaShop = new JComboBox<>();
        cbLazadaShop.setBounds(63, 7, 150, 21);
        panel.add(cbLazadaShop);
        cbLazadaShop.addItem("正在获取店铺信息");

        checkboxFindByEmail = new JCheckBox("账号关联");
        checkboxFindByEmail.setBounds(218, 6, 80, 23);
        panel.add(checkboxFindByEmail);

        JLabel label = new JLabel("订单状态：");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBounds(315, 10, 66, 15);
        panel.add(label);

        cbOrderStatus = new JComboBox<>();
        cbOrderStatus.setBounds(391, 7, 100, 21);
        panel.add(cbOrderStatus);
        cbOrderStatus.addItem(ORDER_STATUS_ALL);
        cbOrderStatus.addItem(OrderBean.Status.Pending.getStatus());
        cbOrderStatus.addItem(OrderBean.Status.Canceled.getStatus());
        cbOrderStatus.addItem(OrderBean.Status.RTS.getStatus());
        cbOrderStatus.addItem(OrderBean.Status.Delivered.getStatus());
        cbOrderStatus.addItem(OrderBean.Status.Returned.getStatus());
        cbOrderStatus.addItem(OrderBean.Status.Shipped.getStatus());
        cbOrderStatus.addItem(OrderBean.Status.Failed.getStatus());
        cbOrderStatus.setSelectedItem(ORDER_STATUS_ALL);

        JLabel label_2 = new JLabel("起始时间：");
        label_2.setHorizontalAlignment(SwingConstants.CENTER);
        label_2.setBounds(515, 10, 66, 15);
        panel.add(label_2);

        tfBeginTime = new JTextField(8);
        tfBeginTime.setBounds(591, 7, 70, 21);
        panel.add(tfBeginTime);
        tfBeginTime.setText(getTodayString());
        tfBeginTime.setEditable(false);

        JLabel label_3 = new JLabel("结束时间：");
        label_3.setHorizontalAlignment(SwingConstants.CENTER);
        label_3.setBounds(680, 10, 75, 15);
        panel.add(label_3);

        tfEndTime = new JTextField(8);
        tfEndTime.setBounds(761, 7, 70, 21);
        panel.add(tfEndTime);
        tfEndTime.setText(getTodayString());
        tfEndTime.setEditable(false);

        btnDataQuery = new JButton("查询");
        btnDataQuery.setBounds(864, 6, 80, 23);
        panel.add(btnDataQuery);

        JSeparator separator_1 = new JSeparator();
        separator_1.setBounds(0, 35, 1000, 1);
        panel.add(separator_1);

        btnQueryExpressInNull = new JButton("查询采购订单快递单号为空的订单");
        btnQueryExpressInNull.setBounds(20, 82, 220, 23);
        panel.add(btnQueryExpressInNull);

        JLabel lblid = new JLabel("第三方订单id");
        lblid.setHorizontalAlignment(SwingConstants.CENTER);
        lblid.setBounds(10, 47, 82, 15);
        panel.add(lblid);

        tfThirdPartyOrderId = new JTextField();
        tfThirdPartyOrderId.setBounds(93, 44, 214, 21);
        panel.add(tfThirdPartyOrderId);
        tfThirdPartyOrderId.setColumns(30);

        btnQueryDataByThirdPartyOrderId = new JButton("根据第三方订单id查询");
        btnQueryDataByThirdPartyOrderId.setBounds(313, 43, 177, 23);
        panel.add(btnQueryDataByThirdPartyOrderId);

        JSeparator separator_2 = new JSeparator();
        separator_2.setBounds(0, 75, 1000, 1);
        panel.add(separator_2);

        JSeparator separator_3 = new JSeparator();
        separator_3.setOrientation(SwingConstants.VERTICAL);
        separator_3.setBounds(500, 41, 2, 30);
        panel.add(separator_3);

        btnQueryOrderDeliveryStatusIsFalse = new JButton("查询未出货订单");
        btnQueryOrderDeliveryStatusIsFalse.setBounds(245, 82, 133, 23);
        panel.add(btnQueryOrderDeliveryStatusIsFalse);

        JLabel label_1 = new JLabel("快递单号");
        label_1.setHorizontalAlignment(SwingConstants.CENTER);
        label_1.setHorizontalTextPosition(SwingConstants.CENTER);
        label_1.setBounds(512, 47, 60, 15);
        panel.add(label_1);

        tfOrderExpressNumber = new JTextField();
        tfOrderExpressNumber.setBounds(580, 44, 177, 21);
        panel.add(tfOrderExpressNumber);
        tfOrderExpressNumber.setColumns(10);

        btnQueryOrderExpressNumber = new JButton("查询快递关联订单");
        btnQueryOrderExpressNumber.setBounds(767, 43, 141, 23);
        panel.add(btnQueryOrderExpressNumber);

        JSeparator separator = new JSeparator();
        separator.setBounds(0, 112, 1000, 1);
        panel.add(separator);

        tfOrderNumberOrSku = new JTextField();
        tfOrderNumberOrSku.setColumns(30);
        tfOrderNumberOrSku.setBounds(407, 82, 160, 21);
        panel.add(tfOrderNumberOrSku);

        btnQueryOrderNumber = new JButton("根据Lazada订单号查询");
        btnQueryOrderNumber.setBounds(575, 79, 177, 29);
        panel.add(btnQueryOrderNumber);

        btnSku = new JButton("根据商品SKU查询未出货订单");
        btnSku.setBounds(765, 79, 200, 29);
        panel.add(btnSku);

        JPanel panel_3 = new JPanel(new BorderLayout(0, 0));
        add(panel_3, BorderLayout.CENTER);

        tableModel = new LazadaOrderInfoTableModel(lazadaOrderInfoList);

        table = new JTable();
        table.setModel(tableModel);

        // 设置列宽
        table.getColumnModel().getColumn(0).setPreferredWidth(45);
        table.getColumnModel().getColumn(1).setPreferredWidth(100);
        table.getColumnModel().getColumn(2).setPreferredWidth(90);
        table.getColumnModel().getColumn(3).setPreferredWidth(130);
        table.getColumnModel().getColumn(4).setPreferredWidth(100);
        table.getColumnModel().getColumn(5).setPreferredWidth(130);
        table.getColumnModel().getColumn(6).setPreferredWidth(145);

        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        // 列的顺序不可拖动
        table.getTableHeader().setReorderingAllowed(false);
        // 只可以选择单行
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        // 只可以选择列
        table.setRowSelectionAllowed(true);
        table.setColumnSelectionAllowed(false);

        JScrollPane scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        panel_3.add(scrollPane, BorderLayout.CENTER);
    }

    private void initData() {
        new Thread(this::getLazadaShopInfoFromServer).start();
    }

    private void initClickEvent() {
        tfEndTime.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                CalendarJDialog calendarJDialog = new CalendarJDialog(tfEndTime, "yyyy-MM-dd");
                calendarJDialog.setVisible(true);
            }
        });
        btnQueryOrderExpressNumber.addActionListener(e -> {
            if (!TextUtils.isEmpty(tfOrderExpressNumber.getText().trim())) {
                getByOrderExpressNumber(tfOrderExpressNumber.getText().trim());
            } else {
                JOptionPane.showMessageDialog(null, "快递单号不能为空:", "WARNING_MESSAGE", JOptionPane.WARNING_MESSAGE);
            }
        });
        btnQueryOrderDeliveryStatusIsFalse.addActionListener(e -> getOrderDeliveryStatusIsFalse());
        btnQueryDataByThirdPartyOrderId.addActionListener(e -> {
            if (TextUtils.isEmpty(tfThirdPartyOrderId.getText())) {
                JOptionPane.showMessageDialog(null, "第三方订单id不能为空:", "WARNING_MESSAGE", JOptionPane.WARNING_MESSAGE);
                return;
            }
            getLazadaOrderInfoByPurchaseOrderInfoThirdPartyOrderId(tfThirdPartyOrderId.getText().trim());
        });
        btnQueryExpressInNull.addActionListener(e -> getLazadaOrderInfoByPurchaseOrderInfoExpressIsNull());
        btnDataQuery.addActionListener(e -> {
            if (cbLazadaShop.getSelectedIndex() != 0 && checkboxFindByEmail.isSelected()) {
                getLazadaOrderInfoByEmail();
            } else {
                getLazadaOrderInfoFromServer();
            }
        });
        tfBeginTime.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                CalendarJDialog calendarJDialog = new CalendarJDialog(tfBeginTime, "yyyy-MM-dd");
                calendarJDialog.setVisible(true);
            }
        });
        // 双击事件
        table.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    if (lazadaOrderInfoDetailJFrame != null) {
                        lazadaOrderInfoDetailJFrame.dispose();
                        lazadaOrderInfoDetailJFrame = null;
                    }

                    int rowIndex = table.rowAtPoint(e.getPoint());
                    LazadaOrderInfo lazadaOrderInfo = lazadaOrderInfoList.get(rowIndex);

                    lazadaOrderInfoDetailJFrame = new LazadaOrderInfoDetailJFrame(lazadaOrderInfo);
                    lazadaOrderInfoDetailJFrame.setVisible(true);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
        btnQueryOrderNumber.addActionListener(e -> {
            if (TextUtils.isEmpty(tfOrderNumberOrSku.getText())) {
                JOptionPane.showMessageDialog(this, "请输入订单Sn", "WARNING_MESSAGE", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (!isNumeric(tfOrderNumberOrSku.getText())) {
                JOptionPane.showMessageDialog(this, "Lazada订单号只能是数字", "WARNING_MESSAGE", JOptionPane.WARNING_MESSAGE);
                return;
            }
            getLazadaOrderInfoByOrderNumber();
        });
        btnSku.addActionListener(e -> {
            if (TextUtils.isEmpty(tfOrderNumberOrSku.getText())) {
                JOptionPane.showMessageDialog(this, "请输入Sku", "WARNING_MESSAGE", JOptionPane.WARNING_MESSAGE);
                return;
            }
            getLazadaOrderInfoByItemSkuAndDeliveryIsTrue();
        });
    }

    private void setAllComponentEnable(boolean isEnable) {
        tfBeginTime.setEnabled(isEnable);
        tfEndTime.setEnabled(isEnable);
        cbLazadaShop.setEnabled(isEnable);
        cbOrderStatus.setEnabled(isEnable);
        btnDataQuery.setEnabled(isEnable);
        btnQueryExpressInNull.setEnabled(isEnable);
        tfThirdPartyOrderId.setEnabled(isEnable);
        btnQueryDataByThirdPartyOrderId.setEnabled(isEnable);
        btnQueryOrderDeliveryStatusIsFalse.setEnabled(isEnable);
        btnQueryOrderExpressNumber.setEnabled(isEnable);
        tfOrderExpressNumber.setEnabled(isEnable);
        tfOrderNumberOrSku.setEnabled(isEnable);
        btnSku.setEnabled(isEnable);
        btnQueryOrderNumber.setEnabled(isEnable);
    }

    private void getLazadaShopInfoFromServer() {
        setAllComponentEnable(false);
        LazadaShopRequest request = RetrofitManager.getInstance().getRetrofit().create(LazadaShopRequest.class);
        Call<List<LazadaShopInfo>> call = request.getAll();
        call.enqueue(new Callback<List<LazadaShopInfo>>() {
            @Override
            public void onResponse(Call<List<LazadaShopInfo>> call, Response<List<LazadaShopInfo>> response) {
                if (response.code() == 200) {
                    setAllComponentEnable(true);
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

    private void getLazadaOrderInfoFromServer() {
        setAllComponentEnable(false);
        LazadaOrderRequest request = RetrofitManager.getInstance().getRetrofit().create(LazadaOrderRequest.class);

        String lazadaShopInfoId = "";
        if (!cbLazadaShop.getSelectedItem().toString().equals(ORDER_STATUS_ALL)) {
            lazadaShopInfoId = lazadaShopInfoList.get(cbLazadaShop.getSelectedIndex() - 1).getId();
        }

        String orderStatus = "";
        if (!cbOrderStatus.getSelectedItem().toString().equals(ORDER_STATUS_ALL)) {
            orderStatus = cbOrderStatus.getSelectedItem().toString();
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        long beginTime = System.currentTimeMillis();
        long endTime = System.currentTimeMillis();
        try {
            beginTime = simpleDateFormat.parse(tfBeginTime.getText() + " 00:00:00").getTime();
            endTime = simpleDateFormat.parse(tfEndTime.getText() + " 24:00:00").getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Call<List<LazadaOrderInfo>> call = request.getAllByLazadaShopInfo(lazadaShopInfoId, orderStatus,
                String.valueOf(beginTime), String.valueOf(endTime));
        call.enqueue(new Callback<List<LazadaOrderInfo>>() {
            @Override
            public void onResponse(Call<List<LazadaOrderInfo>> call, Response<List<LazadaOrderInfo>> response) {
                setAllComponentEnable(true);
                if (response.code() == 200) {
                    lazadaOrderInfoList.clear();
                    lazadaOrderInfoList.addAll(response.body());
                    table.validate();
                    table.updateUI();
                    if (lazadaOrderInfoList.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "无数据", "INFORMATION_MESSAGE",
                                JOptionPane.INFORMATION_MESSAGE);
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

    private void getLazadaOrderInfoByEmail() {
        setAllComponentEnable(false);
        LazadaOrderRequest request = RetrofitManager.getInstance().getRetrofit().create(LazadaOrderRequest.class);

        String email = lazadaShopInfoList.get(cbLazadaShop.getSelectedIndex() - 1).getEmail();

        String orderStatus = "";
        if (!cbOrderStatus.getSelectedItem().toString().equals(ORDER_STATUS_ALL)) {
            orderStatus = cbOrderStatus.getSelectedItem().toString();
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        long beginTime = System.currentTimeMillis();
        long endTime = System.currentTimeMillis();
        try {
            beginTime = simpleDateFormat.parse(tfBeginTime.getText() + " 00:00:00").getTime();
            endTime = simpleDateFormat.parse(tfEndTime.getText() + " 24:00:00").getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Call<List<LazadaOrderInfo>> call = request.getByEmail(email, orderStatus,
                String.valueOf(beginTime), String.valueOf(endTime));
        call.enqueue(new Callback<List<LazadaOrderInfo>>() {
            @Override
            public void onResponse(Call<List<LazadaOrderInfo>> call, Response<List<LazadaOrderInfo>> response) {
                setAllComponentEnable(true);
                if (response.code() == 200) {
                    lazadaOrderInfoList.clear();
                    lazadaOrderInfoList.addAll(response.body());
                    table.validate();
                    table.updateUI();
                    if (lazadaOrderInfoList.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "无数据", "INFORMATION_MESSAGE",
                                JOptionPane.INFORMATION_MESSAGE);
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

    private void getLazadaOrderInfoByPurchaseOrderInfoExpressIsNull() {
        setAllComponentEnable(false);
        LazadaOrderRequest request = RetrofitManager.getInstance().getRetrofit()
                .create(LazadaOrderRequest.class);

        Call<List<LazadaOrderInfo>> call = request.getAllByPurchaseOrderInfoExpressIsNull();
        call.enqueue(new Callback<List<LazadaOrderInfo>>() {
            @Override
            public void onResponse(Call<List<LazadaOrderInfo>> call, Response<List<LazadaOrderInfo>> response) {
                setAllComponentEnable(true);
                if (response.code() == 200) {
                    lazadaOrderInfoList.clear();
                    lazadaOrderInfoList.addAll(response.body());
                    table.validate();
                    table.updateUI();
                    if (lazadaOrderInfoList.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "无数据", "INFORMATION_MESSAGE",
                                JOptionPane.INFORMATION_MESSAGE);
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

    private void getOrderDeliveryStatusIsFalse() {
        setAllComponentEnable(false);
        LazadaOrderRequest request = RetrofitManager.getInstance().getRetrofit()
                .create(LazadaOrderRequest.class);

        Call<List<LazadaOrderInfo>> call = request.getOrderDeliveryStatusIsFalse();
        call.enqueue(new Callback<List<LazadaOrderInfo>>() {
            @Override
            public void onResponse(Call<List<LazadaOrderInfo>> call, Response<List<LazadaOrderInfo>> response) {
                setAllComponentEnable(true);
                if (response.code() == 200) {
                    lazadaOrderInfoList.clear();
                    lazadaOrderInfoList.addAll(response.body());
                    table.validate();
                    table.updateUI();
                    if (lazadaOrderInfoList.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "无数据", "INFORMATION_MESSAGE",
                                JOptionPane.INFORMATION_MESSAGE);
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

    private void getLazadaOrderInfoByPurchaseOrderInfoThirdPartyOrderId(String id) {
        setAllComponentEnable(false);
        LazadaOrderRequest request = RetrofitManager.getInstance().getRetrofit()
                .create(LazadaOrderRequest.class);

        Call<List<LazadaOrderInfo>> call = request.getAllByPurchaseOrderInfoThirdPartyOrderId(id);
        call.enqueue(new Callback<List<LazadaOrderInfo>>() {
            @Override
            public void onResponse(Call<List<LazadaOrderInfo>> call, Response<List<LazadaOrderInfo>> response) {
                setAllComponentEnable(true);
                if (response.code() == 200) {
                    lazadaOrderInfoList.clear();
                    lazadaOrderInfoList.addAll(response.body());
                    table.validate();
                    table.updateUI();
                    if (lazadaOrderInfoList.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "无数据", "INFORMATION_MESSAGE",
                                JOptionPane.INFORMATION_MESSAGE);
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

    private void getByOrderExpressNumber(String orderExpressNumber) {
        setAllComponentEnable(false);
        LazadaOrderRequest request = RetrofitManager.getInstance().getRetrofit()
                .create(LazadaOrderRequest.class);

        Call<List<LazadaOrderInfo>> call = request.getByOrderExpressNumber(orderExpressNumber);
        call.enqueue(new Callback<List<LazadaOrderInfo>>() {
            @Override
            public void onResponse(Call<List<LazadaOrderInfo>> call, Response<List<LazadaOrderInfo>> response) {
                setAllComponentEnable(true);
                if (response.code() == 200) {
                    lazadaOrderInfoList.clear();
                    lazadaOrderInfoList.addAll(response.body());
                    table.validate();
                    table.updateUI();
                    if (lazadaOrderInfoList.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "无数据", "INFORMATION_MESSAGE",
                                JOptionPane.INFORMATION_MESSAGE);
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

    private void getLazadaOrderInfoByOrderNumber() {
        setAllComponentEnable(false);
        LazadaOrderRequest request = RetrofitManager.getInstance().getRetrofit().create(LazadaOrderRequest.class);

        Call<List<LazadaOrderInfo>> call = request.getByOrderNumber(Long.parseLong(tfOrderNumberOrSku.getText()));
        call.enqueue(new Callback<List<LazadaOrderInfo>>() {
            @Override
            public void onResponse(Call<List<LazadaOrderInfo>> call, Response<List<LazadaOrderInfo>> response) {
                setAllComponentEnable(true);
                if (response.code() == 200) {
                    lazadaOrderInfoList.clear();
                    lazadaOrderInfoList.addAll(response.body());
                    table.validate();
                    table.updateUI();
                    if (lazadaOrderInfoList.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "无数据", "INFORMATION_MESSAGE",
                                JOptionPane.INFORMATION_MESSAGE);
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

    private void getLazadaOrderInfoByItemSkuAndDeliveryIsTrue() {
        setAllComponentEnable(false);
        LazadaOrderRequest request = RetrofitManager.getInstance().getRetrofit().create(LazadaOrderRequest.class);

        Call<List<LazadaOrderInfo>> call = request.getByItemSkuAndDeliveryIsFalse(tfOrderNumberOrSku.getText());
        call.enqueue(new Callback<List<LazadaOrderInfo>>() {
            @Override
            public void onResponse(Call<List<LazadaOrderInfo>> call, Response<List<LazadaOrderInfo>> response) {
                setAllComponentEnable(true);
                if (response.code() == 200) {
                    lazadaOrderInfoList.clear();
                    lazadaOrderInfoList.addAll(response.body());
                    table.validate();
                    table.updateUI();
                    if (lazadaOrderInfoList.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "无数据", "INFORMATION_MESSAGE",
                                JOptionPane.INFORMATION_MESSAGE);
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
     * @param isSuccessful
     */
    private void initShopItem(boolean isSuccessful) {
        cbLazadaShop.removeAllItems();
        if (isSuccessful) {
            cbLazadaShop.addItem("ALL");
            for (LazadaShopInfo lazadaShopInfo : lazadaShopInfoList) {
                cbLazadaShop.addItem(lazadaShopInfo.getShopName());
            }
        } else {
            cbLazadaShop.addItem("获取失败");
        }
    }

    private String getTodayString() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(new Date());
    }

    /**
     * 利用正则表达式判断字符串是否是数字
     *
     * @param str
     * @return
     */
    private boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        return isNum.matches();
    }
}
