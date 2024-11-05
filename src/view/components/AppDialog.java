package view.components;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.fonts.jetbrains_mono.FlatJetBrainsMonoFont;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AppDialog extends JFrame {
    private static final AppDialog instance = new AppDialog();
    private final JPanel panel = new JPanel(new MigLayout("fill, insets 16", "[center]"));

    public AppDialog() {
        init();
    }

    private void init() {
        customFlatLaf();
        setSize(360, 320);
        setLocationRelativeTo(null);
        add(panel);
    }

    public static AppDialog getInstance() {
        return instance;
    }

    public void showMessageDialog(String title, String message) {
        showMessageDialog(title, message, "OK");
    }

    private void initTextComponents(String title, String message) {
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 20));
        panel.add(titleLabel, "dock north, gap 16 8 16 8");

        JLabel messageLabel = new JLabel("<html>" + message + "</html>");
        panel.add(messageLabel, "growy");
    }

    public void showMessageDialog(String title, String message, String button) {
        showMessageDialog(title, message, button, new AppDialogAction(){});
    }

    public void showMessageDialog(String title, String message, String button, AppDialogAction action) {
        setTitle(title);
        removeWindowListener(getWindowListeners().length > 0 ? getWindowListeners()[0] : null);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                action.onNo();
                action.onClose();
                System.out.println("Dialog closed");
                super.windowClosing(e);
            }
        });
        panel.removeAll();

        initTextComponents(title, message);

        JButton okButton = new JButton(button);
        okButton.addActionListener(e -> {
            action.onYes();
            action.onNo();
            dispose();
        });
        panel.add(okButton, "dock south, gap 16 16 16 16");

        setVisible(true);
    }

    public void showOptionDialog(String title, String message, String yesButton, String noButton, AppDialogAction action) {
        setTitle(title);
        removeWindowListener(getWindowListeners().length > 0 ? getWindowListeners()[0] : null);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                action.onNo();
                action.onClose();
                System.out.println("Dialog closed");
                super.windowClosing(e);
            }
        });
        panel.removeAll();

        initTextComponents(title, message);

        JPanel buttonPanel = new JPanel(new MigLayout("fillx, insets 16", "[][][]", ""));
        JButton yesButtonComponent = new JButton(yesButton);

        yesButtonComponent.addActionListener(e -> {
            action.onYes();
            dispose();
        });
        JButton noButtonComponent = new JButton(noButton);
        noButtonComponent.addActionListener(e -> {
            action.onNo();
            dispose();
        });

        buttonPanel.add(yesButtonComponent, "growx, span 2");
        buttonPanel.add(noButtonComponent, "growx");
        panel.add(buttonPanel, "dock south");

        setVisible(true);
    }

    private void customFlatLaf() {
        FlatJetBrainsMonoFont.install();
        FlatLaf.registerCustomDefaultsSource("themes");
        UIManager.put("defaultFont", new Font(FlatJetBrainsMonoFont.FAMILY, Font.PLAIN, 14));
        FlatLightLaf.setup();
    }
}
