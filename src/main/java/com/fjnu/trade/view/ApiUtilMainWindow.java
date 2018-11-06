package com.fjnu.trade.view;

import com.fjnu.trade.view.common.calendar.CalendarPanel;
import org.apache.http.util.TextUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Lazada平台数据抓取、导出成excel 应用主工程类
 */
public class ApiUtilMainWindow {

    private JFrame frame;
    private JTextField tfBeginTime;
    private JTextField tfEndTime;
    private JTextField tfFilePath;
    private JButton btnChooseFilePath;
    private JProgressBar progressBar;
    private JLabel lbProgressHint;
    private JButton btnGetData;
    private JComboBox cbRequestServer;
    private JComboBox cbOrderStatus;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        System.out.println("主程序开始运行");
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ApiUtilMainWindow window = new ApiUtilMainWindow();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public ApiUtilMainWindow() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 700, 428);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        panel.setLayout(null);

        JLabel label = new JLabel("数据源选择");
        label.setBounds(40, 38, 65, 15);
        panel.add(label);

       /* cbRequestServer = new JComboBox(LazadaApiController.getInstance().getApiKeyMapsNames().toArray());
        cbRequestServer.setBounds(154, 35, 296, 21);
        cbRequestServer.setSelectedIndex(0);
        panel.add(cbRequestServer);*/

        JSeparator separator_1 = new JSeparator();
        separator_1.setBounds(0, 76, 700, 2);
        panel.add(separator_1);

        JLabel label_1 = new JLabel("起始时间");
        label_1.setBounds(45, 104, 54, 15);
        panel.add(label_1);

        tfBeginTime = new JTextField();
        tfBeginTime.setEditable(false);
        tfBeginTime.setBounds(123, 101, 132, 21);
        tfBeginTime.setColumns(10);
        panel.add(tfBeginTime);

        CalendarPanel.CalendarPanelVisibleChangeCallback callback = new CalendarPanel.CalendarPanelVisibleChangeCallback() {
            public void afterShow() {
                btnGetData.setEnabled(false);
            }

            public void afterHide() {
                btnGetData.setEnabled(true);
            }
        };

        //给 tfBeginTime 空间添加弹出日历逻辑
        // 定义日历控件面板类
        CalendarPanel calendarPanelBegin = new CalendarPanel(tfBeginTime, "yyyy-MM-dd");
        calendarPanelBegin.initCalendarPanel(callback);
        panel.add(calendarPanelBegin, 0);

        JLabel lblNewLabel = new JLabel("结束时间");
        lblNewLabel.setBounds(325, 104, 54, 15);
        panel.add(lblNewLabel);

        tfEndTime = new JTextField();
        tfEndTime.setEditable(false);
        tfEndTime.setBounds(401, 101, 137, 21);
        panel.add(tfEndTime);
        tfEndTime.setColumns(10);

        //给 tfBeginTime 空间添加弹出日历逻辑
        // 定义日历控件面板类
        CalendarPanel calendarPanelEnd = new CalendarPanel(tfEndTime, "yyyy-MM-dd");
        calendarPanelEnd.initCalendarPanel(callback);
        panel.add(calendarPanelEnd, 0);

        JLabel label_2 = new JLabel("订单状态");
        label_2.setBounds(45, 172, 54, 15);
        panel.add(label_2);

      /*  cbOrderStatus = new JComboBox(OrdersFilter.Status.values());
        cbOrderStatus.addItem("All");
        cbOrderStatus.setSelectedItem("All");
        cbOrderStatus.setBounds(121, 169, 164, 21);
        panel.add(cbOrderStatus);*/

        JLabel label_3 = new JLabel("数据保存路径");
        label_3.setBounds(33, 235, 78, 15);
        panel.add(label_3);

        tfFilePath = new JTextField();
        tfFilePath.setBounds(123, 232, 390, 21);
        tfFilePath.setColumns(10);
        panel.add(tfFilePath);

        btnChooseFilePath = new JButton("路径选择");
        btnChooseFilePath.setBounds(523, 231, 93, 23);
        btnChooseFilePath.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser jfc = new JFileChooser();
                jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                jfc.showDialog(new JLabel(), "选择");
                File file = jfc.getSelectedFile();
                if (file == null) {
                    return;
                }
                if (file.isDirectory()) {
                    System.out.println("文件夹:" + file.getAbsolutePath());
                } else if (file.isFile()) {
                    System.out.println("文件:" + file.getAbsolutePath());
                }
                tfFilePath.setText(jfc.getSelectedFile().getAbsolutePath());
            }
        });
        panel.add(btnChooseFilePath);

        btnGetData = new JButton("开始获取数据");
        btnGetData.setBounds(260, 327, 120, 23);
        btnGetData.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                progressBar.setVisible(false);
                lbProgressHint.setVisible(false);
                lbProgressHint.setForeground(Color.BLACK);

                if (TextUtils.isEmpty(tfFilePath.getText().trim())) {
                    lbProgressHint.setVisible(true);
                    lbProgressHint.setText("必须选择数据保存路径");
                    lbProgressHint.setForeground(Color.RED);
                    return;
                }

                final String beginTime = tfBeginTime.getText();
                final String endTIme = tfEndTime.getText();

                // 判断选取时间
                if (!TextUtils.isEmpty(beginTime.trim()) && !TextUtils.isEmpty(endTIme.trim())) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        Date begin = simpleDateFormat.parse(beginTime);
                        Date end = simpleDateFormat.parse(endTIme);
                        if (begin.getTime() > end.getTime()) {
                            lbProgressHint.setVisible(true);
                            lbProgressHint.setText("起始时间不能大于结束时间");
                            lbProgressHint.setForeground(Color.RED);
                            return;
                        }
                    } catch (ParseException e1) {
                        e1.printStackTrace();
                        lbProgressHint.setVisible(true);
                        lbProgressHint.setText("格式化时间出错，请重新选择时间");
                        lbProgressHint.setForeground(Color.RED);
                    }
                }

                final String apiKeyMapsName = cbRequestServer.getSelectedItem().toString();
                final String orderStatus = cbOrderStatus.getSelectedItem().toString();


                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                final String filePath = tfFilePath.getText().trim()
                        + File.separator + cbRequestServer.getSelectedItem().toString()
                        + "_" + simpleDateFormat.format(new Date()) + ".xls";

                //回调事件
                /*new Thread(() -> LazadaApiController.getInstance().generateExcel(apiKeyMapsName, orderStatus, beginTime, endTIme, filePath, new GenerateExcelCallback() {
                    public void onStart() {
                        lbProgressHint.setVisible(true);
                        lbProgressHint.setText("开始获取数据");
                        lbProgressHint.setForeground(Color.BLACK);
                        btnGetData.setEnabled(false);
                    }

                    public void onProcessing(int percent, String msg) {
                        lbProgressHint.setVisible(true);
                        lbProgressHint.setText(msg);
                        lbProgressHint.setForeground(Color.BLACK);
                    }

                    public void onError(Exception e12) {
                        lbProgressHint.setVisible(true);
                        if (e12.toString().contains("timed out")) {
                            lbProgressHint.setText("网络请求超时，请重试");
                        } else {
                            lbProgressHint.setText("获取失败");
                        }
                        lbProgressHint.setForeground(Color.RED);
                        btnGetData.setEnabled(true);

                        //删除文件
                        new Thread(() -> {
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e1) {
                                e1.printStackTrace();
                            }
                            File file = new File(filePath);
                            if (file.exists()) {
                                file.delete();
                            }
                        }).start();
                    }

                    public void onFinish(String resultStr) {
                        lbProgressHint.setVisible(true);
                        lbProgressHint.setText(resultStr);
                        lbProgressHint.setForeground(Color.BLACK);
                        btnGetData.setEnabled(true);
                    }
                })).start();*/
            }
        });
        panel.add(btnGetData);

        progressBar = new JProgressBar();
        progressBar.setBounds(86, 286, 448, 14);
        panel.add(progressBar);

        lbProgressHint = new JLabel();
        lbProgressHint.setBounds(200, 263, 240, 15);
        panel.add(lbProgressHint);

        progressBar.setVisible(false);
        lbProgressHint.setVisible(false);
    }

}
