package bms.view;

import bms.components.RoundedTextField;
import bms.controller.CustomerDAO;
import bms.model.LoanDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class Loants extends JFrame {

    public DefaultTableModel model;
    public JTable table;
    public JButton btnSearch, reqLoan;
    public JTextField searchField;
    public JLabel loanStatusLabel;

    public JTextField amountField, durationField;
    public JComboBox<String> loanTypeBox;
    public JTextArea reasonArea;

    public JLabel selectedCustomerLabel;

    public int selectedCustomerId = -1;

    public Loants() {
        setTitle("Loan Management");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 650);
        setLayout(null);
        getContentPane().setBackground(Color.DARK_GRAY);

        // Table setup
        String[] columns = {"ID", "Name", "Phone", "Age", "Username", "Saving"};
        model = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 150, 600, 400);
        add(scrollPane);

        // Search field
        searchField = new RoundedTextField("", 12);
        searchField.setBounds(50, 20, 300, 40);
        add(searchField);

        btnSearch = new JButton("Search");
        btnSearch.setBounds(370, 20, 100, 40);
        styleButton(btnSearch);
        add(btnSearch);

        selectedCustomerLabel = new JLabel("Selected Customer: None");
        selectedCustomerLabel.setForeground(Color.WHITE);
        selectedCustomerLabel.setBounds(50, 70, 400, 20);
        add(selectedCustomerLabel);

        // Loan Details labels and inputs
        JLabel lblLoanDetails = new JLabel("Loan Details");
        lblLoanDetails.setForeground(Color.WHITE);
        lblLoanDetails.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblLoanDetails.setBounds(700, 60, 200, 30);
        add(lblLoanDetails);

        JLabel lblAmount = new JLabel("Amount:");
        JLabel lblDuration = new JLabel("Duration (months):");
        JLabel lblType = new JLabel("Type:");
        JLabel lblReason = new JLabel("Reason:");

        lblAmount.setBounds(680, 100, 120, 25);
        lblDuration.setBounds(680, 150, 120, 25);
        lblType.setBounds(680, 200, 120, 25);
        lblReason.setBounds(680, 250, 120, 25);

        for (JLabel lbl : new JLabel[]{lblAmount, lblDuration, lblType, lblReason}) {
            lbl.setForeground(Color.WHITE);
            lbl.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            add(lbl);
        }

        amountField = new RoundedTextField("", 12);
        durationField = new RoundedTextField("", 12);
        loanTypeBox = new JComboBox<>(new String[]{"Home", "Car", "Education", "Personal"});
        reasonArea = new JTextArea();
        reasonArea.setLineWrap(true);
        reasonArea.setWrapStyleWord(true);
        JScrollPane reasonScroll = new JScrollPane(reasonArea);

        amountField.setBounds(800, 100, 150, 30);
        durationField.setBounds(800, 150, 150, 30);
        loanTypeBox.setBounds(800, 200, 150, 30);
        reasonScroll.setBounds(800, 250, 150, 70);

        add(amountField);
        add(durationField);
        add(loanTypeBox);
        add(reasonScroll);

        reqLoan = new JButton("Request Loan");
        reqLoan.setBounds(800, 340, 150, 40);
        styleButton(reqLoan);
        add(reqLoan);

        loanStatusLabel = new JLabel("Loan Info: No customer selected.");
        loanStatusLabel.setForeground(Color.YELLOW);
        loanStatusLabel.setBounds(50, 90, 600, 20);
        add(loanStatusLabel);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void styleButton(JButton btn) {
        btn.setBackground(Color.BLACK);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
    }

    public double getRequestedLoanAmount() {
        try {
            return Double.parseDouble(amountField.getText());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public String getRequestedLoanType() {
        return loanTypeBox.getSelectedItem().toString();
    }

    public int getRequestedLoanDuration() {
        try {
            return Integer.parseInt(durationField.getText());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public String getRequestedLoanReason() {
        return reasonArea.getText();
    }

    public void addRequestLoanListener(ActionListener listener) {
        reqLoan.addActionListener(listener);
    }

    public void addSearchListener(ActionListener listener) {
        btnSearch.addActionListener(listener);
    }

    public void setTableData(java.util.List<bms.model.Customer> customers) {
        model.setRowCount(0);
        for (bms.model.Customer c : customers) {
            Object[] row = {
                    c.getId(),
                    c.getName(),
                    c.getPhoneNumber(),
                    c.getAge(),
                    c.getUserName(),
                    c.getSaving()
            };
            model.addRow(row);
        }
    }

    public void setSelectedCustomerLabel(String text) {
        selectedCustomerLabel.setText(text);
    }

    public void setLoanStatusLabel(String text) {
        loanStatusLabel.setText(text);
    }

    public JTable getTable() {
        return table;
    }

    public int getSelectedCustomerId() {
        return selectedCustomerId;
    }

    public void setSelectedCustomerId(int id) {
        this.selectedCustomerId = id;
    }

    public void clearLoanInputs() {
        amountField.setText("");
        durationField.setText("");
        reasonArea.setText("");
        loanTypeBox.setSelectedIndex(0);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Loants view = new Loants();
            bms.controller.CustomerDAO customerDAO = new bms.controller.CustomerDAO();
            bms.model.LoanDAO loanDAO = new bms.model.LoanDAO();
            bms.controller.LoanRequestController controller = new bms.controller.LoanRequestController(view, customerDAO, loanDAO);
            view.setVisible(true);
        });
    }

}
