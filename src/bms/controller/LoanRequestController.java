package bms.controller;

import bms.model.Customer;
import bms.model.LoanDAO;
import bms.view.Loants;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class LoanRequestController {

    private final Loants view;
    private final CustomerDAO customerDAO;
    private final LoanDAO loanDAO;

    public LoanRequestController(Loants view, CustomerDAO customerDAO, LoanDAO loanDAO) {
        this.view = view;
        this.customerDAO = customerDAO;
        this.loanDAO = loanDAO;

        refreshTable();

        view.addSearchListener(new SearchListener());
        view.addRequestLoanListener(new RequestLoanListener());

        view.getTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && view.getTable().getSelectedRow() != -1) {
                    int row = view.getTable().getSelectedRow();
                    int customerId = Integer.parseInt(view.getTable().getValueAt(row, 0).toString());

                    Customer selectedCustomer = customerDAO.getCustomerById(customerId);
                    if (selectedCustomer != null) {
                        view.setSelectedCustomerId(customerId);
                        view.setSelectedCustomerLabel("Selected Customer: " + selectedCustomer.getName() + " (ID: " + customerId + ")");
                        view.setLoanStatusLabel("Loan Info: No active loan.");

                        if (loanDAO.hasActiveLoan(customerId)) {
                            view.setLoanStatusLabel("Loan Info: Customer has an active loan.");
                        }
                    }
                }
            }
        });
    }

    private void refreshTable() {
        List<Customer> customers = customerDAO.getAllCustomers();
        view.setTableData(customers);
    }

    class SearchListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String keyword = view.searchField.getText().trim();
            List<Customer> customers;
            if (keyword.isEmpty()) {
                customers = customerDAO.getAllCustomers();
            } else {
                customers = customerDAO.searchCustomers(keyword);
            }
            view.setTableData(customers);
        }
    }

    class RequestLoanListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int customerId = view.getSelectedCustomerId();
            if (customerId == -1) {
                JOptionPane.showMessageDialog(view, "Please select a customer first.");
                return;
            }

            Customer customer = customerDAO.getCustomerById(customerId);
            if (customer == null) {
                JOptionPane.showMessageDialog(view, "Selected customer not found.");
                return;
            }

            if (loanDAO.hasActiveLoan(customerId)) {
                JOptionPane.showMessageDialog(view, "Customer already has an active loan.");
                return;
            }

            double amount = view.getRequestedLoanAmount();
            if (amount <= 0) {
                JOptionPane.showMessageDialog(view, "Invalid loan amount.");
                return;
            }

            if (amount > customer.getSaving() * 3) {
                JOptionPane.showMessageDialog(view, "Loan exceeds 3x savings.");
                return;
            }

            int duration = view.getRequestedLoanDuration();
            if (duration <= 0) {
                JOptionPane.showMessageDialog(view, "Invalid loan duration.");
                return;
            }

            String type = view.getRequestedLoanType();
            String reason = view.getRequestedLoanReason();

            boolean success = loanDAO.insertLoanRequest(customerId, type, amount, duration, reason);
            if (success) {
                JOptionPane.showMessageDialog(view, "Loan requested successfully.");
                view.setLoanStatusLabel("Loan Info: Loan requested, pending approval.");
                view.clearLoanInputs();
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(view, "Loan request failed.");
            }
        }
    }

    // Main to launch the loan request UI
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Loants view = new Loants();
            CustomerDAO customerDAO = new CustomerDAO();
            LoanDAO loanDAO = new LoanDAO();
            new LoanRequestController(view, customerDAO, loanDAO);
            view.setVisible(true);
        });
    }
}
