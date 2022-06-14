package io.github.taills.webb.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

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
        textFieldTagSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode() == 10) {
                    shellManagerTableModel.addRow(new String[]{"http://4.0.0.1", "JavaDynamicPayload", "JAVA_AES_BASE64", "UTF-8", "NO_PROXY", textFieldTagSearch.getText(), "CTF", "2022-01-01 12:34:56", "2022-01-01 12:34:56"});
                }
            }
        });
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

        shellManagerTableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column >= 7) {
                    return false;
                }
                return super.isCellEditable(row, column);
            }
        };
        shellManagerTableModel.setColumnIdentifiers(new String[]{"URL", "Payload", "Encryption", "Encoding", "Proxy", "Remark", "Tags", "CreateTime", "ModifyTime"});
        shellManagerTableModel.addRow(new String[]{"http://3.0.0.1", "JavaDynamicPayload", "JAVA_AES_BASE64", "UTF-8", "NO_PROXY", "Remark", "CTF", "2022-01-01 12:34:56", "2022-01-01 12:34:56"});

        tableShell = new JTable();
        tableShell.setModel(shellManagerTableModel);
        tableShell.setFillsViewportHeight(true);

        TableColumnModel cm = tableShell.getColumnModel();
        cm.getColumn(3).setCellEditor(new DefaultCellEditor(
                new JComboBox(new DefaultComboBoxModel(new String[]{
                        "UTF-8",
                        "GBK",
                        "March",
                        "April",
                        "May",
                        "June",
                        "July",
                        "August",
                        "September",
                        "October",
                        "November",
                        "December"
                }))));
        cm.getColumn(4).setCellEditor(new DefaultCellEditor(
                new JComboBox(new DefaultComboBoxModel(new String[]{
                        "NO_PROXY",
                        "HTTP",
                        "Socks5",
                        "GLOBAL_PROXY"
                }))));

        ;
    }
}
