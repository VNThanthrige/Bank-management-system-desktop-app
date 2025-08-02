package bms.controller;

import bms.model.Employee;
import bms.model.employeeDAO;
import bms.view.AdminEmp;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.List;

public class EmployeeController2 {
    private AdminEmp view;
    private employeeDAO dao;

    public EmployeeController2(AdminEmp view, employeeDAO dao) {
        this.view = view;
        this.dao = dao;

        loadEmployees();          // Load all employees initially
        addListeners();           // Attach all listeners (CRUD + Search + Table click)
    }

    // Load all employees to the table
    private void loadEmployees() {
        List<Employee> employees = dao.getAllEmployees();
        populateTable(employees);
    }

    // Load searched employees to the table
    private void searchEmployeesByName(String name) {
        List<Employee> employees = dao.searchEmployeesByName(name);
        populateTable(employees);
    }

    // Helper method to populate JTable model with list of employees
    private void populateTable(List<Employee> employees) {
        DefaultTableModel model = view.model;
        model.setRowCount(0); // clear existing rows

        for (Employee emp : employees) {
            model.addRow(new Object[]{
                emp.getName(),
                emp.getId(),
                emp.getRole(),
                emp.getSalary(),
                emp.getBranch(),
                emp.getUserName(),
                emp.getPassword(),
                emp.getAge(),
                emp.getPhoneNumber()
            });
        }
    }

    private void addListeners() {
        // When a row in the table is clicked, populate the input fields
        view.table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = view.table.getSelectedRow();
                if (row >= 0) {
                    view.fieldName.setText(view.model.getValueAt(row, 0).toString());
                    view.fieldId.setText(view.model.getValueAt(row, 1).toString());
                    view.fieldDeposit.setText(view.model.getValueAt(row, 2).toString());    // role
                    view.fieldWithdraw.setText(view.model.getValueAt(row, 3).toString());   // salary
                    view.fieldSaving.setText(view.model.getValueAt(row, 4).toString());     // branch
                    view.fieldUsername.setText(view.model.getValueAt(row, 5).toString());
                    view.fieldPassword.setText(view.model.getValueAt(row, 6).toString());
                    view.fieldAge.setText(view.model.getValueAt(row, 7).toString());
                    view.fieldPhone.setText(view.model.getValueAt(row, 8).toString());
                    
                    
                    
                    view.setSummaryLabels(
                        view.model.getValueAt(row, 0).toString(), // name
                        view.model.getValueAt(row, 5).toString(), // username
                        view.model.getValueAt(row, 6).toString()  // password
                    );
                }
            }
        });

        // CRUD buttons
        view.btnCreate.addActionListener(e -> createEmployee());
        view.btnUpdate.addActionListener(e -> updateEmployee());
        view.btnDelete.addActionListener(e -> deleteEmployee());

        // Search button listener
        view.btnSearch.addActionListener(e -> {
            String searchText = view.searchField.getText().trim();
            if (searchText.isEmpty()) {
                loadEmployees();  // If search empty, reload all
            } else {
                searchEmployeesByName(searchText);
            }
        });
    }

    private void createEmployee() {
        try {
            Employee emp = getEmployeeFromFields();
            dao.insertEmployee(emp);
            loadEmployees();
            JOptionPane.showMessageDialog(view, "Employee created successfully.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view, "Error: " + ex.getMessage());
        }
    }

    private void updateEmployee() {
        try {
            Employee emp = getEmployeeFromFields();
            emp.setId(Integer.parseInt(view.fieldId.getText()));
            dao.updateEmployee(emp);
            loadEmployees();
            JOptionPane.showMessageDialog(view, "Employee updated successfully.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view, "Error: " + ex.getMessage());
        }
    }

    private void deleteEmployee() {
        try {
            int id = Integer.parseInt(view.fieldId.getText());
            dao.deleteEmployee(id);
            loadEmployees();
            JOptionPane.showMessageDialog(view, "Employee deleted successfully.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view, "Error: " + ex.getMessage());
        }
    }

    // Extract employee data from input fields
    private Employee getEmployeeFromFields() {
        Employee emp = new Employee();
        emp.setName(view.fieldName.getText());
        emp.setRole(view.fieldDeposit.getText());
        emp.setSalary(Double.parseDouble(view.fieldWithdraw.getText()));
        emp.setBranch(view.fieldSaving.getText());
        emp.setUserName(view.fieldUsername.getText());
        emp.setPassword(view.fieldPassword.getText());
        emp.setAge(Integer.parseInt(view.fieldAge.getText()));
        emp.setPhoneNumber(view.fieldPhone.getText());
        return emp;
    }
}
