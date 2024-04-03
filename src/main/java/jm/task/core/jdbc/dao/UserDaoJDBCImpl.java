package jm.task.core.jdbc.dao;

import com.mysql.cj.x.protobuf.MysqlxNotice;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDaoJDBCImpl implements UserDao {

    private static Logger log = Logger.getLogger(UserDaoJDBCImpl.class.getName());

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String query = "CREATE TABLE IF NOT EXISTS Users (\n" + "  `id` INT NOT NULL AUTO_INCREMENT,\n" + "  `name` VARCHAR(45) NOT NULL,\n" + "  `lastname` VARCHAR(45) NOT NULL,\n" + "  `age` INT NOT NULL,\n" + "  PRIMARY KEY (`id`))\n";
        try (Connection connection = Util.getConnection()) {

            connection.prepareStatement(query).execute();
            log.info("\u001B[32m ������� ������� Users \u001B[0m");
        } catch (SQLException e) {
            log.log(Level.SEVERE, "������ ��� �������� ������� Users:", e);
        }

    }

    public void dropUsersTable() {
        String query = "DROP TABLE IF EXISTS Users";
        try (Connection connection = Util.getConnection()) {
            connection.prepareStatement(query).execute();
            log.info("\u001B[32m ������� Users ������� \u001B[0m");
        } catch (SQLException e) {
            log.log(Level.SEVERE, "������ ��� �������� ������� Users:", e);
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        String query = "INSERT INTO Users (name,lastname,age) values (?,?,?)";
        try (Connection connection = Util.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setString(3, String.valueOf(age));
            statement.execute();
            log.info("\u001B[32m ������������ " + name + " ������� �������� � ������� \u001B[0m");
        } catch (SQLException e) {
            log.log(Level.SEVERE, "������ ��� ���������� ������������:", e);
        }

    }

    public void removeUserById(long id) {
        String query = "DELETE FROM Users WHERE ID = " + id;
        try (Connection connection = Util.getConnection()) {
            connection.prepareStatement(query).execute();
            log.info("\u001B[32m ������������ � id = " + id + " ������� ����� \u001B[0m");
        } catch (SQLException e) {
            log.log(Level.SEVERE, "������ ��� �������� ������������:", e);
        }

    }

    public List<User> getAllUsers() {
        String query = "SELECT * FROM Users";
        List<User> userList = new ArrayList<>();
        try (Connection connection = Util.getConnection(); ResultSet resultSet = connection.prepareStatement(query).executeQuery()) {
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("lastname");
                byte age = resultSet.getByte("age");
                userList.add(new User(name, lastName, age));
            }
            log.info("\u001B[32m �������� ��� ������������ �� ������� Users \u001B[0m");


        } catch (SQLException e) {
            log.log(Level.SEVERE, "������ ��� ��������� ���� �������������: ", e);
        }
        return userList;
    }

    public void cleanUsersTable() {
        String query = "DELETE FROM Users";
        try (Connection connection = Util.getConnection()) {
            connection.prepareStatement(query).execute();
            log.info("\u001B[32m ������� Users ������� ������� \u001B[0m");
        } catch (SQLException e) {
            log.log(Level.SEVERE, "������ ��� ������� ������� ���� �������������:", e);
        }

    }
}
