package service;

import db.DatabaseManager;
import model.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;
import java.sql.Date;

public class InventoryManager {
    private Map<String, PriorityQueue<Batch>> inventory = new HashMap<>();
    private Queue<MedicineRequest> requestQueue = new LinkedList<>();
    private UsageAnalyzer usageAnalyzer;
    private Map<String, Medicine> medicineDirectory = new HashMap<>();
    public InventoryManager(UsageAnalyzer usageAnalyzer) {
        this.usageAnalyzer = usageAnalyzer;
        loadInventoryFromDB();
        loadRequestsFromDB();
    }
    private void loadInventoryFromDB() {
        String query = "SELECT m.id, m.name, m.category, b.quantity, b.expiry_date " +
                "FROM Batch b JOIN Medicine m ON m.id = b.medicine_id " +
                "ORDER BY b.expiry_date ASC";

        try (Connection conn = DatabaseManager.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                String category = rs.getString("category");
                int qty = rs.getInt("quantity");
                LocalDate expiry = rs.getDate("expiry_date").toLocalDate();

                Medicine med = new Medicine(id, name, category);
                Batch batch = new Batch(med, qty, expiry);

                inventory.putIfAbsent(id, new PriorityQueue<>());
                inventory.get(id).add(batch);
                medicineDirectory.putIfAbsent(id, med);
            }
        } catch (SQLException e) {
            System.out.println("❌ Error loading inventory: " + e.getMessage());
        }
    }

    private void loadRequestsFromDB() {
        try (Connection conn = DatabaseManager.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM MedicineRequest")) {

            while (rs.next()) {
                String medId = rs.getString("medicine_id");
                int qty = rs.getInt("quantity");
                String doc = rs.getString("requested_by");
                LocalDate date = rs.getDate("request_date").toLocalDate();

                requestQueue.offer(new MedicineRequest(medId, qty, doc, date));
            }
        } catch (SQLException e) {
            System.out.println("❌ Error loading requests: " + e.getMessage());
        }
    }

    public void addMedicineBatch(Medicine med, int qty, LocalDate expiry) {
        try (Connection conn = DatabaseManager.connect()) {
            PreparedStatement medStmt = conn.prepareStatement(
                    "INSERT INTO Medicine (id, name, category) VALUES (?, ?, ?) " +
                            "ON DUPLICATE KEY UPDATE name = VALUES(name), category = VALUES(category)");
            medStmt.setString(1, med.id);
            medStmt.setString(2, med.name);
            medStmt.setString(3, med.category);
            medStmt.executeUpdate();

            PreparedStatement batchStmt = conn.prepareStatement(
                    "INSERT INTO Batch (medicine_id, quantity, expiry_date) VALUES (?, ?, ?)");
            batchStmt.setString(1, med.id);
            batchStmt.setInt(2, qty);
            batchStmt.setDate(3, Date.valueOf(expiry));
            batchStmt.executeUpdate();

            Batch newBatch = new Batch(med, qty, expiry);
            inventory.putIfAbsent(med.id, new PriorityQueue<>());
            inventory.get(med.id).add(newBatch);
            medicineDirectory.putIfAbsent(med.id, med);

            System.out.println("✅ Batch added successfully");
        } catch (SQLException e) {
            System.out.println("❌ Error adding batch: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public boolean dispenseMedicine(String medId, int qty) {

        if (!inventory.containsKey(medId)) {
            System.out.println("Medicine not found.");
            return false;
        }

        PriorityQueue<Batch> queue = inventory.get(medId);
        int remaining = qty;
        boolean dispensed = false;

        try (Connection conn = DatabaseManager.connect()) {
            while (!queue.isEmpty() && remaining > 0) {
                Batch top = queue.peek();
                if (top.quantity <= remaining) {
                    remaining -= top.quantity;
                    queue.poll();
                    deleteBatchFromDB(conn, medId, top.expiryDate);
                } else {
                    top.quantity -= remaining;
                    updateBatchInDB(conn, medId, top);
                    remaining = 0;
                }
                dispensed = true;
            }
        } catch (SQLException e) {
            System.out.println("❌ Dispense error: " + e.getMessage());
        }

        if (remaining == 0) {
            System.out.println("Dispensed " + qty + " units.");
            return true;
        } else {
            System.out.println("Only partial stock dispensed. Remaining: " + remaining);
            return false;
        }
    }

    private void deleteBatchFromDB(Connection conn, String medId, LocalDate expiry) throws SQLException {
        PreparedStatement delStmt = conn.prepareStatement(
                "DELETE FROM Batch WHERE medicine_id = ? AND expiry_date = ?");
        delStmt.setString(1, medId);
        delStmt.setDate(2, Date.valueOf(expiry));
        delStmt.executeUpdate();
    }

    private void updateBatchInDB(Connection conn, String medId, Batch batch) throws SQLException {
        PreparedStatement updStmt = conn.prepareStatement(
                "UPDATE Batch SET quantity = ? WHERE medicine_id = ? AND expiry_date = ?");
        updStmt.setInt(1, batch.quantity);
        updStmt.setString(2, medId);
        updStmt.setDate(3, Date.valueOf(batch.expiryDate));
        updStmt.executeUpdate();
    }

    public void addRequest(MedicineRequest request) {
        requestQueue.offer(request);
        try (Connection conn = DatabaseManager.connect()) {
            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO MedicineRequest (medicine_id, quantity, requested_by, request_date) " +
                            "VALUES (?, ?, ?, ?)");
            stmt.setString(1, request.getMedicineId());
            stmt.setInt(2, request.getQuantity());
            stmt.setString(3, request.getDoctorName());
            stmt.setDate(4, Date.valueOf(request.getRequestDate()));
            stmt.executeUpdate();

            System.out.println("✅ Request saved to DB.");
        } catch (SQLException e) {
            System.out.println("❌ Request error: " + e.getMessage());
        }
    }

    public void showInventory() {
        if (inventory.isEmpty()) {
            System.out.println("Inventory is empty.");
            return;
        }
        for (String medId : inventory.keySet()) {
            PriorityQueue<Batch> queue = inventory.get(medId);
            if (!queue.isEmpty()) {
                Medicine med = medicineDirectory.get(medId);
                if (med == null) {
                    med = queue.peek().getMedicine();
                }
                System.out.println("\n" + med.name + " (" + med.id + ") - Category: " + med.category);
                for (Batch b : queue) {
                    System.out.println("   Qty: " + b.quantity + " | Expiry: " + b.expiryDate);
                }
            }
        }
    }

    public void viewRequests() {
        if (requestQueue.isEmpty()) {
            System.out.println("No pending medicine requests.");
            return;
        }
        System.out.println("\nPending Requests:");
        for (MedicineRequest r : requestQueue) {
            System.out.println("• " + r.getMedicineId() + " | Qty: " + r.getQuantity() +
                    " | Doctor: " + r.getDoctorName() + " | Date: " + r.getRequestDate());
        }
    }

    public void approveAndDispense() {
        if (requestQueue.isEmpty()) {
            System.out.println("No pending requests to process.");
            return;
        }
        try (Connection conn = DatabaseManager.connect()) {
            while (!requestQueue.isEmpty()) {
                MedicineRequest r = requestQueue.poll();
                String medId = r.getMedicineId();
                int qty = r.getQuantity();
                System.out.println("Processing request for: " + medId + " | Qty: " + qty);

                if (dispenseMedicine(medId, qty)) {
                    deleteRequestFromDB(conn, r);
                    System.out.println("✅ Request approved and dispensed.");
                } else {
                    System.out.println("❌ Could not fulfill request. Not enough stock.");
                }
            }
        } catch (Exception e) {
            System.out.println("❌ Error during request processing: " + e.getMessage());
        }
    }

    private void deleteRequestFromDB(Connection conn, MedicineRequest r) throws SQLException {
        PreparedStatement del = conn.prepareStatement(
                "DELETE FROM MedicineRequest WHERE medicine_id = ? AND quantity = ? " +
                        "AND requested_by = ? AND request_date = ?");
        del.setString(1, r.getMedicineId());
        del.setInt(2, r.getQuantity());
        del.setString(3, r.getDoctorName());
        del.setDate(4, java.sql.Date.valueOf(r.getRequestDate()));
        del.executeUpdate();
    }

    public String getInventoryReportContent() {
        StringBuilder sb = new StringBuilder();
        sb.append("INVENTORY REPORT - ").append(LocalDate.now()).append("\n");
        sb.append("--------------------------------------------\n");

        for (String medId : inventory.keySet()) {
            PriorityQueue<Batch> queue = inventory.get(medId);
            if (!queue.isEmpty()) {
                Medicine med = medicineDirectory.get(medId);
                sb.append("Medicine: ").append(med.name).append(" (").append(medId).append(")\n");
                sb.append("Category: ").append(med.category).append("\n");
                for (Batch b : queue) {
                    sb.append("   • Qty: ").append(b.quantity)
                            .append(" | Expiry: ").append(b.expiryDate).append("\n");
                }
                sb.append("--------------------------------------------\n");
            }
        }
        return sb.toString();
    }

    public void checkForExpiredBatches() {
        LocalDate today = LocalDate.now();
        boolean found = false;

        for (String medId : inventory.keySet()) {
            PriorityQueue<Batch> queue = inventory.get(medId);
            for (Batch b : queue) {
                if (b.getExpiryDate().isBefore(today)) {
                    if (!found) {
                        System.out.println("\n⚠️ Expired Batches Found:");
                        found = true;
                    }
                    System.out.println("• " + b.getMedicine().name + " (" + medId + ") expired on " +
                            b.getExpiryDate() + " | Qty left: " + b.quantity);
                }
            }
        }

        if (!found) {
            System.out.println("✅ No expired medicine batches detected.");
        }
    }
}