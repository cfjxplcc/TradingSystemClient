package com.fjnu.trade.view.common.calendar;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Created by LCC on 2018/4/13.
 */
public class CalendarJDialog extends JDialog implements CalendarPanel.CalendarPanelVisibleChangeCallback {

    private CalendarPanel calendarPanel;

    private final JPanel contentPanel = new JPanel();

    /**
     * Create the dialog.
     */
    public CalendarJDialog(Object component, String dateFormat) {
        super(JOptionPane.getRootFrame(), true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); //得到屏幕的尺寸
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        setSize(276, 300);
        setLocation((screenWidth - this.getWidth()) / 2, (screenHeight - this.getHeight()) / 2);

        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(new BorderLayout(0, 0));

        calendarPanel = new CalendarPanel(component, dateFormat);
        calendarPanel.setVisibleCallback(this);
        contentPanel.add(calendarPanel, BorderLayout.CENTER);
    }

    @Override
    public void afterShow() {

    }

    @Override
    public void afterHide() {
        dispose();
    }
}
