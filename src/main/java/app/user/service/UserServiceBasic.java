package app.user.service;

import app.generic.service.GenericCRUD;
import app.user.repository.User;
import app.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UserServiceBasic extends GenericCRUD<User, Long> implements UserService {

    private final UserRepository userRepository;
    /**
     * This is constructor for use generic operations on specific type
     *
     * @param userRepository specified type interface
     */
    public UserServiceBasic(UserRepository userRepository) {
        super(userRepository);
        this.userRepository = userRepository;
    }

    /**
     * This method is find user by username instance
     * @param username instance
     * @return user object
     */
    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsernameIgnoreCase(username);
    }
}
