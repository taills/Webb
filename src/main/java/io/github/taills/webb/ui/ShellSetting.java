package io.github.taills.webb.ui;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextArea;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class ShellSetting {
    private JTabbedPane tabbedPane1;
    private JPanel mainPanel;
    private JTextField textField3;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JComboBox comboBox3;
    private JComboBox comboBox4;
    private JComboBox comboBox5;
    private JPanel requestPanel;
    private JPanel panelBottom;
    private JButton button1;
    private JButton button2;
    private RSyntaxTextArea requestTextArea;
    private RTextScrollPane requestTextScrollPane;

    public ShellSetting() {

    }

    public Component getMainPanel() {
        return mainPanel;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        requestPanel = new JPanel(new BorderLayout());
        requestPanel.setBorder(new TitledBorder("Header"));

         requestTextArea = new RSyntaxTextArea(20, 60);
        requestTextArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_CSS);
        requestTextArea.setCodeFoldingEnabled(true);
        requestTextScrollPane = new RTextScrollPane(requestTextArea);
        requestPanel.add(requestTextScrollPane);



    }
}
