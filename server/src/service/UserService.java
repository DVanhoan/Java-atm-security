package service;

import database.MySQLConnection;
import model.Note;
import model.User;

import java.awt.geom.NoninvertibleTransformException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class UserService {
    private static final Connection connection = MySQLConnection.getConnection();
    private static final String INSERT_USER = "INSERT INTO user(id ,username, password, cost) VALUES(?, ?, ?, ?)";
    private static final String SELECT_USER = "SELECT * FROM user WHERE username = ? AND password = ?";
    private static final String SELECT_USER_BY_ID = "SELECT * FROM user WHERE id = ?";
    private static final String SELECT_USER_BY_USERNAME = "SELECT * FROM user WHERE username = ?";
    private static final String UPDATE_COST = "UPDATE user SET cost = ? WHERE id = ?";
    private static final String UPDATE_PASSWORD = "UPDATE user SET password = ? WHERE username = ?";
    private static final String UPDATE_USERNAME = "UPDATE user SET username = ? WHERE id = ?";
    private static final String DELETE_USER = "DELETE FROM user WHERE id = ?";
    private static final String SELECT_ALL_USER = "SELECT * FROM user";
    private static final String Check_Cost = "SELECT cost FROM user WHERE username = ?";




    public UserService() {

    }


    public static Note<User> insertUser(User user) throws SQLException {
        boolean check = checkExitUserByUserName(user.getUsername()).isCheck();
        if (check) {
            return new Note<>(user, "user exist", false);
        }else {
            int result = 0;
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER);
            preparedStatement.setInt(1, user.getId());
            preparedStatement.setString(2, user.getUsername());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setInt(4, user.getCost());
            result = preparedStatement.executeUpdate();
            if (result == 0) {
                return new Note<>(user, "Insert failed", false);
            }
            else {
                return new Note<>(user, "Insert success", true);
            }
        }
    }

    public static Note<User> updateUserPassword(User user) throws SQLException {
        boolean check = checkExitUserByUserName(user.getUsername()).isCheck();
        if (!check) {
            return new Note<>(null, "user not exist", false);
        }
        else {
            int result = 0;
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PASSWORD);
            preparedStatement.setString(1, user.getPassword());
            preparedStatement.setString(2, user.getUsername());
            result = preparedStatement.executeUpdate();
            if (result == 0) {
                return new Note<>(null, "Update failed", false);
            }
            else {
                User user1 = checkExistUser(user).getData();
                return new Note<>(user1, "Update success", true);
            }
        }
    }

    public static Note<User> updateUserUsername(int id, String username) throws SQLException {
        boolean check = checkExistUserById(id).isCheck();
        if (!check) {
            return new Note<>(null, "user not exist", false);
        }
        else {
            int result = 0;
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USERNAME);
            preparedStatement.setString(1, username);
            preparedStatement.setInt(2, id);
            result = preparedStatement.executeUpdate();
            if (result == 0) {
                return new Note<>(null, "Update failed", false);
            }
            else {
                User user = checkExistUserById(id).getData();
                return new Note<>(user, "Update success", true);
            }
        }
    }

    public static Note<User> updateUserCost(User user) throws SQLException {
        boolean check = checkExitUserByUserName(user.getUsername()).isCheck();
        if (!check) {
            return new Note<>(null, "user not exist", false);
        }
        else {
            int result = 0;
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_COST);
            preparedStatement.setInt(1, user.getCost());
            preparedStatement.setInt(2, checkExitUserByUserName(user.getUsername()).getData().getId());
            result = preparedStatement.executeUpdate();
            if (result == 0) {
                return new Note<>(null, "Update failed", false);
            }
            else {
                User user1 = checkExitUserByUserName(user.getUsername()).getData();
                return new Note<>(user1, "Update success", true);
            }
        }
    }


    public static Note<User> checkExistUser(User user) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER);
        preparedStatement.setString(1, user.getUsername());
        preparedStatement.setString(2, user.getPassword());
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            User user1 = new User();
            user1.setId(resultSet.getInt("id"));
            user1.setUsername(resultSet.getString("username"));
            user1.setPassword(resultSet.getString("password"));
            user1.setCost(resultSet.getInt("cost"));
            return new Note<>(user1, "user exist", true);
        }
        else {
            return new Note<>(null, "user not exist", false);
        }
    }

    public static Note<User> checkExistUserById(int id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            User user = new User();
            user.setId(resultSet.getInt("id"));
            user.setUsername(resultSet.getString("username"));
            user.setPassword(resultSet.getString("password"));
            user.setCost(resultSet.getInt("cost"));
            return new Note<>(user, "user exist", true);
        }
        else {
            return new Note<>(null, "user not exist", false);
        }
    }

    public static Note<User> checkExitUserByUserName(String username) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_USERNAME);
        preparedStatement.setString(1, username);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            User user = new User();
            user.setId(resultSet.getInt("id"));
            user.setUsername(resultSet.getString("username"));
            user.setPassword(resultSet.getString("password"));
            user.setCost(resultSet.getInt("cost"));
            return new Note<>(user, "user exist", true);
        }
        else {
            return new Note<>(null, "user not exist", false);
        }
    }

    public static ArrayList<User> getAllUser() throws SQLException {
        ArrayList<User> list = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USER);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            User user = new User();
            user.setId(resultSet.getInt("id"));
            user.setUsername(resultSet.getString("username"));
            user.setPassword(resultSet.getString("password"));
            user.setCost(resultSet.getInt("cost"));
            list.add(user);
        }
        return list;
    }

    public static HashMap<String, Integer> selectAllUser() throws SQLException {
        HashMap<String, Integer> list = new HashMap<>();
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USER);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            list.put(resultSet.getString("username"), resultSet.getInt("cost"));
        }
        return list;
    }

    public static Note<User> deleteUser(User user) throws SQLException {
        boolean check = checkExistUserById(user.getId()).isCheck();
        if (!check) {
            return new Note<>(null, "user not exist", false);
        }
        else {
            int result = 0;
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER);
            preparedStatement.setInt(1, user.getId());
            result = preparedStatement.executeUpdate();
            if (result == 0) {
                return new Note<>(user, "Delete failed", false);
            }
            else {
                return new Note<>(user, "Delete success", true);
            }
        }
    }

    public static Note<User> checkCostUser(User user) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(Check_Cost);
        statement.setString(1, user.getUsername());
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            User user1 = checkExitUserByUserName(user.getUsername()).getData();
            return new Note<>(user1, "success", true);

        }else {
            return new Note<>(null, "fail", false);
        }
    }


    public static void main(String[] args) throws SQLException {
        Note<User> user = checkExitUserByUserName("hoan");
        System.out.println(user);

    }


}