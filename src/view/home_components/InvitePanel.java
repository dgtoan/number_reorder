package view.home_components;

import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class InvitePanel extends JPanel {
    boolean isOnline;
    JButton inviteButton;
    public InvitePanel(boolean isOnline) {
        this.isOnline = isOnline;
        initComponents();
    }

    private void initComponents() {
        setLayout(new MigLayout("fill, insets 0", "[center]", "[center]"));
        putClientProperty(FlatClientProperties.STYLE, "" +
                "background:lighten(@background, 5%);" +
                "border:0,0,1,0,@background;");
        inviteButton = new JButton("Mời");
        inviteButton.putClientProperty(FlatClientProperties.STYLE, "" +
                "font:bold;" +
                "foreground:#34C759;" +
                "border:1,1,1,1,#34C759,1,16;");
        JLabel offlineLabel = new JLabel("Offline");
        offlineLabel.putClientProperty(FlatClientProperties.STYLE, "" +
                "foreground:#FF3B30;");
        if (isOnline) {
            add(inviteButton, "grow, h 32!, gapx 8 8");
        } else {
            add(offlineLabel, "grow, gapx 8 8");
        }
    }

    public void setOnline(boolean isOnline) {
        this.isOnline = isOnline;
        removeAll();
        initComponents();
    }
}
