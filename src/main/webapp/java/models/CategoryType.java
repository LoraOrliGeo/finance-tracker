package models;

public class CategoryType {

    public enum Type {
        INCOME, EXPENSE
    }

    private int id;
    private Type name;

    public CategoryType(Type name) {
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
