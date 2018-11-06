package com.fjnu.trade.view.modules.lazadaorder;

import com.fjnu.common.http.RetrofitManager;
import com.fjnu.common.utils.DateUtils;
import com.fjnu.common.utils.CalculateUtils;
import com.fjnu.trade.http.request.LazadaOrderInfoRequest;
import com.fjnu.trade.http.request.LazadaOrderItemsInfoRequest;
import com.fjnu.trade.http.request.PurchaseOrderInfoRequest;
import com.fjnu.trade.model.ExchangeRate;
import com.fjnu.trade.model.lazada.LazadaOrderInfo;
import com.fjnu.trade.model.lazada.LazadaOrderItemsInfo;
import com.fjnu.trade.model.lazada.PurchaseOrderInfo;
import com.fjnu.trade.view.ViewType;
import com.lazada.platform.bean.OrderBean;
import org.apache.http.util.TextUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class LazadaOrderInfoDetailJFrame extends JFrame {

    private JPanel contentPane;
    private JTextField tfShopName;
    private JTextField tfLazadaOrderId;
    private JTextField tfOrderStatus;
    private JTextField tfOrderCreateTime;
    private JTextField tfOrderTotalPrice;
    private JTextField tfPurchaseTotalPrice;
    private JTextField tfOrderItemsSize;
    private JTextField tfOrderItemsInfo;
    private JTextField tfLazadaOrderRemark;
    private JTextField tfRemark;
    private JTextField tfFirstName;
    private JTextField tfLastName;
    private JTextField tfPhone1;
    private JTextField tfPhone2;
    private JTextField tfAddress1;
    private JTextField tfCustomerEmail;
    private JTextField tfCity;
    private JTextField tfPostCode;
    private JTextField tfAddress2;
    private JTextField tfAddress3;
    private JTextField tfAddress4;
    private JTextField tfOverseasExpress;
    private JTextField tfOverseasExpressPrice;
    private JTextField tfCalculationCoefficient;
    private JTextField tfCostOfSales;
    private JTextField tfProfit;
    private JTextField tfProfitRate;
    private JComboBox cbExchange;
    private JLabel lbCostOfSales;
    private JLabel lbProfit;
    private JCheckBox cbIsDelivery;

    private JButton btnRefreshOrderInfo;
    private JButton btnEditOrderInfoData;
    private JButton btnChangeOrderStatus;
    private JButton btnRefreshOrderItemInfo;
    private JButton btnRefreshPurchaseInfo;
    private JButton btnAddPurchaseInfo;
    private JButton btnEditOverseasExpressPrice;
    private JButton btnRecalculate;
    private JButton btnUpdateOrderDeliveryStatus;
    private JButton btnSyncOrderData;

    private LazadaOrderInfoChooseShipmentProvidersJFrame lazadaOrderInfoChooseShipmentProvidersJFrame;

    private JTable orderItemsInfoTable;
    private LazadaOrderItemsInfoTableModel lazadaOrderItemsInfoTableModel;
    private List<LazadaOrderItemsInfo> lazadaOrderItemsInfos = new ArrayList<>();
    private LazadaOrderItemsInfoJFrame lazadaOrderItemsInfoJFrame;

    private JTable purchaseInfoTable;
    private LazadaPurchaseOrderInfoTableModel lazadaPurchaseOrderInfoTableModel;
    private List<PurchaseOrderInfo> purchaseOrderInfos = new ArrayList<>();
    private PurchaseOrderInfoDetailJFrame purchaseOrderInfoDetailJFrame;
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

    private LazadaOrderInfo lazadaOrderInfo;

    /**
     * Create the frame.
     */
    public LazadaOrderInfoDetailJFrame(LazadaOrderInfo lazadaOrderInfo) {
        this.lazadaOrderInfo = lazadaOrderInfo;
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

        btnChangeOrderStatus = new JButton("更改订单状态为ready_to_ship");
        panel.add(btnChangeOrderStatus);

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

        JLabel label = new JLabel("店铺名称");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBounds(25, 21, 66, 15);
        panel_2.add(label);

        tfShopName = new JTextField();
        tfShopName.setEditable(false);
        tfShopName.setBounds(101, 18, 117, 21);
        panel_2.add(tfShopName);
        tfShopName.setColumns(10);

        JLabel lblLazada = new JLabel("Lazada订单号");
        lblLazada.setHorizontalAlignment(SwingConstants.CENTER);
        lblLazada.setBounds(241, 21, 103, 15);
        panel_2.add(lblLazada);

        tfLazadaOrderId = new JTextField();
        tfLazadaOrderId.setEditable(false);
        tfLazadaOrderId.setBounds(354, 18, 173, 21);
        panel_2.add(tfLazadaOrderId);
        tfLazadaOrderId.setColumns(10);

        JLabel label_1 = new JLabel("订单状态");
        label_1.setHorizontalAlignment(SwingConstants.CENTER);
        label_1.setBounds(573, 21, 60, 15);
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

        JLabel label_3 = new JLabel("订单总收入金额");
        label_3.setHorizontalAlignment(SwingConstants.CENTER);
        label_3.setBounds(258, 62, 103, 15);
        panel_2.add(label_3);

        tfOrderTotalPrice = new JTextField();
        tfOrderTotalPrice.setEditable(false);
        tfOrderTotalPrice.setBounds(371, 59, 54, 21);
        panel_2.add(tfOrderTotalPrice);
        tfOrderTotalPrice.setColumns(10);

        JLabel label_4 = new JLabel("采购成本");
        label_4.setHorizontalAlignment(SwingConstants.CENTER);
        label_4.setBounds(473, 62, 54, 15);
        panel_2.add(label_4);

        tfPurchaseTotalPrice = new JTextField();
        tfPurchaseTotalPrice.setEditable(false);
        tfPurchaseTotalPrice.setBounds(537, 59, 66, 21);
        panel_2.add(tfPurchaseTotalPrice);
        tfPurchaseTotalPrice.setColumns(10);

        JLabel label_5 = new JLabel("汇率选择");
        label_5.setHorizontalAlignment(SwingConstants.CENTER);
        label_5.setBounds(648, 62, 72, 15);
        panel_2.add(label_5);

        cbExchange = new JComboBox();
        cbExchange.setBounds(730, 59, 158, 21);
        panel_2.add(cbExchange);

        JLabel label_6 = new JLabel("订单商品数量");
        label_6.setHorizontalAlignment(SwingConstants.CENTER);
        label_6.setBounds(25, 99, 84, 15);
        panel_2.add(label_6);

        tfOrderItemsSize = new JTextField();
        tfOrderItemsSize.setEditable(false);
        tfOrderItemsSize.setBounds(119, 96, 54, 21);
        panel_2.add(tfOrderItemsSize);
        tfOrderItemsSize.setColumns(10);

        JLabel label_7 = new JLabel("订单物品信息");
        label_7.setHorizontalAlignment(SwingConstants.CENTER);
        label_7.setBounds(25, 137, 92, 15);
        panel_2.add(label_7);

        tfOrderItemsInfo = new JTextField();
        tfOrderItemsInfo.setEditable(false);
        tfOrderItemsInfo.setBounds(129, 134, 720, 21);
        panel_2.add(tfOrderItemsInfo);
        tfOrderItemsInfo.setColumns(10);

        JLabel lblLazada_1 = new JLabel("Lazada平台订单备注");
        lblLazada_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblLazada_1.setBounds(10, 184, 132, 15);
        panel_2.add(lblLazada_1);

        tfLazadaOrderRemark = new JTextField();
        tfLazadaOrderRemark.setEditable(false);
        tfLazadaOrderRemark.setColumns(10);
        tfLazadaOrderRemark.setBounds(152, 181, 697, 21);
        panel_2.add(tfLazadaOrderRemark);

        JLabel label_10 = new JLabel("公司内部备注");
        label_10.setHorizontalAlignment(SwingConstants.CENTER);
        label_10.setBounds(25, 232, 92, 15);
        panel_2.add(label_10);

        tfRemark = new JTextField();
        tfRemark.setEditable(false);
        tfRemark.setColumns(10);
        tfRemark.setBounds(129, 229, 720, 21);
        panel_2.add(tfRemark);

        tfOrderStatus = new JTextField();
        tfOrderStatus.setEditable(false);
        tfOrderStatus.setBounds(637, 18, 117, 21);
        panel_2.add(tfOrderStatus);
        tfOrderStatus.setColumns(10);

        cbIsDelivery = new JCheckBox("出货");
        cbIsDelivery.setEnabled(false);
        cbIsDelivery.setBounds(207, 95, 72, 23);
        panel_2.add(cbIsDelivery);

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setBounds(0, 285, 968, 298);
        panel_1.add(tabbedPane);

        JPanel customerInfoPanel = new JPanel();
        tabbedPane.addTab("客户信息", null, customerInfoPanel, null);
        customerInfoPanel.setLayout(null);

        JLabel lblFirstName = new JLabel("FirstName");
        lblFirstName.setHorizontalAlignment(SwingConstants.CENTER);
        lblFirstName.setBounds(36, 28, 72, 15);
        customerInfoPanel.add(lblFirstName);

        tfFirstName = new JTextField();
        tfFirstName.setEditable(false);
        tfFirstName.setBounds(115, 25, 100, 21);
        customerInfoPanel.add(tfFirstName);
        tfFirstName.setColumns(10);

        JLabel lblLastName = new JLabel("LastName");
        lblLastName.setHorizontalAlignment(SwingConstants.CENTER);
        lblLastName.setBounds(249, 25, 72, 15);
        customerInfoPanel.add(lblLastName);

        tfLastName = new JTextField();
        tfLastName.setEditable(false);
        tfLastName.setColumns(10);
        tfLastName.setBounds(328, 22, 66, 21);
        customerInfoPanel.add(tfLastName);

        JLabel lblPhone = new JLabel("Phone-1");
        lblPhone.setHorizontalAlignment(SwingConstants.CENTER);
        lblPhone.setBounds(472, 25, 60, 15);
        customerInfoPanel.add(lblPhone);

        tfPhone1 = new JTextField();
        tfPhone1.setEditable(false);
        tfPhone1.setColumns(10);
        tfPhone1.setBounds(539, 22, 128, 21);
        customerInfoPanel.add(tfPhone1);

        JLabel lblPhone_1 = new JLabel("Phone-2");
        lblPhone_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblPhone_1.setBounds(711, 25, 60, 15);
        customerInfoPanel.add(lblPhone_1);

        tfPhone2 = new JTextField();
        tfPhone2.setEditable(false);
        tfPhone2.setColumns(10);
        tfPhone2.setBounds(777, 22, 143, 21);
        customerInfoPanel.add(tfPhone2);

        JLabel lblNewLabel = new JLabel("Address-1");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setBounds(36, 76, 72, 15);
        customerInfoPanel.add(lblNewLabel);

        tfAddress1 = new JTextField();
        tfAddress1.setEditable(false);
        tfAddress1.setBounds(118, 73, 246, 21);
        customerInfoPanel.add(tfAddress1);
        tfAddress1.setColumns(10);

        JLabel lblCustomeremail = new JLabel("CustomerEmail");
        lblCustomeremail.setHorizontalAlignment(SwingConstants.CENTER);
        lblCustomeremail.setBounds(23, 176, 100, 15);
        customerInfoPanel.add(lblCustomeremail);

        tfCustomerEmail = new JTextField();
        tfCustomerEmail.setEditable(false);
        tfCustomerEmail.setBounds(144, 173, 569, 21);
        customerInfoPanel.add(tfCustomerEmail);
        tfCustomerEmail.setColumns(10);

        tfCity = new JTextField();
        tfCity.setEditable(false);
        tfCity.setColumns(10);
        tfCity.setBounds(77, 224, 128, 21);
        customerInfoPanel.add(tfCity);

        JLabel lblCity = new JLabel("City");
        lblCity.setHorizontalAlignment(SwingConstants.CENTER);
        lblCity.setBounds(10, 227, 60, 15);
        customerInfoPanel.add(lblCity);

        JLabel lblPostcode = new JLabel("PostCode");
        lblPostcode.setHorizontalAlignment(SwingConstants.CENTER);
        lblPostcode.setBounds(259, 227, 60, 15);
        customerInfoPanel.add(lblPostcode);

        tfPostCode = new JTextField();
        tfPostCode.setEditable(false);
        tfPostCode.setColumns(10);
        tfPostCode.setBounds(326, 224, 128, 21);
        customerInfoPanel.add(tfPostCode);

        JLabel lblAddress = new JLabel("Address-2");
        lblAddress.setHorizontalAlignment(SwingConstants.CENTER);
        lblAddress.setBounds(425, 76, 72, 15);
        customerInfoPanel.add(lblAddress);

        tfAddress2 = new JTextField();
        tfAddress2.setEditable(false);
        tfAddress2.setColumns(10);
        tfAddress2.setBounds(507, 73, 246, 21);
        customerInfoPanel.add(tfAddress2);

        JLabel lblAddress_1 = new JLabel("Address-3");
        lblAddress_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblAddress_1.setBounds(36, 123, 72, 15);
        customerInfoPanel.add(lblAddress_1);

        tfAddress3 = new JTextField();
        tfAddress3.setEditable(false);
        tfAddress3.setColumns(10);
        tfAddress3.setBounds(118, 120, 246, 21);
        customerInfoPanel.add(tfAddress3);

        JLabel lblAddress_2 = new JLabel("Address-4");
        lblAddress_2.setHorizontalAlignment(SwingConstants.CENTER);
        lblAddress_2.setBounds(425, 123, 72, 15);
        customerInfoPanel.add(lblAddress_2);

        tfAddress4 = new JTextField();
        tfAddress4.setEditable(false);
        tfAddress4.setColumns(10);
        tfAddress4.setBounds(507, 120, 246, 21);
        customerInfoPanel.add(tfAddress4);

        JPanel orderItemsInfoPanel = new JPanel();
        tabbedPane.addTab("订单详细信息", null, orderItemsInfoPanel, null);
        orderItemsInfoPanel.setLayout(new BorderLayout(0, 0));

        JPanel panel_3 = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        orderItemsInfoPanel.add(panel_3, BorderLayout.NORTH);

        btnRefreshOrderItemInfo = new JButton("刷新数据");
        panel_3.add(btnRefreshOrderItemInfo);

        orderItemsInfoTable = new JTable();

        lazadaOrderItemsInfoTableModel = new LazadaOrderItemsInfoTableModel(lazadaOrderItemsInfos);
        orderItemsInfoTable.setModel(lazadaOrderItemsInfoTableModel);

        // 设置列宽
        orderItemsInfoTable.getColumnModel().getColumn(0).setPreferredWidth(100);
        orderItemsInfoTable.getColumnModel().getColumn(1).setPreferredWidth(140);
        orderItemsInfoTable.getColumnModel().getColumn(2).setPreferredWidth(160);
        orderItemsInfoTable.getColumnModel().getColumn(3).setPreferredWidth(400);
        orderItemsInfoTable.getColumnModel().getColumn(4).setPreferredWidth(110);
        orderItemsInfoTable.getColumnModel().getColumn(5).setPreferredWidth(110);
        orderItemsInfoTable.getColumnModel().getColumn(6).setPreferredWidth(300);
        orderItemsInfoTable.getColumnModel().getColumn(7).setPreferredWidth(200);
        orderItemsInfoTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        // 列的顺序不可拖动
        orderItemsInfoTable.getTableHeader().setReorderingAllowed(false);
        // 只可以选择单行
        orderItemsInfoTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        // 只可以选择列
        orderItemsInfoTable.setRowSelectionAllowed(true);
        orderItemsInfoTable.setColumnSelectionAllowed(false);
        // 双击事件
        orderItemsInfoTable.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    if (lazadaOrderItemsInfoJFrame != null) {
                        lazadaOrderItemsInfoJFrame.dispose();
                    }
                    int rowIndex = orderItemsInfoTable.rowAtPoint(e.getPoint());
                    lazadaOrderItemsInfoJFrame = new LazadaOrderItemsInfoJFrame(lazadaOrderItemsInfos.get(rowIndex));
                    lazadaOrderItemsInfoJFrame.setVisible(true);
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

        lazadaPurchaseOrderInfoTableModel = new LazadaPurchaseOrderInfoTableModel(purchaseOrderInfos);
        purchaseInfoTable.setModel(lazadaPurchaseOrderInfoTableModel);

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
                    if (purchaseOrderInfoDetailJFrame != null) {
                        purchaseOrderInfoDetailJFrame.removeWindowListener(purchaseOrderInfoDetailJFrameListener);
                        purchaseOrderInfoDetailJFrame.dispose();
                        purchaseOrderInfoDetailJFrame = null;
                    }

                    int rowIndex = purchaseInfoTable.rowAtPoint(e.getPoint());
                    PurchaseOrderInfo purchaseOrderInfo = purchaseOrderInfos.get(rowIndex);

                    purchaseOrderInfoDetailJFrame = new PurchaseOrderInfoDetailJFrame(ViewType.SHOW, purchaseOrderInfo,
                            lazadaOrderItemsInfos);
                    purchaseOrderInfoDetailJFrame.addWindowListener(purchaseOrderInfoDetailJFrameListener);
                    purchaseOrderInfoDetailJFrame.setVisible(true);
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
        if (lazadaOrderInfo != null) {
            ExchangeRate exchangeRate = lazadaOrderInfo.getExchangeRate();
            tfShopName.setText(lazadaOrderInfo.getLazadaShopInfo().getShopName());
            tfLazadaOrderId.setText(String.valueOf(lazadaOrderInfo.getLazadaOrderId()));
            tfOrderStatus.setText(lazadaOrderInfo.getOrderStatus());
            tfOrderCreateTime
                    .setText(DateUtils.dateToStr(lazadaOrderInfo.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
            float orderTotalPrice = CalculateUtils.convertPriceToRMB(lazadaOrderInfo.getPrice(),
                    exchangeRate.getExchangeRate());
            tfOrderTotalPrice.setText(String.valueOf(orderTotalPrice));
            tfPurchaseTotalPrice.setText("0.00");

            // 填充汇率数据
            String rate = exchangeRate.getExchangeRate() + "(" + exchangeRate.getCountryCode() + "-"
                    + DateUtils.dateToStr(exchangeRate.getDate(), "yyyy-MM-dd") + ")";
            cbExchange.addItem(rate);
            cbExchange.setSelectedIndex(0);

            tfLazadaOrderRemark.setText(lazadaOrderInfo.getLazadaOrderRemarks());
            tfRemark.setText(lazadaOrderInfo.getRemarks());
            tfFirstName.setText(lazadaOrderInfo.getAddressShippingFirstName());
            tfLastName.setText(lazadaOrderInfo.getAddressShippingLastName());
            tfPhone1.setText(lazadaOrderInfo.getAddressShippingPhone1());
            tfPhone2.setText(lazadaOrderInfo.getAddressShippingPhone2());
            tfAddress1.setText(lazadaOrderInfo.getAddressShippingAddress1());
            tfAddress2.setText(lazadaOrderInfo.getAddressShippingAddress2());
            tfCustomerEmail.setText(lazadaOrderInfo.getAddressShippingCustomerEmail());
            tfCity.setText(lazadaOrderInfo.getAddressShippingCity());
            tfPostCode.setText(lazadaOrderInfo.getAddressShippingPostCode());
            tfPhone2.setText(lazadaOrderInfo.getAddressShippingPhone2());
            tfPhone2.setText(lazadaOrderInfo.getAddressShippingPhone2());
            tfPhone2.setText(lazadaOrderInfo.getAddressShippingPhone2());
            tfPhone2.setText(lazadaOrderInfo.getAddressShippingPhone2());
            tfOverseasExpress.setText(lazadaOrderInfo.getOverseasExpressNumber());
            btnEditOverseasExpressPrice.setText("编辑数据");
            tfOverseasExpressPrice.setEditable(false);
            tfOverseasExpressPrice.setText(String.format("%.2f", lazadaOrderInfo.getOverseasExpressPrice()));
            getLazadaOrderItemsInfoFromServer();
            getPurchaseOrderInfoFromServer();

            if (tfOrderStatus.getText().equals(OrderBean.Status.Pending.getStatus())) {
                btnChangeOrderStatus.setEnabled(true);
            } else {
                btnChangeOrderStatus.setEnabled(false);
            }
            if (!TextUtils.isEmpty(lazadaOrderInfo.getOverseasExpressNumber()) && lazadaOrderInfo.isDelivery()) {
                cbIsDelivery.setSelected(true);
                btnUpdateOrderDeliveryStatus.setEnabled(false);
            } else {
                cbIsDelivery.setSelected(false);
                btnUpdateOrderDeliveryStatus.setEnabled(true);
            }
        }
    }

    private void initClickEvent() {
        btnUpdateOrderDeliveryStatus.addActionListener(e ->
                updateLazadaOrderInfoIsDelivery(true)
        );
        btnRefreshOrderInfo.addActionListener(e -> refreshLazadaOrderInfo());
        btnChangeOrderStatus.addActionListener(e -> {
            if (lazadaOrderInfoChooseShipmentProvidersJFrame != null) {
                lazadaOrderInfoChooseShipmentProvidersJFrame.dispose();
                lazadaOrderInfoChooseShipmentProvidersJFrame = null;
            }

            lazadaOrderInfoChooseShipmentProvidersJFrame = new LazadaOrderInfoChooseShipmentProvidersJFrame(
                    lazadaOrderInfo);
            lazadaOrderInfoChooseShipmentProvidersJFrame.setVisible(true);
        });
        btnEditOrderInfoData.addActionListener(e -> {
            if ("编辑数据".equals(btnEditOrderInfoData.getText())) {
                tfRemark.setEditable(true);
                btnEditOrderInfoData.setText("保存数据");
            } else {
                btnEditOrderInfoData.setText("编辑数据");
                if (!tfRemark.getText().equals(lazadaOrderInfo.getRemarks())) {
                    updateLazadaOrderInfoRemark();
                } else {
                    tfRemark.setEditable(false);
                }
            }
        });
        btnSyncOrderData.addActionListener(e -> syncLazadaOrderInfo());
        btnRefreshOrderItemInfo.addActionListener(e -> getLazadaOrderItemsInfoFromServer());
        btnRefreshPurchaseInfo.addActionListener(e -> getPurchaseOrderInfoFromServer());
        btnAddPurchaseInfo.addActionListener(e -> {
            if (purchaseOrderInfoDetailJFrame != null) {
                purchaseOrderInfoDetailJFrame.removeWindowListener(purchaseOrderInfoDetailJFrameListener);
                purchaseOrderInfoDetailJFrame.dispose();
                purchaseOrderInfoDetailJFrame = null;
            }

            purchaseOrderInfoDetailJFrame = new PurchaseOrderInfoDetailJFrame(ViewType.SAVE, null,
                    lazadaOrderItemsInfos);
            purchaseOrderInfoDetailJFrame.addWindowListener(purchaseOrderInfoDetailJFrameListener);
            purchaseOrderInfoDetailJFrame.setVisible(true);
        });
        btnEditOverseasExpressPrice.addActionListener(e -> {
            if (tfOverseasExpressPrice.isEditable()) {
                // 保存数据
                try {
                    float overseasExpressPrice = Float.valueOf(tfOverseasExpressPrice.getText().trim());
                    tfOverseasExpressPrice.setText(String.format("%.2f", overseasExpressPrice));
                    if (overseasExpressPrice == lazadaOrderInfo.getOverseasExpressPrice()) {
                        tfOverseasExpressPrice.setEditable(false);
                        btnEditOverseasExpressPrice.setText("编辑数据");
                        return;
                    }
                    updateLazadaOrderInfoOverseasExpressPrice();
                } catch (NumberFormatException e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(null, "请输入正确的金额", "WARNING_MESSAGE", JOptionPane.WARNING_MESSAGE);
                    tfOverseasExpressPrice.setText(String.format("%.2f", lazadaOrderInfo.getOverseasExpressPrice()));
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

    private void refreshLazadaOrderInfo() {
        btnRefreshOrderInfo.setEnabled(false);
        btnChangeOrderStatus.setEnabled(false);
        btnSyncOrderData.setEnabled(false);
        btnEditOrderInfoData.setEnabled(false);
        LazadaOrderInfoRequest request = RetrofitManager.getInstance().getRetrofit()
                .create(LazadaOrderInfoRequest.class);
        Call<LazadaOrderInfo> call = request.getById(lazadaOrderInfo.getId());
        call.enqueue(new Callback<LazadaOrderInfo>() {
            @Override
            public void onResponse(Call<LazadaOrderInfo> call, Response<LazadaOrderInfo> response) {
                btnRefreshOrderInfo.setEnabled(true);
                btnChangeOrderStatus.setEnabled(true);
                btnSyncOrderData.setEnabled(true);
                btnEditOrderInfoData.setEnabled(true);
                if (response.code() == 200 && response.body() != null) {
                    lazadaOrderInfo = response.body();
                    initData();
                } else {
                    JOptionPane.showMessageDialog(null, "服务器异常", "WARNING_MESSAGE", JOptionPane.WARNING_MESSAGE);
                }
            }

            @Override
            public void onFailure(Call<LazadaOrderInfo> call, Throwable throwable) {
                btnRefreshOrderInfo.setEnabled(true);
                btnChangeOrderStatus.setEnabled(true);
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
    private void syncLazadaOrderInfo() {
        btnRefreshOrderInfo.setEnabled(false);
        btnChangeOrderStatus.setEnabled(false);
        btnSyncOrderData.setEnabled(false);
        btnEditOrderInfoData.setEnabled(false);
        LazadaOrderInfoRequest request = RetrofitManager.getInstance().getRetrofit()
                .create(LazadaOrderInfoRequest.class);
        Call<LazadaOrderInfo> call = request.syncLazadaOrderInfo(lazadaOrderInfo.getId());
        call.enqueue(new Callback<LazadaOrderInfo>() {
            @Override
            public void onResponse(Call<LazadaOrderInfo> call, Response<LazadaOrderInfo> response) {
                btnRefreshOrderInfo.setEnabled(true);
                btnChangeOrderStatus.setEnabled(true);
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
                btnChangeOrderStatus.setEnabled(true);
                btnSyncOrderData.setEnabled(true);
                btnEditOrderInfoData.setEnabled(true);
                JOptionPane.showMessageDialog(null, "发送请求失败:" + throwable.toString(), "WARNING_MESSAGE",
                        JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    private void getLazadaOrderItemsInfoFromServer() {
        btnRefreshOrderItemInfo.setEnabled(false);
        LazadaOrderItemsInfoRequest request = RetrofitManager.getInstance().getRetrofit()
                .create(LazadaOrderItemsInfoRequest.class);
        Call<List<LazadaOrderItemsInfo>> call = request.getByLazadaOrderInfo(lazadaOrderInfo.getId());
        call.enqueue(new Callback<List<LazadaOrderItemsInfo>>() {
            @Override
            public void onResponse(Call<List<LazadaOrderItemsInfo>> call,
                                   Response<List<LazadaOrderItemsInfo>> response) {
                btnRefreshOrderItemInfo.setEnabled(true);
                if (response.code() == 200) {
                    lazadaOrderItemsInfos.clear();
                    lazadaOrderItemsInfos.addAll(response.body());
                    tfOrderItemsSize.setText(String.valueOf(lazadaOrderItemsInfos.size()));
                    orderItemsInfoTable.validate();
                    orderItemsInfoTable.updateUI();
                } else {
                    JOptionPane.showMessageDialog(null, "服务器异常", "WARNING_MESSAGE", JOptionPane.WARNING_MESSAGE);
                }
            }

            @Override
            public void onFailure(Call<List<LazadaOrderItemsInfo>> call, Throwable throwable) {
                btnRefreshOrderItemInfo.setEnabled(true);
                JOptionPane.showMessageDialog(null, "发送请求失败:" + throwable.toString(), "WARNING_MESSAGE",
                        JOptionPane.WARNING_MESSAGE);
                throwable.printStackTrace();
            }
        });
    }

    /**
     * 获取采购订单信息，获取完之后，计算采购成本、销售成本、利润
     */
    private void getPurchaseOrderInfoFromServer() {
        btnRefreshPurchaseInfo.setEnabled(false);
        btnAddPurchaseInfo.setEnabled(false);
        PurchaseOrderInfoRequest request = RetrofitManager.getInstance().getRetrofit()
                .create(PurchaseOrderInfoRequest.class);
        Call<List<PurchaseOrderInfo>> call = request.getByLazadaOrderInfo(lazadaOrderInfo.getId());
        call.enqueue(new Callback<List<PurchaseOrderInfo>>() {
            @Override
            public void onResponse(Call<List<PurchaseOrderInfo>> call, Response<List<PurchaseOrderInfo>> response) {
                btnRefreshPurchaseInfo.setEnabled(true);
                btnAddPurchaseInfo.setEnabled(true);
                if (response.code() == 200) {
                    purchaseOrderInfos.clear();
                    purchaseOrderInfos.addAll(response.body());
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
            public void onFailure(Call<List<PurchaseOrderInfo>> call, Throwable throwable) {
                btnRefreshPurchaseInfo.setEnabled(true);
                btnAddPurchaseInfo.setEnabled(true);
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
        LazadaOrderInfoRequest request = RetrofitManager.getInstance().getRetrofit()
                .create(LazadaOrderInfoRequest.class);
        Call<String> call = request.updateOverseasExpressPrice(lazadaOrderInfo.getId(),
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
                        lazadaOrderInfo.setOverseasExpressPrice(Float.valueOf(tfOverseasExpressPrice.getText().trim()));
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
    private void updateLazadaOrderInfoIsDelivery(final boolean isDelivery) {
        btnUpdateOrderDeliveryStatus.setEnabled(false);
        LazadaOrderInfoRequest request = RetrofitManager.getInstance().getRetrofit()
                .create(LazadaOrderInfoRequest.class);
        Call<String> call = request.updateLazadaOrderInfoIsDelivery(lazadaOrderInfo.getId(), isDelivery);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.code() == 200) {
                    if (response.body().equals("Updated is successful:true")) {
                        lazadaOrderInfo.setDelivery(isDelivery);
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
    private void updateLazadaOrderInfoRemark() {
        btnEditOrderInfoData.setEnabled(false);
        tfRemark.setEditable(false);
        LazadaOrderInfoRequest request = RetrofitManager.getInstance().getRetrofit().create(LazadaOrderInfoRequest.class);
        Call<String> call = request.updateLazadaOrderInfoRemark(lazadaOrderInfo.getId(), tfRemark.getText());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                btnEditOrderInfoData.setEnabled(true);
                if (response.code() == 200) {
                    if (response.body().equals("Updated is successful:true")) {
                        lazadaOrderInfo.setRemarks(tfRemark.getText());
                        JOptionPane.showMessageDialog(null, "更新成功", "INFORMATION_MESSAGE",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    tfRemark.setText(lazadaOrderInfo.getRemarks());
                    JOptionPane.showMessageDialog(null, "服务器异常", "WARNING_MESSAGE", JOptionPane.WARNING_MESSAGE);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                btnEditOrderInfoData.setEnabled(true);
                tfRemark.setText(lazadaOrderInfo.getRemarks());
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
        for (PurchaseOrderInfo purchaseOrderInfo : purchaseOrderInfos) {
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
                lazadaOrderInfo.getOverseasExpressPrice()));
        tfCostOfSales.setText(String.valueOf(purchaseTotalPrice + lazadaOrderInfo.getOverseasExpressPrice()));
    }

    /**
     * 计算利润
     */
    private void calculateProfit() {
        ExchangeRate exchangeRate = lazadaOrderInfo.getExchangeRate();
        float orderTotalPrice = CalculateUtils.convertPriceToRMB(lazadaOrderInfo.getPrice(),
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
