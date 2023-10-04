package ru.itis;

import servlets.UsersRepository;
import servlets.UsersRepositoryJdbcImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.UUID;

@WebServlet("/reg")
public class RegServlet extends HttpServlet {
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "qwikWell12";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/test";
    private static final java.util.UUID UUID = null;
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
            String result;
            String status;

            if (usersRepository.findUserByEmail(email)) {
                result = "User with this email: " + email + " is already registered";
                status = "Registration Failed";
            } else {
                if (!password.isEmpty() && !username.isEmpty() && password.equals(secondPassword) && !email.isEmpty()) {
                    String uniqueID = UUID.randomUUID().toString();
//                    System.out.println(uniqueID);

                    String sql1 = "insert into users(username, email, password)" +
                            " values ('" + username + "', '" + email + "', '" + password + "');";
                    statement.executeUpdate(sql1);

                    String sql2 = "insert into uuid(uuid)" +
                            " values ('" + uniqueID + "');";
                    statement.executeUpdate(sql2);

                    Cookie uuidCookie = new Cookie("id",uniqueID);
                    response.addCookie(uuidCookie);
                    uuidCookie.setMaxAge(3600 * 24);

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
}