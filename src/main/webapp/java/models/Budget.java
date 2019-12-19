package models;

import java.time.LocalDate;

public class Budget {
    private int id;
    private LocalDate fromDate;
    private LocalDate toDate;
    private Category category;
    private double amount;
    private Account account;

    public Budget(LocalDate fromDate, LocalDate toDate, Category category, double amount, Account account) {
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.category = category;
        this.amount = amount;
        this.account = account;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public Category getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }

    public Account getAccount() {
        return account;
    }
}
