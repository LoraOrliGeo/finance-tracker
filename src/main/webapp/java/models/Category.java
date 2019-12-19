package models;

public class Category {

    enum CategoryName {
        // general categories
        FOOD_AND_DRINKS, SHOPPING, HOUSING, TRANSPORTATION, VEHICLE, LIFE_AND_ENTERTAINMENT,
        COMMUNICATION_PC, FINANCIAL_EXPENSES, INVESTMENTS, INCOME, OTHERS
    }

    //    private static int uniqueId;
    private int id;
    private CategoryName name;
    private String iconURL;
    private CategoryType type;

    public Category(CategoryName name, String iconURL, CategoryType type) {
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

    public String getIconURL() {
        return iconURL;
    }

    public CategoryType getType() {
        return type;
    }
}