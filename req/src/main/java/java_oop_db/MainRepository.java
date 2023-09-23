package java_oop_db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

public class MainRepository {
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "qwikWell12";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/test";

    public static void main(String[] args) throws Exception{
        Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        UserRepository userRepository = new UserRepositoryJdbcImp(connection);
        List<User> users = userRepository.findAllByAge(25);
    }
}
