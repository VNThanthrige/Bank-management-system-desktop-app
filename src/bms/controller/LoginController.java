package bms.controller;

import bms.model.employeeDAO;
import bms.view.AdminDashboardView;
import bms.view.Emp;
import bms.view.LoginView;

import javax.swing.*;

public class LoginController {
    private LoginView loginView;
    private employeeDAO empDao;

    public LoginController(LoginView loginView, employeeDAO empDao) {
        this.loginView = loginView;
        this.empDao = empDao;

        loginView.loginButton.addActionListener(e -> performLogin());
    }

    private void performLogin() {
        String username = loginView.usernameField.getText().trim();
        String password = new String(loginView.passwordField.getPassword());

        if (username.equals("admin") && password.equals("123")) {
            // Admin login success
            JOptionPane.showMessageDialog(loginView, "Welcome Admin!");
            AdminDashboardView adminView = new AdminDashboardView();
            CustomerDAO customerDao = new CustomerDAO();
            // You need to import and create CustomerController accordingly:
            new CustomerController(adminView, customerDao); // Open Admin dashboard
            loginView.dispose();  // Close login window
        } else {
            // Check employee credentials
            boolean validEmployee = empDao.isValidEmployee(username, password);
            if (validEmployee) {
                JOptionPane.showMessageDialog(loginView, "Employee login successful!");
                Emp view = new Emp();
                CustomerDAO dao = new CustomerDAO();
                new EmployeeController(view, dao);
                loginView.dispose();  // Close login window
            } else {
                JOptionPane.showMessageDialog(loginView, "Invalid username or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
