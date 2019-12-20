package models.db;

import models.CategoryType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryTypeDAO {

    private static CategoryTypeDAO INSTANCE = new CategoryTypeDAO();

    private CategoryTypeDAO() {
    }

    public synchronized static CategoryTypeDAO getInstance() {
        return INSTANCE;
    }

    public CategoryType.Type getCategoryTypeById(int id) throws SQLException {
        Connection connection = DBManager.getInstance().getConnection();
        String sql = "SELECT name FROM category_types WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        CategoryType.Type type = CategoryType.Type.valueOf(resultSet.getString("name"));
        return type;
    }
}
