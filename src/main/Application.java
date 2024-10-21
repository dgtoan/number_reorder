package main;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.fonts.jetbrains_mono.FlatJetBrainsMonoFont;
import view.ComponentResizeListener;
import view.LoginScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class Application extends JFrame {
    private final JPanel navigator = new JPanel(new CardLayout());

    private static final Application instance = new Application();

    public ComponentResizeListener componentResizeListener;

    private Application() {
        customFlatLaf();
        setTitle("Sắp xếp thứ tự");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1280, 720);
        setLocationRelativeTo(null);
        navigator.setBounds(0, 0, 1280, 720);
        add(navigator);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                if (componentResizeListener != null)
                    componentResizeListener.componentResized(e);
            }
        });
    }

    public static Application getInstance() {
        return instance;
    }

    private void customFlatLaf() {
        FlatJetBrainsMonoFont.install();
        FlatLaf.registerCustomDefaultsSource("themes");
        UIManager.put("defaultFont", new Font(FlatJetBrainsMonoFont.FAMILY, Font.PLAIN, 14));
        FlatLightLaf.setup();
    }

    public void setRoot(JPanel panel) {
        System.out.println("Navigation Log: Set root to " + panel.getName());

        panel.setBounds(getBounds());
        navigator.removeAll();
        navigator.add(panel, panel.getName());
        CardLayout cardLayout = (CardLayout) navigator.getLayout();
        cardLayout.next(navigator);
    }

    public void nextTo(JPanel panel) {
        System.out.println("Navigation Log: Next to " + panel.getName());

        panel.setBounds(getBounds());
        navigator.add(panel, panel.getName());
        CardLayout cardLayout = (CardLayout) navigator.getLayout();
        cardLayout.next(navigator);
    }

    public void back() {
        if (navigator.getComponentCount() == 1) {
            System.out.println("Navigation Log: Cannot go back");
            return;
        }

        System.out.println("Navigation Log: Back");

        CardLayout cardLayout = (CardLayout) navigator.getLayout();
        cardLayout.previous(navigator);
        navigator.remove(navigator.getComponentCount() - 1);
    }


    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            Application app = Application.getInstance();
            app.getInstance().setVisible(true);
            app.setRoot(new LoginScreen());
        });
    }
}
