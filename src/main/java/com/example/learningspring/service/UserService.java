package com.example.learningspring.service;

import java.util.List;
import com.example.learningspring.dto.CreateDepositDto;
import com.example.learningspring.dto.UserDto;
import com.example.learningspring.model.User;


public interface UserService {

    User createUser(final UserDto userData);

    List<User> readUsers();

    User retrieveUser(final long id);

    User updateUser(final UserDto userData, final long id);

    void deleteUser(final long id);

    User createDeposit(final CreateDepositDto depositData, final long id);

}
