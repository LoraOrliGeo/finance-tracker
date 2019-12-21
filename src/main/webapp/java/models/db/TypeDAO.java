package models.db;

import models.Type;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TypeDAO {

    private static TypeDAO INSTANCE = new TypeDAO();

    private TypeDAO() {
    }

    public synchronized static TypeDAO getInstance() {
        return INSTANCE;
    }

    public Type getTypeById(int id) throws SQLException {
        Connection connection = DBManager.getInstance().getConnection();
        String sql = "SELECT name FROM types WHERE id = ?;";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet set = statement.executeQuery();
        String type = "";
        while(set.next()){
            type = set.getString("name");
        }
        set.close();
        statement.close();
        return Type.valueOf(type);
    }
}
