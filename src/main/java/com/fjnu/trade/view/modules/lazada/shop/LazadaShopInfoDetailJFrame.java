package com.fjnu.trade.view.modules.lazada.shop;

import com.fjnu.common.http.RetrofitManager;
import com.fjnu.trade.http.request.lazada.LazadaShopRequest;
import com.fjnu.trade.model.lazada.LazadaShopInfo;
import org.apache.http.util.TextUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.WindowListener;
import java.sql.Timestamp;

public class LazadaShopInfoDetailJFrame extends JFrame {

    private JPanel contentPane;
    private JTextField tfId;
    private JTextField tfShopName;
    private JTextField tfEmail;
    private JTextField tfSellerId;
    private JTextField tfCountryCode;
    private JButton btnSave;
    private JButton btnRemoveAuthorization;
    private JButton btnBack;

    private LazadaShopInfo lazadaShopInfo;

    public LazadaShopInfoDetailJFrame(LazadaShopInfo lazadaShopInfo) {
        this.lazadaShopInfo = lazadaShopInfo;

        initView();
        initClickEvent();
        initData();
    }

    private void initData() {
        if (lazadaShopInfo == null) {
            return;
        }
        tfId.setText(lazadaShopInfo.getId());
        tfId.setEditable(false);
        tfEmail.setText(lazadaShopInfo.getEmail());
        tfEmail.setEditable(false);
        tfSellerId.setText(lazadaShopInfo.getSellerId());
        tfSellerId.setEditable(false);
        tfShopName.setText(lazadaShopInfo.getShopName());
        tfCountryCode.setText(lazadaShopInfo.getCountryCode());
        tfCountryCode.setEditable(false);
    }

    private void initClickEvent() {
        btnSave.addActionListener(e -> {
            if (checkInfoFormat()) {
                updateLazadaShopInfo();
            } else {
                JOptionPane.showMessageDialog(LazadaShopInfoDetailJFrame.this,
                        "信息不完整",
                        "WARNING_MESSAGE",
                        JOptionPane.WARNING_MESSAGE);
            }
        });
        btnRemoveAuthorization.addActionListener(e -> removeShopAuthorization());
        btnBack.addActionListener(e -> {
            WindowListener[] windowListeners = getWindowListeners();
            for (WindowListener listener : windowListeners) {
                removeWindowListener(listener);
            }
            dispose();
        });
    }

    private void initView() {
        setTitle("编辑Lazada店铺信息");

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 469, 297);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblId = new JLabel("id：");
        lblId.setHorizontalAlignment(SwingConstants.CENTER);
        lblId.setBounds(35, 25, 84, 15);
        contentPane.add(lblId);

        tfId = new JTextField();
        tfId.setEditable(false);
        tfId.setBounds(129, 22, 272, 21);
        contentPane.add(tfId);
        tfId.setColumns(10);

        JLabel label = new JLabel("店铺名称：");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBounds(35, 56, 84, 15);
        contentPane.add(label);

        tfShopName = new JTextField();
        tfShopName.setColumns(10);
        tfShopName.setBounds(129, 53, 272, 21);
        contentPane.add(tfShopName);

        JLabel lblEmail = new JLabel("Email：");
        lblEmail.setHorizontalAlignment(SwingConstants.CENTER);
        lblEmail.setBounds(35, 84, 84, 15);
        contentPane.add(lblEmail);

        tfEmail = new JTextField();
        tfEmail.setColumns(10);
        tfEmail.setBounds(129, 81, 272, 21);
        contentPane.add(tfEmail);

        JLabel lblSellerId = new JLabel("Seller ID：");
        lblSellerId.setHorizontalAlignment(SwingConstants.CENTER);
        lblSellerId.setBounds(35, 112, 84, 15);
        contentPane.add(lblSellerId);

        tfSellerId = new JTextField();
        tfSellerId.setColumns(10);
        tfSellerId.setBounds(129, 109, 272, 21);
        contentPane.add(tfSellerId);

        JLabel label_3 = new JLabel("国家码：");
        label_3.setHorizontalAlignment(SwingConstants.CENTER);
        label_3.setBounds(35, 140, 84, 15);
        contentPane.add(label_3);

        tfCountryCode = new JTextField();
        tfCountryCode.setColumns(10);
        tfCountryCode.setBounds(129, 137, 272, 21);
        contentPane.add(tfCountryCode);

        btnSave = new JButton("保存");
        btnSave.setBounds(30, 178, 93, 23);
        contentPane.add(btnSave);

        btnRemoveAuthorization = new JButton("移除授权");
        btnRemoveAuthorization.setBounds(180, 178, 93, 23);
        contentPane.add(btnRemoveAuthorization);

        btnBack = new JButton("返回");
        btnBack.setBounds(325, 178, 93, 23);
        contentPane.add(btnBack);
    }

    private void setComponentEnable(boolean b) {
        tfShopName.setEditable(b);
        btnSave.setEnabled(b);
        btnRemoveAuthorization.setEnabled(b);
        btnBack.setEnabled(b);
    }

    /**
     * 判断数据是否符合标准
     *
     * @return
     */
    private boolean checkInfoFormat() {
        if (TextUtils.isEmpty(tfShopName.getText().trim())) {
            return false;
        }
        return true;
    }

    private void updateLazadaShopInfo() {
        lazadaShopInfo.setShopName(tfShopName.getText().trim());

        setComponentEnable(false);

        Retrofit retrofit = RetrofitManager.getInstance().getRetrofit();
        LazadaShopRequest request = retrofit.create(LazadaShopRequest.class);
        Call<String> call = request.update(lazadaShopInfo.getId(), lazadaShopInfo);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                setComponentEnable(true);
                if (response.code() == 200) {
                    if (response.body().equals("Updated successfully")) {
                        JOptionPane.showMessageDialog(null,
                                "修改成功",
                                "INFORMATION_MESSAGE",
                                JOptionPane.INFORMATION_MESSAGE);
                        dispose();
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
                setComponentEnable(true);
                throwable.printStackTrace();
                JOptionPane.showMessageDialog(null,
                        "发送请求失败:" + throwable.toString(),
                        "WARNING_MESSAGE",
                        JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    private void removeShopAuthorization() {
        lazadaShopInfo.setAccessToken("");
        lazadaShopInfo.setExpireeIn(0);
        lazadaShopInfo.setRefreshToken("");
        lazadaShopInfo.setRefreshExpireeIn(0);
        // FIXME: 2018/7/21 json数据传输的时候时间类型最好用字符串，不要用Timestamp，先用临时方案代替
        lazadaShopInfo.setLastTokenUpdateTime(new Timestamp(System.currentTimeMillis() - 1000 * 60 * 60 * 8));

        setComponentEnable(false);

        Retrofit retrofit = RetrofitManager.getInstance().getRetrofit();
        LazadaShopRequest request = retrofit.create(LazadaShopRequest.class);
        Call<String> call = request.update(lazadaShopInfo.getId(), lazadaShopInfo);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                setComponentEnable(true);
                if (response.code() == 200) {
                    if (response.body().equals("Updated successfully")) {
                        JOptionPane.showMessageDialog(null,
                                "移除授权成功",
                                "INFORMATION_MESSAGE",
                                JOptionPane.INFORMATION_MESSAGE);
                        dispose();
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
                setComponentEnable(true);
                throwable.printStackTrace();
                JOptionPane.showMessageDialog(null,
                        "发送请求失败:" + throwable.toString(),
                        "WARNING_MESSAGE",
                        JOptionPane.WARNING_MESSAGE);
            }
        });
    }
}
