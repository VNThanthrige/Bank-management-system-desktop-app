#  Banking Management System

A desktop application built with **Java Swing (MVC Architecture)** for managing bank operations with different roles: **Admin, Employee, and Customer**.  

---

##  Features

###  User Authentication
- Role-based login for:
  - **Admin**
  - **Employee**
  - **Customer**

###  Admin Dashboard
- Manage Customers (Create, Read, Update, Delete)  
- Manage Employees (Create, Read, Update, Delete)  
- Approve / Reject Loan Requests  

###  Employee Dashboard
- Create loan requests on behalf of customers  
- View and update customer details  

###  Customer Dashboard
- View savings balance  
- Check loan status (approved / rejected / pending)  

###  Loan Management
- Each customer can have **only one loan**  
- Loan amount must be **≤ 3× savings balance**  

---

##  Tech Stack
- **Java SE 17+**  
- **Swing** (for UI)  
- **MVC Architecture**  
- **MySQL** (for database)  
- **JDBC** (for database connectivity)  

---

## 📂 Project Structure
- This project is a simple bank managment system made according to the MVC Architecture
-  /model      → Business logic & DB handling
   /view       → Swing UI components
   /controller → Handles interaction between model & view
   /database     → SQL scripts for creating tables

   

