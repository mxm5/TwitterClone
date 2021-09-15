package Service;

import Base.Service.Service;
import Domain.User;
import Repository.UserRepository;

import java.util.List;

public class UserService extends Service<User, Long, UserRepository> {

    public UserService(UserRepository repository) {
        super(repository);
    }

    public User login(User enteredUser) {
        return repository.findUserNamePasswordInDb(enteredUser);
    }

    public void register(User registered) {
        try {
            repository.save(registered);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<User> findUserByUserNameLike(String query) {
        return repository.findUserByUserName(query);
    }
}
