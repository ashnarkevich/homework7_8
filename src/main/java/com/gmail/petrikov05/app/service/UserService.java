package com.gmail.petrikov05.app.service;

import java.sql.SQLException;
import java.util.List;

import com.gmail.petrikov05.app.service.model.AddUserDTO;
import com.gmail.petrikov05.app.service.model.AddedUserDTO;
import com.gmail.petrikov05.app.service.model.UserDTO;

public interface UserService {

    AddedUserDTO add(AddUserDTO addUserDTO);

    List<UserDTO> getListUser();

    int deleteById(int id);

    int updateAddressById(AddUserDTO updateUserDTO);

}
