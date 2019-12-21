package models.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class DBManager {

    private static DBManager mInstance;

    private Connection connection;
    private File config = new File("C:\\Users\\Lori\\Desktop\\config.txt");

    private DBManager() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver not loaded! " + e.getMessage());
        }
        connection = createConnection();
    }

    private Connection createConnection() {
        try (Scanner scanner = new Scanner(new FileInputStream(config))) {
            String input = "";
            while (scanner.hasNextLine()) {
                input = scanner.nextLine();
            }
            String[] credentials = input.split(",");
            return DriverManager.getConnection("jdbc:mysql://" +
                    credentials[0] + ":" + credentials[1] + "/" + credentials[2], credentials[3], credentials[4]);
        } catch (SQLException e) {
            System.out.println("Connection failed! " + e.getMessage());
            return null;
        } catch (FileNotFoundException e) {
            System.out.println("Error! Opening the file failed! " + e.getMessage());
            return null;
        }
    }

    public Connection getConnection() throws SQLException {
        if (connection.isClosed()) {
            connection = createConnection();
        }
        return connection;
    }

    public synchronized static DBManager getInstance() {
        if (mInstance == null) {
            mInstance = new DBManager();
        }
        return mInstance;
    }
}
