package com.fjnu.trade.view.modules.person;

import com.fjnu.common.http.RetrofitManager;
import com.fjnu.trade.http.request.PersonInfoRequest;
import com.fjnu.trade.model.PersonInfo;
import com.fjnu.trade.view.ViewType;
import org.apache.http.util.TextUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class PersonInfoDetailJFrame extends JFrame {

    private JPanel contentPane;
    private JTextField tfId;
    private JTextField tfLoginName;
    private JTextField tfPassword;
    private JTextField tfPersonName;
    private JButton btnSave;
    private JButton btnBack;
    private JButton btnDelete;

    private PersonInfo personInfo;
    private ViewType type;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    PersonInfoDetailJFrame frame = new PersonInfoDetailJFrame(ViewType.SAVE);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public PersonInfoDetailJFrame(ViewType type, PersonInfo personInfo) {
        this.type = type;
        switch (type) {
            case SAVE:
                setTitle("添加新账户");
                break;
            case EDIT:
                setTitle("编辑账户信息");
                break;
        }
        this.personInfo = personInfo;
        initView();
        if (type == ViewType.EDIT) {
            initData();
        }
    }

    public PersonInfoDetailJFrame(ViewType type) {
        this(type, null);
    }

    private void initView() {
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 438, 285);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblId = new JLabel("id：");
        lblId.setHorizontalAlignment(SwingConstants.CENTER);
        lblId.setBounds(43, 20, 54, 15);
        contentPane.add(lblId);

        tfId = new JTextField();
        tfId.setEditable(false);
        tfId.setBounds(121, 17, 247, 21);
        contentPane.add(tfId);
        tfId.setColumns(10);

        JLabel label = new JLabel("账户：");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBounds(82, 56, 54, 15);
        contentPane.add(label);

        tfLoginName = new JTextField();
        tfLoginName.setBounds(172, 53, 148, 21);
        contentPane.add(tfLoginName);
        tfLoginName.setColumns(10);

        JLabel label_1 = new JLabel("密码：");
        label_1.setHorizontalAlignment(SwingConstants.CENTER);
        label_1.setBounds(82, 94, 54, 15);
        contentPane.add(label_1);

        tfPassword = new JTextField();
        tfPassword.setBounds(172, 91, 148, 21);
        contentPane.add(tfPassword);
        tfPassword.setColumns(10);

        JLabel label_2 = new JLabel("姓名：");
        label_2.setHorizontalAlignment(SwingConstants.CENTER);
        label_2.setBounds(82, 136, 54, 15);
        contentPane.add(label_2);

        tfPersonName = new JTextField();
        tfPersonName.setColumns(10);
        tfPersonName.setBounds(172, 133, 148, 21);
        contentPane.add(tfPersonName);

        btnSave = new JButton("保存");
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (checkInfoFormat()) {
                    switch (type) {
                        case EDIT:
                            updatePerson();
                            break;
                        case SAVE:
                            PersonInfo personInfo = new PersonInfo();
                            personInfo.setLoginName(tfLoginName.getText().trim());
                            personInfo.setLoginPassword(tfPassword.getText().trim());
                            personInfo.setPersonName(tfPersonName.getText().trim());
                            addPerson(personInfo);
                            break;
                    }
                } else {
                    JOptionPane.showMessageDialog(null,
                            "信息不完整",
                            "WARNING_MESSAGE",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        btnSave.setBounds(43, 191, 93, 23);
        contentPane.add(btnSave);

        btnDelete = new JButton("删除");
        btnDelete.setVisible(false);
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deletePerson();
            }
        });
        btnDelete.setBounds(165, 191, 93, 23);
        contentPane.add(btnDelete);

        btnBack = new JButton("返回");
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WindowListener[] windowListeners = getWindowListeners();
                for (WindowListener listener : windowListeners) {
                    removeWindowListener(listener);
                }
                dispose();
            }
        });
        btnBack.setBounds(274, 191, 93, 23);
        contentPane.add(btnBack);
    }

    private boolean checkInfoFormat() {
        // TODO: 2018/4/9 数据格式要求还未添加
        if (TextUtils.isEmpty(tfLoginName.getText().trim())
                || TextUtils.isEmpty(tfPassword.getText().trim())
                || TextUtils.isEmpty(tfPersonName.getText().trim())) {
            return false;
        }
        return true;
    }

    private void initData() {
        if (personInfo == null) {
            return;
        }
        tfId.setText(personInfo.getId());
        tfLoginName.setText(personInfo.getLoginName());
        tfLoginName.setEditable(false);
        tfPassword.setText(personInfo.getLoginPassword());
        tfPersonName.setText(personInfo.getPersonName());
        btnDelete.setVisible(true);
    }

    private void setComponentEnable(boolean b) {
        if (type != ViewType.EDIT) {
            //编辑模式不能更改用户账号
            tfLoginName.setEditable(b);
        }
        tfPassword.setEditable(b);
        tfPersonName.setEditable(b);
        btnSave.setEnabled(b);
        btnDelete.setEnabled(b);
        btnBack.setEnabled(b);
    }

    private void addPerson(PersonInfo personInfo) {
        setComponentEnable(false);

        Retrofit retrofit = RetrofitManager.getInstance().getRetrofit();
        PersonInfoRequest request = retrofit.create(PersonInfoRequest.class);
        Call<String> call = request.save(personInfo);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                setComponentEnable(true);
                if (response.code() == 200) {
                    if (response.body().contains("New PersonInfo has been saved with ID")) {
                        JOptionPane.showMessageDialog(null,
                                "添加成功",
                                "INFORMATION_MESSAGE",
                                JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                    } else if (response.body().equals("LoginName is existed")) {
                        JOptionPane.showMessageDialog(null,
                                "已经存在该账户名",
                                "WARNING_MESSAGE",
                                JOptionPane.WARNING_MESSAGE);
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

    private void updatePerson() {
        personInfo.setLoginPassword(tfPassword.getText().trim());
        personInfo.setPersonName(tfPersonName.getText().trim());

        setComponentEnable(false);

        Retrofit retrofit = RetrofitManager.getInstance().getRetrofit();
        PersonInfoRequest request = retrofit.create(PersonInfoRequest.class);
        Call<String> call = request.update(personInfo.getId(), personInfo);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                setComponentEnable(true);
                if (response.code() == 200) {
                    if (response.body().equals("update successful")) {
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

    private void deletePerson() {
        setComponentEnable(false);

        Retrofit retrofit = RetrofitManager.getInstance().getRetrofit();
        PersonInfoRequest request = retrofit.create(PersonInfoRequest.class);
        Call<String> call = request.delete(personInfo.getId());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                setComponentEnable(true);
                if (response.code() == 200) {
                    if (response.body().equals("delete successful")) {
                        JOptionPane.showMessageDialog(null,
                                "删除成功",
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
