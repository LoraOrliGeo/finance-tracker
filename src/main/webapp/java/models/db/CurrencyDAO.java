package models.db;

import models.Currency;

import java.sql.*;

public class CurrencyDAO {

    private static CurrencyDAO mInstance;

    private CurrencyDAO() {
    }

    public synchronized static CurrencyDAO getInstance() {
        if (mInstance == null) {
            mInstance = new CurrencyDAO();
        }
        return mInstance;
    }

    public void addCurrency(Currency currency) throws SQLException {
        Connection connection = DBManager.getInstance().getConnection();
        String sql = "INSERT INTO currencies (name) VALUES (?);";
        PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, currency.getName().toString());
        statement.executeUpdate();
        ResultSet keys = statement.getGeneratedKeys();
        keys.next();
        int id = keys.getInt(1);
        currency.setId(id);
        keys.close();
        statement.close();
    }

    public Currency getCurrencyById(int currency_id) throws SQLException {
        Connection connection = DBManager.getInstance().getConnection();
        String sql = "SELECT id, name FROM currencies WHERE id = " + currency_id;
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet set = statement.executeQuery();
        Currency currency = null;
        while(set.next()){
            currency = new Currency(Currency.CurrencyName.valueOf(set.getString("name")));
            currency.setId(set.getInt("id"));
        }
        set.close();
        statement.close();
        return currency;
    }
}