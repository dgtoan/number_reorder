package view;

import com.formdev.flatlaf.FlatClientProperties;
import main.Application;
import model.ObjectWrapper;
import net.miginfocom.swing.MigLayout;
import view.base.BaseView;
import view.components.AppDialog;
import view.components.AppDialogAction;
import view.components.UserInfoPanel;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameView extends BaseView {
    JPanel opponentPanel;
    JPanel myPanel;
    JPanel opponentInfoContainer;
    JPanel myInfoContainer;
    UserInfoPanel opponentInfoPanel;
    UserInfoPanel myInfoPanel;
    JButton surrenderButton;
    JLabel timeRemainingLabel;
    JButton doneButton;

    JButton[] myResultButtons = new JButton[10];
    JButton[] myInitialButtons = new JButton[10];
    JButton[] opponentResultButtons = new JButton[10];
    JButton[] opponentInitialButtons = new JButton[10];

    List<Integer> myResult = new ArrayList<Integer>();
    List<Integer> myInitial = new ArrayList<Integer>();
    List<Integer> opponentResult = new ArrayList<Integer>();
    List<Integer> opponentInitial = new ArrayList<Integer>();

    public GameView() {
        setName("Game View");
        setLayout(new MigLayout("fill, insets 0"));
        Application.getInstance().componentResizeListener = this;

        initComponents();
        initEvents();

        // mock data
        opponentInfoPanel.setUserInfo("Opponent", "#2", "1200");
        myInfoPanel.setUserInfo("Duong Van toan", "#1", "1300");

        setTimeRemaining(26);

        for (int i = 1; i <= 10; i++) {
            myInitial.add(i);
            opponentInitial.add(i);
        }

        Collections.shuffle(myInitial);
        Collections.shuffle(opponentInitial);
        opponentResult.add(5);
        opponentResult.add(2);

        setMyGameState(myInitial, myResult);
        setOpponentGameState(opponentInitial, opponentResult);
    }

    private void onSurrender() {
        AppDialog.getInstance().showOptionDialog(
                "Chịu thua",
                "Bạn có chắc chắn muốn chịu thua?",
                "Chịu thua",
                "Hủy",
                new AppDialogAction() {
                    @Override
                    public void onYes() {
                        // TODO: send surrender to server
                        Application.getInstance().back();
                    }
                }
        );
    }

    private void onDone() {
        if (!myInitialButtons[0].isEnabled()) {
            AppDialog.getInstance().showMessageDialog("Thông báo", "Bạn đã hoàn thành rồi");
            return;
        }

        // TODO: send my result to server for checking

        // disable all buttons
        for (int i = 0; i < 10; i++) {
            myInitialButtons[i].setEnabled(false);
            myResultButtons[i].setEnabled(false);
        }
        AppDialog.getInstance().showMessageDialog("Thành công", "Đã gửi kết quả, vui lòng chờ đối thủ hoàn thành");
    }

    private void onGameOver(boolean isWin) {
        Application.getInstance().closeAllDialogs();

        String message = isWin ? "Bạn đã thắng, bạn có muốn đấu lại?" : "Bạn đã thua, bạn có muốn đấu lại?";
        AppDialog.getInstance().showOptionDialog(
                isWin ? "Bạn thắng" : "Bạn thua",
                message,
                "Đấu lại",
                "Thoát",
                new AppDialogAction() {
                    @Override
                    public void onYes() {
                        // TODO: send invite play again
                    }
                    @Override
                    public void onNo() {
                        Application.getInstance().back();
                    }
                }
        );
    }

    private void onChangeMyGameState(List<Integer> newInitial, List<Integer> newResult) {
        // TODO: update new game state to server
        setMyGameState(newInitial, newResult);
    }

    private void initEvents() {
        for (int i = 0; i < 10; i++) {
            myInitialButtons[i].addActionListener(e -> {
                JButton button = (JButton) e.getSource();
                int index = Integer.parseInt(button.getText());
                myResult.add(index);
                onChangeMyGameState(myInitial, myResult);
            });

            myResultButtons[i].addActionListener(e -> {
                JButton button = (JButton) e.getSource();
                int index = Integer.parseInt(button.getText());
                myResult.remove(Integer.valueOf(index));
                onChangeMyGameState(myInitial, myResult);
            });
        }

        surrenderButton.addActionListener(e -> {
            onSurrender();
        });

        doneButton.addActionListener(e -> {
            onDone();
        });
    }

    private void setMyGameState(List<Integer> myInitial, List<Integer> myResult) {
        for (int i = 0; i < 10; i++) {
            try {
                myResultButtons[i].setText(String.valueOf(myResult.get(i)));
                myResultButtons[i].setVisible(true);
            } catch (IndexOutOfBoundsException e) {
                myResultButtons[i].setVisible(false);
            }

            Integer initialValue = myInitial.get(i);
            if (myResult.contains(initialValue)) {
                myInitialButtons[i].setVisible(false);
            } else {
                myInitialButtons[i].setText(String.valueOf(initialValue));
                myInitialButtons[i].setVisible(true);
            }
        }
        revalidate();
    }

    private void setOpponentGameState(List<Integer> opponentInitial, List<Integer> opponentResult) {
        for (int i = 0; i < 10; i++) {
            try {
                opponentResultButtons[i].setText(String.valueOf(opponentResult.get(i)));
                opponentResultButtons[i].setVisible(true);
            } catch (IndexOutOfBoundsException e) {
                opponentResultButtons[i].setVisible(false);
            }

            Integer initialValue = opponentInitial.get(i);
            if (opponentResult.contains(initialValue)) {
                opponentInitialButtons[i].setVisible(false);
            } else {
                opponentInitialButtons[i].setText(String.valueOf(initialValue));
                opponentInitialButtons[i].setVisible(true);
            }
        }
        revalidate();
    }

    private void setTimeRemaining(int seconds) {
        int minutes = seconds / 60;
        int remainingSeconds = seconds % 60;
        timeRemainingLabel.setText("Thời gian còn lại: " + String.format("%02d:%02d", minutes, remainingSeconds));
    }

    private void initComponents() {
        opponentPanel = new JPanel(new MigLayout("fill, insets 16", "[center]", "[center]"));
        opponentPanel.putClientProperty(FlatClientProperties.STYLE, "" +
                "background:darken(@background, 5%);");
        add(opponentPanel, "dock north, grow");
        initOpponentInfoComponents();
        initOpponentBoardComponents();

        myPanel = new JPanel(new MigLayout("fill, insets 16", "[center]", "[center]"));
        myPanel.setBorder(new MatteBorder(2, 0, 0, 0, Color.decode("#00C7BE")));
        add(myPanel, "grow");
        initMyInfoComponents();
        initMyBoardComponents();
    }

    private void initOpponentInfoComponents() {
        opponentInfoContainer = new JPanel(new MigLayout("fill, insets 16"));
        opponentPanel.add(opponentInfoContainer, "dock west, grow, hidemode 3");
        opponentInfoContainer.putClientProperty(FlatClientProperties.STYLE, "" +
                "background:darken(@background, 5%);");
        opponentInfoContainer.setBorder(new MatteBorder(0, 0, 0, 4, Color.LIGHT_GRAY));
        opponentInfoPanel = new UserInfoPanel(false);
        opponentInfoContainer.add(opponentInfoPanel);
    }

    private void initMyInfoComponents() {
        myInfoContainer = new JPanel(new MigLayout("fill, insets 16, wrap", "[center]", "[center]"));
        myInfoContainer.setBorder(new MatteBorder(0, 0, 0, 4, Color.LIGHT_GRAY));
        myInfoPanel = new UserInfoPanel(false);
        myInfoContainer.add(myInfoPanel);
        myPanel.add(myInfoContainer, "dock west, grow, hidemode 3");
    }

    private void initOpponentBoardComponents() {
        JPanel opponentBoardTitle = new JPanel(new MigLayout("fill"));
        opponentBoardTitle.putClientProperty(FlatClientProperties.STYLE, "" +
                "background:darken(@background, 5%);");
        opponentPanel.add(opponentBoardTitle, "dock north, grow");

        JLabel opponentBoardTitleLabel = new JLabel("Đối thủ");
        opponentBoardTitleLabel.putClientProperty(FlatClientProperties.STYLE, "" +
                "font:bold +4;");
        opponentBoardTitle.add(opponentBoardTitleLabel, "dock west, gap 16 16 16 0");

        surrenderButton = new JButton("Chịu thua");
        surrenderButton.putClientProperty(FlatClientProperties.STYLE, "" +
                "foreground:#FF3B30;" +
                "background:tint(#FF3B30, 95%);" +
                "borderColor:#FF3B30;" +
                "hoverBorderColor:#FF3B30;" +
                "focusedBorderColor: #FF3B30;" +
                "font:semibold;");
        opponentBoardTitle.add(surrenderButton, "dock east, gap 16 16 16 0, w 96::, h 32::");

        JPanel opponentBoardContainer = new JPanel(new MigLayout("fill, insets 0", "[center]", "[center]16[center]16[center]16[center]"));
        opponentBoardContainer.putClientProperty(FlatClientProperties.STYLE, "" +
                "background:darken(@background, 5%);");
        opponentPanel.add(opponentBoardContainer);

        JLabel opponentBoardLabel = new JLabel("Sắp xếp tăng dần");
        opponentBoardLabel.putClientProperty(FlatClientProperties.STYLE, "" +
                "foreground:darken(@background, 50%);" +
                "font:bold +4;");
        opponentBoardContainer.add(opponentBoardLabel, "wrap");

        JPanel opponentInitialBoard = new JPanel(new MigLayout("gap 8", "[center]", "[center]"));
        opponentInitialBoard.putClientProperty(FlatClientProperties.STYLE, "" +
                "arc:32;");
        opponentBoardContainer.add(opponentInitialBoard, "wrap");

        for (int i = 0; i < 10; i++) {
            opponentInitialButtons[i] = new JButton();
            opponentInitialButtons[i].putClientProperty(FlatClientProperties.STYLE, "" +
                    "font:semibold;");
            opponentInitialButtons[i].setEnabled(false);
            opponentInitialBoard.add(opponentInitialButtons[i], "w 48!, h 48!, hidemode 0");
        }

        JPanel sectionSeparator = new JPanel();
        sectionSeparator.putClientProperty(FlatClientProperties.STYLE, "" +
                "arc:4;");
        opponentBoardContainer.add(sectionSeparator, "wrap, w 256!, h 4!");

        JPanel opponentResultBoard = new JPanel(new MigLayout("gap 8", "[center]", "[center]"));
        opponentResultBoard.putClientProperty(FlatClientProperties.STYLE, "" +
                "background:tint(tint(@accentColor, 80%), 95%);" +
                "border:1,1,1,1,tint(@accentColor, 80%);" +
                "arc:32;");
        opponentBoardContainer.add(opponentResultBoard, "wrap");

        for (int i = 0; i < 10; i++) {
            opponentResultButtons[i] = new JButton();
            opponentResultButtons[i].putClientProperty(FlatClientProperties.STYLE, "" +
                    "disabledText:tint(@accentColor, 20%);" +
                    "disabledBackground:tint(tint(@accentColor, 50%), 95%);" +
                    "border:1,1,1,1,tint(@accentColor, 50%),1,16;" +
                    "font:semibold;");
            opponentResultButtons[i].setEnabled(false);
            opponentResultBoard.add(opponentResultButtons[i], "w 48!, h 48!, hidemode 0");
        }
    }

    private void initMyBoardComponents() {
        JPanel myBoardTitle = new JPanel(new MigLayout("fill"));
        myPanel.add(myBoardTitle, "dock north, grow");

        timeRemainingLabel = new JLabel("Thời gian còn lại: 00:30");
        timeRemainingLabel.putClientProperty(FlatClientProperties.STYLE, "" +
                "font:bold +4;");
        myBoardTitle.add(timeRemainingLabel, "dock west, gap 16 16 16 0");

        doneButton = new JButton("Hoàn thành");
        doneButton.putClientProperty(FlatClientProperties.STYLE, "" +
                "foreground:@accentColor;" +
                "background:tint(@accentColor, 95%);" +
                "borderColor:@accentColor;" +
                "hoverBorderColor:@accentColor;" +
                "focusedBorderColor: @accentColor;" +
                "font:semibold;");
        myBoardTitle.add(doneButton, "dock east, gap 16 16 16 0, w 96::, h 32::");

        JPanel myBoardContainer = new JPanel(new MigLayout("fill, insets 0", "[center]", "[center]16[center]16[center]16[center]"));
        myPanel.add(myBoardContainer);

        JLabel myBoardLabel = new JLabel("Sắp xếp giảm dần");
        myBoardLabel.putClientProperty(FlatClientProperties.STYLE, "" +
                "font:bold +4;");
        myBoardContainer.add(myBoardLabel, "wrap");


        JPanel myResultBoard = new JPanel(new MigLayout("gap 8", "[center]", "[center]"));
        myResultBoard.putClientProperty(FlatClientProperties.STYLE, "" +
                "background:tint(@accentColor, 95%);" +
                "border:1,1,1,1,@accentColor;" +
                "arc:32;");
        myBoardContainer.add(myResultBoard, "wrap");

        for (int i = 0; i < 10; i++) {
            myResultButtons[i] = new JButton();
            myResultButtons[i].putClientProperty(FlatClientProperties.STYLE, "" +
                    "disabledText:tint(@accentColor, 20%);" +
                    "disabledBackground:tint(tint(@accentColor, 50%), 95%);" +
                    "foreground:@accentColor;" +
                    "background:tint(@accentColor, 95%);" +
                    "border:1,1,1,1,@accentColor,1,16;" +
                    "font:bold +4;");
            myResultBoard.add(myResultButtons[i], "w 52!, h 52!, hidemode 0");
        }

        JPanel sectionSeparator = new JPanel();
        sectionSeparator.putClientProperty(FlatClientProperties.STYLE, "" +
                "background:lighten(@background, 5%);" +
                "arc:4;");
        myBoardContainer.add(sectionSeparator, "wrap, w 256!, h 4!");

        JPanel myInitialBoard = new JPanel(new MigLayout("gap 8", "[center]", "[center]"));
        myInitialBoard.putClientProperty(FlatClientProperties.STYLE, "" +
                "background:lighten(@background, 5%);" +
                "arc:32;");
        myBoardContainer.add(myInitialBoard, "wrap");

        for (int i = 0; i < 10; i++) {
            myInitialButtons[i] = new JButton();
            myInitialButtons[i].putClientProperty(FlatClientProperties.STYLE, "" +
                    "font:bold +4;");
            myInitialBoard.add(myInitialButtons[i], "w 52!, h 52!, hidemode 0");
        }
    }

    @Override
    public void componentResized(ComponentEvent e) {
        if (e.getComponent().getWidth() < 800) {
            opponentInfoContainer.setVisible(false);
            myInfoContainer.setVisible(false);
            revalidate();
        } else {
            opponentInfoContainer.setVisible(true);
            myInfoContainer.setVisible(true);
            revalidate();
        }
    }

    @Override
    public void onDataReceivedForView(ObjectWrapper data) {
        // TODO: on time remaining data received call setTimeRemaining()
        // TODO: on opponent's data received call setOpponentGameState()

        // TODO: on game finished (time out or surrender or both done) call onGameOver()
    }
}
