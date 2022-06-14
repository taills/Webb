package io.github.taills;

import com.formdev.flatlaf.FlatLightLaf;
import io.github.taills.webb.ui.Main;
import io.github.taills.webb.ui.ShellManager;

import javax.swing.*;
import java.awt.*;


public class App {
    /**
     * 程序主入口
     *
     * @param args
     */
    public static void main(String[] args) {
        FlatLightLaf.setup();
        Main mainPanel = new Main();
        final JFrame mainJFrame = new JFrame("\u5468\u516d\u665a\u4e0a10\u70b9\uff0c\u79cb\u540d\u5c71\u9876\u7b49\u4f60\u3002");
        mainJFrame.add(mainPanel.getMainPanel());
        mainJFrame.setDefaultCloseOperation(3);
        mainJFrame.setSize(1000, 500);
        mainJFrame.setPreferredSize(new Dimension(1000, 500));
        mainJFrame.pack();
        mainJFrame.setVisible(true);
        mainJFrame.setLocationRelativeTo(null);

//        SwingUtilities.updateComponentTreeUI(mainJFrame.getContentPane());

    }
}
