package com.gmail.petrikov05.app.repository.model;

public enum DropTableEnum {

    DROP_USER_INFORMATION_TABLE("DROP TABLE IF EXISTS user_information"),
    DROP_USER_TABLE("DROP TABLE IF EXISTS user");

    private final String query;

    DropTableEnum(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }

}
