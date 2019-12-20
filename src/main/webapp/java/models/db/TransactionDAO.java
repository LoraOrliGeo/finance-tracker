package models.db;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import models.Category;
import models.Transaction;
import models.TransactionType;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TransactionDAO {

    private static TransactionDAO INSTANCE = new TransactionDAO();

    private TransactionDAO() {
    }

    public synchronized static TransactionDAO getInstance() {
        return INSTANCE;
    }

    public void addTransaction(Transaction transaction) throws SQLException {
        Connection connection = DBManager.getInstance().getConnection();
        String sql = "INSERT INTO transactions (type_id, category_id, amount, date) " +
                "VALUES (?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, transaction.getType().getId());
        statement.setInt(2, transaction.getCategory().getType().getId());
        statement.setDouble(3, transaction.getAmount());
        statement.setDate(4, Date.valueOf(transaction.getDate().toLocalDate()));
        statement.executeUpdate();
        ResultSet keys = statement.getGeneratedKeys();
        keys.next();
        int id = keys.getInt(1);
        transaction.setId(id);
        keys.close();
        statement.close();
    }

    public void editTransaction(int id, TransactionType type, int categoryId, double amount) throws SQLException {
        Connection connection = DBManager.getInstance().getConnection();
        String sql = "UPDATE transactions SET type_id = ?, category_id = ?, amount = ?, date = ? WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, type.getId());
        statement.setInt(2, categoryId);
        statement.setDouble(3, amount);
        statement.setDate(4, Date.valueOf(LocalDateTime.now().toLocalDate()));
        statement.setInt(5, id);
        statement.executeUpdate();
        statement.close();
    }

    public void deleteTransaction(Transaction transaction) throws SQLException {
        Connection connection = DBManager.getInstance().getConnection();
        String sql = "DELETE FROM transactions WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, transaction.getId());
        statement.close();
    }

    public List<Transaction> getAllTransactions() throws SQLException {
        List<Transaction> transactions = new ArrayList<>();

        Connection connection = DBManager.getInstance().getConnection();
        connection.setAutoCommit(false);
        String sql = "SELECT id FROM transactions";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                transactions.add(getTransactionById(set.getInt("id")));
            }
            connection.commit();
            set.close();
            return Collections.unmodifiableList(transactions);
        } catch (Exception e) {
            connection.rollback();
            connection.setAutoCommit(true);
            throw new SQLException("Transactions cannot be seen!");
        }
    }

    public List<Transaction> getTransactionsByType(TransactionType type) throws SQLException {
        List<Transaction> transactions = new ArrayList<>();
        Connection connection = DBManager.getInstance().getConnection();
        connection.setAutoCommit(false);
        String sql = "SELECT id FROM transactions WHERE type_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, type.getId());
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                transactions.add(getTransactionById(set.getInt("id")));
            }
            connection.commit();
            set.close();
            return Collections.unmodifiableList(transactions);
        } catch (Exception e) {
            connection.setAutoCommit(true);
            connection.rollback();
            throw new SQLException("Get transactions by type cannot be proceed!");
        }
    }

    public List<Transaction> getTransactionsByCategory(int categoryId) throws SQLException {
        List<Transaction> transactions = new ArrayList<>();
        Connection connection = DBManager.getInstance().getConnection();
        connection.setAutoCommit(false);
        String sql = "SELECT id FROM transactions WHERE category_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, categoryId);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                transactions.add(getTransactionById(set.getInt("id")));
            }
            connection.commit();
            set.close();
            return Collections.unmodifiableList(transactions);
        } catch (Exception e) {
            connection.rollback();
            connection.setAutoCommit(true);
            throw new SQLException("Get transactions by category cannot be proceed!");
        }
    }

    public List<Transaction> getTransactionsByAccount(int accountId) throws SQLException {
        // tried to add column "account_id" - fk to accounts table ("id") but it wasn't successful
        List<Transaction> transactions = new ArrayList<>();
        Connection connection = DBManager.getInstance().getConnection();
        connection.setAutoCommit(false);
        String sql = "SELECT id FROM transactions WHERE account_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, accountId);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                transactions.add(getTransactionById(set.getInt("id")));
            }
            set.close();
            connection.commit();
            return Collections.unmodifiableList(transactions);
        } catch (Exception e) {
            connection.rollback();
            connection.setAutoCommit(true);
            throw new SQLException("Get transaction by account cannot be proceed!");
        }
    }

    public List<Transaction> getTransactionsFromDate(LocalDate date) throws SQLException {
        List<Transaction> transactions = new ArrayList<>();
        Connection connection = DBManager.getInstance().getConnection();
        connection.setAutoCommit(false);
        String sql = "SELECT id FROM transactions WHERE date = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDate(1, Date.valueOf(date));
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                transactions.add(getTransactionById(set.getInt("id")));
            }
            return Collections.unmodifiableList(transactions);
        } catch (Exception e) {
            connection.rollback();
            connection.setAutoCommit(true);
            throw new SQLException("Get transactions by date cannot be proceed!");
        }
    }

    public Transaction getTransactionById(int id) throws SQLException {
        Connection connection = DBManager.getInstance().getConnection();
        Transaction transaction = null;
        String sql = "SELECT type_id, category_id, amount, date FROM transactions WHERE id = ?";

        connection.setAutoCommit(false);
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                TransactionType.Type type = TransactionTypeDAO.getInstance()
                        .getTransactionTypeById(set.getInt("type_id"));
                TransactionType transactionType = new TransactionType(type);
                Category category = CategoryDAO.getInstance().getCategoryById(set.getInt("category_id"));
                transaction = new Transaction(transactionType, category, set.getDouble("amount"));
                transaction.setDate(set.getTimestamp("date").toLocalDateTime());
            }
            connection.commit();
            return transaction;
        } catch (Exception e) {
            connection.rollback();
            connection.setAutoCommit(true);
            throw new SQLException("Get transaction by id cannot be proceed!");
        }
    }

    public void exportTransactionToPDF(Transaction transaction) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("transaction.pdf"));
            document.open();
            Font titleFont = FontFactory.getFont(FontFactory.TIMES_BOLDITALIC, 20, BaseColor.BLACK);
            document.add(new Paragraph("Transaction Information" + System.lineSeparator(), titleFont));
            Font contentFont = FontFactory.getFont(FontFactory.TIMES_ITALIC, 15, BaseColor.LIGHT_GRAY);
            document.add(new Chunk(
                    "Type: " + transaction.getType().getName() + System.lineSeparator(),
                    contentFont)); // income or expense
            document.add(new Chunk(
                    "Category: " + transaction.getCategory().getName() + System.lineSeparator(),
                    contentFont));
            document.add(new Chunk("Amount: " + transaction.getAmount() + System.lineSeparator(),
                    contentFont));
            document.add(new Chunk("Date: " + transaction.getDate() + System.lineSeparator(),
                    contentFont));
            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
