package ru.db_test;

import ru.itis.UniqueIDGenerator;

import java.sql.*;
import java.util.Scanner;

public class addUser {

    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "qwikWell12";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/test";

    public static void main(String[] args) throws Exception {
        Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        Statement statement = connection.createStatement();
        statement.executeQuery("select * from accounts");

        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите имя: ");
        String firstName = scanner.nextLine();
        System.out.print("Введите фамилию: ");
        String lastName = scanner.nextLine();
        System.out.print("Введите возраст: ");
        int age = scanner.nextInt();
        System.out.print("Введите ID: ");
        int id = scanner.nextInt();

        String sqlInsertUser = "insert into accounts(id, first_name, last_name, age)" +
                " values ('" + id + "', '" + firstName + "', '" + lastName + "', '" + age + "');";

        PreparedStatement preparedStatement = connection.prepareStatement(sqlInsertUser);
        int affectedRows = preparedStatement.executeUpdate();
        System.out.println("Было добавлено " + affectedRows + " строк");

    }
}