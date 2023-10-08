package repository;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataRepositoryJdbc implements DataRepository {
    private final Connection connection;
    private final Statement statement;
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "qwikWell12";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/test";

    public DataRepositoryJdbc(Connection connection, Statement statement) {
        this.connection = connection;
        this.statement = statement;
    }

    @Override
    public void userSave(String username, String email, String password, String uuid) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);) {
            Statement statement = connection.createStatement();

            String sql1 = "insert into users(username, email, password)" +
                    " values ('" + username + "', '" + email + "', '" + password + "');";
            statement.executeUpdate(sql1);

            String sql2 = "insert into uuid(uuid)" +
                    " values ('" + uuid + "');";
            statement.executeUpdate(sql2);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void addCookieId(String uuid, HttpServletResponse response) {
        Cookie uuidCookie = new Cookie("id", uuid);
        response.addCookie(uuidCookie);
        uuidCookie.setMaxAge(3600 * 24);
    }

}