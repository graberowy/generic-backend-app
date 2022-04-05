package app.address.service;

import app.address.client.Address;
import app.address.client.UserAddressAPI;
import app.address.client.UserAddress;
import app.address.model.UserAddressTotal;
import app.organisation.repository.Organisation;
import app.role.repository.Role;
import app.user.repository.User;
import app.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class UserAddressServiceBasicTest {
    private final UserAddressAPI userAddressAPI = Mockito.mock(UserAddressAPI.class);
    private final UserService userService = Mockito.mock(UserService.class);
    private final UserAddressServiceBasic userAddressServiceBasic = new UserAddressServiceBasic(userService, userAddressAPI);

    @Test
    void when_use_findUserAddress_then_should_return_userAddressTotal_record() {
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

        List<UserAddress> userAddressList = new ArrayList<>();
        userAddressList.add(userAddress);
        userAddressList.add(userAddress2);
        userAddressList.add(userAddress3);
        userAddressList.add(userAddress4);
        userAddressList.add(userAddress5);
        userAddressList.add(userAddress6);
        userAddressList.add(userAddress7);
        userAddressList.add(userAddress8);
        userAddressList.add(userAddress9);

        Organisation organisation = new Organisation();
        organisation.setId(1L);
        organisation.setName("Some organisation");

        Role role = new Role();
        role.setId(1L);
        role.setName("Some role");

        Set<Role> roles = new HashSet<>();
        roles.add(role);

        User user = new User();
        user.setId(1L);
        user.setUsername("Adam Kowalski");
        user.setEmail("adam.kowalski@gmail.com");
        user.setRoles(roles);
        user.setOrganisation(organisation);

        Mockito.when(userAddressAPI.getUserAddressDetails()).thenReturn(userAddressList);
        Mockito.when(userService.findById(1L)).thenReturn(Optional.of(user));
        //when
        Optional<UserAddressTotal> userAddressTotal = userAddressServiceBasic.findUserAddress(1L);
        //then
        assertEquals("Adam Kowalski", userAddressTotal.get().getUsername());
        assertEquals("adam.kowalski@gmail.com", userAddressTotal.get().getEmail());
        assertEquals("Adamsa 1", userAddressTotal.get().getAddress().getStreet());
        assertEquals("00-001", userAddressTotal.get().getAddress().getCode());
        assertEquals(roles, userAddressTotal.get().getRoles());
        assertEquals(1L, userAddressTotal.get().getId());
        assertEquals(organisation, userAddressTotal.get().getOrganisation());

    }

    @Test
    void when_use_findUserAddress_and_user_has_no_address_then_should_return_userAddressTotal_record_with_no_address() {
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

        List<UserAddress> userAddressList = new ArrayList<>();
        userAddressList.add(userAddress);
        userAddressList.add(userAddress2);
        userAddressList.add(userAddress3);
        userAddressList.add(userAddress4);
        userAddressList.add(userAddress5);
        userAddressList.add(userAddress6);
        userAddressList.add(userAddress7);
        userAddressList.add(userAddress8);
        userAddressList.add(userAddress9);

        Organisation organisation = new Organisation();
        organisation.setId(1L);
        organisation.setName("Some organisation");

        Role role = new Role();
        role.setId(1L);
        role.setName("Some role");

        Set<Role> roles = new HashSet<>();
        roles.add(role);

        User user = new User();
        user.setId(1L);
        user.setUsername("Jan Kowalski");
        user.setEmail("janek.kowalski@gmail.com");
        user.setRoles(roles);
        user.setOrganisation(organisation);

        Mockito.when(userAddressAPI.getUserAddressDetails()).thenReturn(userAddressList);
        Mockito.when(userService.findById(1L)).thenReturn(Optional.of(user));
        //when
        Optional<UserAddressTotal> userAddressTotal = userAddressServiceBasic.findUserAddress(1L);
        //then
        assertEquals("Jan Kowalski", userAddressTotal.get().getUsername());
        assertEquals("janek.kowalski@gmail.com", userAddressTotal.get().getEmail());
        assertNull(userAddressTotal.get().getAddress());
        assertEquals(roles, userAddressTotal.get().getRoles());
        assertEquals(1L, userAddressTotal.get().getId());
        assertEquals(organisation, userAddressTotal.get().getOrganisation());

    }

    @Test
    void when_use_findUserAddress_with_no_existing_id_then_should_return_optional_empty() {
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

        List<UserAddress> userAddressList = new ArrayList<>();
        userAddressList.add(userAddress);
        userAddressList.add(userAddress2);
        userAddressList.add(userAddress3);
        userAddressList.add(userAddress4);
        userAddressList.add(userAddress5);
        userAddressList.add(userAddress6);
        userAddressList.add(userAddress7);
        userAddressList.add(userAddress8);
        userAddressList.add(userAddress9);


        Mockito.when(userAddressAPI.getUserAddressDetails()).thenReturn(userAddressList);
        Mockito.when(userService.findById(1L)).thenReturn(Optional.empty());
        //when
        Optional<UserAddressTotal> userAddressTotal = userAddressServiceBasic.findUserAddress(1L);
        //then
        assertFalse(userAddressTotal.isPresent());

    }
}