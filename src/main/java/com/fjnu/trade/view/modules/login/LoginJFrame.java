package com.fjnu.trade.view.modules.login;

import com.fjnu.common.Constants;
import com.fjnu.common.http.RetrofitManager;
import com.fjnu.trade.http.request.PersonInfoRequest;
import com.fjnu.trade.model.PersonInfo;
import com.fjnu.trade.module.ServerConnectInfoManager;
import com.fjnu.trade.view.TradingSystemMainJFrame;
import org.apache.http.util.TextUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class LoginJFrame extends JFrame {

    private JPanel contentPane;
    private JTextField tfUserName;
    private JPasswordField pfPassword;
    private JLabel lblNewLabel;
    private JButton btnLogin;
    private JButton btnSetting;

    private LoginSettingJFrame loginSettingJFrame;

    private WindowListener loginSettingJFrameListener = new WindowListener() {
        @Override
        public void windowOpened(WindowEvent e) {
            LoginJFrame.this.setVisible(false);
        }

        @Override
        public void windowClosing(WindowEvent e) {
        }

        @Override
        public void windowClosed(WindowEvent e) {
            LoginJFrame.this.setVisible(true);
            refreshTitle();
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

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                LoginJFrame frame = new LoginJFrame();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the frame.
     */
    public LoginJFrame() {
        initView();
        initClickEvent();
        initData();
    }

    private void initView() {
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setSize(400, 222);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // 得到屏幕的尺寸
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        setLocation((screenWidth - this.getWidth()) / 2, (screenHeight - this.getHeight()) / 2);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel label = new JLabel("用户名：");
        label.setBounds(48, 35, 55, 15);
        contentPane.add(label);

        JLabel label_1 = new JLabel("密码：");
        label_1.setBounds(48, 70, 43, 15);
        contentPane.add(label_1);

        tfUserName = new JTextField();
        tfUserName.setBounds(113, 32, 195, 21);
        contentPane.add(tfUserName);
        tfUserName.setColumns(10);

        pfPassword = new JPasswordField();
        pfPassword.setBounds(113, 67, 195, 21);
        contentPane.add(pfPassword);
        pfPassword.setColumns(10);

        btnLogin = new JButton("登陆");
        btnLogin.setBounds(59, 106, 93, 23);
        contentPane.add(btnLogin);

        btnSetting = new JButton("设置");
        btnSetting.setBounds(223, 106, 93, 23);
        contentPane.add(btnSetting);

        JSeparator separator = new JSeparator();
        separator.setBackground(Color.GRAY);
        separator.setBounds(0, 145, 400, 1);
        contentPane.add(separator);

        lblNewLabel = new JLabel("Version : " + Constants.CLIENT_VERSION);
        lblNewLabel.setBounds(50, 156, 253, 20);
        contentPane.add(lblNewLabel);
    }

    private void initClickEvent() {
        btnLogin.addActionListener(e -> {
            boolean needShowErrorMessage = false;
            try {
                if (TextUtils.isEmpty(tfUserName.getText())
                        || TextUtils.isEmpty(new String(pfPassword.getPassword()))) {
                    needShowErrorMessage = true;
                }
            } catch (NullPointerException exception) {
                exception.printStackTrace();
                needShowErrorMessage = true;
            }
            if (needShowErrorMessage) {
                JOptionPane.showMessageDialog(null, "用户名及密码不能为空",
                        "WARNING_MESSAGE", JOptionPane.WARNING_MESSAGE);
                return;
            }

            btnLogin.setEnabled(false);
            login(tfUserName.getText(), new String(pfPassword.getPassword()));
        });

        btnSetting.addActionListener(e -> {
            loginSettingJFrame = new LoginSettingJFrame();
            loginSettingJFrame.addWindowListener(loginSettingJFrameListener);
            loginSettingJFrame.setVisible(true);
        });
    }

    private void initData() {
        refreshTitle();
    }

    private void refreshTitle() {
        String title = "贸易交易系统-登陆";
        if (ServerConnectInfoManager.getInstance().getServerConnectInfo().getServerConnectWay()
                == ServerConnectInfoManager.ServerConnectWayEnum.WAN_WAY) {
            title += "(外网）";
        } else {
            title += "(内网）";
        }
        setTitle(title);
    }

    private void login(String loginName, String password) {
        Retrofit retrofit = RetrofitManager.getInstance().getRetrofit();
        PersonInfoRequest personInfoRequest = retrofit.create(PersonInfoRequest.class);
        Call<PersonInfo> call = personInfoRequest.login(loginName, password);
        call.enqueue(new Callback<PersonInfo>() {
            @Override
            public void onResponse(Call<PersonInfo> call, Response<PersonInfo> response) {
                System.out.println("login response code:" + response.code());
                btnLogin.setEnabled(true);
                if (response.code() == 200) {
                    if (response.body() == null) {
                        JOptionPane.showMessageDialog(null, "用户名或密码错误",
                                "WARNING_MESSAGE", JOptionPane.WARNING_MESSAGE);
                    } else {
                        // 登陆成功
                        System.out.println(response.body().toString());
                        SwingUtilities.invokeLater(() -> {
                            TradingSystemMainJFrame mainJFrame = new TradingSystemMainJFrame();
                            mainJFrame.setPersonInfo(response.body());
                            mainJFrame.setVisible(true);
                            LoginJFrame.this.dispose();
                        });
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "服务器异常", "WARNING_MESSAGE", JOptionPane.WARNING_MESSAGE);
                }
            }

            @Override
            public void onFailure(Call<PersonInfo> call, Throwable throwable) {
                btnLogin.setEnabled(true);
                throwable.printStackTrace();
                JOptionPane.showMessageDialog(null, "发送请求失败:" + throwable.toString(), "WARNING_MESSAGE",
                        JOptionPane.WARNING_MESSAGE);
            }
        });
    }
}
