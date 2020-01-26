package com.gmail.petrikov05.app.repository;

import java.sql.Connection;
import java.sql.SQLException;

public interface TableRepository {

    void executeQuery(Connection connection, String query) throws SQLException;

}
