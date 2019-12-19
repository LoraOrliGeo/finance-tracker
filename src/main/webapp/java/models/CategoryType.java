package models;

public class CategoryType {

    enum Type {
        INCOME, EXPENSE
    }

    private int id;
    private Type type;

    public CategoryType(Type type) {
        this.type = type;
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
}
