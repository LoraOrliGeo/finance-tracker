package models;

import java.time.LocalDateTime;

public class Account {
    private int id;
    private User user;
    private LocalDateTime createdOn;
    private double balance;
    private Currency currency;

    public Account(User user, double balance, Currency currency) {
        this.user = user;
        this.balance = balance;
        this.currency = currency;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public double getBalance() {
        return balance;
    }

    public Currency getCurrency() {
        return currency;
    }
}
