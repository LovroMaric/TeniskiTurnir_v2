package Service;

import Model.User;

import java.util.Optional;

public interface IUserService {

    Optional<User> login(String username, String password);

}