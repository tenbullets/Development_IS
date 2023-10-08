package repository;

import models.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class UsersRepositoryJdbcImpl implements UsersRepository {

    private final Connection connection;

    private final Statement statement;

    private static final String USERS = "select * from users";
    private static final String UUID = "select * from uuid";

    public UsersRepositoryJdbcImpl(Connection connection, Statement statement) {
        this.statement = statement;
        this.connection = connection;
    }

    @Override
    public List allUsers() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(USERS);
            List<User> result = new ArrayList<>();

            while (resultSet.next()) {
                User user = User.builder()
                        .username(resultSet.getString("username"))
                        .email(resultSet.getString("email"))
                        .password(resultSet.getString("password"))
                        .build();
                result.add(user);
                if (result.isEmpty()) {
                    System.out.println("Пользователь не найден");
                }
            }
            return result;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void save(User entity) {}

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public Optional<User> findByLogin(User login) {
        return Optional.empty();
    }

    @Override
    public boolean findUser(String username, String password) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(USERS);

            while (resultSet.next()) {
                User user = User.builder()
                        .username(resultSet.getString("username"))
                        .password(resultSet.getString("password"))
                        .build();
                if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                    return true;
                }
            }
            return false;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public boolean findUserByEmail(String email) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(USERS);

            while (resultSet.next()) {
                User user = User.builder()
                        .email((resultSet.getString("email")))
                        .build();
                if (user.getEmail().equals(email)) {
                    return true;
                }
            }
            return false;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public String findUserByUuid(String uuid) {
        try (Statement statement = connection.createStatement()) {

            User copy = null;
            ResultSet uuidDb = statement.executeQuery(UUID);
            while (uuidDb.next()) {
                User user = User.builder()
                        .id(uuidDb.getString("user_id"))
                        .uuid(uuidDb.getString("uuid"))
                        .build();
                if(user.getUuid().equals(uuid)) {
                    copy = user;
                }
            }

            ResultSet usersDb = statement.executeQuery(USERS);
            while (usersDb.next()) {
                User user = User.builder()
                        .id(usersDb.getString("user_id"))
                        .username(usersDb.getString("username"))
                        .build();
                assert copy != null;
                if(Objects.equals(copy.getId(), user.getId())) {
                    return user.getUsername();
                }
            }

            return "0";
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public String returnUuid(String username) {
        try (Statement statement = connection.createStatement()){

            User copy = null;
            ResultSet usersDb = statement.executeQuery(USERS);
            while (usersDb.next()) {
                User user = User.builder()
                        .id(usersDb.getString("user_id"))
                        .username(usersDb.getString("username"))
                        .build();
                if(user.getUsername().equals(username)) {
                    copy = user;
                }
            }

            ResultSet uuidDb = statement.executeQuery(UUID);
            while (uuidDb.next()) {
                User user = User.builder()
                        .id(uuidDb.getString("user_id"))
                        .uuid(uuidDb.getString("uuid"))
                        .build();
                assert copy != null;
                if(Objects.equals(copy.getId(), user.getId())) {
                    return user.getUuid();
                }
            }

            return "0";
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public boolean findUserByName(String username) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(USERS);

            while (resultSet.next()) {
                User user = User.builder()
                        .username(resultSet.getString("username"))
                        .build();
                if (user.getUsername().equals(username)){
                    return true;
                }
            }
            return false;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
