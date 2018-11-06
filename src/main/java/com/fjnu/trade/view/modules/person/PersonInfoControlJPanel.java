package com.fjnu.trade.view.modules.person;

import com.fjnu.common.http.RetrofitManager;
import com.fjnu.trade.http.request.PersonInfoRequest;
import com.fjnu.trade.model.PersonInfo;
import com.fjnu.trade.view.ViewType;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.swing.*;

import java.awt.FlowLayout;
import java.awt.event.*;
import java.util.ArrayList;
import java.awt.BorderLayout;

import java.util.List;

public class PersonInfoControlJPanel extends JPanel {

    private JTable table;
    private JButton btnRefresh;
    private JButton btnAddPerson;

    private PersonInfoTableModel tableModel;
    private PersonInfoDetailJFrame personInfoDetailJFrame;

    private List<PersonInfo> personData = new ArrayList<>();

    private WindowListener windowListener = new WindowListener() {
        @Override
        public void windowOpened(WindowEvent e) {
        }

        @Override
        public void windowClosing(WindowEvent e) {
        }

        @Override
        public void windowClosed(WindowEvent e) {
            refreshDataFromServer();
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
     * Create the panel.
     */
    public PersonInfoControlJPanel() {
        initView();
        initData();
    }

    private void initView() {
        setLayout(new BorderLayout(0, 0));

        JPanel panel = new JPanel();
        add(panel, BorderLayout.NORTH);
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

        btnAddPerson = new JButton("添加");
        btnAddPerson.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if (personInfoDetailJFrame != null) {
                    personInfoDetailJFrame.removeWindowListener(windowListener);
                    personInfoDetailJFrame.dispose();
                    personInfoDetailJFrame = null;
                }

                personInfoDetailJFrame = new PersonInfoDetailJFrame(ViewType.SAVE);
                personInfoDetailJFrame.addWindowListener(windowListener);
                personInfoDetailJFrame.setVisible(true);
            }
        });
        panel.add(btnAddPerson);

        btnRefresh = new JButton("刷新数据");
        btnRefresh.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                refreshDataFromServer();
            }
        });
        panel.add(btnRefresh);

        table = new JTable();
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        //列的顺序不可拖动
        table.getTableHeader().setReorderingAllowed(false);
        //只可以选择单行
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //只可以选择列
        table.setRowSelectionAllowed(true);
        table.setColumnSelectionAllowed(false);
        //双击事件
        table.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    if (personInfoDetailJFrame != null) {
                        personInfoDetailJFrame.removeWindowListener(windowListener);
                        personInfoDetailJFrame.dispose();
                        personInfoDetailJFrame = null;
                    }

                    int rowIndex = table.rowAtPoint(e.getPoint());
                    PersonInfo personInfo = personData.get(rowIndex);

                    personInfoDetailJFrame = new PersonInfoDetailJFrame(ViewType.EDIT, personInfo);
                    personInfoDetailJFrame.addWindowListener(windowListener);
                    personInfoDetailJFrame.setVisible(true);
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

        tableModel = new PersonInfoTableModel(personData);
        table.setModel(tableModel);

        //将JTable放置于JScrollPane中才可以显示列信息
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                refreshDataFromServer();
            }
        }).start();
    }

    private void setAllButtonEnable(boolean isEnable) {
        btnAddPerson.setEnabled(isEnable);
        btnRefresh.setEnabled(isEnable);
    }

    private void refreshDataFromServer() {
        setAllButtonEnable(false);

        PersonInfoRequest request = RetrofitManager.getInstance().getRetrofit().create(PersonInfoRequest.class);
        Call<List<PersonInfo>> call = request.getAll();
        call.enqueue(new Callback<List<PersonInfo>>() {
            @Override
            public void onResponse(Call<List<PersonInfo>> call, Response<List<PersonInfo>> response) {
                setAllButtonEnable(true);
                if (response.code() == 200) {
                    personData.clear();
                    personData.addAll(response.body());
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
            public void onFailure(Call<List<PersonInfo>> call, Throwable throwable) {
                setAllButtonEnable(true);
                JOptionPane.showMessageDialog(null,
                        "发送请求失败:" + throwable.toString(),
                        "WARNING_MESSAGE",
                        JOptionPane.WARNING_MESSAGE);
            }
        });
    }

}
