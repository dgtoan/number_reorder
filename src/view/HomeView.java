package view;

import main.Application;

import javax.swing.*;

public class HomeView extends JPanel {
    public HomeView() {
        setName("Home View");
        setLayout(null);

        // back button
        JButton button = new JButton("Back");
        button.setBounds(10, 40, 100, 20);
        button.addActionListener(e -> {
            Application.getInstance().back();
        });
        add(button);
    }
}
