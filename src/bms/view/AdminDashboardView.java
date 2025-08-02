package bms.view;

import bms.components.Left_Admin;
import bms.components.Right_admin;
import bms.components.RoundedTextField;
import bms.controller.CustomerController;
import bms.controller.LoginController;
import bms.model.Customer;
import bms.controller.CustomerDAO;
import bms.model.LoanDAO;
import bms.model.employeeDAO;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class AdminDashboardView extends JFrame {

    public RoundedTextField fieldName, fieldDeposit, fieldWithdraw, fieldSaving,
            fieldId, fieldUsername, fieldPassword, fieldAge, fieldPhone;
    public DefaultTableModel model;
    public JTable table;
    public JButton btnUpdate, btnDelete, btnCreate, btnSearch, bt1, bt2, bt3, bt4, btnwd;
    public RoundedTextField searchField;
    public JLabel lblName, lblId, lblSaving;
    public JTable customerTable;
    public JLabel lblName1, lblId1, lblSaving1;

    public AdminDashboardView() {
        setTitle("Admin Dashboard");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1200, 670);
        setLayout(null);
        getContentPane().setBackground(Color.DARK_GRAY);

        Left_Admin leftAdmin = new Left_Admin(58, new Color(51, 51, 51));
        leftAdmin.setBounds(850, 30, 330, 568);
        add(leftAdmin);

        Right_admin rightAdmin = new Right_admin(58, new Color(51, 51, 51));
        rightAdmin.setBounds(50, 30, 300, 568);
        add(rightAdmin);

        // Labels on leftAdmin (keep Card_No label but no input field)
        String[] labels = {"Name", "Phone_number", "age", "Saving", "Card_No", "User_name", "Password", "Withdraw", "Deposit"};
        int[] yPositions = {10, 60, 110, 160, 210, 260, 310, 360, 410};

        for (int i = 0; i < labels.length; i++) {
            JLabel label = new JLabel(labels[i]);
            label.setBounds(20, yPositions[i], 100, 80);
            label.setForeground(new Color(225, 225, 225));
            label.setFont(new Font("Courier New", Font.BOLD, 15));
            leftAdmin.add(label);
        }

        JLabel welcome = new JLabel("Welcome, Admin Customer Accounts !!");
        welcome.setBounds(380, 60, 500, 80);
        welcome.setForeground(new Color(225, 225, 225));
        welcome.setFont(new Font("Segoe UI", Font.BOLD, 25));
        add(welcome);

        // Input fields
        fieldName = new RoundedTextField("", 12);
        fieldName.setBounds(120, 30, 200, 35);
        leftAdmin.add(fieldName);

        fieldPhone = new RoundedTextField("", 12);
        fieldPhone.setBounds(120, 80, 200, 35);
        leftAdmin.add(fieldPhone);

        fieldAge = new RoundedTextField("", 12);
        fieldAge.setBounds(120, 130, 200, 35);
        leftAdmin.add(fieldAge);

        fieldSaving = new RoundedTextField("", 12);
        fieldSaving.setBounds(120, 180, 200, 35);
        leftAdmin.add(fieldSaving);

        fieldId = new RoundedTextField("", 12);
        fieldId.setBounds(120, 230, 200, 35);
        leftAdmin.add(fieldId);

        fieldUsername = new RoundedTextField("", 12);
        fieldUsername.setBounds(120, 280, 200, 35);
        leftAdmin.add(fieldUsername);

        fieldPassword = new RoundedTextField("", 12);
        fieldPassword.setBounds(120, 330, 200, 35);
        leftAdmin.add(fieldPassword);

        fieldWithdraw = new RoundedTextField("", 12);
        fieldWithdraw.setBounds(120, 380, 200, 35);
        leftAdmin.add(fieldWithdraw);

        fieldDeposit = new RoundedTextField("", 12);
        fieldDeposit.setBounds(120, 430, 200, 35);
        leftAdmin.add(fieldDeposit);//fieldDeposit//fieldWithdraw    fieldAge   fieldPhone

        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setBounds(35, 370, 270, 4);
        leftAdmin.add(separator);

        // CRUD buttons
        btnUpdate = new JButton("Update");
        btnUpdate.setBounds(30, 470, 100, 40);
        styleButton(btnUpdate);
        leftAdmin.add(btnUpdate);

        btnDelete = new JButton("Delete");
        btnDelete.setBounds(200, 470, 100, 40);
        styleButton(btnDelete);
        leftAdmin.add(btnDelete);

        btnCreate = new JButton("Create");
        btnCreate.setBounds(30, 525, 100, 40);
        styleButton(btnCreate);
        leftAdmin.add(btnCreate);

        btnwd = new JButton("W/D");
        btnwd.setBounds(200, 525, 100, 40);
        styleButton(btnwd);
        leftAdmin.add(btnwd);

        // Summary panels
        Left_Admin summ01 = new Left_Admin(58, new Color(240, 240, 240));
        summ01.setBounds(355, 150, 160, 100);
        add(summ01);

        Left_Admin summ02 = new Left_Admin(58, new Color(240, 240, 240));
        summ02.setBounds(520, 150, 160, 100);
        add(summ02);

        Left_Admin summ03 = new Left_Admin(58, new Color(240, 240, 240));
        summ03.setBounds(685, 150, 160, 100);
        add(summ03);

        lblName = new JLabel();
        lblName.setBounds(32, 30, 100, 50);
        lblName.setForeground(Color.BLACK);
        lblName.setFont(new Font("Segoe UI", Font.BOLD, 16));
        summ01.add(new JLabel("Name:")).setBounds(50, 5, 70, 50);
        summ01.add(lblName);

        lblId = new JLabel();
        lblId.setBounds(32, 30, 100, 50);
        lblId.setForeground(Color.BLACK);
        lblId.setFont(new Font("Segoe UI", Font.BOLD, 16));
        summ02.add(new JLabel("ID:")).setBounds(50, 5, 70, 50);
        summ02.add(lblId);

        lblSaving = new JLabel();
        lblSaving.setBounds(32, 30, 100, 50);
        lblSaving.setForeground(Color.BLACK);
        lblSaving.setFont(new Font("Segoe UI", Font.BOLD, 16));
        summ03.add(new JLabel("Saving:")).setBounds(50, 5, 70, 50);
        summ03.add(lblSaving);
        
        lblName1 = new JLabel();
        lblName1.setBounds(32, 30, 100, 50);
        lblName1.setForeground(Color.BLACK);
        lblName1.setFont(new Font("Segoe UI", Font.BOLD, 16));
        summ01.add(new JLabel("")).setBounds(50, 40, 70, 50);
        summ01.add(lblName1);

        lblId1 = new JLabel();
        lblId1.setBounds(32, 30, 100, 50);
        lblId1.setForeground(Color.BLACK);
        lblId1.setFont(new Font("Segoe UI", Font.BOLD, 16));
        summ02.add(new JLabel("")).setBounds(50, 40, 70, 50);
        summ02.add(lblId1);

        lblSaving1 = new JLabel();
        lblSaving1.setBounds(32, 30, 100, 50);
        lblSaving1.setForeground(Color.BLACK);
        lblSaving1.setFont(new Font("Segoe UI", Font.BOLD, 16));
        summ03.add(new JLabel("")).setBounds(50, 40, 70, 50);
        summ03.add(lblSaving1);

        // Table
        String[] columns = {"ID", "Name", "Phone", "Age", "Username", "Saving", "Password"};
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(360, 280, 470, 300);
        add(scrollPane);

        // Search field + button
        searchField = new RoundedTextField("", 12);
        searchField.setBounds(360, 20, 300, 40);
        add(searchField);

        btnSearch = new JButton("Search");
        btnSearch.setBounds(690, 20, 100, 40);
        styleButton(btnSearch);
        add(btnSearch);

        bt1 = new JButton("ACCOUNTS");
        bt1.setBounds(90, 150, 150, 40);
        styleButton(bt1);
        rightAdmin.add(bt1);

        bt2 = new JButton("EMPLOYEES");
        bt2.setBounds(90, 250, 150, 40);
        styleButton(bt2);
        rightAdmin.add(bt2);

        bt2.addActionListener(e -> {
            AdminEmp view = new AdminEmp();
            bms.model.employeeDAO dao = new bms.model.employeeDAO();
            bms.controller.EmployeeController2 controller = new bms.controller.EmployeeController2(view, dao);
            dispose();
        });

        bt3 = new JButton("Loans/Card");
        bt3.setBounds(90, 350, 150, 40);
        styleButton(bt3);
        rightAdmin.add(bt3);
        
        bt3.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> {
                Loan view = new Loan();
                LoanDAO dao = new LoanDAO();
                new bms.controller.LoanApprovalController(view, dao);
                view.setVisible(true);
            });
        });

        bt4 = new JButton("LOG OUT");
        bt4.setBounds(90, 450, 150, 40);
        styleButton(bt4);
        rightAdmin.add(bt4);

        bt4.addActionListener(e -> {
            LoginView loginView = new LoginView();
            employeeDAO dao = new employeeDAO();
            new LoginController(loginView, dao);
            dispose();
        });

        // Right panel content (optional)
        ImageIcon icon = new ImageIcon("src/bank_man/icons8-x-100.png");
        Image scaledImage = icon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
        imageLabel.setBounds(30, 20, 120, 120);
        rightAdmin.add(imageLabel);

        JLabel TITLE = new JLabel("XILIN");
        TITLE.setBounds(140, 38, 150, 80);
        TITLE.setForeground(new Color(225, 225, 225));
        TITLE.setFont(new Font("Courier New", Font.BOLD, 35));
        rightAdmin.add(TITLE);

        // Add table row listener to fill input fields when a row is selected
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int row = table.getSelectedRow();
                fieldId.setText(model.getValueAt(row, 0).toString());
                fieldName.setText(model.getValueAt(row, 1).toString());
                fieldPhone.setText(model.getValueAt(row, 2).toString());
                fieldAge.setText(model.getValueAt(row, 3).toString());
                fieldUsername.setText(model.getValueAt(row, 4).toString());
                fieldSaving.setText(model.getValueAt(row, 5).toString());
                fieldPassword.setText(model.getValueAt(row, 6).toString());
                fieldDeposit.setText("");
                fieldWithdraw.setText("");
            }
            });

            setLocationRelativeTo(null);
            setVisible(true);
        }

    // Update table data from Controller
    public void setTableData(List<Customer> customers) {
        model.setRowCount(0);
        for (Customer c : customers) {
            model.addRow(new Object[]{
                    c.getId(),
                    c.getName(),
                    c.getPhoneNumber(),
                    c.getAge(),
                    c.getUserName(),
                    c.getSaving(),
                    c.getPassword()
            });
        }
    }
    
    
    public void setSummaryLabels(String id ,String name,String saving){  
        lblSaving.setText(saving);
        lblId1.setText(id);
        lblName1.setText(name);
    }

    // Add button listeners
    public void addCreateListener(ActionListener listener) {
        btnCreate.addActionListener(listener);
    }

    public void addUpdateListener(ActionListener listener) {
        btnUpdate.addActionListener(listener);
    }

    public void addDeleteListener(ActionListener listener) {
        btnDelete.addActionListener(listener);
    }

    public void addSearchListener(ActionListener listener) {
        btnSearch.addActionListener(listener);
    }

    public void addWithdrawDepositListener(ActionListener listener) {
        btnwd.addActionListener(listener);
    }

    public void addTableRowListener(ListSelectionListener listener) {
        table.getSelectionModel().addListSelectionListener(listener);
    }

    public double getWithdrawAmount() {
        try {
            return Double.parseDouble(fieldWithdraw.getText());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public double getDepositAmount() {
        try {
            return Double.parseDouble(fieldDeposit.getText());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private void styleButton(JButton btn) {
        btn.setBackground(Color.BLACK);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
    }

    public static void main(String[] args) {
        AdminDashboardView view = new AdminDashboardView();
        CustomerDAO dao = new CustomerDAO();
        new CustomerController(view, dao);
    }

}
