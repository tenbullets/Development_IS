package servlets;

import repository.DataRepositoryJdbc;
import repository.UsersRepository;
import repository.UsersRepositoryJdbcImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@WebServlet("/aut")
public class AutServlet extends HttpServlet {
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "qwikWell12";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/test";
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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String result;
        String status;

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if(usersRepository.findUserByName(username)) {
            String uniqueID = usersRepository.returnUuid(username);
            data.addCookieId(uniqueID, response);
        }

        if(usersRepository.findUser(username, password)) {
            result = username + " welcome";
            status = "Login Successful";
        } else {
            result = "User " + username + " not found, check password or username";
            status = "Login Failed";
        }

        request.setAttribute("resultOfAut", result);
        request.setAttribute("status", status);
        request.getRequestDispatcher("/jsp/result.jsp").forward(request, response);
    }
}