package app.address.web.controller;

import app.address.client.Address;
import app.address.model.UserAddressTotal;
import app.address.resources.UserAddressTotalMapperImpl;
import app.address.service.UserAddressServiceBasic;
import app.organisation.repository.Organisation;
import app.role.repository.Role;
import app.user.repository.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserAddressTotalController.class)
@ContextConfiguration(classes = {UserAddressTotalMapperImpl.class, UserAddressTotalController.class})
class UserAddressTotalControllerTest {

    @MockBean
    private UserAddressServiceBasic userAddressServiceBasic;

    @Autowired
    MockMvc mockMvc;

    @Test
    void when_get_by_id_request_with_existing_id_and_address_then_userAddressTotalDTO_should_be_returned() throws Exception {
        //given
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

        Address address = new Address();
        address.setStreet("Adamsa 1");
        address.setCode("00-001");

        UserAddressTotal userAddressTotal = new UserAddressTotal(user, address);

        Mockito.when(userAddressServiceBasic.findUserAddress(1L)).thenReturn(Optional.of(userAddressTotal));
        //when
        //then
        mockMvc.perform(get("/api/v1/user/address/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.username", equalTo("Adam Kowalski")))
                .andExpect(jsonPath("$.email", equalTo("adam.kowalski@gmail.com")))
                .andExpect(jsonPath("$.address.street", equalTo("Adamsa 1")))
                .andExpect(jsonPath("$.address.code", equalTo("00-001")))
                .andExpect(jsonPath("$.organisation").doesNotExist())
                .andExpect(jsonPath("$.roles").doesNotExist());

    }

    @Test
    void when_get_by_id_request_with_existing_id_and_no_address_then_userAddressTotalDTO_no_address_should_be_returned() throws Exception {
        //given
        UserAddressTotal userAddressTotal = new UserAddressTotal();
        userAddressTotal.setUsername("Jan Kowalski");
        userAddressTotal.setEmail("janek.kowalski@gmail.com");
        userAddressTotal.setId(1L);


        Mockito.when(userAddressServiceBasic.findUserAddress(1L)).thenReturn(Optional.of(userAddressTotal));
        //when
        //then
        mockMvc.perform(get("/api/v1/user/address/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.username", equalTo("Jan Kowalski")))
                .andExpect(jsonPath("$.email", equalTo("janek.kowalski@gmail.com")))
                .andExpect(jsonPath("$.address").doesNotExist())
                .andExpect(jsonPath("$.organisation").doesNotExist())
                .andExpect(jsonPath("$.roles").doesNotExist());

    }

    @Test
    void when_get_by_id_request_with_no_existing_id_then_not_found_should_be_returned() throws Exception {
        //given
         Mockito.when(userAddressServiceBasic.findUserAddress(2L)).thenReturn(Optional.empty());
        //when
        //then
        mockMvc.perform(get("/api/v1/user/address/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn();

    }
}