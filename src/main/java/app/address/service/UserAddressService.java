package app.address.service;

import app.address.model.UserAddressTotal;

import java.util.Optional;

/**
 * This interface provides methods on userAddress service
 */
public interface UserAddressService {
    /**
     * This method is for attach address to user record
     * @param id of user object
     * @return userAddressTotal
     */
    Optional<UserAddressTotal> findUserAddress(Long id);
}
