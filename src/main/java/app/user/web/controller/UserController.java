package app.user.web.controller;

import app.generic.web.controller.GenericControllerImpl;
import app.user.repository.User;
import app.user.service.UserServiceBasic;
import app.user.web.resources.UserDTO;
import app.user.web.resources.UserMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * This class if for cover CRUD operations on user
 *
 * @author pawel.jankowski
 */

@RestController
@RequestMapping("/api/v1/users")
public class UserController extends GenericControllerImpl<UserDTO, User, Long> {

    private final UserServiceBasic userServiceBasic;
    private final UserMapper userMapper;

    /**
     * This is constructor for use generic operations on specific type
     * @param userServiceBasic      user service
     * @param userMapper user object mapper
     */
    public UserController(UserServiceBasic userServiceBasic, UserMapper userMapper) {
        super(userServiceBasic, userMapper);
        this.userServiceBasic = userServiceBasic;
        this.userMapper = userMapper;
    }

    /**
     * This method is to find user record by username instance
     * @param username object
     * @return userDTO or error if not found
     */
    @GetMapping("/username/{username}")
    public ResponseEntity<UserDTO> getByUsername(@PathVariable String username) {
        return userServiceBasic.findByUsername(username)
                .map(userMapper::mapToUserDTO)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


}
