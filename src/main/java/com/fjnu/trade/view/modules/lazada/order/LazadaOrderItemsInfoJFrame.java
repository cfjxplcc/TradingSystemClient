package com.fjnu.trade.view.modules.lazada.order;

import com.fjnu.common.utils.CalculateUtils;
import com.fjnu.common.utils.DesktopBrowseUtils;
import com.fjnu.trade.model.lazada.LazadaOrderItemsInfo;
import org.apache.http.util.TextUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LazadaOrderItemsInfoJFrame extends JFrame {

    private JPanel contentPane;
    private JTextField tfLazadaOrderItemId;
    private JTextField tfSku;
    private JTextField tfName;
    private JTextField tfItemPrice;
    private JTextField tfPaidPrice;
    private JButton btnQueryPurchaseBySku;
    private JButton btnBack;
    private JTextField tfUrl;
    private JButton btnOpenUrl;

    private LazadaOrderItemsInfo lazadaOrderItemsInfo;

    /**
     * Create the frame.
     */
    public LazadaOrderItemsInfoJFrame(LazadaOrderItemsInfo lazadaOrderItemsInfo) {
        this.lazadaOrderItemsInfo = lazadaOrderItemsInfo;
        initView();
        initClickEvent();
        initData();
    }

    private void initData() {
        if (lazadaOrderItemsInfo != null) {
            tfLazadaOrderItemId.setText(String.valueOf(lazadaOrderItemsInfo.getLazadaOrderItemId()));
            tfSku.setText(lazadaOrderItemsInfo.getSku());
            tfName.setText(lazadaOrderItemsInfo.getName());
            tfItemPrice.setText(String.valueOf(CalculateUtils.convertPriceToRMB(lazadaOrderItemsInfo.getItemPrice(),
                    lazadaOrderItemsInfo.getLazadaOrderInfo().getExchangeRate().getExchangeRate())));
            tfPaidPrice.setText(String.valueOf(CalculateUtils.convertPriceToRMB(lazadaOrderItemsInfo.getPaidPrice(),
                    lazadaOrderItemsInfo.getLazadaOrderInfo().getExchangeRate().getExchangeRate())));
            tfUrl.setText(lazadaOrderItemsInfo.getOrderItemUrl());
        }
    }

    private void initView() {
        setTitle("订单商品信息");
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setSize(450, 300);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // 得到屏幕的尺寸
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        setLocation((screenWidth - this.getWidth()) / 2, (screenHeight - this.getHeight()) / 2);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblId = new JLabel("Lazada平台商品id");
        lblId.setHorizontalAlignment(SwingConstants.CENTER);
        lblId.setBounds(35, 23, 114, 15);
        contentPane.add(lblId);

        tfLazadaOrderItemId = new JTextField();
        tfLazadaOrderItemId.setEditable(false);
        tfLazadaOrderItemId.setBounds(159, 20, 226, 21);
        contentPane.add(tfLazadaOrderItemId);
        tfLazadaOrderItemId.setColumns(10);

        JLabel lblSku = new JLabel("sku");
        lblSku.setHorizontalAlignment(SwingConstants.CENTER);
        lblSku.setBounds(35, 67, 114, 15);
        contentPane.add(lblSku);

        tfSku = new JTextField();
        tfSku.setEditable(false);
        tfSku.setColumns(10);
        tfSku.setBounds(159, 64, 226, 21);
        contentPane.add(tfSku);

        JLabel label = new JLabel("商品信息");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBounds(10, 107, 72, 15);
        contentPane.add(label);

        tfName = new JTextField();
        tfName.setEditable(false);
        tfName.setColumns(10);
        tfName.setBounds(92, 104, 342, 21);
        contentPane.add(tfName);

        JLabel lblNewLabel = new JLabel("商品价格");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setBounds(21, 179, 66, 15);
        contentPane.add(lblNewLabel);

        tfItemPrice = new JTextField();
        tfItemPrice.setEditable(false);
        tfItemPrice.setBounds(94, 176, 84, 21);
        contentPane.add(tfItemPrice);
        tfItemPrice.setColumns(10);

        JLabel lblNewLabel_1 = new JLabel("PaidPrice");
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_1.setBounds(231, 179, 66, 15);
        contentPane.add(lblNewLabel_1);

        tfPaidPrice = new JTextField();
        tfPaidPrice.setEditable(false);
        tfPaidPrice.setBounds(307, 176, 78, 21);
        contentPane.add(tfPaidPrice);
        tfPaidPrice.setColumns(10);

        btnQueryPurchaseBySku = new JButton("查找对应sku购买记录");
        btnQueryPurchaseBySku.setEnabled(false);
        btnQueryPurchaseBySku.setBounds(45, 223, 170, 23);
        contentPane.add(btnQueryPurchaseBySku);

        btnBack = new JButton("返回");
        btnBack.setBounds(277, 223, 93, 23);
        contentPane.add(btnBack);

        JLabel lblNewLabel_2 = new JLabel("商品快照url");
        lblNewLabel_2.setBounds(10, 145, 72, 15);
        contentPane.add(lblNewLabel_2);

        tfUrl = new JTextField();
        tfUrl.setEditable(false);
        tfUrl.setBounds(92, 142, 242, 21);
        contentPane.add(tfUrl);
        tfUrl.setColumns(10);

        btnOpenUrl = new JButton("打开URL");
        btnOpenUrl.setBounds(340, 141, 90, 23);
        contentPane.add(btnOpenUrl);
    }

    private void initClickEvent() {
        btnBack.addActionListener(e -> dispose());
        btnOpenUrl.addActionListener(e -> {
            if (!TextUtils.isEmpty(tfUrl.getText())) {
                DesktopBrowseUtils.useDesktopBrowseOpenUrl(tfUrl.getText());
            }
        });
    }

}
