package view.components;

import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class StatsItemPanel extends JPanel {
    public StatsItemPanel(String title, String value) {
        setLayout(new MigLayout("fill, insets 0", "[center]", "[center]"));
        putClientProperty(FlatClientProperties.STYLE, "" +
                "background:lighten(@background, 5%);" +
                "border:1,1,1,1,@accentColor;" + "arc:16");

        JLabel titleLabel = new JLabel(title);
        titleLabel.putClientProperty(FlatClientProperties.STYLE, "" +
                "foreground:lighten(@foreground, 20%);");
        add(titleLabel, "dock north, gap 8 8 8 8");

        JLabel valueLabel = new JLabel(value);
        valueLabel.putClientProperty(FlatClientProperties.STYLE, "" +
                "font:bold +4; " +
                "foreground:lighten(@foreground, 20%);");
        add(valueLabel, "gapbottom 8");
    }
}
