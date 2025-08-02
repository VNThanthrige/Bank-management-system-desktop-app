package bms.controller;

import bms.model.Customer;
import bms.view.AdminDashboardView;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CustomerController {

    private AdminDashboardView view;
    private CustomerDAO dao;

    public CustomerController(AdminDashboardView view, CustomerDAO dao) {
        this.view = view;
        this.dao = dao;

        // Load data at start
        refreshTable();

        // Add listeners to buttons
        this.view.addCreateListener(new CreateListener());
        this.view.addUpdateListener(new UpdateListener());
        this.view.addDeleteListener(new DeleteListener());
        this.view.addSearchListener(new SearchListener());
        this.view.addWithdrawDepositListener(new WithdrawDepositListener());

        // Add table selection listener to update summary labels and input fields on row select
        this.view.table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && view.table.getSelectedRow() != -1) {
                int row = view.table.getSelectedRow();
                int customerId = Integer.parseInt(view.table.getValueAt(row, 0).toString());

                Customer selectedCustomer = dao.getCustomerById(customerId);
                if (selectedCustomer != null) {
                    // Update summary labels
                    view.setSummaryLabels(
                        String.valueOf(selectedCustomer.getId()),
                        selectedCustomer.getName(),
                        String.valueOf(selectedCustomer.getSaving())
                    );

                    // Update input fields with selected customer data
                    view.fieldId.setText(String.valueOf(selectedCustomer.getId()));
                    view.fieldName.setText(selectedCustomer.getName());
                    view.fieldPhone.setText(selectedCustomer.getPhoneNumber());
                    view.fieldAge.setText(String.valueOf(selectedCustomer.getAge()));
                    view.fieldUsername.setText(selectedCustomer.getUserName());
                    view.fieldSaving.setText(String.valueOf(selectedCustomer.getSaving()));
                    view.fieldPassword.setText(selectedCustomer.getPassword());

                    // Clear deposit and withdraw fields
                    view.fieldDeposit.setText("");
                    view.fieldWithdraw.setText("");
                }
            }
        });
    }

    private void refreshTable() {
        List<Customer> customers = dao.getAllCustomers();
        view.setTableData(customers);
    }

    // Create Listener: always create new customer, ignore ID field
    class CreateListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Customer c = new Customer();
                c.setName(view.fieldName.getText());
                c.setPhoneNumber(view.fieldPhone.getText());
                c.setAge(Integer.parseInt(view.fieldAge.getText()));
                c.setSaving(Double.parseDouble(view.fieldSaving.getText()));
                c.setUserName(view.fieldUsername.getText());
                c.setPassword(view.fieldPassword.getText());

                dao.insertCustomer(c);
                refreshTable();
                JOptionPane.showMessageDialog(view, "Customer created successfully.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Error creating customer: " + ex.getMessage());
            }
        }
    }

    // Update Listener: update only if ID is valid and exists
    class UpdateListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String idText = view.fieldId.getText();
                if (idText == null || idText.isEmpty()) {
                    JOptionPane.showMessageDialog(view, "Please select a customer to update.");
                    return;
                }

                int id = Integer.parseInt(idText);
                Customer existing = dao.getCustomerById(id);
                if (existing == null) {
                    JOptionPane.showMessageDialog(view, "Customer with ID " + id + " not found.");
                    return;
                }

                Customer c = new Customer();
                c.setId(id);
                c.setName(view.fieldName.getText());
                c.setPhoneNumber(view.fieldPhone.getText());
                c.setAge(Integer.parseInt(view.fieldAge.getText()));
                c.setSaving(Double.parseDouble(view.fieldSaving.getText()));
                c.setUserName(view.fieldUsername.getText());
                c.setPassword(view.fieldPassword.getText());

                dao.updateCustomer(c);
                refreshTable();
                JOptionPane.showMessageDialog(view, "Customer updated successfully.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Error updating customer: " + ex.getMessage());
            }
        }
    }

    // Delete Listener
    class DeleteListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String idText = view.fieldId.getText();
                if (idText == null || idText.isEmpty()) {
                    JOptionPane.showMessageDialog(view, "Please select a customer to delete.");
                    return;
                }

                int id = Integer.parseInt(idText);
                Customer existing = dao.getCustomerById(id);
                if (existing == null) {
                    JOptionPane.showMessageDialog(view, "Customer with ID " + id + " not found.");
                    return;
                }

                dao.deleteCustomer(id);
                refreshTable();
                JOptionPane.showMessageDialog(view, "Customer deleted successfully.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Error deleting customer: " + ex.getMessage());
            }
        }
    }

    // Search Listener
    class SearchListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String keyword = view.searchField.getText();
            List<Customer> results = dao.searchCustomers(keyword);
            view.setTableData(results);
        }
    }

    // Withdraw / Deposit Listener
    class WithdrawDepositListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String idText = view.fieldId.getText();
                if (idText == null || idText.isEmpty()) {
                    JOptionPane.showMessageDialog(view, "Please select a customer for transaction.");
                    return;
                }

                int id = Integer.parseInt(idText);
                Customer customer = dao.getCustomerById(id);
                if (customer == null) {
                    JOptionPane.showMessageDialog(view, "Customer not found.");
                    return;
                }

                double withdrawAmount = view.getWithdrawAmount();
                double depositAmount = view.getDepositAmount();

                double currentSaving = customer.getSaving();

                if (withdrawAmount > 0) {
                    if (withdrawAmount > currentSaving) {
                        JOptionPane.showMessageDialog(view, "Insufficient saving to withdraw.");
                        return;
                    }
                    customer.setSaving(currentSaving - withdrawAmount);
                } else if (depositAmount > 0) {
                    customer.setSaving(currentSaving + depositAmount);
                } else {
                    JOptionPane.showMessageDialog(view, "Please enter an amount to withdraw or deposit.");
                    return;
                }

                dao.updateCustomer(customer);
                refreshTable();
                JOptionPane.showMessageDialog(view, "Transaction successful.");
                // Clear deposit and withdraw fields after operation
                view.fieldDeposit.setText("");
                view.fieldWithdraw.setText("");
                view.fieldSaving.setText(String.valueOf(customer.getSaving()));

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Transaction error: " + ex.getMessage());
            }
        }
    }
}
