package service;

import db.DatabaseManager;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;
import java.sql.Date;
public class UsageAnalyzer {

    public void log(String medId, int qty) {
        String query = "INSERT INTO UsageLog (medicine_id, usage_date, quantity) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, medId);
            stmt.setDate(2, Date.valueOf(LocalDate.now()));
            stmt.setInt(3, qty);
            stmt.executeUpdate();

            System.out.println("📊 Usage logged for " + medId + ": " + qty + " units");
        } catch (SQLException e) {
            System.out.println("❌ Usage log error: " + e.getMessage());
        }
    }

    public int predict(String medId) {
        List<Integer> history = getUsageHistory(medId, 7);
        if (history.isEmpty()) {
            return 0;
        }

        return history.stream()
                .mapToInt(Integer::intValue)
                .sum() / history.size();
    }

    public List<Integer> getUsageHistory(String medId, int days) {
        List<Integer> usageList = new ArrayList<>();
        LocalDate today = LocalDate.now();
        LocalDate startDate = today.minusDays(days - 1);
        String query = """
            SELECT usage_date, SUM(quantity) as total 
            FROM UsageLog 
            WHERE medicine_id = ? AND usage_date >= ? 
            GROUP BY usage_date 
            ORDER BY usage_date ASC
            """;

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, medId);
            stmt.setDate(2, Date.valueOf(startDate));

            Map<LocalDate, Integer> dailyUsage = new HashMap<>();
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    LocalDate date = rs.getDate("usage_date").toLocalDate();
                    int totalQty = rs.getInt("total");
                    dailyUsage.put(date, totalQty);
                }
            }

            for (int i = 0; i < days; i++) {
                LocalDate currentDate = startDate.plusDays(i);
                usageList.add(dailyUsage.getOrDefault(currentDate, 0));
            }

        } catch (SQLException e) {
            System.out.println("❌ Usage fetch error: " + e.getMessage());
        }
        return usageList;
    }

    public void printUsageHistory(String medId, int days) {
        List<Integer> data = getUsageHistory(medId, days);
        LocalDate currentDate = LocalDate.now().minusDays(days - 1);

        System.out.println("\n📈 Usage history for " + medId + " (last " + days + " days):");
        System.out.println("----------------------------------");

        for (int qty : data) {
            System.out.printf("%-12s → %4d units%n", currentDate, qty);
            currentDate = currentDate.plusDays(1);
        }
        System.out.println("----------------------------------");
    }
}
