package main;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.fonts.jetbrains_mono.FlatJetBrainsMonoFont;
import control.ClientControl;
import model.ObjectWrapper;
import model.Player;
import view.LoginView;
import view.base.BaseView;
import view.base.ComponentResizeListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.Stack;

public class Application extends JFrame {
    private final JPanel navigator = new JPanel(new CardLayout());
    private ClientControl clientControl;

    private Stack<BaseView> backStack;

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

        clientControl = new ClientControl();
        backStack = new Stack<>();

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

    public void setRoot(BaseView panel) {
        System.out.println("Navigation Log: Set root to " + panel.getName());
        clientControl.setBaseView(panel);
        backStack.push(panel);
        panel.setBounds(getBounds());
        navigator.removeAll();
        navigator.add(panel, panel.getName());
        CardLayout cardLayout = (CardLayout) navigator.getLayout();
        cardLayout.next(navigator);
    }

    public void nextTo(BaseView panel) {
        System.out.println("Navigation Log: Next to " + panel.getName());
        backStack.peek().onPause();
        clientControl.setBaseView(panel);
        backStack.push(panel);
        panel.setBounds(getBounds());
        navigator.add(panel, panel.getName());
        CardLayout cardLayout = (CardLayout) navigator.getLayout();
        cardLayout.next(navigator);
        panel.initState();
    }

    public void back() {
        if (navigator.getComponentCount() == 1) {
            System.out.println("Navigation Log: Cannot go back");
            return;
        }
        backStack.pop().dispose();
        backStack.peek().onResume();
        clientControl.setBaseView(backStack.peek());

        System.out.println("Navigation Log: Back");

        CardLayout cardLayout = (CardLayout) navigator.getLayout();
        cardLayout.previous(navigator);
        navigator.remove(navigator.getComponentCount() - 1);
    }

    public void closeAllDialogs() {
        for (Window window : Window.getWindows()) {
            if (window instanceof JDialog) {
                window.dispose();
            }
        }
    }

    public boolean sendData(ObjectWrapper obj) {
        return clientControl.sendData(obj);
    }

    public static void main(String[] args) {
        System.out.println("App Starting . . .");
        EventQueue.invokeLater(() -> {
            Application app = Application.getInstance();
            app.getInstance().setVisible(true);
            app.setRoot(new LoginView());
        });
    }

    public Integer getCurrentPlayerId() {
        return clientControl.getCurrentPlayerId();
    }

    public ArrayList<Player> getPlayerList() {
        return clientControl.getPlayerList();
    }
}
