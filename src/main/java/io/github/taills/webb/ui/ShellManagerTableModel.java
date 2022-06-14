package io.github.taills.webb.ui;

import javax.swing.table.DefaultTableModel;

public class ShellManagerTableModel extends DefaultTableModel {
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == 2)
            return String.class;
        return super.getColumnClass(columnIndex);
    }

    @Override
    public String getColumnName(int column) {
        return super.getColumnName(column);
    }

    @Override
    public int getColumnCount() {
        return super.getColumnCount();
    }

    public ShellManagerTableModel() {
        this.setColumnIdentifiers(new String[]{"URL", "Payload", "Encryption", "Encoding", "Proxy", "Remark", "Tags", "CreateTime", "ModifyTime"});
    }
}
