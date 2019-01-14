package com.fjnu.trade.view.modules.shop;

import com.fjnu.common.http.RetrofitManager;
import com.fjnu.common.utils.DesktopBrowseUtils;
import com.fjnu.trade.http.request.lazada.LazadaShopRequest;
import com.fjnu.trade.model.lazada.LazadaShopInfo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;

public class LazadaShopInfoControlJPanel extends JPanel {

    private JTable table;
    private JButton btnRefresh;
    private JButton btnAddShop;

    private LazadaShopInfoTableModel lazadaShopInfoTableModel;
    private LazadaShopInfoDetailJFrame lazadaShopInfoDetailJFrame;

    private List<LazadaShopInfo> shopData = new ArrayList<>();

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
    public LazadaShopInfoControlJPanel() {
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

        btnAddShop = new JButton("店铺授权");
        panel.add(btnAddShop);

        lazadaShopInfoTableModel = new LazadaShopInfoTableModel(shopData);

        table = new JTable();
        table.setModel(lazadaShopInfoTableModel);

        //设置列宽
        table.getColumnModel().getColumn(0).setPreferredWidth(250);
        table.getColumnModel().getColumn(1).setPreferredWidth(100);
        table.getColumnModel().getColumn(2).setPreferredWidth(150);
        table.getColumnModel().getColumn(3).setPreferredWidth(50);
        table.getColumnModel().getColumn(5).setPreferredWidth(130);
        table.getColumnModel().getColumn(7).setPreferredWidth(110);
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

        btnAddShop.addActionListener(e -> {
            getAuthorization();
        });

        //双击事件
        table.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    if (lazadaShopInfoDetailJFrame != null) {
                        lazadaShopInfoDetailJFrame.removeWindowListener(windowListener);
                        lazadaShopInfoDetailJFrame.dispose();
                        lazadaShopInfoDetailJFrame = null;
                    }

                    int rowIndex = table.rowAtPoint(e.getPoint());
                    LazadaShopInfo lazadaShopInfo = shopData.get(rowIndex);

                    lazadaShopInfoDetailJFrame = new LazadaShopInfoDetailJFrame(lazadaShopInfo);
                    lazadaShopInfoDetailJFrame.addWindowListener(windowListener);
                    lazadaShopInfoDetailJFrame.setVisible(true);
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
    }

    private void initData() {
        new Thread(() -> refreshDataFromServer()).start();
    }

    private void setAllButtonEnable(boolean isEnable) {
        btnAddShop.setEnabled(isEnable);
        btnRefresh.setEnabled(isEnable);
    }

    private void refreshDataFromServer() {
        setAllButtonEnable(false);

        LazadaShopRequest request = RetrofitManager.getInstance().getRetrofit().create(LazadaShopRequest.class);
        Call<List<LazadaShopInfo>> call = request.getAll();
        call.enqueue(new Callback<List<LazadaShopInfo>>() {
            @Override
            public void onResponse(Call<List<LazadaShopInfo>> call, Response<List<LazadaShopInfo>> response) {
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
            public void onFailure(Call<List<LazadaShopInfo>> call, Throwable throwable) {
                setAllButtonEnable(true);
                JOptionPane.showMessageDialog(null,
                        "发送请求失败:" + throwable.toString(),
                        "WARNING_MESSAGE",
                        JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    private void getAuthorization() {
        setAllButtonEnable(false);

        LazadaShopRequest request = RetrofitManager.getInstance().getRetrofit().create(LazadaShopRequest.class);
        Call<String> call = request.getAuthorizationUrl();
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

}
