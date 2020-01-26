package com.gmail.petrikov05.app.repository.model;

public enum CreateTableEnum {
    CREATE_USER_TABLE("CREATE TABLE user(" +
            "id INT PRIMARY KEY AUTO_INCREMENT," +
            "username VARCHAR(40) NOT NULL," +
            "password VARCHAR(40) NOT NULL," +
            "is_active TINYINT(1) DEFAULT true," +
            "age INT(11)" +
            ");"),

    CREATE_USER_INFORMATION_TABLE("CREATE TABLE user_information(" +
            "user_id INT(11) PRIMARY KEY," +
            "address VARCHAR(100) NOT NULL," +
            "telephone VARCHAR(40) NOT NULL," +
            "FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE);");

    private final String query;

    CreateTableEnum(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}
