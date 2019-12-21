package models;

import models.db.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Test {

    public static void main(String[] args){

        try {
            Transaction transaction = new Transaction(TypeDAO.getInstance().getTypeById(1),
                    CategoryDAO.getInstance().getCategoryById(2), 23.40, 1);
            TransactionDAO.getInstance().addTransaction(transaction);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
