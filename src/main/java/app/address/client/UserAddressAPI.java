package app.address.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * This class is to get data from outside API
 */
@Component
public class UserAddressAPI {

    private final RestTemplate restTemplate;
    private final String addressUrl;

    /**
     * This is constructor
     * @param restTemplate support Http Accessor
     * @param addressUrl API url path
     */
    public UserAddressAPI(RestTemplate restTemplate, @Value("${address.url}") String addressUrl) {
        this.restTemplate = restTemplate;
        this.addressUrl = addressUrl;
    }

    /**
     * This is method which get data from outside API and put it in userAddress model
     * @return List of userAddress
     */
    public List<UserAddress> getUserAddressDetails() {
        ResponseEntity<UserAddress[]> object = restTemplate.getForEntity(addressUrl, UserAddress[].class);
        return Arrays.asList(Objects.requireNonNull(object.getBody()));
    }

}
