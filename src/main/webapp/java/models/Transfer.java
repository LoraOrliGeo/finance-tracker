package models;

public class Transfer {

    private int id;
    private Account fromAccount;
    private Account toAccount;
    private double amount;

    public Transfer(Account fromAccount, Account toAccount, double amount) {
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Account getFromAccount() {
        return fromAccount;
    }

    public Account getToAccount() {
        return toAccount;
    }

    public double getAmount() {
        return amount;
    }
}
