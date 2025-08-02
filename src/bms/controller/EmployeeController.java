package bms.controller;

import bms.model.Customer;
import bms.view.Emp;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class EmployeeController {

    private Emp view;
    private CustomerDAO dao;

    public EmployeeController(Emp view, CustomerDAO dao) {
        this.view = view;
        this.dao = dao;

        // Load and display all customers initially
        loadCustomers();

        // Add listener for W/D button
        this.view.addWithdrawDepositListener(new WithdrawDepositListener());

        // Add listener for search button
        this.view.addSearchListener(e -> searchCustomers());

        // Add listener for table row selection to update summary labels
        this.view.table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && view.table.getSelectedRow() != -1) {
                    int row = view.table.getSelectedRow();
                    int customerId = Integer.parseInt(view.model.getValueAt(row, 0).toString());

                    // Fetch latest customer details from DB
                    Customer selectedCustomer = dao.getCustomerById(customerId);
                    if (selectedCustomer != null) {
                        view.setSummaryLabels(
                                String.valueOf(selectedCustomer.getId()),
                                selectedCustomer.getName(),
                                String.valueOf(selectedCustomer.getSaving())
                        );
                    }
                }
            }
        });
    }

    private void loadCustomers() {
        List<Customer> customers = dao.getAllCustomers();
        view.setTableData(customers);
    }

    private void searchCustomers() {
        String keyword = view.searchField.getText().trim();
        if (keyword.isEmpty()) {
            loadCustomers();
        } else {
            List<Customer> customers = dao.searchCustomers(keyword);
            view.setTableData(customers);
        }
    }

    private class WithdrawDepositListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int customerId = Integer.parseInt(view.fieldId.getText());
                double currentSaving = Double.parseDouble(view.fieldSaving.getText());
                double withdrawAmount = view.getWithdrawAmount();
                double depositAmount = view.getDepositAmount();

                double newSaving = currentSaving + depositAmount - withdrawAmount;
                if (newSaving < 0) {
                    JOptionPane.showMessageDialog(view, "Insufficient balance!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                dao.updateSaving(customerId, newSaving);
                JOptionPane.showMessageDialog(view, "Transaction successful!");

                // Refresh table and UI fields
                loadCustomers();

                // Optionally clear deposit/withdraw fields
                view.fieldDeposit.setText("");
                view.fieldWithdraw.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(view, "Invalid number format!", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(view, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }
}
