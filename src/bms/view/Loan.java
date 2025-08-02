package bms.view;

import bms.components.RoundedTextField;
import bms.model.LoanDAO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Loan extends JFrame {

    public DefaultTableModel model;
    public JTable table;
    public JButton btnSearch, btnApprove, btnReject, btnShowAll, btnShowApproved;
    public JTextField searchField;
    private int id;
    private int customerId;
    private String customerName;
    private String loanType;
    private double amount;
    private int durationMonths;
    private String reason;
    private String status;

    private LoanDAO loanDAO;

    public Loan() {
        loanDAO = new LoanDAO();

        setTitle("Loan Requests Management");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900, 600);
        setLayout(null);
        getContentPane().setBackground(Color.DARK_GRAY);

        // Table columns focused on loan info
        String[] columns = {"Loan ID", "Customer ID", "Customer Name", "Loan Type", "Amount", "Duration (months)", "Reason", "Status"};
        model = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(30, 100, 830, 400);
        add(scrollPane);

        // Search field and button
        searchField = new RoundedTextField("", 12);
        searchField.setBounds(30, 20, 300, 40);
        add(searchField);

        btnSearch = new JButton("Search");
        btnSearch.setBounds(340, 20, 90, 40);
        styleButton(btnSearch);
        add(btnSearch);

        // Approve and Reject buttons
        btnApprove = new JButton("Approve");
        btnApprove.setBounds(450, 20, 100, 40);
        styleButton(btnApprove);
        add(btnApprove);

        btnReject = new JButton("Reject");
        btnReject.setBounds(560, 20, 100, 40);
        styleButton(btnReject);
        add(btnReject);

        // Show all and show approved buttons
        btnShowAll = new JButton("Show All");
        btnShowAll.setBounds(670, 20, 90, 40);
        styleButton(btnShowAll);
        add(btnShowAll);

        btnShowApproved = new JButton("Accepted");
        btnShowApproved.setBounds(770, 20, 110, 40);
        styleButton(btnShowApproved);
        add(btnShowApproved);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void styleButton(JButton btn) {
        btn.setBackground(Color.BLACK);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Loan view = new Loan();
            LoanDAO dao = new LoanDAO();
            new bms.controller.LoanApprovalController(view, dao);
            view.setVisible(true);
        });
    }
}
