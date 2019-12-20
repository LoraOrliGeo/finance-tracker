package models.db;

import models.Account;
import models.Budget;
import models.Category;

import java.sql.*;

public class BudgetDAO {

    private static BudgetDAO mInstance;

    private BudgetDAO() {
    }

    public synchronized static BudgetDAO getInstance() {
        if (mInstance == null) {
            mInstance = new BudgetDAO();
        }
        return mInstance;
    }

    public void addBudget(Budget budget) throws SQLException {
        Connection connection = DBManager.getInstance().getConnection();
        String sql = "INSERT INTO budgets (title, from_date, to_date, category_id, amount, account_id) VALUES (?,?,?,?,?,?);";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, budget.getTitle()); // listen to Krasi
        statement.setDate(2, Date.valueOf(budget.getFromDate()));
        statement.setDate(3, Date.valueOf(budget.getToDate()));
        statement.setInt(4, budget.getCategory().getId());
        statement.setDouble(5, budget.getAmount());
        statement.setInt(6, budget.getAccount().getId());
        statement.executeUpdate();
        ResultSet keys = statement.getGeneratedKeys();
        keys.next();
        int id = keys.getInt(1);
        budget.setId(id);
        keys.close();
        statement.close();
    }

    public Budget getBudgetById(int id) throws SQLException {
        Connection connection = DBManager.getInstance().getConnection();
        String sql = "SELECT id, title, from_date, to_date, category_id, amount, account_id FROM budgets WHERE id = " + id;
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet set = statement.executeQuery();
        Budget budget = null;
        while(set.next()){
            Category category = CategoryDAO.getInstance().getCategoryById(set.getInt("category_id"));
            Account account = AccountDAO.getInstance().getAccountById(set.getInt("account_id"));
            budget = new Budget(set.getString("title"), set.getDate("from_date").toLocalDate(),
                    set.getDate("to_date").toLocalDate(), category, set.getDouble("amount"), account);
            budget.setId(set.getInt("id"));
        }
        set.close();
        statement.close();
        return budget;
    }
}
