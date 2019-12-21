package models.db;

import models.Account;
import models.Currency;
import models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

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
        PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
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

    public void editAccount(int id, double balance) throws SQLException {
        Connection connection = DBManager.getInstance().getConnection();
        String sql = "UPDATE accounts SET balance = ? WHERE id = " + id;
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setDouble(1, balance);
        statement.executeUpdate();
        statement.close();
    }

    public List<Account> getAllAccounts() throws SQLException {
        Connection connection = DBManager.getInstance().getConnection();
        connection.setAutoCommit(false);
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT id FROM accounts;";
        try ( PreparedStatement statement = connection.prepareStatement(sql);
              ResultSet set = statement.executeQuery()) {
            while (set.next()) {
                accounts.add(getAccountById(set.getInt("id")));
            }
            connection.commit();
            return Collections.unmodifiableList(accounts);
        } catch (Exception e){
            connection.rollback();
            throw new SQLException("Accounts cannot be seen!");
        }
    }

    public List<Account> getAccountsByOwner(long ownerId) throws SQLException {
        Connection connection = DBManager.getInstance().getConnection();
        connection.setAutoCommit(false);
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT id FROM accounts WHERE owner_id = " + ownerId;
        try ( PreparedStatement statement = connection.prepareStatement(sql);
              ResultSet set = statement.executeQuery()) {
            while (set.next()) {
                accounts.add(getAccountById(set.getInt("id")));
            }
            connection.commit();
            return Collections.unmodifiableList(accounts);
        } catch (Exception e){
            connection.rollback();
            throw new SQLException("Accounts cannot be seen!");
        }
    }
}
