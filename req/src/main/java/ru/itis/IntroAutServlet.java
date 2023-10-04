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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@WebServlet("/introAut")
public class IntroAutServlet extends HttpServlet {
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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getCookies() == null) {
            request.getRequestDispatcher("/html/aut.html").forward(request, response);
        } else {
            Cookie[] cookie = (request.getCookies());
            String username = usersRepository.findUserByUuid(returnUuid(cookie));

//            String username = usersRepository.findUserByUuid(cookie[0].getValue());

//            for (int i = 0; i < uuid.length; i++) {
//                System.out.println(uuid[i].getValue() + " " + uuid[i].getName());
//            }
//            System.out.println();

            if(!username.equals("0")) {
                request.setAttribute("username", username);
                request.getRequestDispatcher("/jsp/choice.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("/html/aut.html").forward(request, response);
            }
        }
    }

    public String returnUuid(Cookie[] cookie) {
        for (Cookie value : cookie) {
            if (value.getName().equals("id")) {
                return value.getValue();
            }
        }
        return null;
    }

}