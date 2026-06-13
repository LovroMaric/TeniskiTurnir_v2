package Service;

import Model.User;
import Repository.IUserRepository;
import Repository.UserRepository;

import java.util.Optional;

public class UserService implements IUserService {

    private final IUserRepository repository = new UserRepository();

    @Override
    public Optional<User> login(String username, String password) {
        return repository.login(username, password);
    }
}