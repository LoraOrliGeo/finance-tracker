package models.db;

import models.User;

import java.sql.*;
import java.time.LocalDateTime;

public class UserDAO {

    private static UserDAO INSTANCE = new UserDAO();

    private UserDAO() {
    }

    public static synchronized UserDAO getInstance() {
        return INSTANCE;
    }

    public void addUser(User user) throws SQLException {
        Connection connection = DBManager.getInstance().getConnection();
        String sql = "INSERT INTO users (first_name, last_name, password, email, date_created, last_login)" +
                "VALUES (?,?,?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, user.getFirstName());
        statement.setString(2, user.getLastName());
        statement.setString(3, user.getPassword());
        statement.setString(4, user.getEmail());

        LocalDateTime time = LocalDateTime.now();
        user.setDateCreated(time);
        user.setLastLogin(time);
        statement.setDate(5, Date.valueOf(time.toLocalDate()));
        statement.setDate(6, Date.valueOf(time.toLocalDate()));

        ResultSet generatedKeys = statement.getGeneratedKeys();
        long id = generatedKeys.getLong(1);
        user.setId(id);

        statement.executeUpdate();
        statement.close();
    }

    public void editUser(long id, String[] parameters) throws SQLException {
        Connection connection = DBManager.getInstance().getConnection();
        String sql = "UPDATE users SET first_name = ?, last_name = ?, password = ? WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, parameters[0]);
        statement.setString(2, parameters[1]);
        statement.setString(3, parameters[2]);
        statement.setLong(4, id);
        statement.executeUpdate();
        statement.close();
    }

    public boolean checkUser(String email, String password) throws SQLException {
        Connection connection = DBManager.getInstance().getConnection();
        String sql = "SELECT * FROM users WHERE email = ?, password = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, email);
        statement.setString(2, password);
        if (statement.executeUpdate() == 0) {
            statement.close();
            return false;
        } else {
            statement.close();
            return true;
        }
    }

    public User getUserById(long id) throws SQLException {
        User user = new User();
        Connection connection = DBManager.getInstance().getConnection();
        String sql = "SELECT id, first_name, last_name, password, email, date_created, last_login " +
                "FROM users WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setLong(1, id);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            user.setId(resultSet.getInt("id"));
            user.setFirstName(resultSet.getString("first_name"));
            user.setLastName(resultSet.getString("last_name"));
            user.setPassword(resultSet.getString("password"));
            user.setEmail(resultSet.getString("email"));
            user.setDateCreated(resultSet.getTimestamp("date_created").toLocalDateTime());
            user.setLastLogin(resultSet.getTimestamp("last_login").toLocalDateTime());
        }

        statement.close();
        return user;
    }

}
