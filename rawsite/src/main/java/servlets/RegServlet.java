package servlets;

import repository.UsersRepository;
import repository.DataRepositoryJdbc;
import repository.UsersRepositoryJdbcImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.*;

@WebServlet("/reg")
public class RegServlet extends HttpServlet {
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "qwikWell12";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/test";
    private static final java.util.UUID UUID = null;
    private UsersRepository usersRepository;
    private DataRepositoryJdbc data;

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
            data = new DataRepositoryJdbc(connection, statement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String secondPassword = request.getParameter("second_password");

        HttpSession httpSession = request.getSession(true);

        String result;
        String status;

        if (usersRepository.findUserByEmail(email)) {
            result = "User with this email: " + email + " is already registered";
            status = "Registration Failed";
        } else {
            if (!password.isEmpty() && !username.isEmpty() && password.equals(secondPassword) && !email.isEmpty()) {
                String uniqueID = UUID.randomUUID().toString();
                data.userSave(username, email, password, uniqueID);

                httpSession.setAttribute("authenticated", true);
                httpSession.setAttribute("username", username);

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
    }

}