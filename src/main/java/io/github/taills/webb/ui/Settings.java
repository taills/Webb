package io.github.taills.webb.ui;

import com.formdev.flatlaf.demo.intellijthemes.IJThemesPanel;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * 全局设置
 */
public class Settings {
    private JPanel mainPanel;
    private JTabbedPane tabbedPane1;
    private JPanel themesPanel;
    private JPanel panelThemesList;
    private JPanel panelUISetting;
    private JPanel requestHeaderPanel;
    private JTable tableProxy;
    private JTextField textField1;
    private JComboBox comboBox1;
    private JButton addButton;
    private JTextField textField3;
    private JTextField textField4;
    private JPanel authInfoPanel;
    private RTextScrollPane requestTextScrollPane;
    private RSyntaxTextArea requestTextArea;

    private IJThemesPanel ijThemesPanel;

    private JCheckBox checkBoxRandomRequestParams;

    private JCheckBox checkBoxRandomProxy;


    public Settings() {

        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("comboBoxChanged")) {
                    String proxyType = comboBox1.getSelectedItem().toString();
                    System.out.println(proxyType);
                    authInfoPanel.setVisible(proxyType.endsWith("With Auth"));
                }

            }
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here

        this.ijThemesPanel = new IJThemesPanel();
        panelThemesList = new JPanel();
        panelThemesList.add(ijThemesPanel);
        panelThemesList.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                //让皮肤选择框的高度随着窗口变化
                ijThemesPanel.setPreferredSize(new Dimension(ijThemesPanel.getWidth(), e.getComponent().getHeight()));
            }
        });

        requestHeaderPanel = new JPanel(new BorderLayout());
        requestHeaderPanel.setBorder(new TitledBorder("Global Request Headers"));
        requestTextArea = new RSyntaxTextArea(20, 60);
        requestTextArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_CSS);
        requestTextArea.setCodeFoldingEnabled(true);
        requestTextScrollPane = new RTextScrollPane(requestTextArea);
        requestHeaderPanel.add(requestTextScrollPane);

        JPanel panel = new JPanel(new GridLayout());
        panel.setBorder(new TitledBorder("Advanced"));

        checkBoxRandomProxy = new JCheckBox("Using Random Proxy");
        panel.add(checkBoxRandomProxy);


        checkBoxRandomRequestParams = new JCheckBox("Append Random Params");
        panel.add(checkBoxRandomRequestParams);


        requestHeaderPanel.add(panel, BorderLayout.AFTER_LAST_LINE);


        tableProxy = new JTable();

        DefaultTableModel tableModelProxy = new DefaultTableModel() {

        };
        tableModelProxy.setColumnIdentifiers(new String[]{"Type", "Host:Port", "Username", "Password", "State"});
        tableModelProxy.addRow(new String[]{"HTTP", "127.0.0.1:8080", "user", "1234","unknown"});
        tableModelProxy.addRow(new String[]{"Socks5", "127.0.0.1:8080", "user", "1234","failed"});
        tableModelProxy.addRow(new String[]{"HTTP", "127.0.0.1:8080", "user", "1234","successful"});
        tableProxy.setModel(tableModelProxy);

    }
}
