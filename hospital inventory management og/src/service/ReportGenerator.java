package service;

import db.DatabaseManager;
import java.sql.*;
import java.time.LocalDate;

public class ReportGenerator {

    public String generateAndSave(String content) {
        LocalDate today = LocalDate.now();

        try (Connection conn = DatabaseManager.connect()) {
            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO Report (generated_date, content) VALUES (?, ?)"
            );
            stmt.setDate(1, Date.valueOf(today));
            stmt.setString(2, content);
            stmt.executeUpdate();

            System.out.println("✅ Report saved to database for " + today);
        } catch (SQLException e) {
            System.out.println("❌ Error saving report: " + e.getMessage());
        }

        return content;
    }

    public void printToConsole(String content) {
        System.out.println("\n📊 Inventory Report Preview:");
        System.out.println("--------------------------------------------------");
        System.out.println(content);
        System.out.println("--------------------------------------------------");
    }

    public void showReportHistory() {
        String query = "SELECT report_id, generated_date FROM Report ORDER BY generated_date DESC";

        try (Connection conn = DatabaseManager.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            System.out.println("\n🗂 Past Reports:");
            while (rs.next()) {
                int id = rs.getInt("report_id");
                LocalDate date = rs.getDate("generated_date").toLocalDate();
                System.out.println(" • Report ID: " + id + " | Date: " + date);
            }
        } catch (SQLException e) {
            System.out.println("❌ Error fetching report history: " + e.getMessage());
        }
    }

    public void showReportById(int id) {
        String query = "SELECT * FROM Report WHERE report_id = ?";

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                LocalDate date = rs.getDate("generated_date").toLocalDate();
                String content = rs.getString("content");

                System.out.println("\n📋 Report ID: " + id + " | Date: " + date);
                System.out.println("--------------------------------------------------");
                System.out.println(content);
                System.out.println("--------------------------------------------------");
            } else {
                System.out.println("❌ No report found for ID " + id);
            }
        } catch (SQLException e) {
            System.out.println("❌ Error loading report: " + e.getMessage());
        }
    }
}