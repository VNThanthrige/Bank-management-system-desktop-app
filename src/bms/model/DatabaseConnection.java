package bms.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/bank_db?useSSL=false&serverTimezone=UTC"; // your DB URL
    private static final String USER = "root";      // your DB user
    private static final String PASSWORD = "Mnnv(@)1234122506";  // your DB password

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
