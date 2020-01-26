package com.gmail.petrikov05.app.repository;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;

public interface GeneralRepository<T> {

    T add(Connection connection, T t) throws SQLException;

    int delete(Connection connection, Serializable id) throws SQLException, UnsupportedOperationException;

}
