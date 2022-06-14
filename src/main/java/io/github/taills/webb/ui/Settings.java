package io.github.taills.webb.ui;

import com.formdev.flatlaf.demo.intellijthemes.IJThemesPanel;

import javax.swing.*;

/**
 * 全局设置
 */
public class Settings {
    private JPanel mainPanel;
    private JTabbedPane tabbedPane1;
    private JPanel themesPanel;

    private IJThemesPanel ijThemesPanel;

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here

        this.ijThemesPanel = new IJThemesPanel();
        themesPanel = new JPanel();
        themesPanel.add(ijThemesPanel);

    }
}
