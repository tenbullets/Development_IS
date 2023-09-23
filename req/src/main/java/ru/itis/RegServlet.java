package ru.itis;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/reg")
public class RegServlet extends HttpServlet {
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

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter writer = response.getWriter();
        String userName = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String secondPassword = request.getParameter("second_password");

        if(password.equals(secondPassword)) {

            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);) {
                Statement statement = connection.createStatement();
                String sql = "insert into users(username, email, password)" +
                        " values ('" + userName + "', '" + email + "', '" + password + "');";
                int affectedRows = statement.executeUpdate(sql);
                if (affectedRows > 0) {
                    writer.println("<p>User added: " + userName + "</p>");
                } else {
                    writer.println("<p>User not added: " + userName + "</p>");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            } finally {
                writer.close();
            }

        } else { writer.println("<h1>passwords don't match</h1>");}

    }
}
