package bms.controller;

import bms.model.Customer;
import bms.model.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {

    // Get all customers
    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        String query = "SELECT id, name, phone_number, age, user_name, saving, password FROM customers";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Customer customer = new Customer();
                customer.setId(rs.getInt("id"));
                customer.setName(rs.getString("name"));
                customer.setPhoneNumber(rs.getString("phone_number"));
                customer.setAge(rs.getInt("age"));
                customer.setUserName(rs.getString("user_name"));
                customer.setSaving(rs.getDouble("saving"));
                customer.setPassword(rs.getString("password"));
                customers.add(customer);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    // Get a customer by ID
    public Customer getCustomerById(int id) {
        Customer c = null;
        String sql = "SELECT * FROM customers WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    c = new Customer();
                    c.setId(rs.getInt("id"));
                    c.setName(rs.getString("name"));
                    c.setPhoneNumber(rs.getString("phone_number"));
                    c.setAge(rs.getInt("age"));
                    c.setUserName(rs.getString("user_name"));
                    c.setSaving(rs.getDouble("saving"));
                    c.setPassword(rs.getString("password"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return c;
    }

    // Insert a new customer
    public void insertCustomer(Customer c) throws SQLException {
        String sql = "INSERT INTO customers (name, phone_number, age, user_name, saving, password) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getName());
            ps.setString(2, c.getPhoneNumber());
            ps.setInt(3, c.getAge());
            ps.setString(4, c.getUserName());
            ps.setDouble(5, c.getSaving());
            ps.setString(6, c.getPassword());
            ps.executeUpdate();
        }
    }

    // Update an existing customer
    public void updateCustomer(Customer c) throws SQLException {
        String sql = "UPDATE customers SET name=?, phone_number=?, age=?, user_name=?, saving=?, password=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getName());
            ps.setString(2, c.getPhoneNumber());
            ps.setInt(3, c.getAge());
            ps.setString(4, c.getUserName());
            ps.setDouble(5, c.getSaving());
            ps.setString(6, c.getPassword());
            ps.setInt(7, c.getId());
            ps.executeUpdate();
        }
    }

    // Delete a customer by ID
    public void deleteCustomer(int id) throws SQLException {
        String sql = "DELETE FROM customers WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
    
    // Update only the saving balance of a customer
    public void updateSaving(int customerId, double newSaving) throws SQLException {
        String sql = "UPDATE customers SET saving = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, newSaving);
            ps.setInt(2, customerId);
            ps.executeUpdate();
        }
    }


    // Search customers by name or username
    public List<Customer> searchCustomers(String keyword) {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM customers WHERE name LIKE ? OR user_name LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            String likeKeyword = "%" + keyword + "%";
            ps.setString(1, likeKeyword);
            ps.setString(2, likeKeyword);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Customer c = new Customer();
                    c.setId(rs.getInt("id"));
                    c.setName(rs.getString("name"));
                    c.setPhoneNumber(rs.getString("phone_number"));
                    c.setAge(rs.getInt("age"));
                    c.setUserName(rs.getString("user_name"));
                    c.setSaving(rs.getDouble("saving"));
                    c.setPassword(rs.getString("password"));
                    customers.add(c);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }
}
