package io.github.taills.webb.ui;

import javax.swing.*;

/**
 * 程序主窗口
 */
public class Main {
    private JTabbedPane tabbedPane1;
    private JPanel mainPanel;
    private JPanel panelAbout;
    private JLabel labelAbout;

    public JPanel getMainPanel() {
        return this.mainPanel;
    }


    private void createUIComponents() {
        // TODO: place custom component creation code here
        tabbedPane1 = new JTabbedPane();
        tabbedPane1.add("Shell",new ShellManager().getMainPanel());
        tabbedPane1.add("Settings",new Settings().getMainPanel());
//        tabbedPane1.add("sssss",new ShellSetting().getMainPanel());
    }
}
