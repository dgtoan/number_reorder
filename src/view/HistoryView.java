package view;

import com.formdev.flatlaf.FlatClientProperties;
import main.Application;
import model.ObjectWrapper;
import net.miginfocom.swing.MigLayout;
import view.base.BaseView;
import view.components.UserInfoPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;

public class HistoryView extends BaseView {
    JPanel userInfoContainer;
    UserInfoPanel userInfoPanel;
    JButton backButton;
    JTable historyTable;

    public HistoryView() {
        setName("History View");
        setLayout(new MigLayout("fill, insets 16", "[center]", ""));
        Application.getInstance().componentResizeListener = this;

        initComponents();
        initEvents();
    }

    public void initEvents() {
        backButton.addActionListener(e -> {
            Application.getInstance().back();
        });
    }

    public void initComponents() {
        initUserInfoPanel();
    }

    private void initUserInfoPanel() {
        userInfoContainer = new JPanel();
        userInfoContainer.setLayout(new MigLayout("fill, insets 16, wrap", "[center]", "[center]"));
        userInfoContainer.putClientProperty(FlatClientProperties.STYLE, "" +
                "background:lighten(@background, 5%);" +
                "arc:32");
        add(userInfoContainer, "dock west, hidemode 3");

        // user info container
        userInfoPanel = new UserInfoPanel();
        userInfoContainer.add(userInfoPanel);

        // back button
        JPanel backButtonPanel = new JPanel();
        backButtonPanel.setLayout(new MigLayout("fill", "[center]", ""));
        backButtonPanel.putClientProperty(FlatClientProperties.STYLE, "" +
                "background:lighten(@background, 5%);");
        userInfoContainer.add(backButtonPanel, "dock south, grow, gap 16 16 16 16");

        ImageIcon backIcon = new ImageIcon("assets/back_icon.png");
        Image backImage = backIcon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        backButton = new JButton("Quay lại", new ImageIcon(backImage));
        backButtonPanel.add(backButton, "grow");
    }

    @Override
    public void componentResized(ComponentEvent e) {
        if (e.getComponent().getWidth() < 640) {
            userInfoContainer.setVisible(false);
            revalidate();
        } else {
            userInfoContainer.setVisible(true);
            revalidate();
        }
    }

    @Override
    public void onDataReceived(ObjectWrapper data) {
    }
}
