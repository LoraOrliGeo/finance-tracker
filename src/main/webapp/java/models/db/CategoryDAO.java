package models.db;

import models.Category;
import models.CategoryType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {

    private static CategoryDAO INSTANCE = new CategoryDAO();

    private CategoryDAO() {
    }

    public synchronized static CategoryDAO getInstance() {
        return INSTANCE;
    }

    public void addCategory(Category category) throws SQLException {
        Connection connection = DBManager.getInstance().getConnection();
        String sql = "INSERT INTO catgories (name, icon_url, type_id) VALUES (?,?,?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, category.getName().toString());
        statement.setString(2, category.getIconURL());
        statement.setInt(3, category.getType().getId());
        statement.executeUpdate();
        ResultSet keys = statement.getGeneratedKeys();
        keys.next();
        int id = keys.getInt(1);
        category.setId(id);
        keys.close();
        statement.close();
    }

    public void editCategory(int id, String iconURL, int typeId) throws SQLException {
        // possibility to change icon_url and type
        Connection connection = DBManager.getInstance().getConnection();
        String sql = "UPDATE categories SET icon_url = ?, type_id = ? WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, iconURL);
        statement.setInt(2, typeId);
        statement.setInt(3, id);
        statement.executeUpdate();
        statement.close();
    }

    public Category getCategoryById(int id) throws SQLException {
        Category category = new Category();

        Connection connection = DBManager.getInstance().getConnection();
        String sql = "SELECT name, icon_url, type_id FROM categories WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            category.setName(Category.CategoryName.valueOf(resultSet.getString("name")));
            category.setIconURL(resultSet.getString("icon_url"));
            CategoryType.Type type = CategoryTypeDAO.getInstance().getCategoryTypeById(resultSet.getInt("type_id"));
            CategoryType categoryType = new CategoryType(type);
            category.setType(categoryType);
        }
        resultSet.close();
        statement.close();
        return category;
    }

    public List<String> getIcons(Category.CategoryName name) {
        List<String> iconsForCategory = new ArrayList<>();
        // TODO
        // another table with name of category (groceries, shopping, etc) with its available icon urls ?
        // String sql = "SELECT icon_url FROM another_table WHERE category_name = name"
        // PrepareStatement statement = connection.prepareStatement(sql);
        // ResultSet set = statement.executeQuery();
        // while (set.next()) {
        // iconsForCategory.add(set.getString(icon_url));
        // }
        return iconsForCategory;
    }
}
