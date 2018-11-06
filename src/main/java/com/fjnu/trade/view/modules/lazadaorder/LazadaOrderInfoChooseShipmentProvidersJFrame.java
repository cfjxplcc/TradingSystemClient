package com.fjnu.trade.view.modules.lazadaorder;

import com.fjnu.common.http.RetrofitManager;
import com.fjnu.trade.http.request.LazadaOrderInfoRequest;
import com.fjnu.trade.model.lazada.LazadaOrderInfo;
import com.lazada.platform.bean.ShipmentProviderBean;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class LazadaOrderInfoChooseShipmentProvidersJFrame extends JFrame {

    private JPanel contentPane;
    private JButton btnNext;
    private JButton btnBack;
    private JComboBox cbShipmentProviders;

    private LazadaOrderInfo lazadaOrderInfo;
    private List<ShipmentProviderBean> shipmentProviderList = new ArrayList<>();

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                LazadaOrderInfoChooseShipmentProvidersJFrame frame = new LazadaOrderInfoChooseShipmentProvidersJFrame(null);
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the frame.
     */
    public LazadaOrderInfoChooseShipmentProvidersJFrame(LazadaOrderInfo lazadaOrderInfo) {
        this.lazadaOrderInfo = lazadaOrderInfo;
        initView();
        getShipmentProvider(lazadaOrderInfo.getLazadaShopInfo().getId());
    }

    private void initView() {
        setTitle("选择仓库");
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setSize(465, 177);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); //得到屏幕的尺寸
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        setLocation((screenWidth - this.getWidth()) / 2, (screenHeight - this.getHeight()) / 2);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        cbShipmentProviders = new JComboBox();
        cbShipmentProviders.setBounds(66, 36, 311, 21);
        contentPane.add(cbShipmentProviders);

        btnNext = new JButton("下一步");
        btnNext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeOrderStatusToReadyToShip(lazadaOrderInfo.getId(), shipmentProviderList.get(cbShipmentProviders.getSelectedIndex()).getName());
            }
        });
        btnNext.setBounds(66, 94, 93, 23);
        contentPane.add(btnNext);

        btnBack = new JButton("返回");
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        btnBack.setBounds(261, 94, 93, 23);
        contentPane.add(btnBack);
    }

    private void setAllComponentEnable(boolean b) {
        btnNext.setEnabled(b);
        cbShipmentProviders.setEnabled(b);
        btnBack.setEnabled(b);
    }

    private void getShipmentProvider(String id) {
        setAllComponentEnable(false);
        LazadaOrderInfoRequest request = RetrofitManager.getInstance().getRetrofit().create(LazadaOrderInfoRequest.class);

        Call<List<ShipmentProviderBean>> call = request.getShipmentProviderByLazadaShopInfo(id);
        call.enqueue(new Callback<List<ShipmentProviderBean>>() {
            @Override
            public void onResponse(Call<List<ShipmentProviderBean>> call, Response<List<ShipmentProviderBean>> response) {
                if (response.code() == 200 && response.body() != null) {
                    shipmentProviderList.clear();
                    shipmentProviderList.addAll(response.body());
                    if (shipmentProviderList.isEmpty()) {
                        JOptionPane.showMessageDialog(null,
                                "无数据",
                                "INFORMATION_MESSAGE",
                                JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                    setAllComponentEnable(true);

                    cbShipmentProviders.removeAllItems();
                    //默认选中FM-40
                    boolean isSelected = false;
                    for (ShipmentProviderBean shipmentProvider : shipmentProviderList) {
                        cbShipmentProviders.addItem(shipmentProvider.getName());
                        if (shipmentProvider.getName().equals("FGS-FM40")) {
                            cbShipmentProviders.setSelectedItem(shipmentProvider.getName());
                            isSelected = true;
                        }
                    }
                    if (!shipmentProviderList.isEmpty() && !isSelected) {
                        cbShipmentProviders.setSelectedIndex(0);
                    }
                } else {
                    btnBack.setEnabled(true);
                    JOptionPane.showMessageDialog(null,
                            "服务器异常",
                            "WARNING_MESSAGE",
                            JOptionPane.WARNING_MESSAGE);
                }
            }

            @Override
            public void onFailure(Call<List<ShipmentProviderBean>> call, Throwable throwable) {
                btnBack.setEnabled(true);
                throwable.printStackTrace();
                cbShipmentProviders.removeAllItems();
                cbShipmentProviders.addItem("请求失败，请关闭对话框重试");
                cbShipmentProviders.setSelectedIndex(0);
                JOptionPane.showMessageDialog(null,
                        "发送请求失败:" + throwable.toString(),
                        "WARNING_MESSAGE",
                        JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    private void changeOrderStatusToReadyToShip(String orderId, String shippingProvider) {
        setAllComponentEnable(false);
        LazadaOrderInfoRequest request = RetrofitManager.getInstance().getRetrofit().create(LazadaOrderInfoRequest.class);

        Call<String> call = request.changeOrderStatusToReadyToShip(orderId, shippingProvider);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                setAllComponentEnable(true);

                if (response.code() == 200 && response.body() != null) {
                    System.out.println(response.body());
                    if (response.body().contains("true")) {
                        JOptionPane.showMessageDialog(null,
                                "更新状态成功，请手动刷新订单数据",
                                "INFORMATION_MESSAGE",
                                JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "更新状态失败(" + response.body() + ")",
                                "INFORMATION_MESSAGE",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null,
                            "服务器异常",
                            "WARNING_MESSAGE",
                            JOptionPane.WARNING_MESSAGE);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                btnBack.setEnabled(true);
                throwable.printStackTrace();
                JOptionPane.showMessageDialog(null,
                        "发送请求失败:" + throwable.toString(),
                        "WARNING_MESSAGE",
                        JOptionPane.WARNING_MESSAGE);
            }
        });
    }

}
