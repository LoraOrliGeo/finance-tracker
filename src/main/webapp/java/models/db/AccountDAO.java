package models.db;

import models.Account;
import models.Currency;
import models.User;

import java.sql.*;
import java.time.LocalDateTime;

public class AccountDAO {

    private static AccountDAO INSTANCE = new AccountDAO();

    private AccountDAO() {
    }

    public synchronized static AccountDAO getInstance() {
        return INSTANCE;
    }

    public void addAccount(Account account) throws SQLException {
        Connection connection = DBManager.getInstance().getConnection();
        String sql = "INSERT INTO accounts (owner_id, created_on, balance, currency_id) VALUES (?,?,?,?);";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setLong(1, account.getUser().getId());
        statement.setDate(2, Date.valueOf(account.getCreatedOn().toLocalDate()));
        statement.setDouble(3, account.getBalance());
        statement.setInt(4, account.getCurrency().getId());
        statement.executeUpdate();
        ResultSet keys = statement.getGeneratedKeys();
        keys.next();
        int id = keys.getInt(1);
        account.setId(id);
        keys.close();
        statement.close();
    }

    public Account getAccountById(int id) throws SQLException {
        Connection connection = DBManager.getInstance().getConnection();
        String sql = "SELECT id, owner_id, created_on, balance, currency_id FROM accounts WHERE id = " + id;
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet set = statement.executeQuery();
        Account account = null;
        User user;
        Currency currency;
        while(set.next()){
            user = UserDAO.getInstance().getUserById(set.getLong("owner_id"));
            currency = CurrencyDAO.getInstance().getCurrencyById(set.getInt("currency_id"));
            account = new Account(user, set.getDouble("balance"), currency);
            account.setId(set.getInt("id"));
            account.setCreatedOn(set.getTimestamp("created_on").toLocalDateTime());
        }
        set.close();
        statement.close();
        return account;
    }

    public void editAccount(int id){
        //TODO
            //update query maybe
    }
}
