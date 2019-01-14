package com.fjnu.trade.view.modules.login;

import com.fjnu.common.Constants;
import com.fjnu.common.http.RetrofitManager;
import com.fjnu.trade.http.request.ClientConnectRequest;
import com.fjnu.trade.module.ServerConnectInfoManager;
import okhttp3.ResponseBody;
import org.apache.http.util.TextUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LoginSettingJFrame extends JFrame {

    private JPanel contentPane;
    private JTextField tfServerAddress;
    private JButton btnConnectServerTest;
    private JButton btnSave;
    private JButton btnBack;
    private JRadioButton rdbtnLAN;
    private JRadioButton rdbtnWAN;

    private ServerConnectInfoManager.ServerConnectInfo serverConnectInfo;

    /**
     * Create the frame.
     */
    public LoginSettingJFrame() {
        initView();
        initClickEvent();
        initData();
    }

    private void initView() {
        setResizable(false);
        setTitle("贸易交易系统-登陆 / 设置");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setSize(400, 222);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // 得到屏幕的尺寸
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        setLocation((screenWidth - this.getWidth()) / 2, (screenHeight - this.getHeight()) / 2);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 394, 193);
        contentPane.add(panel);
        panel.setLayout(null);

        JLabel lblip = new JLabel("服务器地址");
        lblip.setHorizontalAlignment(SwingConstants.CENTER);
        lblip.setBounds(53, 36, 76, 15);
        panel.add(lblip);

        tfServerAddress = new JTextField();
        tfServerAddress.setBounds(152, 33, 185, 21);
        panel.add(tfServerAddress);
        tfServerAddress.setColumns(10);

        btnConnectServerTest = new JButton("连接测试");
        btnConnectServerTest.setBounds(221, 71, 93, 23);
        panel.add(btnConnectServerTest);

        btnSave = new JButton("保存");
        btnSave.setBounds(50, 111, 93, 23);
        panel.add(btnSave);

        btnBack = new JButton("返回");
        btnBack.setBounds(244, 111, 93, 23);
        panel.add(btnBack);

        JSeparator separator = new JSeparator();
        separator.setBounds(0, 150, 394, 2);
        panel.add(separator);

        JLabel label = new JLabel("Version : " + Constants.CLIENT_VERSION);
        label.setBounds(25, 162, 253, 20);
        panel.add(label);

        rdbtnLAN = new JRadioButton("内网");
        rdbtnLAN.setBounds(73, 71, 70, 23);
        panel.add(rdbtnLAN);

        rdbtnWAN = new JRadioButton("外网");
        rdbtnWAN.setBounds(142, 71, 70, 23);
        panel.add(rdbtnWAN);
    }

    private void initClickEvent() {
        rdbtnLAN.addActionListener(e -> {
            rdbtnWAN.setSelected(false);
            tfServerAddress.setText(serverConnectInfo.getServerLANAddress());
        });

        rdbtnWAN.addActionListener(e -> {
            rdbtnLAN.setSelected(false);
            tfServerAddress.setText(serverConnectInfo.getServerWANAddress());
        });

        btnConnectServerTest.addActionListener(e -> {
            if (!TextUtils.isEmpty(tfServerAddress.getText().trim())) {
                connectServerTest(getServerUrl(tfServerAddress.getText().trim()));
            } else {
                JOptionPane.showMessageDialog(null,
                        "服务器地址不能为空",
                        "WARNING_MESSAGE",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        btnSave.addActionListener(e -> {
            if (TextUtils.isEmpty(tfServerAddress.getText().trim())) {
                JOptionPane.showMessageDialog(null,
                        "服务器地址不能为空",
                        "WARNING_MESSAGE",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (rdbtnLAN.isSelected()) {
                serverConnectInfo.setServerConnectWay(ServerConnectInfoManager.ServerConnectWayEnum.LAN_WAY);
                serverConnectInfo.setServerLANAddress(tfServerAddress.getText().trim());
            } else {
                serverConnectInfo.setServerConnectWay(ServerConnectInfoManager.ServerConnectWayEnum.WAN_WAY);
                serverConnectInfo.setServerWANAddress(tfServerAddress.getText().trim());
            }

            if (ServerConnectInfoManager.getInstance().writeServerConnectInfoIntoLocalFile(serverConnectInfo)) {
                JOptionPane.showMessageDialog(null,
                        "保存成功",
                        "INFORMATION_MESSAGE",
                        JOptionPane.INFORMATION_MESSAGE);
                RetrofitManager.getInstance().resetBaseUrl(getServerUrl(tfServerAddress.getText().trim()));
                LoginSettingJFrame.this.dispose();
            } else {
                JOptionPane.showMessageDialog(null,
                        "保存失败",
                        "WARNING_MESSAGE",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        btnBack.addActionListener(e -> dispose());
    }

    private void initData() {
        serverConnectInfo = ServerConnectInfoManager.getInstance().getServerConnectInfo();

        String serverUrl;
        switch (serverConnectInfo.getServerConnectWay()) {
            case WAN_WAY:
                rdbtnLAN.setSelected(false);
                rdbtnWAN.setSelected(true);
                serverUrl = serverConnectInfo.getServerWANAddress();
                break;
            case LAN_WAY:
            default:
                rdbtnLAN.setSelected(true);
                rdbtnWAN.setSelected(false);
                serverUrl = serverConnectInfo.getServerLANAddress();
                break;
        }

        if (TextUtils.isEmpty(serverUrl)) {
            serverUrl = Constants.SERVER_DEFAULT_IP;
        }
        tfServerAddress.setText(serverUrl);
    }

    private void connectServerTest(String baseUrl) {
        setComponentEnable(false);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl).build();
        ClientConnectRequest clientConnectRequest = retrofit.create(ClientConnectRequest.class);
        Call<ResponseBody> repos = clientConnectRequest.connect();
        repos.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                setComponentEnable(true);
                if (response.code() == 200) {
                    JOptionPane.showMessageDialog(null,
                            "连接成功",
                            "INFORMATION_MESSAGE",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null,
                            "服务器异常",
                            "WARNING_MESSAGE",
                            JOptionPane.WARNING_MESSAGE);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                throwable.printStackTrace();
                setComponentEnable(true);
                JOptionPane.showMessageDialog(null,
                        "连接失败!请检查ip地址是否正确或电脑是否正常联网",
                        "WARNING_MESSAGE",
                        JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    private String getServerUrl(String ip) {
        return "http://" + ip + "/trading_system_server/";
    }

    private void setComponentEnable(boolean enable) {
        btnSave.setEnabled(enable);
        btnBack.setEnabled(enable);
        btnConnectServerTest.setEnabled(enable);
        rdbtnWAN.setEnabled(enable);
        rdbtnLAN.setEnabled(enable);
        tfServerAddress.setEditable(enable);
    }
}
