package app.address.client;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class UserAddressAPITest {
    private final RestTemplate restTemplate = Mockito.mock(RestTemplate.class);
    private final UserAddressAPI userAddressAPI = new UserAddressAPI(restTemplate, "http://localhost:8080//api/v1/address");

    @Test
    void when_get_user_address_API_then_array_of_user_address_should_be_returned() {
        //given
        Address address = new Address();
        address.setStreet("Adamsa 1");
        address.setCode("00-001");

        Address address2 = new Address();
        address2.setStreet("Bora 2");
        address2.setCode("00-002");

        Address address3 = new Address();
        address3.setStreet("Czecha 3");
        address3.setCode("00-003");

        Address address4 = new Address();
        address4.setStreet("Dobra 4");
        address4.setCode("00-004");

        Address address5 = new Address();
        address5.setStreet("Erki 5");
        address5.setCode("00-005");

        Address address6 = new Address();
        address6.setStreet("Testowa 6");
        address6.setCode("00-006");

        Address address7 = new Address();
        address7.setStreet("Testowa 7");
        address7.setCode("00-007");

        Address address8 = new Address();
        address8.setStreet("Testowa 8");
        address8.setCode("00-008");

        Address address9 = new Address();
        address9.setStreet("Testowa 9");
        address9.setCode("00-009");

        UserAddress userAddress = new UserAddress();
        userAddress.setEmail("adam.kowalski@gmail.com");
        userAddress.setAddress(address);

        UserAddress userAddress2 = new UserAddress();
        userAddress2.setEmail("barbara.kowalski@gmail.com");
        userAddress2.setAddress(address2);

        UserAddress userAddress3 = new UserAddress();
        userAddress3.setEmail("cecylia.kowalski@gmail.com");
        userAddress3.setAddress(address3);

        UserAddress userAddress4 = new UserAddress();
        userAddress4.setEmail("daniel.kowalski@gmail.com");
        userAddress4.setAddress(address4);

        UserAddress userAddress5 = new UserAddress();
        userAddress5.setEmail("eryk.kowalski@gmail.com");
        userAddress5.setAddress(address5);

        UserAddress userAddress6 = new UserAddress();
        userAddress6.setEmail("test6@mail.com");
        userAddress6.setAddress(address6);

        UserAddress userAddress7 = new UserAddress();
        userAddress7.setEmail("test7@mail.com");
        userAddress7.setAddress(address7);

        UserAddress userAddress8 = new UserAddress();
        userAddress8.setEmail("test8@mail.com");
        userAddress8.setAddress(address8);

        UserAddress userAddress9 = new UserAddress();
        userAddress9.setEmail("test9@mail.com");
        userAddress9.setAddress(address9);

        UserAddress[] userAddresses = {userAddress, userAddress2, userAddress3, userAddress4, userAddress5, userAddress6, userAddress7, userAddress8, userAddress9};

        Mockito.when(restTemplate.getForEntity("http://localhost:8080//api/v1/address", UserAddress[].class))
                .thenReturn(new ResponseEntity(userAddresses, HttpStatus.OK));

        //when
        List<UserAddress> userAddressesResultList = userAddressAPI.getUserAddressDetails();
        //then
        assertEquals(9, userAddressesResultList.size());
        assertEquals(userAddresses[0], userAddressesResultList.get(0));
        assertEquals(userAddresses[1], userAddressesResultList.get(1));
        assertEquals(userAddresses[2], userAddressesResultList.get(2));
        assertEquals(userAddresses[3], userAddressesResultList.get(3));
        assertEquals(userAddresses[4], userAddressesResultList.get(4));
        assertEquals(userAddresses[5], userAddressesResultList.get(5));
        assertEquals(userAddresses[6], userAddressesResultList.get(6));
        assertEquals(userAddresses[7], userAddressesResultList.get(7));
        assertEquals(userAddresses[8], userAddressesResultList.get(8));


    }
}