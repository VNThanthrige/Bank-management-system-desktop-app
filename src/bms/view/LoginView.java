package bms.view;

import bms.components.RoundPasswordField;
import javax.swing.*;
import java.awt.*;

import bms.components.RoundedPannel;
import bms.components.RoundedPannel2;
import bms.components.RoundedTextField;
import bms.controller.LoginController;
import bms.model.employeeDAO;

public class LoginView extends JFrame {
    public RoundedTextField usernameField;
    public RoundPasswordField passwordField;  // use RoundPasswordField as before
    public JButton loginButton;

    public LoginView() {
        setTitle("Login Page");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1200, 670);
        setLayout(null);

        RoundedPannel roundedPanel = new RoundedPannel(58, new Color(51, 51, 51));
        roundedPanel.setBounds(596, 30, 545, 568);
        add(roundedPanel);

        RoundedPannel2 roundedPane2 = new RoundedPannel2(60, new Color(225, 225, 225));
        roundedPane2.setBounds(50, 30, 1091, 568);
        add(roundedPane2);

        JLabel title = new JLabel("Hello, Welcome");
        title.setBounds(80, 175, 900, 80);
        title.setForeground(new Color(51, 51, 51));
        title.setFont(new Font("Segoe UI", Font.BOLD, 50));
        roundedPane2.add(title);

        JLabel subtitle = new JLabel("Where your data is safe and your");
        subtitle.setBounds(90, 235, 1200, 80);
        subtitle.setForeground(new Color(51, 51, 51));
        subtitle.setFont(new Font("Courier New", Font.BOLD, 19));
        roundedPane2.add(subtitle);

        JLabel subtitle1 = new JLabel(" service is seamless");
        subtitle1.setBounds(150, 275, 1200, 80);
        subtitle1.setForeground(new Color(51, 51, 51));
        subtitle1.setFont(new Font("Courier New", Font.BOLD, 19));
        roundedPane2.add(subtitle1);

        JLabel loginText = new JLabel("LOGIN");
        loginText.setBounds(205, 107, 300, 80);
        loginText.setForeground(new Color(225, 225, 225));
        loginText.setFont(new Font("Segoe UI", Font.BOLD, 40));
        roundedPanel.add(loginText);

        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setBounds(135, 177, 300, 80);
        usernameLabel.setForeground(new Color(225, 225, 225));
        usernameLabel.setFont(new Font("Courier New", Font.BOLD, 15));
        roundedPanel.add(usernameLabel);

        usernameField = new RoundedTextField("     Username", 20);
        usernameField.setBounds(120, 229, 300, 40);
        roundedPanel.add(usernameField);

        passwordField = new RoundPasswordField("      Password", 20);
        passwordField.setBounds(120, 315, 300, 40);
        roundedPanel.add(passwordField);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(135, 265, 300, 80);
        passwordLabel.setForeground(new Color(225, 225, 225));
        passwordLabel.setFont(new Font("Courier New", Font.BOLD, 15));
        roundedPanel.add(passwordLabel);

        loginButton = new JButton("Login");
        loginButton.setBounds(120, 385, 300, 40);
        loginButton.setBackground(new Color(0, 0, 0));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        loginButton.setFocusPainted(false);
        loginButton.setBorderPainted(false);
        roundedPanel.add(loginButton);

        getContentPane().setBackground(Color.DARK_GRAY);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    public static void main(String[] args) {
        LoginView loginView = new LoginView();
        employeeDAO dao = new employeeDAO();
        new LoginController(loginView, dao);
    }
}
