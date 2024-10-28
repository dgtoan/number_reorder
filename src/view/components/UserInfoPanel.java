package view.components;

import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class UserInfoPanel extends JPanel {
    public JLabel usernameLabel;
    public StatsItemPanel userRankPanel;
    public StatsItemPanel userEloPanel;
    public StatsItemPanel userTotalGamesPanel;
    public StatsItemPanel userWinRatePanel;

    public UserInfoPanel() {
        initComponents(true);
    }

    public UserInfoPanel(boolean showFullStats) {
        initComponents(showFullStats);
    }

    private void initComponents(boolean fullStats) {

        setLayout(new MigLayout("fill, insets 8, gapy 16", "[center]"));
        putClientProperty(FlatClientProperties.STYLE, "" +
                "background:tint(@accentColor, 95%);" +
                "border:1,1,1,1,@accentColor;" +
                "arc:32");

        // avatar
        JLabel avatarLabel = new JLabel();
        ImageIcon avatarIcon = new ImageIcon("assets/user_avatar.png");
        Image avatarImage = avatarIcon.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
        avatarLabel.setIcon(new ImageIcon(avatarImage));
        add(avatarLabel, "wrap, w 64::, gaptop 16");

        // username
        usernameLabel = new JLabel("");
        usernameLabel.putClientProperty(FlatClientProperties.STYLE, "" +
                "font:semibold +4; " +
                "foreground:lighten(@foreground, 20%);");
        add(usernameLabel, "wrap 24");

        // stats container
        JPanel statsContainer = new JPanel();
        statsContainer.setLayout(new MigLayout("fill, insets 0, gap 8 8"));
        statsContainer.putClientProperty(FlatClientProperties.STYLE, "" +
                "background:tint(@accentColor, 95%);");
        String constraints = fullStats ? "wrap" : "";
        add(statsContainer, constraints);

        // stats items
        userRankPanel = new StatsItemPanel("Hạng", "");
        userEloPanel = new StatsItemPanel("Elo", "");
        userTotalGamesPanel = new StatsItemPanel("Tổng số trận", "");
        userWinRatePanel = new StatsItemPanel("Tỉ lệ thắng", "");
        statsContainer.add(userRankPanel, "sg statsItem, w 96::");
        statsContainer.add(userEloPanel, "sg statsItem, wrap");

        if (!fullStats) return;
        statsContainer.add(userTotalGamesPanel, "sg statsItem");
        statsContainer.add(userWinRatePanel, "sg statsItem");
    }

    public void setUserInfo(String username, String rank, String elo) {
        usernameLabel.setText(username);
        ((JLabel) userRankPanel.getComponent(1)).setText(rank);
        ((JLabel) userEloPanel.getComponent(1)).setText(elo);
    }

    public void setUserInfo(String username, String rank, String elo, String totalGames, String winRate) {
        usernameLabel.setText(username);
        ((JLabel) userRankPanel.getComponent(1)).setText(rank);
        ((JLabel) userEloPanel.getComponent(1)).setText(elo);
        ((JLabel) userTotalGamesPanel.getComponent(1)).setText(totalGames);
        ((JLabel) userWinRatePanel.getComponent(1)).setText(winRate);
    }
}
