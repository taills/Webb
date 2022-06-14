package io.github.taills;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.demo.DemoPrefs;
import com.formdev.flatlaf.extras.FlatInspector;
import com.formdev.flatlaf.extras.FlatUIDefaultsInspector;
import com.formdev.flatlaf.util.SystemInfo;
import io.github.taills.webb.ui.Main;

import javax.swing.*;
import java.awt.*;


public class App {
    /**
     * 程序主入口
     *
     * @param args
     */
    public static void main(String[] args) {

        if( SystemInfo.isMacOS ) {
            // enable screen menu bar
            // (moves menu bar from JFrame window to top of screen)
            System.setProperty( "apple.laf.useScreenMenuBar", "true" );

            // application name used in screen menu bar
            // (in first menu after the "apple" menu)
            System.setProperty( "apple.awt.application.name", "FlatLaf Demo" );

            // appearance of window title bars
            // possible values:
            //   - "system": use current macOS appearance (light or dark)
            //   - "NSAppearanceNameAqua": use light appearance
            //   - "NSAppearanceNameDarkAqua": use dark appearance
            // (needs to be set on main thread; setting it on AWT thread does not work)
            System.setProperty( "apple.awt.application.appearance", "system" );
        }

        // Linux
        if( SystemInfo.isLinux ) {
            // enable custom window decorations
            JFrame.setDefaultLookAndFeelDecorated( true );
            JDialog.setDefaultLookAndFeelDecorated( true );
        }

//        if(!SystemInfo.isJava_9_orLater && System.getProperty( "flatlaf.uiScale" ) == null )
//            System.setProperty( "flatlaf.uiScale", "1x" );

        SwingUtilities.invokeLater( () -> {

            DemoPrefs.init( "/Webb" );

            // application specific UI defaults
            FlatLaf.registerCustomDefaultsSource( "com.formdev.flatlaf.demo" );

            // set look and feel
            DemoPrefs.setupLaf( args );

            // install inspectors
            FlatInspector.install( "ctrl shift alt X" );
            FlatUIDefaultsInspector.install( "ctrl shift alt Y" );

            // create frame
            Main mainPanel = new Main();
            final JFrame mainJFrame = new JFrame("\u5468\u516d\u665a\u4e0a10\u70b9\uff0c\u79cb\u540d\u5c71\u9876\u7b49\u4f60\u3002");
            mainJFrame.add(mainPanel.getMainPanel());
            mainJFrame.setDefaultCloseOperation(3);
            int width = (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.8);
            int height = (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2);

            mainJFrame.setSize(width, height);

            mainJFrame.setPreferredSize(new Dimension(width, height));
//            mainJFrame.pack();
            mainJFrame.setVisible(true);
            mainJFrame.setLocationRelativeTo(null);

        } );

//        SwingUtilities.updateComponentTreeUI(mainJFrame.getContentPane());

    }
}
