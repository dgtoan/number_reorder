package view;

import com.formdev.flatlaf.FlatClientProperties;
import main.Application;
import model.ObjectWrapper;
import net.miginfocom.swing.MigLayout;
import view.base.BaseView;
import view.components.UserInfoPanel;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ComponentEvent;

public class GameView extends BaseView {
    JPanel opponentInfoContainer;
    JPanel myInfoContainer;
    UserInfoPanel opponentInfoPanel;
    UserInfoPanel myInfoPanel;

    public GameView() {
        setName("Game View");
        setLayout(new MigLayout("fill, insets 0"));
        Application.getInstance().componentResizeListener = this;

        initComponents();
        initEvents();

        // mock data
        opponentInfoPanel.setUserInfo("Opponent", "#2", "1200");
        myInfoPanel.setUserInfo("Duong Van toan", "#1", "1300");
    }

    private void initEvents() {
    }

    private void initComponents() {
        JPanel opponentPanel = new JPanel(new MigLayout("fill, insets 16"));
        opponentPanel.putClientProperty(FlatClientProperties.STYLE, "" +
                "background:darken(@background, 5%);");
        add(opponentPanel, "dock north, grow");

        opponentInfoContainer = new JPanel(new MigLayout("fill, insets 16, wrap", "[center]", "[center]"));
        opponentPanel.add(opponentInfoContainer, "dock west, grow, hidemode 3");
        opponentInfoContainer.putClientProperty(FlatClientProperties.STYLE, "" +
                "background:darken(@background, 5%);");
        opponentInfoContainer.setBorder(new MatteBorder(0, 0, 0, 4, Color.LIGHT_GRAY));
        opponentInfoPanel = new UserInfoPanel(false);
        opponentInfoContainer.add(opponentInfoPanel);

        JPanel myPanel = new JPanel(new MigLayout("fill, insets 16"));
        myPanel.setBorder(new MatteBorder(2, 0, 0, 0, Color.decode("#00C7BE")));
        add(myPanel, "grow");

        myInfoContainer = new JPanel(new MigLayout("fill, insets 16, wrap", "[center]", "[center]"));
        myInfoContainer.setBorder(new MatteBorder(0, 0, 0, 4, Color.LIGHT_GRAY));
        myInfoPanel = new UserInfoPanel(false);
        myInfoContainer.add(myInfoPanel);
        myPanel.add(myInfoContainer, "dock west, grow, hidemode 3");
    }

    @Override
    public void componentResized(ComponentEvent e) {
        if (e.getComponent().getWidth() < 720) {
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
    public void onDataReceived(ObjectWrapper data) {

    }
}
