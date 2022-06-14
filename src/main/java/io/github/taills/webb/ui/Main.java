package io.github.taills.webb.ui;

import javax.swing.*;

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
        tabbedPane1.add("Shell管理1",new ShellManager().getMainPanel());
    }
}
