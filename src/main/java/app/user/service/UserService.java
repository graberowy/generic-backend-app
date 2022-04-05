package app.user.service;

import app.generic.service.GenericService;
import app.user.repository.User;

import java.util.Optional;

/**
 * This interface extends service methods on user
 */
public interface UserService extends GenericService<User, Long> {
    /**
     * This method is find user by username instance
     *
     * @param username instance
     * @return user object
     */
    Optional<User> findByUsername(String username);
}
