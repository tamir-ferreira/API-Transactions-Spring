package springApplication.service;

import java.util.List;
import springApplication.dto.CreateDepositDto;
import springApplication.dto.UserDto;
import springApplication.model.User;


public interface UserService {

    User createUser(final UserDto userData);

    List<User> readUsers();

    User retrieveUser(final long id);

    User updateUser(final UserDto userData, final long id);

    void deleteUser(final long id);

    User createDeposit(final CreateDepositDto depositData, final long id);

}
