package models.db;

import models.TransactionType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionTypeDAO {

    private static TransactionTypeDAO INSTANCE = new TransactionTypeDAO();

    private TransactionTypeDAO() {
    }

    public synchronized static TransactionTypeDAO getInstance() {
        return INSTANCE;
    }

    public TransactionType.Type getTransactionTypeById(int id) throws SQLException {
        Connection connection = DBManager.getInstance().getConnection();
        String sql = "SELECT name FROM transaction_types WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        TransactionType.Type type = TransactionType.Type.valueOf(resultSet.getString("name"));
        return type;
    }
}
