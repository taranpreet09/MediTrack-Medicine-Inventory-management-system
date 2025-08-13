package model;

import java.time.LocalDate;

public class MedicineRequest {
    private String medicineId;
    private int quantity;
    private String requestedBy; // Doctor's name or ID
    private LocalDate requestDate;

    public MedicineRequest(String medicineId, int quantity, String requestedBy) {
        this.medicineId = medicineId;
        this.quantity = quantity;
        this.requestedBy = requestedBy;
        this.requestDate = LocalDate.now();
    }

    public MedicineRequest(String medicineId, int quantity, String requestedBy, LocalDate requestDate) {
        this.medicineId = medicineId;
        this.quantity = quantity;
        this.requestedBy = requestedBy;
        this.requestDate = requestDate;
    }

    public String getMedicineId() {
        return medicineId;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getRequestedBy() {
        return requestedBy;
    }

    public LocalDate getRequestDate() {
        return requestDate;
    }

    public String getDoctorName() {
        return requestedBy;
    }

    @Override
    public String toString() {
        return medicineId + " | Qty: " + quantity + " | By: " + requestedBy + " | Date: " + requestDate;
    }
}
