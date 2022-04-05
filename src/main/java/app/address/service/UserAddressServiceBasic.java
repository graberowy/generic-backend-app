package app.address.service;

import app.address.client.UserAddressAPI;
import app.address.model.UserAddressTotal;
import app.user.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * This class is for provide service methods on userAddressTotal
 */
@Service
public class UserAddressServiceBasic implements UserAddressService {
    private final UserService userService;
    private final UserAddressAPI userAddressAPI;

    public UserAddressServiceBasic(UserService userService, UserAddressAPI userAddressAPI) {
        this.userService = userService;
        this.userAddressAPI = userAddressAPI;
    }

    /**
     * This method is for attach address to user record
     * @param id of user object
     * @return userAddressTotal
     */
    @Override
    public Optional<UserAddressTotal> findUserAddress(Long id) {
        return userService.findById(id)
                .map(UserAddressTotal::new)
                .map(this::connectAddressToUser);

    }

    /**
     * This method is for connect address to user record
     * @param userAddressTotal object
     * @return userAddressTotal
     */
    private UserAddressTotal connectAddressToUser(UserAddressTotal userAddressTotal){
        userAddressAPI.getUserAddressDetails().stream()
                .filter(userAddress -> userAddressTotal.getEmail().equals(userAddress.getEmail()))
                .findAny()
                .ifPresent(userAddress -> userAddressTotal.setAddress(userAddress.getAddress()));
        return userAddressTotal;
    }
}
