package Repository;

import Model.User;

import java.util.Optional;

public interface IUserRepository {

    Optional<User> login(String username, String password);

    void register(String username, String password);
}