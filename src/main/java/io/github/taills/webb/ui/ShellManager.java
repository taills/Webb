package io.github.taills.webb.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Shell 管理
 */
public class ShellManager {
    private JPanel mainPanel;
    private JTextField textFieldTagSearch;
    private JList listTags;
    private JTable tableShell;
    private JLabel labelStatus;
    private JPanel panelLeft;
    private JPanel panelRight;
    private JScrollPane scrollPaneTable;
    private JSplitPane splitPane1;
    private JScrollPane scrollPaneTags;
    private JToolBar toolBar;


    /**
     * shell 表格
     */
    private DefaultTableModel shellManagerTableModel;


    public ShellManager() {
    }

    /**
     * 获取主窗体
     *
     * @return
     */
    public JPanel getMainPanel() {
        return this.mainPanel;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here

        shellManagerTableModel = new DefaultTableModel();
        shellManagerTableModel.setColumnIdentifiers(new String[]{"URL", "Payload", "Encryption", "Encoding", "Proxy", "Remark", "Tags", "CreateTime", "ModifyTime"});
        tableShell = new JTable();
        tableShell.setModel(shellManagerTableModel);
        tableShell.setFillsViewportHeight(true);
        shellManagerTableModel.addRow(new String[]{"http://1.0.0.1", "JavaDynamicPayload", "JAVA_AES_BASE64", "UTF-8","NO_PROXY","备注","confluence","2022-01-01 12:34:56","2022-01-01 12:34:56"});
        shellManagerTableModel.addRow(new String[]{"http://2.0.0.1", "JavaDynamicPayload", "JAVA_AES_BASE64", "UTF-8","NO_PROXY","备注","wordpress","2022-01-01 12:34:56","2022-01-01 12:34:56"});
        shellManagerTableModel.addRow(new String[]{"http://3.0.0.1", "JavaDynamicPayload", "JAVA_AES_BASE64", "UTF-8","NO_PROXY","备注","test","2022-01-01 12:34:56","2022-01-01 12:34:56"});
        shellManagerTableModel.addRow(new String[]{"http://4.0.0.1", "JavaDynamicPayload", "JAVA_AES_BASE64", "UTF-8","NO_PROXY","备注","CTF","2022-01-01 12:34:56","2022-01-01 12:34:56"});

    }
}
