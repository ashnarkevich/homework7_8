package com.gmail.petrikov05.app.repository.impl;

import java.io.Serializable;
import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.gmail.petrikov05.app.repository.UserRepository;
import com.gmail.petrikov05.app.repository.model.User;
import com.gmail.petrikov05.app.repository.model.UserInformation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserRepositoryImpl extends GeneralRepositoryImpl<User> implements UserRepository {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private static final String ADDRESS = "address";
    private static final String AGE = "age";
    private static final String ID = "id";
    private static final String IS_ACTIVE = "is_active";
    private static final String PASSWORD = "password";
    private static final String TELEPHONE = "telephone";
    private static final String USERNAME = "username";
    private static UserRepository instance;

    private UserRepositoryImpl() {
    }

    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepositoryImpl();
        }
        return instance;
    }

    @Override
    public User add(Connection connection, User user) throws SQLException {
        try (
                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO user(username, password, is_active, age) VALUES (?,?,?,?)",
                        Statement.RETURN_GENERATED_KEYS
                )
        ) {
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getPassword());
            statement.setBoolean(3, user.getActive());
            statement.setInt(4, user.getAge());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
            return user;
        }
    }

    @Override
    public List<User> findAll(Connection connection) throws SQLException {
        try (
                PreparedStatement statement = connection.prepareStatement(
                        "SELECT u.id, username, password, age, is_active, ui.address, ui.telephone FROM user u " +
                                "JOIN user_information ui on u.id = ui.user_id;"
                )
        ) {
            List<User> users = new ArrayList<>();
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    User person = getUser(rs);
                    users.add(person);
                }
                return users;
            }
        }
    }

    @Override
    public boolean findUserById(Connection connection, User user) throws SQLException {
        try (
                PreparedStatement statement = connection.prepareStatement(
                        "SELECT id FROM user WHERE id = ?;"
                )
        ) {
            statement.setInt(1, user.getId());
            return statement.execute();
        }
    }

    @Override
    public int delete(Connection connection, Serializable id) throws SQLException {
        try (
                PreparedStatement statement = connection.prepareStatement(
                        "DELETE FROM user WHERE id=?"
                )
        ) {
            statement.setInt(1, (Integer) id);
            return statement.executeUpdate();
        }
    }

    private User getUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt(ID));
        user.setUserName(rs.getString(USERNAME));
        user.setPassword(rs.getString(PASSWORD));
        user.setActive(rs.getBoolean(IS_ACTIVE));
        user.setAge(rs.getInt(AGE));

        UserInformation userInformation = new UserInformation();
        userInformation.setAddress(rs.getString(ADDRESS));
        userInformation.setTelephone(rs.getString(TELEPHONE));
        user.setUserInformation(userInformation);

        return user;
    }

}
