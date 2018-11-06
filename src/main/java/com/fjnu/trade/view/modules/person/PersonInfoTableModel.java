package com.fjnu.trade.view.modules.person;

import com.fjnu.trade.model.PersonInfo;

import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * Created by LCC on 2018/4/8.
 */
public class PersonInfoTableModel extends AbstractTableModel {

    private final String[] columnNames = {"id", "账户", "姓名", "密码", "权限"};

    private List<PersonInfo> data;

    public PersonInfoTableModel(List<PersonInfo> data) {
        this.data = data;
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col].toString();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int colIndex) {
        PersonInfo personInfo = data.get(rowIndex);
        switch (colIndex) {
            case 0:
                return personInfo.getId();
            case 1:
                return personInfo.getLoginName();
            case 2:
                return personInfo.getPersonName();
            case 3:
                return personInfo.getLoginPassword();
            case 4:
                return personInfo.getLevel_id();
            default:
                return "";
        }
    }
}
