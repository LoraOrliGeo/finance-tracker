package models;

import models.db.*;

import java.sql.SQLException;
import java.time.LocalDate;

public class Demo {
    public static void main(String[] args) {

        try {
//            User user = new User("Lora", "Georgieva", "pass", "lori@gmail.com");
////            // add user
////            UserDAO.getInstance().addUser(user);
////            System.out.println("user added");
////            Currency currency = new Currency(Currency.CurrencyName.USD);
////            // add currency
////            CurrencyDAO.getInstance().addCurrency(currency);
////            System.out.println("currency added");
////            Account account = new Account(user, 1500.67, currency);
////            // add account
////            AccountDAO.getInstance().addAccount(account);
////            System.out.println("account added");
////            Category category = new Category(Category.CategoryName.SHOPPING, "url", Type.EXPENSE);
////            // add category
////            CategoryDAO.getInstance().addCategory(category);
////            System.out.println("category added");
////            // add budget
////            Budget budget = new Budget("rnd title",
////                    LocalDate.of(2019, 12, 1),
////                    LocalDate.of(2019, 12, 31),
////                    category, 200, account.getId());
////            BudgetDAO.getInstance().addBudget(budget);
////            System.out.println("budget added");

            // transaction
            Type type = TypeDAO.getInstance().getTypeById(1);
            Transaction transaction = new Transaction(type,
                    CategoryDAO.getInstance().getCategoryById(1),
                    200,
                    AccountDAO.getInstance().getAccountById(1).getId());
            TransactionDAO.getInstance().addTransaction(transaction);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
