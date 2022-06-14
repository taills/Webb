package io.github.taills.webb.ui;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class ShellSetting {
    private JTabbedPane tabbedPane1;
    private JPanel mainPanel;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JComboBox comboBox3;
    private JComboBox comboBox4;
    private JComboBox comboBox5;
    private JPanel requestHeaderPanel;
    private JPanel panelBottom;
    private JButton oKButton;
    private JButton testButton;
    private RSyntaxTextArea requestTextArea;
    private RTextScrollPane requestTextScrollPane;

    public ShellSetting() {

    }

    public Component getMainPanel() {
        return mainPanel;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        requestHeaderPanel = new JPanel(new BorderLayout());
        requestHeaderPanel.setBorder(new TitledBorder("Header"));

        requestTextArea = new RSyntaxTextArea(20, 60);
        requestTextArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_CSS);
        requestTextArea.setCodeFoldingEnabled(true);
        requestTextScrollPane = new RTextScrollPane(requestTextArea);
        requestHeaderPanel.add(requestTextScrollPane);



    }
}
