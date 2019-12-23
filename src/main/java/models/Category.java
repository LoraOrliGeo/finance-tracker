package models;

public class Category {

    public enum CategoryName {
        // general categories
        FOOD_AND_DRINKS, SHOPPING, HOUSING, TRANSPORTATION, VEHICLE, LIFE_AND_ENTERTAINMENT,
        COMMUNICATION_PC, FINANCIAL_EXPENSES, INVESTMENTS, INCOME, OTHERS
    }

    //    private static int uniqueId;
    private int id;
    private CategoryName name;
    private String iconURL;
    private Type type;

    public Category() {
    }

    public Category(CategoryName name, String iconURL, Type type) {
        this.name = name;
        this.iconURL = iconURL;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CategoryName getName() {
        return name;
    }

    public void setName(CategoryName name) {
        this.name = name;
    }

    public String getIconURL() {
        return iconURL;
    }

    public void setIconURL(String iconURL) {
        this.iconURL = iconURL;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}