package com.gmail.petrikov05.app.repository.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.gmail.petrikov05.app.repository.UserInformationRepository;
import com.gmail.petrikov05.app.repository.model.UserInformation;

public class UserInformationRepositoryImpl implements UserInformationRepository {

    private static UserInformationRepository instance;

    private UserInformationRepositoryImpl() {
    }

    public static UserInformationRepository getInstance() {
        if (instance == null) {
            instance = new UserInformationRepositoryImpl();
        }
        return instance;
    }

    @Override
    public UserInformation add(Connection connection, UserInformation userInformation) throws SQLException {
        try (
                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO user_information(user_id, address, telephone) VALUES (?,?,?)"
                )
        ) {
            statement.setInt(1, userInformation.getUserId());
            statement.setString(2, userInformation.getAddress());
            statement.setString(3, userInformation.getTelephone());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating user information failed, no rows affected.");
            }
            return userInformation;
        }
    }

    @Override
    public int delete(Connection connection, Serializable id) throws SQLException, UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int updateAddressById(Connection connection, UserInformation userInformation) throws SQLException {
        try (
                PreparedStatement statement = connection.prepareStatement(
                        "UPDATE user_information SET address=? WHERE user_id=?"
                )
        ) {
            statement.setString(1, userInformation.getAddress());
            statement.setInt(2, userInformation.getUserId());
            return statement.executeUpdate();
        }
    }

}
