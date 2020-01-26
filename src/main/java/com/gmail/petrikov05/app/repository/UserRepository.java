package com.gmail.petrikov05.app.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.gmail.petrikov05.app.repository.model.User;

public interface UserRepository extends GeneralRepository<User> {

    List<User> findAll(Connection connection) throws SQLException;

    boolean findUserById(Connection connection, User user) throws SQLException;

}
