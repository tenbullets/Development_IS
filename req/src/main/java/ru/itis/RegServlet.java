package ru.itis;

import servlets.UsersRepository;
import servlets.UsersRepositoryJdbcImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

@WebServlet("/reg")
public class RegServlet extends HttpServlet {
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "qwikWell12";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/test";
    private UsersRepository usersRepository;

    @Override
    public void init() throws ServletException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Statement statement = connection.createStatement();
            usersRepository = new UsersRepositoryJdbcImpl(connection, statement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String secondPassword = request.getParameter("second_password");

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);) {
            Statement statement = connection.createStatement();
//            String res = check(username, password, secondPassword, email, affectedRows);
//            String[] parts = res.split("&");
            String result;
            String status;

            if(usersRepository.findUserByEmail(email)) {
                result = "User " + username + " with this email: " + email + " is already registered";
                status = "Registration Failed";
            } else {
                if (!password.isEmpty() && !username.isEmpty() && password.equals(secondPassword) && !email.isEmpty()) {
                    String sql = "insert into users(username, email, password)" +
                            " values ('" + username + "', '" + email + "', '" + password + "');";
                    statement.executeUpdate(sql);

                    result = "User " + username + " is registered";
                    status = "Successful Registration";
                } else {
                    result = "User is not registered";
                    status = "Failed Registration";
                }
            }

            request.setAttribute("resultOfAut", result);
            request.setAttribute("status", status);
            request.getRequestDispatcher("/jsp/result.jsp").forward(request, response);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

//    public String check(String username, String password, String secondPassword, String email, int affectedRows) {
//        String result;
//        String status;
//
//        if(usersRepository.findUserByEmail(email)) {
////            result = "User " + username + " is already registered";
//            result = "User " + username + " with this email: " + email + " is already registered";
//            status = "Registration Failed";
//        } else {
//            if (affectedRows > 0 &&
//                    !password.isEmpty() &&
//                    !username.isEmpty() &&
//                    password.equals(secondPassword) &&
//                    !email.isEmpty()) {
//                result = "User " + username + " is registered";
//                status = "Successful Registration";
//            } else {
//                result = "User is not registered";
//                status = "Failed Registration";
//            }
//        }
//        return result + "&" + status;
//    }
}