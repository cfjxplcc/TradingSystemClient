package com.fjnu.trade.view;

import com.fjnu.common.Constants;
import com.fjnu.trade.model.PersonInfo;
import com.fjnu.trade.module.ServerConnectInfoManager;
import com.fjnu.trade.view.common.ButtonTabComponent;
import com.fjnu.trade.view.modules.lazada.order.LazadaOrderInfoControlJPanel;
import com.fjnu.trade.view.modules.person.PersonInfoControlJPanel;
import com.fjnu.trade.view.modules.lazada.shop.LazadaShopInfoControlJPanel;
import com.fjnu.trade.view.modules.lazada.summary.LazadaShopMonthlySummaryJPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class TradingSystemMainJFrame extends JFrame {

    private JPanel contentPane;
    private JTabbedPane tabbedPane;

    private PersonInfoControlJPanel personInfoControlJPanel;
    private LazadaShopInfoControlJPanel lazadaShopInfoControlJPanel;
    private LazadaOrderInfoControlJPanel lazadaOrderInfoControlJPanel;
    private LazadaShopMonthlySummaryJPanel lazadaShopMonthlySummaryJPanel;

    private PersonInfo personInfo;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                TradingSystemMainJFrame frame = new TradingSystemMainJFrame();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the frame.
     */
    public TradingSystemMainJFrame() {
        String title = "TradingSystemClient " + Constants.CLIENT_VERSION;
        if (ServerConnectInfoManager.getInstance().getServerConnectInfo().getServerConnectWay()
                == ServerConnectInfoManager.ServerConnectWayEnum.WAN_WAY) {
            title += " (外网）";
        } else {
            title += " (内网）";
        }
        setTitle(title);

        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 630);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); //得到屏幕的尺寸
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        setLocation((screenWidth - this.getWidth()) / 2, (screenHeight - this.getHeight()) / 2);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu menu = new JMenu("人员管理");
        if (Constants.IS_MANAGER_MODEL) {
            menuBar.add(menu);
        }

        JMenuItem menuItemAccount = new JMenuItem("账户管理");
        menuItemAccount.addActionListener(e -> {
            if (personInfoControlJPanel != null && tabbedPane.indexOfComponent(personInfoControlJPanel) != -1) {
                tabbedPane.setSelectedIndex(tabbedPane.indexOfComponent(personInfoControlJPanel));
            } else {
                personInfoControlJPanel = new PersonInfoControlJPanel();
                tabbedPane.addTab("账户管理", null, personInfoControlJPanel, null);
                initTabComponent(tabbedPane.getTabCount() - 1);
                tabbedPane.setSelectedIndex(tabbedPane.indexOfComponent(personInfoControlJPanel));
            }
        });
        menu.add(menuItemAccount);

        JMenu lazadaJMenu = new JMenu("Lazada订单系统");
        menuBar.add(lazadaJMenu);

        JMenuItem menuItem = new JMenuItem("Lazada店铺信息管理");
        menuItem.addActionListener(arg0 -> {
            if (lazadaShopInfoControlJPanel != null && tabbedPane.indexOfComponent(lazadaShopInfoControlJPanel) != -1) {
                tabbedPane.setSelectedIndex(tabbedPane.indexOfComponent(lazadaShopInfoControlJPanel));
            } else {
                lazadaShopInfoControlJPanel = new LazadaShopInfoControlJPanel();
                tabbedPane.addTab("Lazada店铺信息管理", null, lazadaShopInfoControlJPanel, null);
                initTabComponent(tabbedPane.getTabCount() - 1);
                tabbedPane.setSelectedIndex(tabbedPane.indexOfComponent(lazadaShopInfoControlJPanel));
            }
        });
        if (Constants.IS_MANAGER_MODEL) {
            lazadaJMenu.add(menuItem);
        }

        JMenuItem menuItem_1 = new JMenuItem("Lazada订单信息管理");
        menuItem_1.addActionListener(e -> {
            if (lazadaOrderInfoControlJPanel != null && tabbedPane.indexOfComponent(lazadaOrderInfoControlJPanel) != -1) {
                tabbedPane.setSelectedIndex(tabbedPane.indexOfComponent(lazadaOrderInfoControlJPanel));
            } else {
                lazadaOrderInfoControlJPanel = new LazadaOrderInfoControlJPanel();
                tabbedPane.addTab("Lazada订单信息管理", null, lazadaOrderInfoControlJPanel, null);
                initTabComponent(tabbedPane.getTabCount() - 1);
                tabbedPane.setSelectedIndex(tabbedPane.indexOfComponent(lazadaOrderInfoControlJPanel));
            }
        });
        lazadaJMenu.add(menuItem_1);

        JMenuItem menuItem_2 = new JMenuItem("Lazada统计信息");
        menuItem_2.addActionListener(e -> {
            if (lazadaShopMonthlySummaryJPanel != null && tabbedPane.indexOfComponent(lazadaShopMonthlySummaryJPanel) != -1) {
                tabbedPane.setSelectedIndex(tabbedPane.indexOfComponent(lazadaShopMonthlySummaryJPanel));
            } else {
                lazadaShopMonthlySummaryJPanel = new LazadaShopMonthlySummaryJPanel();
                tabbedPane.addTab("Lazada统计信息", null, lazadaShopMonthlySummaryJPanel, null);
                initTabComponent(tabbedPane.getTabCount() - 1);
                tabbedPane.setSelectedIndex(tabbedPane.indexOfComponent(lazadaShopMonthlySummaryJPanel));
            }
        });
        if (Constants.IS_MANAGER_MODEL) {
            lazadaJMenu.add(menuItem_2);
        }

        JMenu shopeeJMenu = new JMenu("Lazada订单系统");
        menuBar.add(shopeeJMenu);

        JMenuItem shopeeJMenuItem = new JMenuItem("Shopee店铺信息管理");
        menuItem.addActionListener(arg0 -> {
            // FIXME: 2019/1/14 
          /*  if (lazadaShopInfoControlJPanel != null && tabbedPane.indexOfComponent(lazadaShopInfoControlJPanel) != -1) {
                tabbedPane.setSelectedIndex(tabbedPane.indexOfComponent(lazadaShopInfoControlJPanel));
            } else {
                lazadaShopInfoControlJPanel = new LazadaShopInfoControlJPanel();
                tabbedPane.addTab("Lazada店铺信息管理", null, lazadaShopInfoControlJPanel, null);
                initTabComponent(tabbedPane.getTabCount() - 1);
                tabbedPane.setSelectedIndex(tabbedPane.indexOfComponent(lazadaShopInfoControlJPanel));
            }*/
        });
        if (Constants.IS_MANAGER_MODEL) {
            lazadaJMenu.add(shopeeJMenuItem);
        }

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        contentPane.add(tabbedPane, BorderLayout.CENTER);
    }

    private void initTabComponent(int i) {
        tabbedPane.setTabComponentAt(i, new ButtonTabComponent(tabbedPane));
    }

    public PersonInfo getPersonInfo() {
        return personInfo;
    }

    public void setPersonInfo(PersonInfo personInfo) {
        this.personInfo = personInfo;
    }
}
