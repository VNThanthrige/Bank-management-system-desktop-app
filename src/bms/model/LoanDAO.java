package bms.model;

import java.sql.*;

public class LoanDAO {

    private final String DB_URL = "jdbc:mysql://localhost:3306/bank_db?useSSL=false&serverTimezone=UTC";
    private final String USER = "root";
    private final String PASS = "Mnnv(@)1234122506";

    public boolean hasActiveLoan(int customerId) {
        String sql = "SELECT COUNT(*) FROM loans WHERE customer_id = ? AND status IN ('Pending', 'Approved')";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean insertLoanRequest(int customerId, String loanType, double amount, int duration, String reason) {
        String sql = "INSERT INTO loans (customer_id, customer_name, loan_type, amount, duration_months, reason, status) " +
                     "VALUES (?, (SELECT name FROM customers WHERE id = ?), ?, ?, ?, ?, 'Pending')";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, customerId);
            ps.setInt(2, customerId); // for subquery to get name
            ps.setString(3, loanType);
            ps.setDouble(4, amount);
            ps.setInt(5, duration);
            ps.setString(6, reason);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ResultSet getAllLoans() {
        String sql = "SELECT * FROM loans";
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ResultSet getApprovedLoans() {
        String sql = "SELECT * FROM loans WHERE status = 'Approved'";
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ResultSet getLoanById(int loanId) {
        String sql = "SELECT * FROM loans WHERE id = ?";
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ps.setInt(1, loanId);
            return ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateLoanStatus(int loanId, String newStatus) {
        String sql = "UPDATE loans SET status = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newStatus);
            ps.setInt(2, loanId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
