package com.fjnu.trade.view.modules.shopee.order;

import com.fjnu.common.http.RetrofitManager;
import com.fjnu.common.utils.CalculateUtils;
import com.fjnu.common.utils.DateUtils;
import com.fjnu.trade.http.request.shopee.ShopeeOrderItemRequest;
import com.fjnu.trade.http.request.shopee.ShopeeOrderRequest;
import com.fjnu.trade.http.request.shopee.ShopeePurchaseRequest;
import com.fjnu.trade.model.ExchangeRate;
import com.fjnu.trade.model.shopee.ShopeeOrderInfo;
import com.fjnu.trade.model.shopee.ShopeeOrderItemsInfo;
import com.fjnu.trade.model.shopee.ShopeePurchaseOrderInfo;
import com.fjnu.trade.view.ViewType;
import com.sun.istack.internal.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;

public class ShopeeOrderInfoDetailJFrame extends JFrame {

    private JPanel contentPane;
    private JTextField tfShopName;
    private JTextField tfOrderSn;
    private JTextField tfOrderStatus;
    private JTextField tfOrderCreateTime;
    private JTextField tfOrderTotalPrice;
    private JTextField tfPurchaseTotalPrice;
    private JTextField tfShopId;
    private JTextField tfDataToShip;
    private JTextField tfEscrowAmount;
    private JTextField tfMessageToSeller;
    private JTextField tfShopeeOrderNote;
    private JTextField tfRemark;
    private JTextField tfRecipientName;
    private JTextField tfRecipientPhone;
    private JTextField tfRecipientCountry;
    private JTextField tfRecipientZipCode;
    private JTextField tfRecipientFullAddress;
    private JTextField tfOverseasExpress;
    private JTextField tfOverseasExpressPrice;
    private JTextField tfCalculationCoefficient;
    private JTextField tfCostOfSales;
    private JTextField tfProfit;
    private JTextField tfProfitRate;
    private JComboBox<String> cbExchange;
    private JLabel lbCostOfSales;
    private JLabel lbProfit;
    private JCheckBox cbIsDelivery;

    private JButton btnRefreshOrderInfo;
    private JButton btnEditOrderInfoData;
    private JButton btnRefreshOrderItemInfo;
    private JButton btnRefreshPurchaseInfo;
    private JButton btnAddPurchaseInfo;
    private JButton btnEditOverseasExpressPrice;
    private JButton btnRecalculate;
    private JButton btnUpdateOrderDeliveryStatus;
    private JButton btnSyncOrderData;

    private JTable orderItemsInfoTable;
    private ShopeeOrderItemsInfoTableModel shopeeOrderItemsInfoTableModel;
    private List<ShopeeOrderItemsInfo> shopeeOrderItemsInfoList = new ArrayList<>();

    private JTable purchaseInfoTable;
    private ShopeePurchaseOrderInfoTableModel shopeePurchaseOrderInfoTableModel;
    private List<ShopeePurchaseOrderInfo> shopeePurchaseOrderInfoList = new ArrayList<>();
    private ShopeePurchaseOrderInfoDetailJFrame shopeePurchaseOrderInfoDetailJFrame;
    private WindowListener purchaseOrderInfoDetailJFrameListener = new WindowListener() {
        @Override
        public void windowOpened(WindowEvent e) {
        }

        @Override
        public void windowClosing(WindowEvent e) {
        }

        @Override
        public void windowClosed(WindowEvent e) {
            getPurchaseOrderInfoFromServer();
        }

        @Override
        public void windowIconified(WindowEvent e) {
        }

        @Override
        public void windowDeiconified(WindowEvent e) {
        }

        @Override
        public void windowActivated(WindowEvent e) {
        }

        @Override
        public void windowDeactivated(WindowEvent e) {
        }
    };

    private ShopeeOrderInfo shopeeOrderInfo;

    public ShopeeOrderInfoDetailJFrame(@NotNull ShopeeOrderInfo shopeeOrderInfo) {
        if (shopeeOrderInfo == null) {
            throw new NullPointerException("ShopeeOrderInfo can not be null!!!");
        }
        this.shopeeOrderInfo = shopeeOrderInfo;
        initView();
        initClickEvent();
        initData();
    }

    private void initView() {
        setTitle("订单详细界面");
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setSize(1000, 655);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // 得到屏幕的尺寸
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        setLocation((screenWidth - this.getWidth()) / 2, (screenHeight - this.getHeight()) / 2);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        contentPane.add(panel, BorderLayout.NORTH);

        btnRefreshOrderInfo = new JButton("刷新数据");
        panel.add(btnRefreshOrderInfo);

        btnEditOrderInfoData = new JButton("编辑数据");
        panel.add(btnEditOrderInfoData);

        btnSyncOrderData = new JButton("同步数据");
        panel.add(btnSyncOrderData);

        JPanel panel_1 = new JPanel();
        contentPane.add(panel_1, BorderLayout.CENTER);
        panel_1.setLayout(null);

        JPanel panel_2 = new JPanel();
        panel_2.setBorder(new LineBorder(new Color(0, 0, 0)));
        panel_2.setBounds(0, 0, 968, 285);
        panel_1.add(panel_2);
        panel_2.setLayout(null);

        JLabel lblid = new JLabel("店铺id");
        lblid.setBounds(25, 21, 48, 16);
        lblid.setHorizontalAlignment(SwingConstants.CENTER);
        panel_2.add(lblid);

        tfShopId = new JTextField();
        tfShopId.setEditable(false);
        tfShopId.setColumns(10);
        tfShopId.setBounds(85, 18, 117, 21);
        panel_2.add(tfShopId);

        JLabel label = new JLabel("店铺名称");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBounds(241, 21, 66, 15);
        panel_2.add(label);

        tfShopName = new JTextField();
        tfShopName.setEditable(false);
        tfShopName.setBounds(317, 18, 117, 21);
        panel_2.add(tfShopName);
        tfShopName.setColumns(10);

        JLabel lblShopee = new JLabel("Shopee订单Sn");
        lblShopee.setHorizontalAlignment(SwingConstants.CENTER);
        lblShopee.setBounds(457, 21, 103, 15);
        panel_2.add(lblShopee);

        tfOrderSn = new JTextField();
        tfOrderSn.setEditable(false);
        tfOrderSn.setBounds(560, 18, 173, 21);
        panel_2.add(tfOrderSn);
        tfOrderSn.setColumns(10);

        JLabel label_1 = new JLabel("订单状态");
        label_1.setHorizontalAlignment(SwingConstants.CENTER);
        label_1.setBounds(755, 21, 60, 15);
        panel_2.add(label_1);

        JLabel label_2 = new JLabel("订单日期");
        label_2.setHorizontalAlignment(SwingConstants.CENTER);
        label_2.setBounds(25, 62, 66, 15);
        panel_2.add(label_2);

        tfOrderCreateTime = new JTextField();
        tfOrderCreateTime.setEditable(false);
        tfOrderCreateTime.setBounds(101, 59, 130, 21);
        panel_2.add(tfOrderCreateTime);
        tfOrderCreateTime.setColumns(10);

        JLabel label_3 = new JLabel("订单总金额");
        label_3.setHorizontalAlignment(SwingConstants.CENTER);
        label_3.setBounds(25, 100, 80, 15);
        panel_2.add(label_3);

        tfOrderTotalPrice = new JTextField();
        tfOrderTotalPrice.setHorizontalAlignment(SwingConstants.CENTER);
        tfOrderTotalPrice.setEditable(false);
        tfOrderTotalPrice.setBounds(111, 96, 100, 21);
        panel_2.add(tfOrderTotalPrice);
        tfOrderTotalPrice.setColumns(10);

        JLabel label_4 = new JLabel("采购成本");
        label_4.setHorizontalAlignment(SwingConstants.CENTER);
        label_4.setBounds(434, 100, 54, 15);
        panel_2.add(label_4);

        tfPurchaseTotalPrice = new JTextField();
        tfPurchaseTotalPrice.setEditable(false);
        tfPurchaseTotalPrice.setBounds(495, 96, 100, 21);
        panel_2.add(tfPurchaseTotalPrice);
        tfPurchaseTotalPrice.setColumns(10);

        JLabel label_5 = new JLabel("汇率选择");
        label_5.setHorizontalAlignment(SwingConstants.CENTER);
        label_5.setBounds(591, 62, 72, 15);
        panel_2.add(label_5);

        cbExchange = new JComboBox<>();
        cbExchange.setBounds(673, 59, 158, 21);
        panel_2.add(cbExchange);

        JLabel label_7 = new JLabel("买家留言");
        label_7.setHorizontalAlignment(SwingConstants.CENTER);
        label_7.setBounds(25, 137, 92, 15);
        panel_2.add(label_7);

        tfMessageToSeller = new JTextField();
        tfMessageToSeller.setEditable(false);
        tfMessageToSeller.setBounds(129, 134, 720, 21);
        panel_2.add(tfMessageToSeller);
        tfMessageToSeller.setColumns(10);

        JLabel lblshopee_1 = new JLabel("Shopee平台订单备注");
        lblshopee_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblshopee_1.setBounds(10, 180, 132, 15);
        panel_2.add(lblshopee_1);

        tfShopeeOrderNote = new JTextField();
        tfShopeeOrderNote.setEditable(false);
        tfShopeeOrderNote.setColumns(10);
        tfShopeeOrderNote.setBounds(152, 177, 697, 21);
        panel_2.add(tfShopeeOrderNote);

        JLabel label_10 = new JLabel("公司内部备注");
        label_10.setHorizontalAlignment(SwingConstants.CENTER);
        label_10.setBounds(25, 230, 92, 15);
        panel_2.add(label_10);

        tfRemark = new JTextField();
        tfRemark.setEditable(false);
        tfRemark.setColumns(10);
        tfRemark.setBounds(129, 227, 720, 21);
        panel_2.add(tfRemark);

        tfOrderStatus = new JTextField();
        tfOrderStatus.setEditable(false);
        tfOrderStatus.setBounds(819, 18, 117, 21);
        panel_2.add(tfOrderStatus);
        tfOrderStatus.setColumns(10);

        cbIsDelivery = new JCheckBox("出货");
        cbIsDelivery.setEnabled(false);
        cbIsDelivery.setBounds(630, 95, 72, 23);
        panel_2.add(cbIsDelivery);

        JLabel label_8 = new JLabel("订单最后出货日期");
        label_8.setHorizontalAlignment(SwingConstants.CENTER);
        label_8.setBounds(270, 62, 120, 15);
        panel_2.add(label_8);

        tfDataToShip = new JTextField();
        tfDataToShip.setEditable(false);
        tfDataToShip.setColumns(10);
        tfDataToShip.setBounds(396, 59, 130, 21);
        panel_2.add(tfDataToShip);

        JLabel label_6 = new JLabel("订单进帐金额");
        label_6.setHorizontalAlignment(SwingConstants.CENTER);
        label_6.setBounds(213, 100, 100, 15);
        panel_2.add(label_6);

        tfEscrowAmount = new JTextField();
        tfEscrowAmount.setHorizontalAlignment(SwingConstants.CENTER);
        tfEscrowAmount.setEditable(false);
        tfEscrowAmount.setColumns(10);
        tfEscrowAmount.setBounds(310, 96, 100, 21);
        panel_2.add(tfEscrowAmount);

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setBounds(0, 285, 968, 298);
        panel_1.add(tabbedPane);

        JPanel customerInfoPanel = new JPanel();
        tabbedPane.addTab("客户信息", null, customerInfoPanel, null);
        customerInfoPanel.setLayout(null);

        JLabel lblRecipientName = new JLabel("收件人姓名");
        lblRecipientName.setHorizontalAlignment(SwingConstants.CENTER);
        lblRecipientName.setBounds(36, 28, 80, 15);
        customerInfoPanel.add(lblRecipientName);

        tfRecipientName = new JTextField();
        tfRecipientName.setEditable(false);
        tfRecipientName.setBounds(120, 24, 150, 21);
        customerInfoPanel.add(tfRecipientName);
        tfRecipientName.setColumns(10);

        JLabel lblRecipientPhone = new JLabel("收件人电话");
        lblRecipientPhone.setHorizontalAlignment(SwingConstants.CENTER);
        lblRecipientPhone.setBounds(370, 28, 80, 15);
        customerInfoPanel.add(lblRecipientPhone);

        tfRecipientPhone = new JTextField();
        tfRecipientPhone.setEditable(false);
        tfRecipientPhone.setColumns(10);
        tfRecipientPhone.setBounds(450, 24, 150, 21);
        customerInfoPanel.add(tfRecipientPhone);

        JLabel lblNewLabel = new JLabel("收件国家");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setBounds(36, 76, 72, 15);
        customerInfoPanel.add(lblNewLabel);

        tfRecipientCountry = new JTextField();
        tfRecipientCountry.setEditable(false);
        tfRecipientCountry.setBounds(118, 73, 200, 21);
        customerInfoPanel.add(tfRecipientCountry);
        tfRecipientCountry.setColumns(10);

        JLabel lblAddress = new JLabel("邮政编码");
        lblAddress.setHorizontalAlignment(SwingConstants.CENTER);
        lblAddress.setBounds(370, 76, 72, 15);
        customerInfoPanel.add(lblAddress);

        tfRecipientZipCode = new JTextField();
        tfRecipientZipCode.setEditable(false);
        tfRecipientZipCode.setColumns(10);
        tfRecipientZipCode.setBounds(450, 73, 200, 21);
        customerInfoPanel.add(tfRecipientZipCode);

        JLabel lblAddress_1 = new JLabel("收件地址");
        lblAddress_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblAddress_1.setBounds(36, 123, 72, 15);
        customerInfoPanel.add(lblAddress_1);

        tfRecipientFullAddress = new JTextField();
        tfRecipientFullAddress.setEditable(false);
        tfRecipientFullAddress.setColumns(10);
        tfRecipientFullAddress.setBounds(118, 120, 600, 21);
        customerInfoPanel.add(tfRecipientFullAddress);


        JPanel orderItemsInfoPanel = new JPanel();
        tabbedPane.addTab("订单详细信息", null, orderItemsInfoPanel, null);
        orderItemsInfoPanel.setLayout(new BorderLayout(0, 0));

        JPanel panel_3 = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        orderItemsInfoPanel.add(panel_3, BorderLayout.NORTH);

        btnRefreshOrderItemInfo = new JButton("刷新数据");
        panel_3.add(btnRefreshOrderItemInfo);

        orderItemsInfoTable = new JTable();

        shopeeOrderItemsInfoTableModel = new ShopeeOrderItemsInfoTableModel(shopeeOrderItemsInfoList);
        orderItemsInfoTable.setModel(shopeeOrderItemsInfoTableModel);

        // 设置列宽
        orderItemsInfoTable.getColumnModel().getColumn(0).setPreferredWidth(100);
        orderItemsInfoTable.getColumnModel().getColumn(1).setPreferredWidth(100);
        orderItemsInfoTable.getColumnModel().getColumn(2).setPreferredWidth(300);
        orderItemsInfoTable.getColumnModel().getColumn(3).setPreferredWidth(100);
        orderItemsInfoTable.getColumnModel().getColumn(4).setPreferredWidth(100);
        orderItemsInfoTable.getColumnModel().getColumn(5).setPreferredWidth(200);
        orderItemsInfoTable.getColumnModel().getColumn(6).setPreferredWidth(100);
        orderItemsInfoTable.getColumnModel().getColumn(7).setPreferredWidth(80);
        orderItemsInfoTable.getColumnModel().getColumn(8).setPreferredWidth(80);
        orderItemsInfoTable.getColumnModel().getColumn(9).setPreferredWidth(80);
        orderItemsInfoTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        // 列的顺序不可拖动
        orderItemsInfoTable.getTableHeader().setReorderingAllowed(false);
        // 只可以选择单行
        orderItemsInfoTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        // 只可以选择列
        orderItemsInfoTable.setRowSelectionAllowed(true);
        orderItemsInfoTable.setColumnSelectionAllowed(false);

        JScrollPane scrollPane = new JScrollPane(orderItemsInfoTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        orderItemsInfoPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel purchaseOrderInflPanel = new JPanel();
        tabbedPane.addTab("采购信息", null, purchaseOrderInflPanel, null);
        purchaseOrderInflPanel.setLayout(new BorderLayout(0, 0));

        JPanel panel_4 = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        purchaseOrderInflPanel.add(panel_4, BorderLayout.NORTH);

        btnRefreshPurchaseInfo = new JButton("刷新数据");
        panel_4.add(btnRefreshPurchaseInfo);

        btnAddPurchaseInfo = new JButton("添加数据");
        panel_4.add(btnAddPurchaseInfo);

        purchaseInfoTable = new JTable();

        shopeePurchaseOrderInfoTableModel = new ShopeePurchaseOrderInfoTableModel(shopeePurchaseOrderInfoList);
        purchaseInfoTable.setModel(shopeePurchaseOrderInfoTableModel);

        // 设置列宽
        purchaseInfoTable.getColumnModel().getColumn(0).setPreferredWidth(100);
        purchaseInfoTable.getColumnModel().getColumn(1).setPreferredWidth(140);
        purchaseInfoTable.getColumnModel().getColumn(2).setPreferredWidth(170);
        purchaseInfoTable.getColumnModel().getColumn(3).setPreferredWidth(300);
        purchaseInfoTable.getColumnModel().getColumn(4).setPreferredWidth(100);
        purchaseInfoTable.getColumnModel().getColumn(5).setPreferredWidth(100);
        purchaseInfoTable.getColumnModel().getColumn(6).setPreferredWidth(300);
        purchaseInfoTable.getColumnModel().getColumn(7).setPreferredWidth(100);
        purchaseInfoTable.getColumnModel().getColumn(8).setPreferredWidth(90);
        purchaseInfoTable.getColumnModel().getColumn(9).setPreferredWidth(200);
        purchaseInfoTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        // 列的顺序不可拖动
        purchaseInfoTable.getTableHeader().setReorderingAllowed(false);
        // 只可以选择单行
        purchaseInfoTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        // 只可以选择列
        purchaseInfoTable.setRowSelectionAllowed(true);
        purchaseInfoTable.setColumnSelectionAllowed(false);
        // 双击事件
        purchaseInfoTable.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    if (shopeePurchaseOrderInfoDetailJFrame != null) {
                        shopeePurchaseOrderInfoDetailJFrame.removeWindowListener(purchaseOrderInfoDetailJFrameListener);
                        shopeePurchaseOrderInfoDetailJFrame.dispose();
                        shopeePurchaseOrderInfoDetailJFrame = null;
                    }

                    int rowIndex = purchaseInfoTable.rowAtPoint(e.getPoint());
                    ShopeePurchaseOrderInfo purchaseOrderInfo = shopeePurchaseOrderInfoList.get(rowIndex);

                    shopeePurchaseOrderInfoDetailJFrame = new ShopeePurchaseOrderInfoDetailJFrame(ViewType.SHOW, purchaseOrderInfo,
                            shopeeOrderItemsInfoList);
                    shopeePurchaseOrderInfoDetailJFrame.addWindowListener(purchaseOrderInfoDetailJFrameListener);
                    shopeePurchaseOrderInfoDetailJFrame.setVisible(true);
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

        JScrollPane scrollPane_1 = new JScrollPane(purchaseInfoTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        purchaseOrderInflPanel.add(scrollPane_1, BorderLayout.CENTER);

        JPanel overseasExpressInfoPanel = new JPanel();
        tabbedPane.addTab("海外快递信息", null, overseasExpressInfoPanel, null);
        overseasExpressInfoPanel.setLayout(new BorderLayout(0, 0));

        JPanel panel_5 = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        panel_5.setBorder(new LineBorder(new Color(0, 0, 0)));
        overseasExpressInfoPanel.add(panel_5, BorderLayout.NORTH);

        btnEditOverseasExpressPrice = new JButton("编辑数据");
        panel_5.add(btnEditOverseasExpressPrice);

        btnUpdateOrderDeliveryStatus = new JButton("修改订单为已出货");
        panel_5.add(btnUpdateOrderDeliveryStatus);

        JPanel panel_8 = new JPanel();
        overseasExpressInfoPanel.add(panel_8, BorderLayout.CENTER);
        panel_8.setLayout(null);

        JLabel label_9 = new JLabel("海外快递单号");
        label_9.setHorizontalAlignment(SwingConstants.CENTER);
        label_9.setBounds(45, 36, 88, 15);
        panel_8.add(label_9);

        tfOverseasExpress = new JTextField();
        tfOverseasExpress.setEditable(false);
        tfOverseasExpress.setBounds(155, 33, 270, 21);
        panel_8.add(tfOverseasExpress);
        tfOverseasExpress.setColumns(10);

        JLabel lblNewLabel_1 = new JLabel("国际运费(人民币)");
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_1.setBounds(31, 82, 113, 15);
        panel_8.add(lblNewLabel_1);

        tfOverseasExpressPrice = new JTextField();
        tfOverseasExpressPrice.setEditable(false);
        tfOverseasExpressPrice.setBounds(155, 79, 66, 21);
        panel_8.add(tfOverseasExpressPrice);
        tfOverseasExpressPrice.setColumns(10);

        JPanel profitPanel = new JPanel();
        tabbedPane.addTab("利润", null, profitPanel, null);
        profitPanel.setLayout(null);

        btnRecalculate = new JButton("重新计算");
        btnRecalculate.setBounds(35, 28, 93, 23);
        profitPanel.add(btnRecalculate);

        JLabel label_11 = new JLabel("系数");
        label_11.setHorizontalAlignment(SwingConstants.CENTER);
        label_11.setBounds(227, 32, 54, 15);
        profitPanel.add(label_11);

        tfCalculationCoefficient = new JTextField();
        tfCalculationCoefficient.setText("0.85");
        tfCalculationCoefficient.setBounds(291, 29, 66, 21);
        profitPanel.add(tfCalculationCoefficient);
        tfCalculationCoefficient.setColumns(10);

        JLabel label_12 = new JLabel("销售成本");
        label_12.setHorizontalAlignment(SwingConstants.CENTER);
        label_12.setBounds(35, 97, 54, 15);
        profitPanel.add(label_12);

        tfCostOfSales = new JTextField();
        tfCostOfSales.setEditable(false);
        tfCostOfSales.setBounds(111, 94, 66, 21);
        profitPanel.add(tfCostOfSales);
        tfCostOfSales.setColumns(10);

        lbCostOfSales = new JLabel("=  采购成本（0.0）+ 国际运费（0.0）");
        lbCostOfSales.setBounds(209, 97, 300, 15);
        profitPanel.add(lbCostOfSales);

        JLabel label_13 = new JLabel("（注：本页均已人民币为单位）");
        label_13.setHorizontalAlignment(SwingConstants.CENTER);
        label_13.setBounds(697, 233, 214, 15);
        profitPanel.add(label_13);

        JLabel label_14 = new JLabel("利润");
        label_14.setHorizontalAlignment(SwingConstants.CENTER);
        label_14.setBounds(35, 147, 54, 15);
        profitPanel.add(label_14);

        tfProfit = new JTextField();
        tfProfit.setEditable(false);
        tfProfit.setColumns(10);
        tfProfit.setBounds(111, 144, 66, 21);
        profitPanel.add(tfProfit);

        lbProfit = new JLabel("=  订单收入（0.00）* 系数（0.00）- 销售成本（0.00）");
        lbProfit.setBounds(209, 147, 400, 15);
        profitPanel.add(lbProfit);

        JLabel label_16 = new JLabel("利润率");
        label_16.setHorizontalAlignment(SwingConstants.CENTER);
        label_16.setBounds(35, 198, 54, 15);
        profitPanel.add(label_16);

        tfProfitRate = new JTextField();
        tfProfitRate.setEditable(false);
        tfProfitRate.setColumns(10);
        tfProfitRate.setBounds(111, 195, 66, 21);
        profitPanel.add(tfProfitRate);

        JLabel lbProfitRate = new JLabel("= 利润 / 销售成本");
        lbProfitRate.setBounds(209, 198, 248, 15);
        profitPanel.add(lbProfitRate);
    }

    private void initData() {
        ExchangeRate exchangeRate = shopeeOrderInfo.getExchangeRate();

        tfShopId.setText(String.valueOf(shopeeOrderInfo.getShopeeShopInfo().getShopId()));
        tfShopName.setText(shopeeOrderInfo.getShopeeShopInfo().getShopName());
        tfOrderSn.setText(shopeeOrderInfo.getOrderSn());
        tfOrderStatus.setText(shopeeOrderInfo.getOrderStatus());
        tfOrderCreateTime.setText(DateUtils.dateToStr(shopeeOrderInfo.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
        tfDataToShip.setText(DateUtils.dateToStr(shopeeOrderInfo.getDateToShip(), "yyyy-MM-dd"));

        String orderTotalPrice = String.format("%.2f外币", shopeeOrderInfo.getTotalAmount());
        if (exchangeRate != null) {
            orderTotalPrice = String.valueOf(CalculateUtils.convertPriceToRMB(shopeeOrderInfo.getTotalAmount(), exchangeRate.getExchangeRate()));
        }
        tfOrderTotalPrice.setText(orderTotalPrice);

        String orderEscrowAmount = String.format("%.2f外币", shopeeOrderInfo.getEscrowAmount());
        if (exchangeRate != null) {
            orderEscrowAmount = String.valueOf(CalculateUtils.convertPriceToRMB(shopeeOrderInfo.getEscrowAmount(), exchangeRate.getExchangeRate()));
        }
        tfEscrowAmount.setText(orderEscrowAmount);

        tfPurchaseTotalPrice.setText("0.00");

        // 填充汇率数据
        if (exchangeRate != null) {
            String rate = exchangeRate.getExchangeRate() + "(" + exchangeRate.getCountryCode() + "-"
                    + DateUtils.dateToStr(exchangeRate.getDate(), "yyyy-MM-dd") + ")";
            cbExchange.addItem(rate);
            cbExchange.setSelectedIndex(0);
        }

        tfMessageToSeller.setText(shopeeOrderInfo.getMessageToSeller());
        tfShopeeOrderNote.setText(shopeeOrderInfo.getNote());
        tfRemark.setText(shopeeOrderInfo.getRemarks());

        tfRecipientName.setText(shopeeOrderInfo.getRecipientName());
        tfRecipientPhone.setText(shopeeOrderInfo.getRecipientPhone());
        tfRecipientCountry.setText(shopeeOrderInfo.getRecipientCountry());
        tfRecipientZipCode.setText(shopeeOrderInfo.getRecipientZipCode());
        tfRecipientFullAddress.setText(shopeeOrderInfo.getRecipientFullAddress());

        tfOverseasExpress.setText(shopeeOrderInfo.getTrackingNo());
        btnEditOverseasExpressPrice.setText("编辑数据");
        tfOverseasExpressPrice.setEditable(false);
        tfOverseasExpressPrice.setText(String.format("%.2f", shopeeOrderInfo.getOverseasExpressPrice()));
        getOrderItemsInfoFromServer();
        getPurchaseOrderInfoFromServer();

        if (shopeeOrderInfo.isDelivery()) {
            cbIsDelivery.setSelected(true);
            btnUpdateOrderDeliveryStatus.setEnabled(false);
        } else {
            cbIsDelivery.setSelected(false);
            btnUpdateOrderDeliveryStatus.setEnabled(true);
        }
    }

    private void initClickEvent() {
        btnUpdateOrderDeliveryStatus.addActionListener(e -> updateOrderInfoIsDelivery(true));
        btnRefreshOrderInfo.addActionListener(e -> refreshOrderInfo());
        btnEditOrderInfoData.addActionListener(e -> {
            if ("编辑数据".equals(btnEditOrderInfoData.getText())) {
                tfRemark.setEditable(true);
                btnEditOrderInfoData.setText("保存数据");
            } else {
                btnEditOrderInfoData.setText("编辑数据");
                if (!tfRemark.getText().equals(shopeeOrderInfo.getRemarks())) {
                    updateOrderInfoRemark();
                } else {
                    tfRemark.setEditable(false);
                }
            }
        });
        btnSyncOrderData.addActionListener(e -> syncOrderInfo());
        btnRefreshOrderItemInfo.addActionListener(e -> getOrderItemsInfoFromServer());
        btnRefreshPurchaseInfo.addActionListener(e -> getPurchaseOrderInfoFromServer());
        btnAddPurchaseInfo.addActionListener(e -> {
            if (shopeePurchaseOrderInfoDetailJFrame != null) {
                shopeePurchaseOrderInfoDetailJFrame.removeWindowListener(purchaseOrderInfoDetailJFrameListener);
                shopeePurchaseOrderInfoDetailJFrame.dispose();
                shopeePurchaseOrderInfoDetailJFrame = null;
            }

            shopeePurchaseOrderInfoDetailJFrame = new ShopeePurchaseOrderInfoDetailJFrame(ViewType.SAVE, null,
                    shopeeOrderItemsInfoList);
            shopeePurchaseOrderInfoDetailJFrame.addWindowListener(purchaseOrderInfoDetailJFrameListener);
            shopeePurchaseOrderInfoDetailJFrame.setVisible(true);
        });
        btnEditOverseasExpressPrice.addActionListener(e -> {
            if (tfOverseasExpressPrice.isEditable()) {
                // 保存数据
                try {
                    float overseasExpressPrice = Float.valueOf(tfOverseasExpressPrice.getText().trim());
                    tfOverseasExpressPrice.setText(String.format("%.2f", overseasExpressPrice));
                    if (overseasExpressPrice == shopeeOrderInfo.getOverseasExpressPrice()) {
                        tfOverseasExpressPrice.setEditable(false);
                        btnEditOverseasExpressPrice.setText("编辑数据");
                        return;
                    }
                    updateLazadaOrderInfoOverseasExpressPrice();
                } catch (NumberFormatException e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(null, "请输入正确的金额", "WARNING_MESSAGE", JOptionPane.WARNING_MESSAGE);
                    tfOverseasExpressPrice.setText(String.format("%.2f", shopeeOrderInfo.getOverseasExpressPrice()));
                }
            } else {
                // 启动编辑模式
                btnEditOverseasExpressPrice.setText("保存数据");
                tfOverseasExpressPrice.setEditable(true);
            }
        });
        btnRecalculate.addActionListener(e -> {
            try {
                Float.valueOf(tfCalculationCoefficient.getText().trim());
                calculateProfit();
                calculateProfitRate();
            } catch (NumberFormatException e1) {
                e1.printStackTrace();
                JOptionPane.showMessageDialog(null, "请输入正确的系数", "WARNING_MESSAGE", JOptionPane.WARNING_MESSAGE);
                tfCalculationCoefficient.setText("0.85");
            }
        });
    }

    private void refreshOrderInfo() {
        btnRefreshOrderInfo.setEnabled(false);
        btnSyncOrderData.setEnabled(false);
        btnEditOrderInfoData.setEnabled(false);
        ShopeeOrderRequest request = RetrofitManager.getInstance().getRetrofit().create(ShopeeOrderRequest.class);
        Call<ShopeeOrderInfo> call = request.getByOrderSn(shopeeOrderInfo.getOrderSn());
        call.enqueue(new Callback<ShopeeOrderInfo>() {
            @Override
            public void onResponse(Call<ShopeeOrderInfo> call, Response<ShopeeOrderInfo> response) {
                btnRefreshOrderInfo.setEnabled(true);
                btnSyncOrderData.setEnabled(true);
                btnEditOrderInfoData.setEnabled(true);
                if (response.code() == 200 && response.body() != null) {
                    shopeeOrderInfo = response.body();
                    initData();
                } else {
                    JOptionPane.showMessageDialog(null, "服务器异常", "WARNING_MESSAGE", JOptionPane.WARNING_MESSAGE);
                }
            }

            @Override
            public void onFailure(Call<ShopeeOrderInfo> call, Throwable throwable) {
                btnRefreshOrderInfo.setEnabled(true);
                btnSyncOrderData.setEnabled(true);
                btnEditOrderInfoData.setEnabled(true);
                JOptionPane.showMessageDialog(null, "发送请求失败:" + throwable.toString(), "WARNING_MESSAGE",
                        JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    /**
     * 手动同步订单数据
     */
    private void syncOrderInfo() {
        // FIXME: 2019/2/15 
       /* btnRefreshOrderInfo.setEnabled(false);
        btnSyncOrderData.setEnabled(false);
        btnEditOrderInfoData.setEnabled(false);
        LazadaOrderRequest request = RetrofitManager.getInstance().getRetrofit().create(LazadaOrderRequest.class);
        Call<LazadaOrderInfo> call = request.syncOrderInfo(lazadaOrderInfo.getId());
        call.enqueue(new Callback<LazadaOrderInfo>() {
            @Override
            public void onResponse(Call<LazadaOrderInfo> call, Response<LazadaOrderInfo> response) {
                btnRefreshOrderInfo.setEnabled(true);
                btnSyncOrderData.setEnabled(true);
                btnEditOrderInfoData.setEnabled(true);
                if (response.code() == 200 && response.body() != null) {
                    lazadaOrderInfo = response.body();
                    initData();
                    JOptionPane.showMessageDialog(null, "同步数据成功", "INFORMATION_MESSAGE",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "同步数据失败", "WARNING_MESSAGE", JOptionPane.WARNING_MESSAGE);
                }
            }

            @Override
            public void onFailure(Call<LazadaOrderInfo> call, Throwable throwable) {
                btnRefreshOrderInfo.setEnabled(true);
                btnSyncOrderData.setEnabled(true);
                btnEditOrderInfoData.setEnabled(true);
                JOptionPane.showMessageDialog(null, "发送请求失败:" + throwable.toString(), "WARNING_MESSAGE",
                        JOptionPane.WARNING_MESSAGE);
            }
        });*/
    }

    /**
     * 获取采购订单信息，获取完之后，计算采购成本、销售成本、利润
     */
    private void getPurchaseOrderInfoFromServer() {
        btnRefreshPurchaseInfo.setEnabled(false);
        btnAddPurchaseInfo.setEnabled(false);
        ShopeePurchaseRequest request = RetrofitManager.getInstance().getRetrofit().create(ShopeePurchaseRequest.class);
        Call<List<ShopeePurchaseOrderInfo>> call = request.getByShopeeOrderInfo(shopeeOrderInfo.getOrderSn());
        call.enqueue(new Callback<List<ShopeePurchaseOrderInfo>>() {
            @Override
            public void onResponse(Call<List<ShopeePurchaseOrderInfo>> call, Response<List<ShopeePurchaseOrderInfo>> response) {
                btnRefreshPurchaseInfo.setEnabled(true);
                btnAddPurchaseInfo.setEnabled(true);
                if (response.code() == 200) {
                    shopeePurchaseOrderInfoList.clear();
                    shopeePurchaseOrderInfoList.addAll(response.body());
                    purchaseInfoTable.validate();
                    purchaseInfoTable.updateUI();
                    // 计算页面数据
                    calculatePurchaseTotalPrice();
                    calculateCostOfSales();
                    calculateProfit();
                    calculateProfitRate();
                } else {
                    JOptionPane.showMessageDialog(null, "服务器异常", "WARNING_MESSAGE", JOptionPane.WARNING_MESSAGE);
                }
            }

            @Override
            public void onFailure(Call<List<ShopeePurchaseOrderInfo>> call, Throwable throwable) {
                btnRefreshPurchaseInfo.setEnabled(true);
                btnAddPurchaseInfo.setEnabled(true);
                JOptionPane.showMessageDialog(null, "发送请求失败:" + throwable.toString(), "WARNING_MESSAGE",
                        JOptionPane.WARNING_MESSAGE);
                throwable.printStackTrace();
            }
        });
    }

    private void getOrderItemsInfoFromServer() {
        btnRefreshOrderItemInfo.setEnabled(false);
        ShopeeOrderItemRequest request = RetrofitManager.getInstance().getRetrofit().create(ShopeeOrderItemRequest.class);
        Call<List<ShopeeOrderItemsInfo>> call = request.getByShopeeOrderInfo(shopeeOrderInfo.getOrderSn());
        call.enqueue(new Callback<List<ShopeeOrderItemsInfo>>() {
            @Override
            public void onResponse(Call<List<ShopeeOrderItemsInfo>> call, Response<List<ShopeeOrderItemsInfo>> response) {
                btnRefreshOrderItemInfo.setEnabled(true);
                if (response.code() == 200) {
                    shopeeOrderItemsInfoList.clear();
                    shopeeOrderItemsInfoList.addAll(response.body());
                    orderItemsInfoTable.validate();
                    orderItemsInfoTable.updateUI();
                } else {
                    JOptionPane.showMessageDialog(null, "服务器异常", "WARNING_MESSAGE", JOptionPane.WARNING_MESSAGE);
                }
            }

            @Override
            public void onFailure(Call<List<ShopeeOrderItemsInfo>> call, Throwable throwable) {
                btnRefreshOrderItemInfo.setEnabled(true);
                JOptionPane.showMessageDialog(null, "发送请求失败:" + throwable.toString(), "WARNING_MESSAGE",
                        JOptionPane.WARNING_MESSAGE);
                throwable.printStackTrace();
            }
        });
    }

    /**
     * 更新海外运费数据
     */
    private void updateLazadaOrderInfoOverseasExpressPrice() {
        tfOverseasExpressPrice.setEditable(false);
        btnEditOverseasExpressPrice.setEnabled(false);
        btnRefreshOrderInfo.setEnabled(false);
        btnSyncOrderData.setEnabled(false);
        btnEditOrderInfoData.setEnabled(false);
        ShopeeOrderRequest request = RetrofitManager.getInstance().getRetrofit().create(ShopeeOrderRequest.class);
        Call<String> call = request.updateOverseasExpressPrice(shopeeOrderInfo.getOrderSn(),
                Float.valueOf(tfOverseasExpressPrice.getText().trim()));
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                tfOverseasExpressPrice.setEditable(true);
                btnEditOverseasExpressPrice.setEnabled(true);
                btnRefreshOrderInfo.setEnabled(true);
                btnSyncOrderData.setEnabled(true);
                btnEditOrderInfoData.setEnabled(true);
                if (response.code() == 200) {
                    if (response.body().equals("Updated is successful:true")) {
                        tfOverseasExpressPrice.setEditable(false);
                        btnEditOverseasExpressPrice.setText("编辑数据");
                        shopeeOrderInfo.setOverseasExpressPrice(Float.valueOf(tfOverseasExpressPrice.getText().trim()));
                        calculateCostOfSales();
                        calculateProfit();
                        calculateProfitRate();
                        JOptionPane.showMessageDialog(null, "更新成功", "INFORMATION_MESSAGE",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "服务器异常", "WARNING_MESSAGE", JOptionPane.WARNING_MESSAGE);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                tfOverseasExpressPrice.setEditable(true);
                btnEditOverseasExpressPrice.setEnabled(true);
                btnRefreshOrderInfo.setEnabled(true);
                btnSyncOrderData.setEnabled(true);
                btnEditOrderInfoData.setEnabled(true);
                JOptionPane.showMessageDialog(null, "发送请求失败:" + throwable.toString(), "WARNING_MESSAGE",
                        JOptionPane.WARNING_MESSAGE);
                throwable.printStackTrace();
            }
        });
    }

    /**
     * 更新订单出货状态
     */
    private void updateOrderInfoIsDelivery(final boolean isDelivery) {
        btnUpdateOrderDeliveryStatus.setEnabled(false);
        ShopeeOrderRequest request = RetrofitManager.getInstance().getRetrofit().create(ShopeeOrderRequest.class);
        Call<String> call = request.updateDeliveryStatus(shopeeOrderInfo.getOrderSn(), isDelivery);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.code() == 200) {
                    if (response.body().equals("Updated is successful:true")) {
                        shopeeOrderInfo.setDelivery(isDelivery);
                        cbIsDelivery.setSelected(isDelivery);
                        JOptionPane.showMessageDialog(null, "更新成功", "INFORMATION_MESSAGE",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    btnUpdateOrderDeliveryStatus.setEnabled(true);
                    JOptionPane.showMessageDialog(null, "服务器异常", "WARNING_MESSAGE", JOptionPane.WARNING_MESSAGE);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                btnUpdateOrderDeliveryStatus.setEnabled(true);
                JOptionPane.showMessageDialog(null, "发送请求失败:" + throwable.toString(), "WARNING_MESSAGE",
                        JOptionPane.WARNING_MESSAGE);
                throwable.printStackTrace();
            }
        });
    }

    /**
     * 更新订单备注
     */
    private void updateOrderInfoRemark() {
        btnEditOrderInfoData.setEnabled(false);
        tfRemark.setEditable(false);
        ShopeeOrderRequest request = RetrofitManager.getInstance().getRetrofit().create(ShopeeOrderRequest.class);
        Call<String> call = request.updateRemark(shopeeOrderInfo.getOrderSn(), tfRemark.getText());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                btnEditOrderInfoData.setEnabled(true);
                if (response.code() == 200) {
                    if (response.body().equals("Updated is successful:true")) {
                        shopeeOrderInfo.setRemarks(tfRemark.getText());
                        JOptionPane.showMessageDialog(null, "更新成功", "INFORMATION_MESSAGE",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    tfRemark.setText(shopeeOrderInfo.getRemarks());
                    JOptionPane.showMessageDialog(null, "服务器异常", "WARNING_MESSAGE", JOptionPane.WARNING_MESSAGE);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                btnEditOrderInfoData.setEnabled(true);
                tfRemark.setText(shopeeOrderInfo.getRemarks());
                JOptionPane.showMessageDialog(null, "发送请求失败:" + throwable.toString(), "WARNING_MESSAGE",
                        JOptionPane.WARNING_MESSAGE);
                throwable.printStackTrace();
            }
        });
    }

    /**
     * 计算采购成本
     */
    private void calculatePurchaseTotalPrice() {
        float totalPrice = 0.00f;
        for (ShopeePurchaseOrderInfo purchaseOrderInfo : shopeePurchaseOrderInfoList) {
            totalPrice += purchaseOrderInfo.getTotalPrice();
        }
        tfPurchaseTotalPrice.setText(String.valueOf(totalPrice));
    }

    /**
     * 计算销售成本
     */
    private void calculateCostOfSales() {
        float purchaseTotalPrice = Float.valueOf(tfPurchaseTotalPrice.getText());
        lbCostOfSales.setText(String.format("=  采购成本（%.2f）+ 国际运费（%.2f）", purchaseTotalPrice,
                shopeeOrderInfo.getOverseasExpressPrice()));
        tfCostOfSales.setText(String.format("%.2f", purchaseTotalPrice + shopeeOrderInfo.getOverseasExpressPrice()));
    }

    /**
     * 计算利润
     */
    private void calculateProfit() {
        ExchangeRate exchangeRate = shopeeOrderInfo.getExchangeRate();
        if (exchangeRate == null) {
            lbProfit.setText("订单无汇率，先关联汇率后才能计算");
            tfProfit.setText("0.00");
            return;
        }
        float orderTotalPrice = CalculateUtils.convertPriceToRMB(shopeeOrderInfo.getEscrowAmount(),
                exchangeRate.getExchangeRate());
        float calculationCoefficient = Float.valueOf(tfCalculationCoefficient.getText().trim());
        float sellingCost = Float.valueOf(tfCostOfSales.getText().trim());
        lbProfit.setText(String.format("=  订单收入（%.2f）* 系数（%.2f）- 销售成本（%.2f）", orderTotalPrice, calculationCoefficient,
                sellingCost));
        tfProfit.setText(
                String.format("%.2f", CalculateUtils.multiply(orderTotalPrice, calculationCoefficient) - sellingCost));
    }

    /**
     * 计算利润率
     */
    private void calculateProfitRate() {
        float profit = Float.valueOf(tfProfit.getText());
        float costOfSales = Float.valueOf(tfCostOfSales.getText());
        String profitRate = "";
        if (costOfSales != 0f) {
            profitRate = String.format("%d%%", (int) (CalculateUtils.divide(profit, costOfSales) * 100));
        }
        tfProfitRate.setText(profitRate);
    }
}
