package models;

import java.time.LocalDateTime;

public class Transaction {

    private int id;
    private Type type;
    private Category category;
    private double amount;
    private LocalDateTime date;
    private int accountId;

    public Transaction(Type type, Category category, double amount, int accountId) {
        this.type = type;
        this.category = category;
        this.amount = amount;
        this.date = LocalDateTime.now();
        this.accountId = accountId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }
}
