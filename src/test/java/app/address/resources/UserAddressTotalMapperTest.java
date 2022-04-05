package app.address.resources;

import app.address.client.Address;
import app.address.model.UserAddressTotal;
import app.organisation.repository.Organisation;
import app.role.repository.Role;
import app.user.repository.User;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserAddressTotalMapperTest {

    private final UserAddressTotalMapper userAddressTotalMapper = new UserAddressTotalMapperImpl();

    @Test
    void when_use_mapToUserAddressTotalDTO_on_UserAddressTotal_should_return_UserAddressTotalDTO() {
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
        user.setEmail("some@wp.pl");
        user.setRoles(roles);
        user.setOrganisation(organisation);

        Address address = new Address();
        address.setStreet("test");
        address.setCode("00-000");

        UserAddressTotal userAddressTotal = new UserAddressTotal(user, address);
        //when
        UserAddressTotalDTO userAddressTotalDTO = userAddressTotalMapper.mapToUserAddressTotalDTO(userAddressTotal);
        //then
        assertEquals("Jan Kowalski", userAddressTotalDTO.getUsername());
        assertEquals("some@wp.pl", userAddressTotalDTO.getEmail());
        assertEquals("test", userAddressTotalDTO.getAddress().getStreet());
        assertEquals("00-000", userAddressTotalDTO.getAddress().getCode());
        assertEquals(1L, userAddressTotalDTO.getId());
        assertNull(userAddressTotalDTO.getOrganisation());
        assertNull(userAddressTotalDTO.getRoles());

    }

    @Test
    void when_use_mapToUserAddressTotal_on_UserAddressTotalDTO_should_return_UserAddressTotal() {
        //given
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setStreet("test");
        addressDTO.setCode("00-000");

        UserAddressTotalDTO userAddressTotalDTO = new UserAddressTotalDTO();
        userAddressTotalDTO.setUsername("Jan Kowalski");
        userAddressTotalDTO.setEmail("some@wp.pl");
        userAddressTotalDTO.setId(1L);
        userAddressTotalDTO.setAddress(addressDTO);
        //when
        UserAddressTotal userAddressTotal = userAddressTotalMapper.mapToUserAddressTotal(userAddressTotalDTO);
        //then
        assertEquals("Jan Kowalski", userAddressTotal.getUsername());
        assertEquals("some@wp.pl", userAddressTotal.getEmail());
        assertEquals("test", userAddressTotal.getAddress().getStreet());
        assertEquals("00-000", userAddressTotal.getAddress().getCode());
        assertEquals(1L, userAddressTotal.getId());
        assertNull(userAddressTotal.getOrganisation());
        assertTrue(userAddressTotal.getRoles().isEmpty());
    }

}