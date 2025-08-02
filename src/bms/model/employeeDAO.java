package bms.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class employeeDAO {

    // Get all employees
    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employees";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                employees.add(mapResultSetToEmployee(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    // Search employees by name
    public List<Employee> searchEmployeesByName(String name) {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employees WHERE name LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + name + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                employees.add(mapResultSetToEmployee(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    // Insert a new employee
    public void insertEmployee(Employee emp) throws SQLException {
        String sql = "INSERT INTO employees (name, role, salary, branch, user_name, password, age, phone_number) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, emp.getName());
            ps.setString(2, emp.getRole());
            ps.setDouble(3, emp.getSalary());
            ps.setString(4, emp.getBranch());
            ps.setString(5, emp.getUserName());
            ps.setString(6, emp.getPassword());
            ps.setInt(7, emp.getAge());
            ps.setString(8, emp.getPhoneNumber());
            ps.executeUpdate();
        }
    }

    // Update existing employee
    public void updateEmployee(Employee emp) throws SQLException {
        String sql = "UPDATE employees SET name=?, role=?, salary=?, branch=?, user_name=?, password=?, age=?, phone_number=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, emp.getName());
            ps.setString(2, emp.getRole());
            ps.setDouble(3, emp.getSalary());
            ps.setString(4, emp.getBranch());
            ps.setString(5, emp.getUserName());
            ps.setString(6, emp.getPassword());
            ps.setInt(7, emp.getAge());
            ps.setString(8, emp.getPhoneNumber());
            ps.setInt(9, emp.getId());
            ps.executeUpdate();
        }
    }

    // Delete employee by ID
    public void deleteEmployee(int id) throws SQLException {
        String sql = "DELETE FROM employees WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    // Validate employee login credentials
    public boolean isValidEmployee(String username, String password) {
        String sql = "SELECT * FROM employees WHERE user_name = ? AND password = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Utility: map ResultSet to Employee object
    private Employee mapResultSetToEmployee(ResultSet rs) throws SQLException {
        Employee emp = new Employee();
        emp.setId(rs.getInt("id"));
        emp.setName(rs.getString("name"));
        emp.setRole(rs.getString("role"));
        emp.setSalary(rs.getDouble("salary"));
        emp.setBranch(rs.getString("branch"));
        emp.setUserName(rs.getString("user_name"));
        emp.setPassword(rs.getString("password"));
        emp.setAge(rs.getInt("age"));
        emp.setPhoneNumber(rs.getString("phone_number"));
        return emp;
    }
}
