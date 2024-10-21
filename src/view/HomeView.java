package view;

import com.formdev.flatlaf.FlatClientProperties;
import main.Application;
import net.miginfocom.layout.ComponentWrapper;
import net.miginfocom.layout.LayoutCallback;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

// UI description:
// 1/3 left: user info: avatar, username, rank, elo, total games, win rate, history button, logout button
// 2/3 right: ranking table: index, name, elo, total games, win rate, invite button
public class HomeView extends JPanel implements ComponentResizeListener {
    public HomeView() {
        setName("Home View");
        setLayout(new MigLayout("fill, insets 16", "[]16[]", ""));

        initComponents();
        initEvents();

        Application.getInstance().componentResizeListener = this;
    }

    private void initComponents() {
        initUserInfo();
        initRankingTable();
    }

    private void initUserInfo() {
        JPanel userInfoPanel = new JPanel();
        userInfoPanel.setLayout(new MigLayout("fill, insets 16, wrap"));
        userInfoPanel.putClientProperty(FlatClientProperties.STYLE, "" +
                "background:darken(@background, 5%);" +
                "arc:16");
        add(userInfoPanel, "grow, hidemode 3");
    }

    private void initRankingTable() {
        JTable rankingTable = new JTable();
        JScrollPane rankingScrollPane = new JScrollPane(rankingTable);
        add(rankingScrollPane, "span 2, grow");
    }

    private void initEvents() {

    }

    @Override
    public void componentResized(ComponentEvent e) {
        System.out.println("Home View resized");
    }
}
