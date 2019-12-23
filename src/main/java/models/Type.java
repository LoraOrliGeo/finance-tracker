package models;

public enum Type {

    EXPENSE(2), INCOME(1);

    private int id;

    Type(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
