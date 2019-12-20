package models;

public class CategoryType {

    public enum Type {
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

    public void setType(Type type) {
        this.type = type;
    }
}
