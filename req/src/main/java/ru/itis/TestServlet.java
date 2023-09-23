package ru.itis;

import java_oop_db.User;
import java_oop_db.UserRepository;
import java_oop_db.UserRepositoryJdbcImp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.sql.*;
import java.util.List;

@WebServlet("/profile")
public class TestServlet extends HttpServlet {
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "qwikWell12";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/test";

    @Override
    public void init() throws ServletException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/html/profile.html").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter writer = response.getWriter();
        String firstName = request.getParameter("first_name");
        String secondName = request.getParameter("second_name");
        System.out.println(firstName);
        System.out.println(secondName);

//        try {
//            writer.println("<p>First: " + firstName + "</p>");
//            writer.println("<p>Second: " + secondName + "</p>");
//        } finally {
//            writer.close();
//        }


//        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);) {
//            String sql = "insert into driver(first_name, second_name) values ('" + firstName + "', '" + secondName + "');";
//            PreparedStatement statement = connection.prepareStatement(sql);
//            statement.setString(1, firstName);
//            statement.setString(2, secondName);
//
//            int result = statement.executeUpdate();
//
//            if (result > 0) {
//                response.getWriter().println("Пользователь добавлен");
//            } else {
//                response.getWriter().println("Пользователь не добавлен");
//            }
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
    }

}

