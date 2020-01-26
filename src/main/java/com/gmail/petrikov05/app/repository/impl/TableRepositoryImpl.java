package com.gmail.petrikov05.app.repository.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.gmail.petrikov05.app.repository.TableRepository;

public class TableRepositoryImpl implements TableRepository {

    private static TableRepository instance;

    public TableRepositoryImpl() {
    }

    public static TableRepository getInstance() {
        if (instance == null) {
            instance = new TableRepositoryImpl();
        }
        return instance;
    }

    @Override
    public void executeQuery(Connection connection, String query) throws SQLException {
        try (
                Statement statement = connection.createStatement()
        ) {
            statement.execute(query);
        }
    }

}
