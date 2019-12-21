package models.db;

import models.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class IconDAO {

    private static IconDAO INSTANCE = new IconDAO();

    private IconDAO() {
    }

    public synchronized static IconDAO getInstance() {
        return INSTANCE;
    }

    public void addIcon(String url, Category category) throws SQLException {
        Connection connection = DBManager.getInstance().getConnection();
        String sql = "INSERT INTO icons (url, category_id) VALUES (?,?);";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, url);
        statement.setInt(2, category.getId());
        statement.executeUpdate();
        statement.close();
    }
}
