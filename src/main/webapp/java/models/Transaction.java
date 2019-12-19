package models;

import java.time.LocalDateTime;

public class Transaction {
    private int id;
    private TransactionType type;
    private Category category;
    private double amount;
    private LocalDateTime date;

    public Transaction(TransactionType type, Category category, double amount) {
        this.type = type;
        this.category = category;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TransactionType getType() {
        return type;
    }

    public Category getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
