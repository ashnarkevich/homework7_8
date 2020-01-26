package com.gmail.petrikov05.app.service.impl;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.gmail.petrikov05.app.repository.ConnectionRepository;
import com.gmail.petrikov05.app.repository.UserInformationRepository;
import com.gmail.petrikov05.app.repository.UserRepository;
import com.gmail.petrikov05.app.repository.impl.ConnectionRepositoryImpl;
import com.gmail.petrikov05.app.repository.impl.UserInformationRepositoryImpl;
import com.gmail.petrikov05.app.repository.impl.UserRepositoryImpl;
import com.gmail.petrikov05.app.repository.model.User;
import com.gmail.petrikov05.app.repository.model.UserInformation;
import com.gmail.petrikov05.app.service.UserService;
import com.gmail.petrikov05.app.service.model.AddUserDTO;
import com.gmail.petrikov05.app.service.model.AddedUserDTO;
import com.gmail.petrikov05.app.service.model.UserDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserServiceImpl implements UserService {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private static UserService instance;
    private UserRepository userRepository;
    private UserInformationRepository userInformationRepository;
    private ConnectionRepository connectionRepository;

    private UserServiceImpl(
            ConnectionRepository connectionRepository,
            UserRepository userRepository,
            UserInformationRepository userInformationRepository
    ) {
        this.connectionRepository = connectionRepository;
        this.userRepository = userRepository;
        this.userInformationRepository = userInformationRepository;
    }

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserServiceImpl(
                    ConnectionRepositoryImpl.getInstance(),
                    UserRepositoryImpl.getInstance(),
                    UserInformationRepositoryImpl.getInstance());
        }
        return instance;
    }

    @Override
    public AddedUserDTO add(AddUserDTO addUserDTO) {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                User user = convertAddUserDTOToUser(addUserDTO);
                User addedUser = userRepository.add(connection, user);
                addedUser.getUserInformation().setUserId(addedUser.getId());
                userInformationRepository.add(connection, addedUser.getUserInformation());
                connection.commit();
                return convertUserToAddedUserDTO(addedUser);
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                throw new SQLException("Add user failed");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public List<UserDTO> getListUser() {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                List<User> userList = userRepository.findAll(connection);
                List<UserDTO> userDTOList = convertUserToUserDTO(userList);
                connection.commit();
                return userDTOList;
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                throw new SQLException("Get user failed");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return Collections.emptyList();
    }

    @Override
    public int deleteById(int id) {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                int affectedRows = userRepository.delete(connection, id);
                connection.commit();
                return affectedRows;
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                throw new SQLException("Delete user failed");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return 0;
    }

    @Override
    public int updateAddressById(AddUserDTO updateUserDTO){
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                User user = convertUpdateUserAddressToUser(updateUserDTO);
                if (userRepository.findUserById(connection, user)) {
                    int affectedRows = userInformationRepository.updateAddressById(connection, user.getUserInformation());
                    connection.commit();
                    return affectedRows;
                }
                return 0;
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                throw new SQLException("Update user failed");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return 0;
    }

    private User convertUpdateUserAddressToUser(AddUserDTO updateUserDTO) {
        User user = new User();
        user.setId(updateUserDTO.getId());

        UserInformation userInformation = new UserInformation();
        userInformation.setUserId(updateUserDTO.getId());
        userInformation.setAddress(updateUserDTO.getAddress());
        user.setUserInformation(userInformation);

        return user;
    }

    private List<UserDTO> convertUserToUserDTO(List<User> userList) {
        return userList.stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    private UserDTO convert(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUserName());
        userDTO.setPassword(user.getPassword());
        userDTO.setAge(user.getAge());
        userDTO.setActive(user.getActive());
        if (user.getUserInformation() != null) {
            userDTO.setTelephone(user.getUserInformation().getTelephone());
            userDTO.setAddress(user.getUserInformation().getAddress());
        }
        return userDTO;
    }

    private AddedUserDTO convertUserToAddedUserDTO(User addedUser) {
        AddedUserDTO addedUserDTO = new AddedUserDTO();
        AddedUserDTO.setUserName(addedUser.getUserName());
        return addedUserDTO;
    }

    private User convertAddUserDTOToUser(AddUserDTO addUserDTO) {
        User user = new User();
        user.setUserName(addUserDTO.getUserName());
        user.setPassword(addUserDTO.getPassword());
        user.setActive(addUserDTO.getIsActive());
        user.setAge(addUserDTO.getAge());

        UserInformation userInformation = new UserInformation();
        userInformation.setTelephone(addUserDTO.getTelephone());
        userInformation.setAddress(addUserDTO.getAddress());
        user.setUserInformation(userInformation);

        return user;
    }

}
