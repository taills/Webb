package io.github.taills.webb.ui;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextArea;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

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

    private RTextArea textArea;

    public ShellSetting() {

    }

    public Component getMainPanel() {
        return mainPanel;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        requestPanel = new JPanel(new BorderLayout());
        requestPanel.setBorder(new TitledBorder("Header"));


        RSyntaxTextArea textArea = new RSyntaxTextArea(20, 60);
        textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_CSS);
        textArea.setCodeFoldingEnabled(true);
        RTextScrollPane sp = new RTextScrollPane(textArea);
        requestPanel.add(sp);


//        panelBottom = new JPanel();
//
//        JButton button = new JButton();
//        button.setText("OK");
//        button.putClientProperty("FlatLaf.styleClass", "small");
//
//        JButton button1 = new JButton();
//        button1.setText("Test");
//        button1.putClientProperty("FlatLaf.styleClass", "small");
//
//        panelBottom.add(button);
//        panelBottom.add(button1);

    }
}
