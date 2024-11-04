package view;

import com.formdev.flatlaf.FlatClientProperties;
import main.Application;
import model.ObjectWrapper;
import model.Player;
import model.Room;
import net.miginfocom.swing.MigLayout;
import view.base.BaseView;
import view.components.InvitePanel;
import view.components.UserInfoPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class HistoryView extends BaseView {
    JPanel userInfoContainer;
    UserInfoPanel userInfoPanel;
    JButton backButton;
    JTable historyTable;

    final String[] columnNames = {"STT", "Đối thủ", "Thời gian", "Kết quả", "Elo"};

    public HistoryView() {
        setName("History View");
        setLayout(new MigLayout("fill, insets 16", "[center]", ""));
        Application.getInstance().componentResizeListener = this;

        initComponents();
        initEvents();
        fetchData();

        // Mock data
        setUserInfo("--- --- ---", "--", "----", "--", "---");
//        setTableModel(new Object[][]{
//                {"1", "Nguyễn Văn A", "2024/11/04 18:00", "Thắng", "+10"},
//                {"2", "Nguyễn Văn C", "2024/11/04 18:00", "Thua", "-10"},
//                {"3", "Nguyễn Văn E", "2024/11/04 18:00", "Thắng", "+12"},
//                {"4", "Nguyễn Văn G", "2024/11/04 18:00", "Thắng", "+10"},
//                {"5", "Nguyễn Văn I", "2024/11/04 18:00", "Thua", "-10"},
//                {"6", "Nguyễn Văn K", "2024/11/04 18:00", "Null", "0"},
//        });
    }

    private void fetchData() {
        ObjectWrapper data = new ObjectWrapper(ObjectWrapper.ROOM_LIST, null);
        Application.getInstance().sendData(data);

        data = new ObjectWrapper(ObjectWrapper.PLAYER_LIST, null);
        Application.getInstance().sendData(data);
    }

    private void setTableModel(Object[][] data) {
        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        historyTable.setModel(model);
        historyTable.getColumnModel().getColumn(0).setPreferredWidth(32);
        historyTable.getColumnModel().getColumn(1).setPreferredWidth(160);
        historyTable.getColumnModel().getColumn(2).setPreferredWidth(128);
        historyTable.getColumnModel().getColumn(3).setPreferredWidth(64);
        historyTable.getColumnModel().getColumn(4).setPreferredWidth(64);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        historyTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        historyTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        historyTable.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        historyTable.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
    }

    public void initEvents() {
        backButton.addActionListener(e -> {
            Application.getInstance().back();
        });
    }

    public void initComponents() {
        initUserInfoPanel();
        initHistoryTable();
        initHistoryTablePanel();
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

    private void initHistoryTable() {
        historyTable = new JTable() {
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);

                String result = (String) getValueAt(row, 3);
                if (result.equals("Thắng")) {
                    c.setBackground(new Color(0, 255, 0, 32));
                    if (column == 3 || column == 4) {
                        c.setFont(c.getFont().deriveFont(Font.BOLD));
                    }
                } else if (result.equals("Thua")) {
                    c.setBackground(new Color(255, 0, 0, 32));
                } else {
                    c.setBackground(Color.WHITE);
                }

                return c;
            }
        };
        historyTable.setShowGrid(true);
        historyTable.getTableHeader().setReorderingAllowed(false);
        historyTable.setRowHeight(48);
        historyTable.setRowSelectionAllowed(false);
        historyTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        historyTable.getTableHeader().setPreferredSize(new Dimension(0, 32));
        historyTable.getTableHeader().putClientProperty(FlatClientProperties.STYLE, "" +
                "background:tint(@accentColor, 90%);" +
                "font:bold;");
    }

    private void initHistoryTablePanel() {
        JPanel historyTablePanel = new JPanel(new MigLayout("fill, insets 0", "[center]", "[shrink]16[grow]"));
        add(historyTablePanel, "grow");

        // history title
        JLabel historyTitleLabel = new JLabel("Lịch sử đấu");
        historyTitleLabel.putClientProperty(FlatClientProperties.STYLE, "" +
                "font:bold +8;" +
                "foreground:lighten(@foreground, 20%);");
        historyTablePanel.add(historyTitleLabel, "wrap");

        // history table
        historyTablePanel.add(new JScrollPane(historyTable), "grow");
    }

    private void setUserInfo(String username, String rank, String elo, String totalGames, String winRate) {
        userInfoPanel.setUserInfo(username, rank, elo, totalGames, winRate);
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
    public void onDataReceivedForView(ObjectWrapper data) {
        if (data.getPerformative() == ObjectWrapper.ROOM_LIST) {
            ArrayList<Room> rooms = (ArrayList<Room>) data.getData();
            ArrayList<Room> historyRooms = new ArrayList<>();

            System.out.println("Room list: " + rooms.size());

            for (Room room : rooms) {
                if (room.getTimeLeft() == 0) {
                    historyRooms.add(room);
                }
            }

            System.out.println("History room list: " + historyRooms.size());


            Object[][] tableData = new Object[historyRooms.size()][5];

            for (int i = 0; i < historyRooms.size(); i++) {
                Room room = historyRooms.get(i);
                final int oppositePlayerId = room.getFirstPlayer() == Application.getInstance().getCurrentPlayerId() ? room.getSecondPlayer() : room.getFirstPlayer();
                String playerName = "";

                for (Player player : Application.getInstance().getPlayerList()) {
                    if (player.getId() == oppositePlayerId) {
                        playerName = player.getPlayerName();
                        break;
                    }
                }

                tableData[i][0] = i + 1;
                tableData[i][1] = playerName;
                tableData[i][2] = room.getCreateAt().toString();
                tableData[i][3] = room.getWinner() == -1 ? "Hòa" : room.getWinner() == Application.getInstance().getCurrentPlayerId() ? "Thắng" : "Thua";
                tableData[i][4] = room.getWinner() == -1 ? "0" : room.getWinner() == Application.getInstance().getCurrentPlayerId() ? "+10" : "-10";
            }

            setTableModel(tableData);
        }

        if (data.getPerformative() == ObjectWrapper.PLAYER_LIST) {
            ArrayList<Player> players = (ArrayList<Player>) data.getData();

            Collections.sort(players, (p1, p2) -> p2.getElo() - p1.getElo());

            int currentPlayer = Application.getInstance().getCurrentPlayerId();

            for (int i = 0; i < players.size(); i++) {
                if (players.get(i).getId() == currentPlayer) {
                    Player player = players.get(i);
                    setUserInfo(player.getPlayerName(), "#" + String.valueOf(i + 1), String.valueOf(player.getElo()), String.valueOf(player.getTotalGames()), player.getWinRate());
                    break;
                }
            }
        }
    }
}
