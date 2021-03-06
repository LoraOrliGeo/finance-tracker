package models.db;

import models.Account;
import models.Transfer;

import java.sql.*;

public class TransferDAO {

    private static TransferDAO mInstance;

    private TransferDAO() {
    }

    public synchronized static TransferDAO getInstance() {
        if (mInstance == null) {
            mInstance = new TransferDAO();
        }
        return mInstance;
    }

    public void addTransfer(Transfer transfer) throws SQLException {
        Connection connection = DBManager.getInstance().getConnection();
        String sql = "INSERT INTO transfers (from_account_id, to_account_id, amount) VALUES (?,?,?);";
        PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, transfer.getFromAccountId());
        statement.setInt(2, transfer.getToAccountId());
        statement.setDouble(3, transfer.getAmount());
        statement.executeUpdate();
        ResultSet keys = statement.getGeneratedKeys();
        keys.next();
        int id = keys.getInt(1);
        transfer.setId(id);
        keys.close();
        statement.close();
    }

    public Transfer getTransferById(int id) throws SQLException {
        Connection connection = DBManager.getInstance().getConnection();
        String sql = "SELECT id, from_account_id, to_account_id, amount FROM transfers WHERE id = " + id;
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet set = statement.executeQuery();
        Transfer transfer = null;
        while (set.next()) {
            Account from = AccountDAO.getInstance().getAccountById(set.getInt("from_account_id"));
            Account to = AccountDAO.getInstance().getAccountById(set.getInt("to_account_id"));
            transfer = new Transfer(from.getId(), to.getId(), set.getDouble("amount"));
            transfer.setId(set.getInt("id"));
        }
        set.close();
        statement.close();
        return transfer;
    }
}
