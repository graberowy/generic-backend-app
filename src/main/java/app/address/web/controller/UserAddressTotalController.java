package app.address.web.controller;

import app.address.resources.UserAddressTotalDTO;
import app.address.resources.UserAddressTotalMapper;
import app.address.service.UserAddressServiceBasic;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class if for cover CRUD operations on userAddressTotal
 */
@RestController
@RequestMapping("/api/v1/user/address")
@RequiredArgsConstructor
public class UserAddressTotalController {

    private final UserAddressServiceBasic userAddressServiceBasic;
    private final UserAddressTotalMapper userAddressTotalMapper;

    /**
     * This method is for get specific object record
     * @param id of user record
     * @return userAddressTotalDTO
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserAddressTotalDTO> getById(@PathVariable Long id) {
        return userAddressServiceBasic.findUserAddress(id)
                .map(userAddressTotalMapper::mapToUserAddressTotalDTO)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
