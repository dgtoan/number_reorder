package view.home_components;

import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class InvitePanel extends JPanel {
    String onlineStatus;
    JButton inviteButton;
    public InvitePanel(String onlineStatus) {
        this.onlineStatus = onlineStatus;
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
        if (onlineStatus == "Online") {
            add(inviteButton, "grow, h 32!, gapx 8 8");
        } else if (onlineStatus == "Playing") {
            // Playing status
            JLabel playingLabel = new JLabel(onlineStatus);
            playingLabel.putClientProperty(FlatClientProperties.STYLE, "" +
                    "foreground:#007AFF;");
            add(playingLabel, "grow, gapx 8 8");
        } else {
            JLabel offlineLabel = new JLabel(onlineStatus);
            offlineLabel.putClientProperty(FlatClientProperties.STYLE, "" +
                    "foreground:#FF3B30;");
            add(offlineLabel, "grow, gapx 8 8");
        }
    }
}
