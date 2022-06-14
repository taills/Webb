package io.github.taills.webb.ui;

import com.formdev.flatlaf.demo.intellijthemes.IJThemesPanel;

import javax.swing.*;
import java.awt.*;
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

    private IJThemesPanel ijThemesPanel;

    public Settings() {
        panelThemesList.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                //让皮肤选择框的高度随着窗口变化
                ijThemesPanel.setPreferredSize(new Dimension(ijThemesPanel.getWidth(), e.getComponent().getHeight()));
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

    }
}
