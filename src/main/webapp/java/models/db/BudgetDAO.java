package models.db;

import models.Account;
import models.Budget;
import models.Category;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, budget.getTitle()); // listen to Krasi
        statement.setDate(2, Date.valueOf(budget.getFromDate()));
        statement.setDate(3, Date.valueOf(budget.getToDate()));
        statement.setInt(4, budget.getCategory().getId());
        statement.setDouble(5, budget.getAmount());
        statement.setInt(6, budget.getAccountId());
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
        while (set.next()) {
            Category category = CategoryDAO.getInstance().getCategoryById(set.getInt("category_id"));
            Account account = AccountDAO.getInstance().getAccountById(set.getInt("account_id"));
            budget = new Budget(set.getString("title"), set.getDate("from_date").toLocalDate(),
                    set.getDate("to_date").toLocalDate(), category, set.getDouble("amount"), account.getId());
            budget.setId(set.getInt("id"));
        }
        set.close();
        statement.close();
        return budget;
    }

    public void editBudget(int id, String title, double amount, int accountId, int categoryId) throws SQLException {
        Connection connection = DBManager.getInstance().getConnection();
        String sql = "UPDATE budgets SET title = ?, amount = ?, account_id = ?, category_id = ? WHERE id = " + id;
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, title);
        statement.setDouble(2, amount);
        statement.setInt(3, accountId);
        statement.setInt(4, categoryId);
        statement.executeUpdate();
        statement.close();
    }

    public List<Budget> getAllBudgets() throws SQLException {
        Connection connection = DBManager.getInstance().getConnection();
        connection.setAutoCommit(false);
        List<Budget> budgets = new ArrayList<>();
        String sql = "SELECT id FROM budgets;";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet set = statement.executeQuery()) {
            while (set.next()) {
                budgets.add(getBudgetById(set.getInt("id")));
            }
            connection.commit();
            return Collections.unmodifiableList(budgets);
        } catch (Exception e) {
            connection.rollback();
            throw new SQLException("Accounts cannot be seen!");
        }
    }

    public List<Budget> getBudgetsByCategory(int categoryId) throws SQLException {
        Connection connection = DBManager.getInstance().getConnection();
        connection.setAutoCommit(false);
        List<Budget> budgets = new ArrayList<>();
        String sql = "SELECT id FROM budgets WHERE category_id = " + categoryId;
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet set = statement.executeQuery()) {
            while (set.next()) {
                budgets.add(getBudgetById(set.getInt("id")));
            }
            connection.commit();
            return Collections.unmodifiableList(budgets);
        } catch (Exception e) {
            connection.rollback();
            throw new SQLException("Accounts cannot be seen!");
        }
    }

    public List<Budget> getBudgetsByAccount(int accountId) throws SQLException {
        Connection connection = DBManager.getInstance().getConnection();
        connection.setAutoCommit(false);
        List<Budget> budgets = new ArrayList<>();
        String sql = "SELECT id FROM budgets WHERE account_id = " + accountId;
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet set = statement.executeQuery()) {
            while (set.next()) {
                budgets.add(getBudgetById(set.getInt("id")));
            }
            connection.commit();
            return Collections.unmodifiableList(budgets);
        } catch (Exception e) {
            connection.rollback();
            throw new SQLException("Accounts cannot be seen!");
        }
    }

    public List<Budget> getBudgetsByPeriod(LocalDate fromDate, LocalDate toDate) throws SQLException {
        Connection connection = DBManager.getInstance().getConnection();
        connection.setAutoCommit(false);
        List<Budget> budgets = new ArrayList<>();
        String sql = "SELECT id FROM budgets WHERE from_date = ?, to_date = ?;";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDate(1, Date.valueOf(fromDate));
            statement.setDate(2, Date.valueOf(toDate));
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                budgets.add(getBudgetById(set.getInt("id")));
            }
            connection.commit();
            set.close();
            return Collections.unmodifiableList(budgets);
        } catch (Exception e) {
            connection.rollback();
            throw new SQLException("Accounts cannot be seen!");
        }
    }
}
