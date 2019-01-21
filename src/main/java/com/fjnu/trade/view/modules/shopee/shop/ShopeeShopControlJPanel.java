package com.fjnu.trade.view.modules.shopee.shop;

import com.fjnu.common.http.RetrofitManager;
import com.fjnu.common.utils.DesktopBrowseUtils;
import com.fjnu.trade.http.request.shopee.ShopeeShopRequest;
import com.fjnu.trade.model.shopee.ShopeeShopInfo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ShopeeShopControlJPanel extends JPanel {

    private JTable table;
    private JButton btnRefresh;
    private JButton btnAuthorization;
    private JButton btnCancelAuthorization;

    private ShopeeShopInfoTableModel shopeeShopInfoTableModel;

    private List<ShopeeShopInfo> shopData = new ArrayList<>();

    /**
     * Create the panel.
     */
    public ShopeeShopControlJPanel() {
        initView();
        initClickEvent();
        initData();
    }

    private void initView() {
        setLayout(new BorderLayout(0, 0));

        JPanel panel = new JPanel();
        add(panel, BorderLayout.NORTH);
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

        btnRefresh = new JButton("刷新数据");
        panel.add(btnRefresh);

        btnAuthorization = new JButton("店铺授权");
        panel.add(btnAuthorization);

        btnCancelAuthorization = new JButton("取消店铺授权");
        panel.add(btnCancelAuthorization);

        shopeeShopInfoTableModel = new ShopeeShopInfoTableModel(shopData);

        table = new JTable();
        table.setModel(shopeeShopInfoTableModel);

        //设置列宽
        table.getColumnModel().getColumn(0).setPreferredWidth(100);
        table.getColumnModel().getColumn(1).setPreferredWidth(150);
        table.getColumnModel().getColumn(2).setPreferredWidth(100);
        table.getColumnModel().getColumn(3).setPreferredWidth(100);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        //列的顺序不可拖动
        table.getTableHeader().setReorderingAllowed(false);
        // 只可以选择单行
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        // 只可以选择列
        table.setRowSelectionAllowed(true);
        table.setColumnSelectionAllowed(false);

        JScrollPane scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void initClickEvent() {
        btnRefresh.addActionListener(e -> refreshDataFromServer());

        btnAuthorization.addActionListener(e -> {
            getAuthorizationUrl();
        });

        btnCancelAuthorization.addActionListener(e -> {
            getCancelAuthorizationUrl();
        });
    }

    private void initData() {
        new Thread(() -> refreshDataFromServer()).start();
    }

    private void setAllButtonEnable(boolean isEnable) {
        btnAuthorization.setEnabled(isEnable);
        btnRefresh.setEnabled(isEnable);
        btnCancelAuthorization.setEnabled(isEnable);
    }

    private void refreshDataFromServer() {
        setAllButtonEnable(false);

        ShopeeShopRequest request = RetrofitManager.getInstance().getRetrofit().create(ShopeeShopRequest.class);
        Call<List<ShopeeShopInfo>> call = request.getShopeeShopInfoAll();
        call.enqueue(new Callback<List<ShopeeShopInfo>>() {
            @Override
            public void onResponse(Call<List<ShopeeShopInfo>> call, Response<List<ShopeeShopInfo>> response) {
                setAllButtonEnable(true);
                if (response.code() == 200) {
                    shopData.clear();
                    shopData.addAll(response.body());
                    table.validate();
                    table.updateUI();
                } else {
                    JOptionPane.showMessageDialog(null,
                            "服务器异常",
                            "WARNING_MESSAGE",
                            JOptionPane.WARNING_MESSAGE);
                }
            }

            @Override
            public void onFailure(Call<List<ShopeeShopInfo>> call, Throwable throwable) {
                setAllButtonEnable(true);
                JOptionPane.showMessageDialog(null,
                        "发送请求失败:" + throwable.toString(),
                        "WARNING_MESSAGE",
                        JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    private void getAuthorizationUrl() {
        setAllButtonEnable(false);

        ShopeeShopRequest request = RetrofitManager.getInstance().getRetrofit().create(ShopeeShopRequest.class);
        Call<String> call = request.getShopeeAuthorizationUrl();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                setAllButtonEnable(true);
                if (response.code() == 200) {
                    System.out.println("AuthorizationUrl:" + response.body());
                    DesktopBrowseUtils.useDesktopBrowseOpenUrl(response.body());
                } else {
                    JOptionPane.showMessageDialog(null,
                            "服务器异常",
                            "WARNING_MESSAGE",
                            JOptionPane.WARNING_MESSAGE);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                setAllButtonEnable(true);
                JOptionPane.showMessageDialog(null,
                        "发送请求失败:" + throwable.toString(),
                        "WARNING_MESSAGE",
                        JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    private void getCancelAuthorizationUrl() {
        setAllButtonEnable(false);

        ShopeeShopRequest request = RetrofitManager.getInstance().getRetrofit().create(ShopeeShopRequest.class);
        Call<String> call = request.getShopeeCancelAuthorizationUrl();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                setAllButtonEnable(true);
                if (response.code() == 200) {
                    System.out.println("CancelAuthorizationUrl:" + response.body());
                    DesktopBrowseUtils.useDesktopBrowseOpenUrl(response.body());
                } else {
                    JOptionPane.showMessageDialog(null,
                            "服务器异常",
                            "WARNING_MESSAGE",
                            JOptionPane.WARNING_MESSAGE);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                setAllButtonEnable(true);
                JOptionPane.showMessageDialog(null,
                        "发送请求失败:" + throwable.toString(),
                        "WARNING_MESSAGE",
                        JOptionPane.WARNING_MESSAGE);
            }
        });
    }

}
