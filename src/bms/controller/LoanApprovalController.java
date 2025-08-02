package bms.controller;

import bms.model.LoanDAO;
import bms.view.Loan;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class LoanApprovalController {

    private Loan view;
    private LoanDAO dao;

    public LoanApprovalController(Loan view, LoanDAO dao) {
        this.view = view;
        this.dao = dao;
        initController();
        loadAllLoans();
    }

    private void initController() {
        view.btnSearch.addActionListener(e -> searchLoanById());
        view.btnShowAll.addActionListener(e -> loadAllLoans());
        view.btnShowApproved.addActionListener(e -> loadApprovedLoans());
        view.btnApprove.addActionListener(e -> updateLoanStatus("Approved"));
        view.btnReject.addActionListener(e -> updateLoanStatus("Rejected"));
    }

    private void loadAllLoans() {
        loadLoans("SELECT * FROM loans");
    }

    private void loadApprovedLoans() {
        loadLoans("SELECT * FROM loans WHERE status = 'Approved'");
    }

    private void searchLoanById() {
        String text = view.searchField.getText();
        if (!text.matches("\\d+")) {
            JOptionPane.showMessageDialog(view, "Enter a valid Loan ID");
            return;
        }
        int loanId = Integer.parseInt(text);
        loadLoans("SELECT * FROM loans WHERE id = " + loanId);
    }

    private void loadLoans(String query) {
        DefaultTableModel model = (DefaultTableModel) view.table.getModel();
        model.setRowCount(0);
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank_db", "root", "Mnnv(@)1234122506");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Object[] row = {
                        rs.getInt("id"),
                        rs.getInt("customer_id"),
                        rs.getString("customer_name"),
                        rs.getString("loan_type"),
                        rs.getDouble("amount"),
                        rs.getInt("duration_months"),
                        rs.getString("reason"),
                        rs.getString("status")
                };
                model.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, "Failed to load loans.");
        }
    }

    private void updateLoanStatus(String newStatus) {
        int selectedRow = view.table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Please select a loan.");
            return;
        }

        int loanId = (int) view.table.getValueAt(selectedRow, 0);
        String sql = "UPDATE loans SET status = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank_db", "root", "Mnnv(@)1234122506");
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newStatus);
            ps.setInt(2, loanId);
            int updated = ps.executeUpdate();
            if (updated > 0) {
                JOptionPane.showMessageDialog(view, "Loan " + newStatus.toLowerCase() + " successfully!");
                loadAllLoans();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, "Error updating loan status.");
        }
    }
}
