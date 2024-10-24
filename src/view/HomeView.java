package view;

import com.formdev.flatlaf.FlatClientProperties;
import main.Application;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;

// UI description:
// 1/3 left: user info: avatar, username, rank, elo, total games, win rate, history button, logout button
// 2/3 right: ranking table: index, name, elo, total games, win rate, invite button
public class HomeView extends JPanel implements ComponentResizeListener {
    JPanel userInfoPanel;

    public HomeView() {
        setName("Home View");
        setLayout(new MigLayout("fill, insets 16", "[]16[][]", ""));

        initComponents();
        initEvents();

        Application.getInstance().componentResizeListener = this;
    }

    private void initComponents() {
        initUserInfo();
        initRankingTable();
    }

    private void initUserInfo() {
        userInfoPanel = new JPanel();
        userInfoPanel.setLayout(new MigLayout("fill, insets 16, wrap", "[center]"));
//        userInfoPanel.putClientProperty(FlatClientProperties.STYLE, "" +
//                "background:lighten(@background, 20%);" +
//                "arc:16");
        add(userInfoPanel, "grow, hidemode 3, w 256::");

        ImageIcon avatarIcon = new ImageIcon("assets/user_avatar.png");
        final int avatarWidth = 80;
        final int avatarHeight = avatarIcon.getIconHeight() * avatarWidth / avatarIcon.getIconWidth();
        Image avatarImage = avatarIcon.getImage().getScaledInstance(avatarWidth, avatarHeight, Image.SCALE_SMOOTH);
        avatarIcon = new ImageIcon(avatarImage);
        JLabel avatarLabel = new JLabel(avatarIcon);
        userInfoPanel.add(avatarLabel);

        JLabel usernameLabel = new JLabel("Dương Thị Hồng Hoan");
        usernameLabel.putClientProperty(FlatClientProperties.STYLE, "" +
                "font:bold +4;");
        userInfoPanel.add(usernameLabel);
    }

    private void initRankingTable() {
        JTable rankingTable = new JTable();
        JScrollPane rankingScrollPane = new JScrollPane(rankingTable);
        add(rankingScrollPane, "span 3, grow");
    }

    private void initEvents() {

    }

    @Override
    public void componentResized(ComponentEvent e) {
        if (e.getComponent().getWidth() < 640) {
            userInfoPanel.setVisible(false);
            revalidate();
        } else {
            userInfoPanel.setVisible(true);
            revalidate();
        }
    }
}
