package models;

public class TransactionType {

    enum Type {
        INCOME, EXPENSE
    }

    private int id;
    private Type type;
}
