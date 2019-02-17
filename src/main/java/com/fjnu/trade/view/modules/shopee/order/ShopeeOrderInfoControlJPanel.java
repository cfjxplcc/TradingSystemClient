package com.fjnu.trade.view.modules.shopee.order;

import com.fjnu.common.http.RetrofitManager;
import com.fjnu.trade.bean.shopee.ShopeeOrderStatus;
import com.fjnu.trade.http.request.shopee.ShopeeOrderRequest;
import com.fjnu.trade.http.request.shopee.ShopeeShopRequest;
import com.fjnu.trade.model.shopee.ShopeeOrderInfo;
import com.fjnu.trade.model.shopee.ShopeeShopInfo;
import com.fjnu.trade.view.common.calendar.CalendarJDialog;
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

public class ShopeeOrderInfoControlJPanel extends JPanel {

    private static final String ORDER_STATUS_ALL = "ALL";

    private JTextField tfBeginTime;
    private JTextField tfEndTime;
    private JComboBox<String> cbShopeeShop;
    private JComboBox<String> cbOrderStatus;
    private JButton btnDataQuery;
    private JButton btnQueryExpressInNull;
    private JTable table;
    private JTextField tfThirdPartyOrderId;
    private JButton btnQueryDataByThirdPartyOrderId;
    private JButton btnQueryOrderExpressNumber;
    private JButton btnQueryOrderDeliveryStatusIsFalse;
    private JCheckBox checkboxFindByEmail;

    private ShopeeOrderInfoTableModel tableModel;

    private ShopeeOrderInfoDetailJFrame shopeeOrderInfoDetailJFrame;

    private List<ShopeeShopInfo> shopeeShopInfoList = new ArrayList<>();
    private List<ShopeeOrderInfo> shopeeOrderInfoList = new ArrayList<>();
    private JTextField tfOrderExpressNumber;

    /**
     * Create the panel.
     */
    public ShopeeOrderInfoControlJPanel() {
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

        cbShopeeShop = new JComboBox<>();
        cbShopeeShop.setBounds(63, 7, 150, 21);
        panel.add(cbShopeeShop);
        cbShopeeShop.addItem("正在获取店铺信息");

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
        for (ShopeeOrderStatus shopeeOrderStatus : ShopeeOrderStatus.values()) {
            cbOrderStatus.addItem(shopeeOrderStatus.getStatus());
        }
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

        btnQueryExpressInNull = new JButton("查询订单号为空的订单");
        btnQueryExpressInNull.setBounds(20, 82, 165, 23);
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
        btnQueryOrderDeliveryStatusIsFalse.setBounds(205, 82, 133, 23);
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

        JPanel panel_3 = new JPanel(new BorderLayout(0, 0));
        add(panel_3, BorderLayout.CENTER);

        tableModel = new ShopeeOrderInfoTableModel(shopeeOrderInfoList);

        table = new JTable();
        table.setModel(tableModel);

        // 设置列宽
        table.getColumnModel().getColumn(0).setPreferredWidth(45);
        table.getColumnModel().getColumn(1).setPreferredWidth(85);
        table.getColumnModel().getColumn(2).setPreferredWidth(150);
        table.getColumnModel().getColumn(3).setPreferredWidth(110);
        table.getColumnModel().getColumn(4).setPreferredWidth(150);
        table.getColumnModel().getColumn(5).setPreferredWidth(100);
        table.getColumnModel().getColumn(6).setPreferredWidth(100);
        table.getColumnModel().getColumn(7).setPreferredWidth(110);
        table.getColumnModel().getColumn(8).setPreferredWidth(130);
        table.getColumnModel().getColumn(9).setPreferredWidth(100);
        table.getColumnModel().getColumn(10).setPreferredWidth(150);

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
        new Thread(() -> getShopeeShopInfoFromServer()).start();
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
        btnQueryOrderDeliveryStatusIsFalse.addActionListener(e -> {
            getOrderDeliveryStatusIsFalse();
        });
        btnQueryDataByThirdPartyOrderId.addActionListener(e -> {
            if (TextUtils.isEmpty(tfThirdPartyOrderId.getText())) {
                JOptionPane.showMessageDialog(null, "第三方订单id不能为空:", "WARNING_MESSAGE", JOptionPane.WARNING_MESSAGE);
                return;
            }
            getLazadaOrderInfoByPurchaseOrderInfoThirdPartyOrderId(tfThirdPartyOrderId.getText().trim());
        });
        btnQueryExpressInNull.addActionListener(e -> getLazadaOrderInfoByPurchaseOrderInfoExpressIsNull());
        btnDataQuery.addActionListener(e -> {
            if (cbShopeeShop.getSelectedIndex() != 0 && checkboxFindByEmail.isSelected()) {
                getLazadaOrderInfoByEmail();
            } else {
                getShopeeOrderInfoFromServer();
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
                    if (shopeeOrderInfoDetailJFrame != null) {
                        shopeeOrderInfoDetailJFrame.dispose();
                        shopeeOrderInfoDetailJFrame = null;
                    }

                    int rowIndex = table.rowAtPoint(e.getPoint());
                    ShopeeOrderInfo shopeeOrderInfo = shopeeOrderInfoList.get(rowIndex);

                    shopeeOrderInfoDetailJFrame = new ShopeeOrderInfoDetailJFrame(shopeeOrderInfo);
                    shopeeOrderInfoDetailJFrame.setVisible(true);
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
    }

    private void setAllComponentEnable(boolean isEnable) {
        tfBeginTime.setEnabled(isEnable);
        tfEndTime.setEnabled(isEnable);
        cbShopeeShop.setEnabled(isEnable);
        cbOrderStatus.setEnabled(isEnable);
        btnDataQuery.setEnabled(isEnable);
        btnQueryExpressInNull.setEnabled(isEnable);
        tfThirdPartyOrderId.setEnabled(isEnable);
        btnQueryDataByThirdPartyOrderId.setEnabled(isEnable);
        btnQueryOrderDeliveryStatusIsFalse.setEnabled(isEnable);
        btnQueryOrderExpressNumber.setEnabled(isEnable);
    }

    private void getShopeeShopInfoFromServer() {
        setAllComponentEnable(false);
        ShopeeShopRequest request = RetrofitManager.getInstance().getRetrofit().create(ShopeeShopRequest.class);
        Call<List<ShopeeShopInfo>> call = request.getShopeeShopInfoAll();
        call.enqueue(new Callback<List<ShopeeShopInfo>>() {
            @Override
            public void onResponse(Call<List<ShopeeShopInfo>> call, Response<List<ShopeeShopInfo>> response) {
                if (response.code() == 200) {
                    setAllComponentEnable(true);
                    shopeeShopInfoList.clear();
                    shopeeShopInfoList.addAll(response.body());
                    initShopItem(true);
                } else {
                    JOptionPane.showMessageDialog(null, "服务器异常,请关闭该界面重试", "WARNING_MESSAGE",
                            JOptionPane.WARNING_MESSAGE);
                    initShopItem(false);
                }
            }

            @Override
            public void onFailure(Call<List<ShopeeShopInfo>> call, Throwable throwable) {
                JOptionPane.showMessageDialog(null, "发送请求失败,请关闭该界面重试:" + throwable.toString(), "WARNING_MESSAGE",
                        JOptionPane.WARNING_MESSAGE);
                initShopItem(false);
            }
        });
    }

    private void getShopeeOrderInfoFromServer() {
        setAllComponentEnable(false);
        ShopeeOrderRequest request = RetrofitManager.getInstance().getRetrofit().create(ShopeeOrderRequest.class);

        int shopId = -1;
        if (!ORDER_STATUS_ALL.equals(cbShopeeShop.getSelectedItem())) {
            shopId = shopeeShopInfoList.get(cbShopeeShop.getSelectedIndex() - 1).getShopId();
        }

        String orderStatus = "";
        if (!ORDER_STATUS_ALL.equals(cbOrderStatus.getSelectedItem())) {
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

        Call<List<ShopeeOrderInfo>> call;
        if (shopId == -1) {
            call = request.getAllByParameters(orderStatus, String.valueOf(beginTime), String.valueOf(endTime));
        } else {
            call = request.getAllByShopInfoAndParameters(shopId, orderStatus, String.valueOf(beginTime), String.valueOf(endTime));
        }
        call.enqueue(new Callback<List<ShopeeOrderInfo>>() {
            @Override
            public void onResponse(Call<List<ShopeeOrderInfo>> call, Response<List<ShopeeOrderInfo>> response) {
                setAllComponentEnable(true);
                if (response.code() == 200) {
                    shopeeOrderInfoList.clear();
                    shopeeOrderInfoList.addAll(response.body());
                    table.validate();
                    table.updateUI();
                    if (shopeeOrderInfoList.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "无数据", "INFORMATION_MESSAGE",
                                JOptionPane.PLAIN_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "服务器异常", "WARNING_MESSAGE", JOptionPane.WARNING_MESSAGE);
                }
            }

            @Override
            public void onFailure(Call<List<ShopeeOrderInfo>> call, Throwable throwable) {
                setAllComponentEnable(true);
                throwable.printStackTrace();
                JOptionPane.showMessageDialog(null, "发送请求失败:" + throwable.toString(), "WARNING_MESSAGE",
                        JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    private void getLazadaOrderInfoByEmail() {
        // FIXME: 2019/2/15 
        /*setAllComponentEnable(false);
        LazadaOrderRequest request = RetrofitManager.getInstance().getRetrofit().create(LazadaOrderRequest.class);

        String email = shopeeShopInfoList.get(cbShopeeShop.getSelectedIndex() - 1).getEmail();

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
                    shopeeOrderInfoList.clear();
                    shopeeOrderInfoList.addAll(response.body());
                    table.validate();
                    table.updateUI();
                    if (shopeeOrderInfoList.isEmpty()) {
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
        });*/
    }

    private void getLazadaOrderInfoByPurchaseOrderInfoExpressIsNull() {
        // FIXME: 2019/2/15 
      /*  setAllComponentEnable(false);
        LazadaOrderRequest request = RetrofitManager.getInstance().getRetrofit()
                .create(LazadaOrderRequest.class);

        Call<List<LazadaOrderInfo>> call = request.getAllByPurchaseOrderInfoExpressIsNull();
        call.enqueue(new Callback<List<LazadaOrderInfo>>() {
            @Override
            public void onResponse(Call<List<LazadaOrderInfo>> call, Response<List<LazadaOrderInfo>> response) {
                setAllComponentEnable(true);
                if (response.code() == 200) {
                    shopeeOrderInfoList.clear();
                    shopeeOrderInfoList.addAll(response.body());
                    table.validate();
                    table.updateUI();
                    if (shopeeOrderInfoList.isEmpty()) {
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
        });*/
    }

    private void getOrderDeliveryStatusIsFalse() {
        // FIXME: 2019/2/15 
       /* setAllComponentEnable(false);
        LazadaOrderRequest request = RetrofitManager.getInstance().getRetrofit()
                .create(LazadaOrderRequest.class);

        Call<List<LazadaOrderInfo>> call = request.getOrderDeliveryStatusIsFalse();
        call.enqueue(new Callback<List<LazadaOrderInfo>>() {
            @Override
            public void onResponse(Call<List<LazadaOrderInfo>> call, Response<List<LazadaOrderInfo>> response) {
                setAllComponentEnable(true);
                if (response.code() == 200) {
                    shopeeOrderInfoList.clear();
                    shopeeOrderInfoList.addAll(response.body());
                    table.validate();
                    table.updateUI();
                    if (shopeeOrderInfoList.isEmpty()) {
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
        });*/
    }

    private void getLazadaOrderInfoByPurchaseOrderInfoThirdPartyOrderId(String id) {
        // FIXME: 2019/2/15 
       /* setAllComponentEnable(false);
        LazadaOrderRequest request = RetrofitManager.getInstance().getRetrofit()
                .create(LazadaOrderRequest.class);

        Call<List<LazadaOrderInfo>> call = request.getAllByPurchaseOrderInfoThirdPartyOrderId(id);
        call.enqueue(new Callback<List<LazadaOrderInfo>>() {
            @Override
            public void onResponse(Call<List<LazadaOrderInfo>> call, Response<List<LazadaOrderInfo>> response) {
                setAllComponentEnable(true);
                if (response.code() == 200) {
                    shopeeOrderInfoList.clear();
                    shopeeOrderInfoList.addAll(response.body());
                    table.validate();
                    table.updateUI();
                    if (shopeeOrderInfoList.isEmpty()) {
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
        });*/
    }

    private void getByOrderExpressNumber(String orderExpressNumber) {
        // FIXME: 2019/2/15 
        /*setAllComponentEnable(false);
        LazadaOrderRequest request = RetrofitManager.getInstance().getRetrofit()
                .create(LazadaOrderRequest.class);

        Call<List<LazadaOrderInfo>> call = request.getByOrderExpressNumber(orderExpressNumber);
        call.enqueue(new Callback<List<LazadaOrderInfo>>() {
            @Override
            public void onResponse(Call<List<LazadaOrderInfo>> call, Response<List<LazadaOrderInfo>> response) {
                setAllComponentEnable(true);
                if (response.code() == 200) {
                    shopeeOrderInfoList.clear();
                    shopeeOrderInfoList.addAll(response.body());
                    table.validate();
                    table.updateUI();
                    if (shopeeOrderInfoList.isEmpty()) {
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
        });*/
    }

    /**
     * @param isSuccessful
     */
    private void initShopItem(boolean isSuccessful) {
        cbShopeeShop.removeAllItems();
        if (isSuccessful) {
            cbShopeeShop.addItem("ALL");
            for (ShopeeShopInfo shopeeShopInfo : shopeeShopInfoList) {
                cbShopeeShop.addItem(shopeeShopInfo.getShopName());
            }
        } else {
            cbShopeeShop.addItem("获取失败");
        }
    }

    private String getTodayString() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(new Date());
    }
}
