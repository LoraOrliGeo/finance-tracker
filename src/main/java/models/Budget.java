package models;

import java.time.LocalDate;

public class Budget {

    private int id;
    private LocalDate fromDate;
    private LocalDate toDate;
    private Category category;
    private double amount;
    private int accountId;
    private String title;

    public Budget(String title, LocalDate fromDate, LocalDate toDate, Category category, double amount, int accountId) {
        setTitle(title);
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.category = category;
        this.amount = amount;
        this.accountId = accountId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title != null && !title.isEmpty()) {
            this.title = title;
        }
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

    public int getAccountId() {
        return accountId;
    }
}
