package view;

import com.formdev.flatlaf.FlatClientProperties;
import main.Application;
import model.ObjectWrapper;
import net.miginfocom.swing.MigLayout;
import view.base.BaseView;
import view.components.InvitePanel;
import view.components.UserInfoPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ComponentEvent;

public class HomeView extends BaseView {
    JPanel userInfoContainer;
    UserInfoPanel userInfoPanel;
    JButton historyButton;
    JButton logoutButton;
    JTable rankingTable;

    final String[] columnNames = {"STT", "Người chơi", "Elo", "Số trận", "Tỉ lệ thắng", "Mời đấu"};

    public HomeView() {
        setName("Home View");
        setLayout(new MigLayout("fill, insets 16", "[center]", ""));
        Application.getInstance().componentResizeListener = this;

        initComponents();
        initEvents();

        // Mock data
        setUserInfo("Duong Van Toan", "#5", "1200", "10", "80%");
        setTableModel(new Object[][]{
                {"1", "Duong Van Toan", "1500", "100", "50%", "Online"},
                {"2", "Nguyen Van A", "1400", "90", "45%", "Online"},
                {"3", "Tran Thi B", "1300", "80", "40%", "5 minutes ago"},
                {"4", "Le Van C", "1200", "70", "35%", "Playing"},
                {"5", "Pham Van D", "1100", "60", "30%", "6 minutes ago"},
                {"6", "Hoang Thi E", "1000", "50", "25%", "Online"},
                {"7", "Do Van F", "900", "40", "20%", "Online"},
                {"8", "Nguyen Thi G", "800", "30", "15%", "2 minutes ago"},
                {"9", "Tran Van H", "700", "20", "10%", "Playing"},
                {"10", "Le Thi I", "600", "10", "5%", "5 minutes ago"},
                {"11", "Pham Van K", "500", "5", "2.5%", "5 days ago"},
                {"12", "Hoang Van L", "400", "2", "1%", "2 days ago"},
                {"13", "Do Thi M", "300", "1", "0.5%", "5 minutes ago"},
                {"14", "Nguyen Van N", "200", "0", "0%", "5 minutes ago"},
                {"15", "Tran Van O", "100", "0", "0%", "5 minutes ago"},
        });
        setUserOnlineStatus("Offline", 0);
    }

    private void initEvents() {
        historyButton.addActionListener(e -> {
            System.out.println("Show history");
        });

        logoutButton.addActionListener(e -> {
            System.out.println("Logout");
        });

        rankingTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = rankingTable.rowAtPoint(evt.getPoint());
                int col = rankingTable.columnAtPoint(evt.getPoint());
                if (col == 5) {
                    TableCellEditor cellEditor = rankingTable.getCellEditor(row, col);
                    cellEditor.stopCellEditing();
                    if (rankingTable.getValueAt(row, col) instanceof Boolean) {
                        boolean isOnline = (Boolean) rankingTable.getValueAt(row, col);
                        if (isOnline) {
                            System.out.println("Invite player " + row);
                        }
                    }
                }
            }
        });
    }

    private void setUserInfo(String username, String rank, String elo, String totalGames, String winRate) {
        userInfoPanel.setUserInfo(username, rank, elo, totalGames, winRate);
    }

    private void setUserOnlineStatus(String status, int row) {
        rankingTable.setValueAt(status, row, 5);
    }

    private void setTableModel(Object[][] data) {
        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        rankingTable.setModel(model);
        rankingTable.getColumnModel().getColumn(0).setPreferredWidth(48);
        rankingTable.getColumnModel().getColumn(1).setPreferredWidth(256);
        rankingTable.getColumnModel().getColumn(2).setPreferredWidth(128);
        rankingTable.getColumnModel().getColumn(3).setPreferredWidth(128);
        rankingTable.getColumnModel().getColumn(4).setPreferredWidth(128);
        rankingTable.getColumnModel().getColumn(5).setPreferredWidth(192);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        rankingTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        rankingTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        rankingTable.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);

        rankingTable.getColumnModel().getColumn(5).setCellRenderer(new DefaultTableCellRenderer(){
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                return new InvitePanel((String) value);
            }
        });
    }

    private void initComponents() {
        initUserInfoPanel();
        initRankingTable();
        initRankingTablePanel();
    }

    private void initRankingTable() {
        rankingTable = new JTable();
        rankingTable.setShowGrid(true);
        rankingTable.getTableHeader().setReorderingAllowed(false);
        rankingTable.setRowHeight(48);
        rankingTable.setRowSelectionAllowed(false);
        rankingTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        rankingTable.getTableHeader().setPreferredSize(new Dimension(0, 32));
        rankingTable.getTableHeader().putClientProperty(FlatClientProperties.STYLE, "" +
                "background:tint(@accentColor, 90%);" +
                "font:bold;");
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

        // history button
        ImageIcon historyIcon = new ImageIcon("assets/history_icon.png");
        Image historyImage = historyIcon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        historyButton = new JButton("Lịch sử đấu", new ImageIcon(historyImage));
        userInfoPanel.add(historyButton, "h 32::, gapx 16, wrap 8");

        // logout panel
        JPanel logoutPanel = new JPanel();
        logoutPanel.setLayout(new MigLayout("fill", "[center]", ""));
        logoutPanel.putClientProperty(FlatClientProperties.STYLE, "" +
                "background:lighten(@background, 5%);");
        userInfoContainer.add(logoutPanel, "dock south, grow, gap 16 16 16 16");

        ImageIcon logoutIcon = new ImageIcon("assets/logout_icon.png");
        Image logoutImage = logoutIcon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        logoutButton = new JButton("Đăng xuất", new ImageIcon(logoutImage));
        logoutButton.putClientProperty(FlatClientProperties.STYLE, "" +
                "foreground:lighten(@foreground, 50%);");
        logoutPanel.add(logoutButton, "grow");
    }

    private void initRankingTablePanel() {
        JPanel rankingTablePanel = new JPanel(new MigLayout("fill, insets 0", "[center]", "[shrink]16[grow]"));
        add(rankingTablePanel, "grow");

        // ranking title
        JLabel rankingTitleLabel = new JLabel("Bảng xếp hạng");
        rankingTitleLabel.putClientProperty(FlatClientProperties.STYLE, "" +
                "font:bold +8;" +
                "foreground:lighten(@foreground, 20%);");
        rankingTablePanel.add(rankingTitleLabel, "wrap");

        // ranking table
        rankingTablePanel.add(new JScrollPane(rankingTable), "grow");
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
