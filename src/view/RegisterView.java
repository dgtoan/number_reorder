package view;

import com.formdev.flatlaf.FlatClientProperties;
import main.Application;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class RegisterView extends JPanel {
    private JButton backButton;
    private JButton registerButton;
    private JTextField nameField;
    private JTextField emailField;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;

    public RegisterView() {
        setName("Register View");
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

        // Register Form
        JPanel registerForm = new JPanel();
        registerForm.setLayout(new MigLayout("wrap, fillx, insets 32", "fill, 160:320", ""));
        registerForm.putClientProperty(FlatClientProperties.STYLE, "" +
                "background:darken(@background, 5%);" +
                "arc:32");
        container.add(registerForm);

//        // Back icon button
//        ImageIcon backIcon = new ImageIcon("assets/back_icon.png");
//        Image image = backIcon.getImage();
//        Image newImage = image.getScaledInstance(24, 24, Image.SCALE_SMOOTH);
//        backIcon = new ImageIcon(newImage);
//        backButton = new JButton("Trở lại", backIcon);
//        backButton.putClientProperty(FlatClientProperties.STYLE, "" +
//                "background:darken(@background, 5%);" +
//                "foreground:darken(@accentColor, 10%);" +
////                "border: 0,0,0,0;" +
//                "font:bold");
//        registerForm.add(backButton, "w 48");

        // Back panel
        JPanel backPanel = new JPanel();
        backPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        backPanel.putClientProperty(FlatClientProperties.STYLE, "" +
                "background:darken(@background, 5%);");
        registerForm.add(backPanel, "w 48, wrap 16");
        ImageIcon backIcon = new ImageIcon("assets/back_icon.png");
        Image image = backIcon.getImage();
        Image newImage = image.getScaledInstance(24, 24, Image.SCALE_SMOOTH);
        backIcon = new ImageIcon(newImage);
        backButton = new JButton("Trở lại", backIcon);
        backButton.putClientProperty(FlatClientProperties.STYLE, "" +
                "background:darken(@background, 5%);" +
                "foreground:darken(@accentColor, 10%);" +
                "border: 0,0,0,0;" +
                "font:bold");
        backPanel.add(backButton);

        // Title
        JLabel titleLabel = new JLabel("Đăng ký");
        titleLabel.putClientProperty(FlatClientProperties.STYLE, "" +
                "font:bold +10");
        registerForm.add(titleLabel);

        // Name
        JLabel nameLabel = new JLabel("Họ và tên");
        registerForm.add(nameLabel);
        nameField = new JTextField();
        nameField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Nguyễn Văn A");
        nameField.putClientProperty(FlatClientProperties.STYLE, "" +
                "arc:16");
        registerForm.add(nameField, "wrap 16, h 36");

        // Username
        JLabel usernameLabel = new JLabel("Tài khoản");
        registerForm.add(usernameLabel);
        usernameField = new JTextField();
        usernameField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "example@gmail.com");
        usernameField.putClientProperty(FlatClientProperties.STYLE, "" +
                "arc:16");
        registerForm.add(usernameField, "wrap 16, h 36");

        // Password
        JLabel passwordLabel = new JLabel("Mật khẩu");
        registerForm.add(passwordLabel);
        passwordField = new JPasswordField();
        passwordField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "********");
        passwordField.putClientProperty(FlatClientProperties.STYLE, "" +
                "arc:16;" +
                "showRevealButton:true");
        registerForm.add(passwordField, "wrap 16, h 36");

        // Confirm Password
        JLabel confirmPasswordLabel = new JLabel("Xác nhận mật khẩu");
        registerForm.add(confirmPasswordLabel);
        confirmPasswordField = new JPasswordField();
        confirmPasswordField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "********");
        confirmPasswordField.putClientProperty(FlatClientProperties.STYLE, "" +
                "arc:16;" +
                "showRevealButton:true");
        registerForm.add(confirmPasswordField, "wrap 32, h 36");

        // Register button
        registerButton = new JButton("Đăng ký");
        registerButton.putClientProperty(FlatClientProperties.STYLE, "" +
                "background:darken(@accentColor, 5%);" +
                "foreground:#ffffff;" +
                "font:bold");
        registerForm.add(registerButton, "span, growx, wrap, h 36");
    }

    private void initEvents() {
        backButton.addActionListener(e -> {
            Application.getInstance().back();
        });

        registerButton.addActionListener(e -> {
            Application.getInstance().nextTo(new HomeView());
        });
    }
}
