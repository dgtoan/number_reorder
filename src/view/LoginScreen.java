package view;

import com.formdev.flatlaf.FlatClientProperties;
import main.Application;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class LoginScreen extends JPanel {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel forgotPasswordButton;
    private JLabel registerButton;

    public LoginScreen() {
        setName("Login Screen");
        setLayout(new MigLayout("fill, insets 0, align center center", "[]0[]"));

        initComponents();
        initEvents();
    }

    private void initComponents() {
        // Thubmnail
        ImageIcon thumbIcon = new ImageIcon("assets/thumb.jpeg");
        final int thumbWidth = Application.getInstance().getWidth() / 2;
        final int thumbHeight = thumbIcon.getIconHeight() * thumbWidth / thumbIcon.getIconWidth();
        Image thumbImage = thumbIcon.getImage().getScaledInstance(thumbWidth, thumbHeight, Image.SCALE_SMOOTH);
        thumbIcon = new ImageIcon(thumbImage);
        JLabel thumbLabel = new JLabel(thumbIcon);
        add(thumbLabel);

        // Container
        JPanel container = new JPanel();
        container.setLayout(new MigLayout("fill", "[center]", "[center]"));
        add(container);

        // Login Form
        JPanel loginForm = new JPanel();
        loginForm.setLayout(new MigLayout("wrap, fillx, insets 32", "fill, 160:320", ""));
        loginForm.putClientProperty(FlatClientProperties.STYLE, "" +
                "background:darken(@background, 5%);" +
                "arc:32");
        container.add(loginForm);

        // Title
        JLabel titleLabel = new JLabel("Chào mừng bạn");
        titleLabel.putClientProperty(FlatClientProperties.STYLE, "" +
                "font:bold +10");
        loginForm.add(titleLabel);

        // Description
        JLabel descriptionLabel = new JLabel("<html>Vui lòng đăng nhập để bắt đầu trò chơi</html>");
        descriptionLabel.putClientProperty(FlatClientProperties.STYLE, "" +
                "foreground:lighten(@foreground,30%);");
        loginForm.add(descriptionLabel, "wrap 32");

        // Username
        JLabel usernameLabel = new JLabel("Tài khoản");
        loginForm.add(usernameLabel);
        usernameField = new JTextField();
        usernameField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "example@gmail.com");
        usernameField.putClientProperty(FlatClientProperties.STYLE, "" +
                "arc:16");
        loginForm.add(usernameField, "wrap 16, h 36");

        // Password
        JLabel passwordLabel = new JLabel("Mật khẩu");
        loginForm.add(passwordLabel);
        passwordField = new JPasswordField();
        passwordField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "********");
        passwordField.putClientProperty(FlatClientProperties.STYLE, "" +
                "arc:16;" +
                "showRevealButton:true");
        loginForm.add(passwordField, "wrap 0, h 36");

        // Forgot Password Panel
        JPanel forgotPasswordPanel = new JPanel();
        forgotPasswordPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        forgotPasswordPanel.putClientProperty(FlatClientProperties.STYLE, "" +
                "background:darken(@background, 5%);");
        forgotPasswordButton = new JLabel("Quên mật khẩu?");
        forgotPasswordButton.putClientProperty(FlatClientProperties.STYLE, "" +
                "foreground:darken(@accentColor, 5%);" +
                "font:italic -1");
        forgotPasswordButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        forgotPasswordPanel.add(forgotPasswordButton);
        loginForm.add(forgotPasswordPanel, "span, growx, wrap");

        // Login Button
        loginButton = new JButton("Đăng nhập");
        loginButton.putClientProperty(FlatClientProperties.STYLE, "" +
                "background:darken(@accentColor, 5%);" +
                "foreground:#ffffff;" +
                "font:bold");
        loginForm.add(loginButton, "span, growx, wrap 32, h 36");

        // Register Panel
        JPanel registerPanel = new JPanel();
        registerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        registerPanel.putClientProperty(FlatClientProperties.STYLE, "" +
                "background:darken(@background, 5%);");
        JLabel registerLabel = new JLabel("Chưa có tài khoản?");
        registerLabel.putClientProperty(FlatClientProperties.STYLE, "" +
                "foreground:lighten(@foreground,30%);");
        registerPanel.add(registerLabel);
        registerButton = new JLabel("Đăng ký");
        registerButton.putClientProperty(FlatClientProperties.STYLE, "" +
                "foreground:darken(@accentColor, 5%);" +
                "font:bold");
        registerButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        registerPanel.add(registerButton);
        loginForm.add(registerPanel, "span, growx, wrap");
    }

    private void initEvents() {
        loginButton.addActionListener(e -> {
            if (usernameField.getText().isEmpty() || passwordField.getPassword().length == 0) {
                JOptionPane.showMessageDialog(Application.getInstance(),
                        "Vui lòng nhập đầy đủ thông tin",
                        "Thông báo",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            Application.getInstance().setRoot(new HomeView());
        });

        forgotPasswordButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
//                Application.getInstance().nextTo(new ForgotPasswordView());
            }
        });

        registerButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Application.getInstance().nextTo(new RegisterView());
            }
        });
    }

}
