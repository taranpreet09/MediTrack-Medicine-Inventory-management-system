package model;

import java.time.LocalDate;

public class Batch implements Comparable<Batch> {
    public Medicine medicine;
    public int quantity;
    public LocalDate expiryDate;

    public Batch(Medicine medicine, int quantity, LocalDate expiryDate) {
        this.medicine = medicine;
        this.quantity = quantity;
        this.expiryDate = expiryDate;
    }

    public Medicine getMedicine() {
        return medicine;
    }

    public int getQuantity() {
        return quantity;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    @Override
    public int compareTo(Batch other) {
        return this.expiryDate.compareTo(other.expiryDate); // FEFO logic
    }

    @Override
    public String toString() {
        return medicine.name + " - Qty: " + quantity + ", Expires: " + expiryDate;
    }
}
