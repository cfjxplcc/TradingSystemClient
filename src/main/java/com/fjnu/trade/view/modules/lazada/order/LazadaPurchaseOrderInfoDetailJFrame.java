package com.fjnu.trade.view.modules.lazada.order;

import com.fjnu.common.http.RetrofitManager;
import com.fjnu.common.utils.DateUtils;
import com.fjnu.common.utils.DesktopBrowseUtils;
import com.fjnu.trade.http.request.PurchaseOrderInfoRequest;
import com.fjnu.trade.model.lazada.LazadaOrderItemsInfo;
import com.fjnu.trade.model.lazada.PurchaseOrderInfo;
import com.fjnu.trade.view.ViewType;
import com.fjnu.trade.view.common.calendar.CalendarJDialog;
import org.apache.http.util.TextUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class LazadaPurchaseOrderInfoDetailJFrame extends JFrame {

    private JPanel contentPane;
    private JTextField tfId;
    private JTextField tfLazadaOrderId;
    private JTextField tfThirdPartyOrderId;
    private JTextField tfDescription;
    private JTextField tfTotalNumber;
    private JTextField tfUrl;
    private JTextField tfDate;
    private JTextField tfExpressNumber;
    private JTextField tfWeight;
    private JTextField tfTotalPrice;
    private JButton btnSaveOrEdit;
    private JButton btnBack;
    private JComboBox cbLazadaOrderItemsInfos;
    private JButton btnDelete;
    private JButton btnOpenUrl;

    private CalendarJDialog calendarJDialog;
    private ViewType viewType;

    private PurchaseOrderInfo purchaseOrderInfo;
    private List<LazadaOrderItemsInfo> lazadaOrderItemsInfoList;

    private MouseAdapter dateMouseAdapter = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (calendarJDialog != null && calendarJDialog.isShowing()) {
                calendarJDialog.dispose();
            }
            calendarJDialog = new CalendarJDialog(tfDate, "yyyy-MM-dd");
            calendarJDialog.setVisible(true);
        }
    };

    /**
     * Create the frame.
     */
    public LazadaPurchaseOrderInfoDetailJFrame(ViewType viewType, PurchaseOrderInfo purchaseOrderInfo, List<LazadaOrderItemsInfo> lazadaOrderItemsInfoList) {
        this.viewType = viewType;
        this.purchaseOrderInfo = purchaseOrderInfo;
        this.lazadaOrderItemsInfoList = lazadaOrderItemsInfoList;
        initView();
        initClickEvent();
        initData();
    }

    private void initClickEvent() {
        btnSaveOrEdit.addActionListener(e -> {
            switch (viewType) {
                case SAVE:
                    if (!checkInfoFormat()) {
                        return;
                    }
                    int itemTotalNumber = Integer.valueOf(tfTotalNumber.getText().trim());
                    float weight = Float.valueOf(tfWeight.getText().trim());
                    float totalPrice = Float.valueOf(tfTotalPrice.getText().trim());

                    PurchaseOrderInfo purchaseOrderInfo1 = new PurchaseOrderInfo();
                    purchaseOrderInfo1.setLazadaOrderInfo(lazadaOrderItemsInfoList.get(cbLazadaOrderItemsInfos.getSelectedIndex()).getLazadaOrderInfo());
                    purchaseOrderInfo1.setLazadaOrderItemsInfo(lazadaOrderItemsInfoList.get(cbLazadaOrderItemsInfos.getSelectedIndex()));
                    purchaseOrderInfo1.setThirdPartyOrderId(tfThirdPartyOrderId.getText().trim());
                    purchaseOrderInfo1.setDescription(tfDescription.getText().trim());
                    purchaseOrderInfo1.setTotalPrice(totalPrice);
                    purchaseOrderInfo1.setItemTotalMunber(itemTotalNumber);
                    purchaseOrderInfo1.setOrderUrl(tfUrl.getText().trim());
                    purchaseOrderInfo1.setWeight(weight);
                    purchaseOrderInfo1.setDate(DateUtils.strToSqlDate(tfDate.getText(), "yyyy-MM-dd"));
                    if (TextUtils.isEmpty(tfExpressNumber.getText())) {
                        purchaseOrderInfo1.setOrderExpressNumber(null);
                    } else {
                        purchaseOrderInfo1.setOrderExpressNumber(tfExpressNumber.getText().trim());
                    }
                    savePurchaseOrderInfo(purchaseOrderInfo1);
                    break;
                case SHOW:
                    viewType = ViewType.EDIT;
                    btnSaveOrEdit.setText("保存");
                    setComponentEnable(true);
                    break;
                case EDIT:
                    if (!checkInfoFormat()) {
                        return;
                    }
                    checkInfoFormat();

                    int itemTotalNumber1 = Integer.valueOf(tfTotalNumber.getText().trim());
                    float weight1 = Float.valueOf(tfWeight.getText().trim());
                    float totalPrice1 = Float.valueOf(tfTotalPrice.getText().trim());

                    purchaseOrderInfo.setLazadaOrderInfo(lazadaOrderItemsInfoList.get(cbLazadaOrderItemsInfos.getSelectedIndex()).getLazadaOrderInfo());
                    purchaseOrderInfo.setLazadaOrderItemsInfo(lazadaOrderItemsInfoList.get(cbLazadaOrderItemsInfos.getSelectedIndex()));
                    purchaseOrderInfo.setThirdPartyOrderId(tfThirdPartyOrderId.getText().trim());
                    purchaseOrderInfo.setDescription(tfDescription.getText().trim());
                    purchaseOrderInfo.setTotalPrice(totalPrice1);
                    purchaseOrderInfo.setItemTotalMunber(itemTotalNumber1);
                    purchaseOrderInfo.setOrderUrl(tfUrl.getText().trim());
                    purchaseOrderInfo.setWeight(weight1);
                    purchaseOrderInfo.setDate(DateUtils.strToSqlDate(tfDate.getText(), "yyyy-MM-dd"));
                    if (TextUtils.isEmpty(tfExpressNumber.getText())) {
                        purchaseOrderInfo.setOrderExpressNumber(null);
                    } else {
                        purchaseOrderInfo.setOrderExpressNumber(tfExpressNumber.getText().trim());
                    }

                    updatePurchaseOrderInfo(purchaseOrderInfo);
            }
        });
        btnBack.addActionListener(e -> dispose());
        btnDelete.addActionListener(e -> deletePurchaseOrderInfo(purchaseOrderInfo.getId()));
        btnOpenUrl.addActionListener(e -> {
            if (!TextUtils.isEmpty(tfUrl.getText())) {
                DesktopBrowseUtils.useDesktopBrowseOpenUrl(tfUrl.getText());
            }
        });
    }

    private void initView() {
        setTitle("采购订单信息");
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 699, 384);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblid = new JLabel("采购订单id");
        lblid.setHorizontalAlignment(SwingConstants.CENTER);
        lblid.setBounds(24, 20, 80, 15);
        contentPane.add(lblid);

        tfId = new JTextField();
        tfId.setEditable(false);
        tfId.setBounds(114, 17, 173, 21);
        contentPane.add(tfId);
        tfId.setColumns(10);

        JLabel lblLazadaid = new JLabel("Lazada订单id");
        lblLazadaid.setHorizontalAlignment(SwingConstants.CENTER);
        lblLazadaid.setBounds(345, 23, 89, 15);
        contentPane.add(lblLazadaid);

        tfLazadaOrderId = new JTextField();
        tfLazadaOrderId.setEditable(false);
        tfLazadaOrderId.setColumns(10);
        tfLazadaOrderId.setBounds(444, 20, 223, 21);
        contentPane.add(tfLazadaOrderId);

        JLabel lblid_1 = new JLabel("第三方平台采购订单Id");
        lblid_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblid_1.setBounds(24, 116, 130, 15);
        contentPane.add(lblid_1);

        tfThirdPartyOrderId = new JTextField();
        tfThirdPartyOrderId.setBounds(164, 113, 315, 21);
        contentPane.add(tfThirdPartyOrderId);
        tfThirdPartyOrderId.setColumns(10);

        JLabel lbllazada = new JLabel("关联lazada订单商品信息");
        lbllazada.setHorizontalAlignment(SwingConstants.CENTER);
        lbllazada.setBounds(24, 65, 159, 15);
        contentPane.add(lbllazada);

        cbLazadaOrderItemsInfos = new JComboBox();
        cbLazadaOrderItemsInfos.setBounds(193, 62, 474, 21);
        contentPane.add(cbLazadaOrderItemsInfos);

        JLabel label = new JLabel("采购物品描述");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBounds(10, 167, 96, 15);
        contentPane.add(label);

        tfDescription = new JTextField();
        tfDescription.setBounds(103, 164, 276, 21);
        contentPane.add(tfDescription);
        tfDescription.setColumns(10);

        JLabel label_1 = new JLabel("采购物品数量");
        label_1.setHorizontalAlignment(SwingConstants.CENTER);
        label_1.setBounds(399, 167, 80, 15);
        contentPane.add(label_1);

        tfTotalNumber = new JTextField();
        tfTotalNumber.setText("0");
        tfTotalNumber.setBounds(489, 164, 34, 21);
        contentPane.add(tfTotalNumber);
        tfTotalNumber.setColumns(10);

        JLabel label_2 = new JLabel("采购订单快照地址");
        label_2.setHorizontalAlignment(SwingConstants.CENTER);
        label_2.setBounds(24, 214, 118, 15);
        contentPane.add(label_2);

        tfUrl = new JTextField();
        tfUrl.setBounds(155, 211, 420, 21);
        contentPane.add(tfUrl);
        tfUrl.setColumns(10);

        btnOpenUrl = new JButton("打开URL");
        btnOpenUrl.setBounds(585, 210, 85, 23);
        contentPane.add(btnOpenUrl);

        JLabel label_3 = new JLabel("采购日期");
        label_3.setHorizontalAlignment(SwingConstants.CENTER);
        label_3.setBounds(495, 116, 66, 15);
        contentPane.add(label_3);

        tfDate = new JTextField();
        tfDate.setEditable(false);
        tfDate.setBounds(571, 113, 96, 21);
        contentPane.add(tfDate);
        tfDate.setColumns(10);

        JLabel lblNewLabel = new JLabel("快递单号");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setBounds(24, 255, 69, 15);
        contentPane.add(lblNewLabel);

        tfExpressNumber = new JTextField();
        tfExpressNumber.setBounds(87, 252, 333, 21);
        contentPane.add(tfExpressNumber);
        tfExpressNumber.setColumns(10);

        btnSaveOrEdit = new JButton("保存");
        btnSaveOrEdit.setBounds(138, 308, 93, 23);
        contentPane.add(btnSaveOrEdit);

        btnBack = new JButton("返回");
        btnBack.setBounds(444, 308, 93, 23);
        contentPane.add(btnBack);

        JLabel label_4 = new JLabel("重量");
        label_4.setHorizontalAlignment(SwingConstants.CENTER);
        label_4.setBounds(536, 167, 50, 15);
        contentPane.add(label_4);

        tfWeight = new JTextField();
        tfWeight.setText("0");
        tfWeight.setBounds(582, 164, 50, 21);
        contentPane.add(tfWeight);
        tfWeight.setColumns(10);

        JLabel label_5 = new JLabel("克");
        label_5.setBounds(642, 167, 27, 15);
        contentPane.add(label_5);

        JLabel label_6 = new JLabel("采购金额(含运费)");
        label_6.setBounds(444, 255, 108, 15);
        contentPane.add(label_6);

        tfTotalPrice = new JTextField();
        tfTotalPrice.setText("0.00");
        tfTotalPrice.setBounds(547, 252, 66, 21);
        contentPane.add(tfTotalPrice);
        tfTotalPrice.setColumns(10);

        btnDelete = new JButton("删除");
        btnDelete.setBounds(292, 308, 93, 23);
        contentPane.add(btnDelete);
    }

    private void initData() {
        if (lazadaOrderItemsInfoList != null && !lazadaOrderItemsInfoList.isEmpty()) {
            tfLazadaOrderId.setText(String.valueOf(lazadaOrderItemsInfoList.get(0).getLazadaOrderInfo().getLazadaOrderId()));
        }
        fillComboBox();

        switch (viewType) {
            case SHOW:
                cbLazadaOrderItemsInfos.setEnabled(false);
                tfThirdPartyOrderId.setEditable(false);
                tfDescription.setEditable(false);
                tfTotalNumber.setEditable(false);
                tfUrl.setEditable(false);
                tfExpressNumber.setEditable(false);
                tfTotalPrice.setEditable(false);
                tfWeight.setEditable(false);
                btnOpenUrl.setEnabled(true);
                fillTextFieldData();
                btnSaveOrEdit.setText("编辑");
                break;
            case SAVE:
                btnSaveOrEdit.setText("保存");
                btnDelete.setVisible(false);
                btnOpenUrl.setEnabled(false);
                setComponentEnable(true);
                tfDate.setText(getTodayString());
                break;
            default:
                break;
        }
    }

    private boolean checkInfoFormat() {
        try {
            Integer.valueOf(tfTotalNumber.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null,
                    "采购物品数量只能为整数",
                    "WARNING_MESSAGE",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }

        try {
            Float.valueOf(tfWeight.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null,
                    "请输入正确的物品重量",
                    "WARNING_MESSAGE",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }

        try {
            Float.valueOf(tfTotalPrice.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null,
                    "请输入正确的物品重量",
                    "WARNING_MESSAGE",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private void fillTextFieldData() {
        if (purchaseOrderInfo != null) {
            tfId.setText(purchaseOrderInfo.getId());
            tfThirdPartyOrderId.setText(purchaseOrderInfo.getThirdPartyOrderId());
            tfDate.setText(DateUtils.dateToStr(purchaseOrderInfo.getDate(), "yyyy-MM-dd"));
            tfDescription.setText(purchaseOrderInfo.getDescription());
            tfTotalNumber.setText(String.valueOf(purchaseOrderInfo.getItemTotalMunber()));
            tfUrl.setText(purchaseOrderInfo.getOrderUrl());
            tfExpressNumber.setText(purchaseOrderInfo.getOrderExpressNumber());
            tfWeight.setText(String.valueOf(purchaseOrderInfo.getWeight()));
            tfTotalPrice.setText(String.valueOf(purchaseOrderInfo.getTotalPrice()));
        }
    }

    private void fillComboBox() {
        if (lazadaOrderItemsInfoList != null && !lazadaOrderItemsInfoList.isEmpty()) {
            for (LazadaOrderItemsInfo lazadaOrderItemsInfo : lazadaOrderItemsInfoList) {
                String lazadaOrderItemId = String.valueOf(lazadaOrderItemsInfo.getLazadaOrderItemId());
                String sku = lazadaOrderItemsInfo.getSku();
                String tempStr = lazadaOrderItemId + "(" + sku + ")";
                cbLazadaOrderItemsInfos.addItem(tempStr);
            }
            if (viewType == ViewType.SHOW && purchaseOrderInfo != null) {
                LazadaOrderItemsInfo lazadaOrderItemsInfo = purchaseOrderInfo.getLazadaOrderItemsInfo();
                for (int i = 0; i < lazadaOrderItemsInfoList.size(); i++) {
                    if (lazadaOrderItemsInfoList.get(i).getId().equals(lazadaOrderItemsInfo.getId())) {
                        cbLazadaOrderItemsInfos.setSelectedIndex(i);
                    }
                }
            } else {
                cbLazadaOrderItemsInfos.setSelectedIndex(0);
            }
        }
    }

    private void setComponentEnable(boolean b) {
        if (viewType != ViewType.SHOW) {
            cbLazadaOrderItemsInfos.setEnabled(b);
            tfThirdPartyOrderId.setEditable(b);
            tfDescription.setEditable(b);
            tfTotalNumber.setEditable(b);
            tfUrl.setEditable(b);
            tfExpressNumber.setEditable(b);
            tfTotalPrice.setEditable(b);
            tfWeight.setEditable(b);
            if (b) {
                tfDate.addMouseListener(dateMouseAdapter);
            } else {
                tfDate.removeMouseListener(dateMouseAdapter);
            }
        }

        btnSaveOrEdit.setEnabled(b);
        btnBack.setEnabled(b);
        btnDelete.setEnabled(b);
    }

    private String getTodayString() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(new Date());
    }

    private void savePurchaseOrderInfo(PurchaseOrderInfo purchaseOrderInfo) {
        setComponentEnable(false);
        PurchaseOrderInfoRequest request = RetrofitManager.getInstance().getRetrofit().create(PurchaseOrderInfoRequest.class);
        Call<String> call = request.save(purchaseOrderInfo);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                setComponentEnable(true);
                if (response.code() == 200 && response.body().contains("New PurchaseOrderInfo has been saved with ID:")) {
                    JOptionPane.showMessageDialog(null,
                            "保存成功",
                            "INFORMATION_MESSAGE",
                            JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null,
                            "服务器异常",
                            "WARNING_MESSAGE",
                            JOptionPane.WARNING_MESSAGE);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                setComponentEnable(true);
                JOptionPane.showMessageDialog(null,
                        "发送请求失败:" + throwable.toString(),
                        "WARNING_MESSAGE",
                        JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    private void updatePurchaseOrderInfo(PurchaseOrderInfo purchaseOrderInfo) {
        setComponentEnable(false);
        PurchaseOrderInfoRequest request = RetrofitManager.getInstance().getRetrofit().create(PurchaseOrderInfoRequest.class);
        Call<String> call = request.update(purchaseOrderInfo.getId(), purchaseOrderInfo);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                setComponentEnable(true);
                if (response.code() == 200 && response.body().equals("Updated successfully")) {
                    JOptionPane.showMessageDialog(null,
                            "保存成功",
                            "INFORMATION_MESSAGE",
                            JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null,
                            "服务器异常",
                            "WARNING_MESSAGE",
                            JOptionPane.WARNING_MESSAGE);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                setComponentEnable(true);
                JOptionPane.showMessageDialog(null,
                        "发送请求失败:" + throwable.toString(),
                        "WARNING_MESSAGE",
                        JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    private void deletePurchaseOrderInfo(String id) {
        setComponentEnable(false);
        PurchaseOrderInfoRequest request = RetrofitManager.getInstance().getRetrofit().create(PurchaseOrderInfoRequest.class);
        Call<String> call = request.delete(id);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                setComponentEnable(true);
                if (response.code() == 200 && response.body().equals("Deleted successfully")) {
                    JOptionPane.showMessageDialog(null,
                            "删除成功",
                            "INFORMATION_MESSAGE",
                            JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null,
                            "服务器异常",
                            "WARNING_MESSAGE",
                            JOptionPane.WARNING_MESSAGE);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                setComponentEnable(true);
                JOptionPane.showMessageDialog(null,
                        "发送请求失败:" + throwable.toString(),
                        "WARNING_MESSAGE",
                        JOptionPane.WARNING_MESSAGE);
            }
        });
    }
}
