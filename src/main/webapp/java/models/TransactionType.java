package models;

public class TransactionType {

    public enum Type {
        INCOME, EXPENSE
    }

    private int id;
    private Type name;

    public TransactionType(Type name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Type getName() {
        return name;
    }

    public void setName(Type name) {
        this.name = name;
    }
}
