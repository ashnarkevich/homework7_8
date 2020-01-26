package com.gmail.petrikov05.app.repository;

import java.sql.Connection;
import java.sql.SQLException;

import com.gmail.petrikov05.app.repository.model.UserInformation;

public interface UserInformationRepository extends GeneralRepository<UserInformation> {

    int updateAddressById(Connection connection, UserInformation userInformation) throws SQLException;

}
