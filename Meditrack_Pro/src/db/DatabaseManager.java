package db;

import java.sql.*;

public class DatabaseManager {
    private static final String URL = "jdbc:mysql://localhost:3306/hospital_inventory";
    private static final String USER = "root";
    private static final String PASSWORD = "newpassword";
    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void initialize() {
        try (Connection conn = connect()) {
            System.out.println("✅ Connected to MySQL database.");
        } catch (SQLException e) {
            System.out.println("❌ MySQL connection error: " + e.getMessage());
        }
    }
}
