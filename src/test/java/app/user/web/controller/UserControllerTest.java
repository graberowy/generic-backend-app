package app.user.web.controller;

import app.organisation.repository.Organisation;
import app.role.repository.Role;
import app.user.repository.User;
import app.user.service.UserServiceBasic;
import app.user.web.resources.UserMapperImpl;
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

import java.util.*;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
@ContextConfiguration(classes = {UserMapperImpl.class, UserController.class})
class UserControllerTest {

    @MockBean
    private UserServiceBasic userServiceBasic;

    @Autowired
    MockMvc mockMvc;

    @Test
    void when_send_get_request_by_id_with_existing_user_then_user_should_be_returned() throws Exception {
        //given
        Organisation organisation = new Organisation();
        organisation.setId(1L);
        organisation.setName("Some organisation");

        Role role = new Role();
        role.setId(1L);
        role.setName("New role");

        Role secondRole = new Role();
        secondRole.setId(2L);
        secondRole.setName("Second role");

        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);
        roleSet.add(secondRole);


        User user = new User();
        user.setUsername("Jan Kowalski");
        user.setEmail("Jan@wp.pl");
        user.setId(1L);
        user.setOrganisation(organisation);
        user.setRoles(roleSet);

        Mockito.when(userServiceBasic.findById(1L)).thenReturn(Optional.of(user));
        //when
        //then
        mockMvc.perform(get("/api/v1/users/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.username", equalTo("Jan Kowalski")))
                .andExpect(jsonPath("$.email", equalTo("Jan@wp.pl")))
                .andExpect(jsonPath("$.organisation.id", equalTo(1)))
                .andExpect(jsonPath("$.organisation.name", equalTo("Some organisation")))
                .andExpect(jsonPath("$.roles", hasSize(2)))
                .andExpect(jsonPath("$.roles[?(@.id == 1)].id", equalTo(List.of(1))))
                .andExpect(jsonPath("$.roles[?(@.id == 1)].name", equalTo(List.of("New role"))))
                .andExpect(jsonPath("$.roles[0].permissions").doesNotExist())
                .andExpect(jsonPath("$.roles[0].users").doesNotExist())
                .andExpect(jsonPath("$.roles[?(@.id == 2)].id", equalTo(List.of(2))))
                .andExpect(jsonPath("$.roles[?(@.id == 2)].name", equalTo(List.of("Second role"))))
                .andExpect(jsonPath("$.roles[1].permissions").doesNotExist())
                .andExpect(jsonPath("$.roles[1].users").doesNotExist());
    }

    @Test
    void when_send_get_request_by_username_with_existing_user_then_user_should_be_returned() throws Exception {
        //given
        Organisation organisation = new Organisation();
        organisation.setId(1L);
        organisation.setName("Some organisation");

        Role role = new Role();
        role.setId(1L);
        role.setName("New role");

        Role secondRole = new Role();
        secondRole.setId(2L);
        secondRole.setName("Second role");

        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);
        roleSet.add(secondRole);


        User user = new User();
        user.setUsername("Jan Kowalski");
        user.setEmail("Jan@wp.pl");
        user.setId(1L);
        user.setOrganisation(organisation);
        user.setRoles(roleSet);

        Mockito.when(userServiceBasic.findByUsername("Jan Kowalski")).thenReturn(Optional.of(user));
        //when
        //then
        mockMvc.perform(get("/api/v1/users/username/Jan Kowalski").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.username", equalTo("Jan Kowalski")))
                .andExpect(jsonPath("$.email", equalTo("Jan@wp.pl")))
                .andExpect(jsonPath("$.organisation.id", equalTo(1)))
                .andExpect(jsonPath("$.organisation.name", equalTo("Some organisation")))
                .andExpect(jsonPath("$.roles", hasSize(2)))
                .andExpect(jsonPath("$.roles[?(@.id == 1)].id", equalTo(List.of(1))))
                .andExpect(jsonPath("$.roles[?(@.id == 1)].name", equalTo(List.of("New role"))))
                .andExpect(jsonPath("$.roles[0].permissions").doesNotExist())
                .andExpect(jsonPath("$.roles[0].users").doesNotExist())
                .andExpect(jsonPath("$.roles[?(@.id == 2)].id", equalTo(List.of(2))))
                .andExpect(jsonPath("$.roles[?(@.id == 2)].name", equalTo(List.of("Second role"))))
                .andExpect(jsonPath("$.roles[1].permissions").doesNotExist())
                .andExpect(jsonPath("$.roles[1].users").doesNotExist());
    }

    @Test
    void when_send_get_request_by_id_which_is_not_exist_then_not_found_error_should_be_returned() throws Exception {
        //given
        Mockito.when(userServiceBasic.findById(1L)).thenReturn(Optional.empty());
        //when
        //then
        mockMvc.perform(get("/api/v1/users/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    void when_send_get_request_by_username_which_is_not_exist_then_not_found_error_should_be_returned() throws Exception {
        //given
        Mockito.when(userServiceBasic.findByUsername("Jan Kowalski")).thenReturn(Optional.empty());
        //when
        //then
        mockMvc.perform(get("/api/v1/users/username/Jan Kowalski").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    void when_send_post_request_which_has_no_existing_user_then_user_should_be_returned_as_response() throws Exception {
        //given
        Organisation organisation = new Organisation();
        organisation.setId(1L);
        organisation.setName("Some organisation");

        Role role = new Role();
        role.setId(1L);
        role.setName("New role");

        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);

        User user = new User();
        user.setUsername("Jan Kowalski");
        user.setEmail("Jan@wp.pl");
        user.setId(1L);
        user.setOrganisation(organisation);
        user.setRoles(roleSet);

        Mockito.when(userServiceBasic.save(any())).thenReturn(user);
        //when
        //then
        mockMvc.perform(post("/api/v1/users/")
                        .content("{\n" +
                                "  \"id\": 1,\n" +
                                "  \"username\": \"Jan Kowalski\",\n" +
                                "  \"email\": \"Jan@wp.pl\",\n" +
                                "  \"organisation\": { \"id\": 1, \"name\": \"Some organisation\", \"users\": [] },\n" +
                                "  \"roles\": [{ \"id\": 1, \"name\": \"New role\", \"users\": [] }]\n" +
                                "}").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.username", equalTo("Jan Kowalski")))
                .andExpect(jsonPath("$.email", equalTo("Jan@wp.pl")))
                .andExpect(jsonPath("$.organisation.id", equalTo(1)))
                .andExpect(jsonPath("$.organisation.name", equalTo("Some organisation")))
                .andExpect(jsonPath("$.organisation.users").doesNotExist())
                .andExpect(jsonPath("$.roles", hasSize(1)))
                .andExpect(jsonPath("$.roles[0].id", equalTo(1)))
                .andExpect(jsonPath("$.roles[0].name", equalTo("New role")))
                .andExpect(jsonPath("$.roles[0].permissions").doesNotExist())
                .andExpect(jsonPath("$.roles[0].users").doesNotExist());
    }

    @Test
    void when_send_user_get_request_then_list_users_should_be_returned() throws Exception {
        //given
        Organisation organisation = new Organisation();
        organisation.setId(1L);
        organisation.setName("Some organisation");

        Role role = new Role();
        role.setId(1L);
        role.setName("New role");

        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);

        User user = new User();
        user.setUsername("Jan Kowalski");
        user.setEmail("Jan@wp.pl");
        user.setId(1L);
        user.setOrganisation(organisation);
        user.setRoles(roleSet);

        User secondUser = new User();
        secondUser.setId(2L);
        secondUser.setUsername("Sebastian Kowalski");
        secondUser.setEmail("Seba@wp.pl");
        secondUser.setOrganisation(organisation);

        Mockito.when(userServiceBasic.findAll()).thenReturn(Arrays.asList(user, secondUser));
        //when
        //then
        mockMvc.perform(get("/api/v1/users/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", equalTo(1)))
                .andExpect(jsonPath("$[1].id", equalTo(2)))
                .andExpect(jsonPath("$[0].username", equalTo("Jan Kowalski")))
                .andExpect(jsonPath("$[1].username", equalTo("Sebastian Kowalski")))
                .andExpect(jsonPath("$[0].email", equalTo("Jan@wp.pl")))
                .andExpect(jsonPath("$[1].email", equalTo("Seba@wp.pl")))
                .andExpect(jsonPath("$[0].roles", hasSize(1)))
                .andExpect(jsonPath("$[0].roles[0].id", equalTo(1)))
                .andExpect(jsonPath("$[0].roles[0].name", equalTo("New role")))
                .andExpect(jsonPath("$[0].roles[0].permissions").doesNotExist())
                .andExpect(jsonPath("$[0].roles[0].users").doesNotExist())
                .andExpect(jsonPath("$[1].roles", hasSize(0)))
                .andExpect(jsonPath("$[0].organisation.id", equalTo(1)))
                .andExpect(jsonPath("$[0].organisation.name", equalTo("Some organisation")))
                .andExpect(jsonPath("$[0].organisation.users").doesNotExist())
                .andExpect(jsonPath("$[1].organisation.id", equalTo(1)))
                .andExpect(jsonPath("$[1].organisation.name", equalTo("Some organisation")))
                .andExpect(jsonPath("$[1].organisation.users").doesNotExist());

    }

    @Test
    void when_send_delete_request_which_existing_id_then_record_should_be_removed() throws Exception {
        //given
        Organisation organisation = new Organisation();
        organisation.setId(1L);
        organisation.setName("Some organisation");

        User user = new User();
        user.setUsername("Jan Kowalski");
        user.setEmail("Jan@wp.pl");
        user.setId(1L);
        user.setOrganisation(organisation);

        Mockito.when(userServiceBasic.findById(1L)).thenReturn(Optional.of(user));

        //when
        userServiceBasic.delete(1L);

        //then
        mockMvc.perform(delete("/api/v1/users/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
    }

    @Test
    void when_send_delete_request_which_is_not_exist_id_then_not_found_error_should_be_returned() throws Exception {
        //given
        //when
        userServiceBasic.delete(1L);

        //then
        mockMvc.perform(delete("/api/v1/users/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    void when_send_put_request_which_existing_id_then_data_should_be_updated() throws Exception {
        //given
        Organisation organisation = new Organisation();
        organisation.setId(1L);
        organisation.setName("Some organisation");

        Organisation secondOrganisation = new Organisation();
        secondOrganisation.setId(1L);
        secondOrganisation.setName("Updated organisation");

        Role role = new Role();
        role.setId(1L);
        role.setName("New role");

        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);

        User user = new User();
        user.setUsername("Jan Kowalski");
        user.setEmail("Jan@wp.pl");
        user.setId(1L);
        user.setOrganisation(organisation);
        user.setRoles(roleSet);

        User secondUser = new User();
        secondUser.setId(1L);
        secondUser.setUsername("Sebastian Kowalski");
        secondUser.setEmail("Seba@wp.pl");
        secondUser.setOrganisation(secondOrganisation);
        secondUser.setRoles(roleSet);

        Mockito.when(userServiceBasic.update(any())).thenReturn(Optional.of(secondUser));
        //when
        //then
        mockMvc.perform(put("/api/v1/users/1")
                        .content("{\n" +
                                "  \"id\": 1,\n" +
                                "  \"username\": \"Sebastian Kowalski\",\n" +
                                "  \"email\": \"Seba@wp.pl\",\n" +
                                "  \"organisation\": { \"id\": 1, \"name\": \"Updated organisation\", \"users\": [] },\n" +
                                "  \"roles\": [{ \"id\": 1, \"name\": \"New role\", \"users\": [] }]\n" +
                                "}").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.username", equalTo("Sebastian Kowalski")))
                .andExpect(jsonPath("$.email", equalTo("Seba@wp.pl")))
                .andExpect(jsonPath("$.organisation.id", equalTo(1)))
                .andExpect(jsonPath("$.organisation.name", equalTo("Updated organisation")))
                .andExpect(jsonPath("$.organisation.users").doesNotExist())
                .andExpect(jsonPath("$.roles", hasSize(1)))
                .andExpect(jsonPath("$.roles[0].id", equalTo(1)))
                .andExpect(jsonPath("$.roles[0].name", equalTo("New role")))
                .andExpect(jsonPath("$.roles[0].permissions").doesNotExist())
                .andExpect(jsonPath("$.roles[0].users").doesNotExist());
    }

    @Test
    void when_send_put_request_which_not_existing_id_then_not_found_error_should_be_returned() throws Exception {
        //given
        Organisation organisation = new Organisation();
        organisation.setId(1L);
        organisation.setName("Some organisation");

        Organisation secondOrganisation = new Organisation();
        secondOrganisation.setId(1L);
        secondOrganisation.setName("Updated organisation");

        Role role = new Role();
        role.setId(1L);
        role.setName("New role");

        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);

        User user = new User();
        user.setUsername("Jan Kowalski");
        user.setEmail("Jan@wp.pl");
        user.setId(1L);
        user.setOrganisation(organisation);
        user.setRoles(roleSet);

        User secondUser = new User();
        secondUser.setId(2L);
        secondUser.setUsername("Sebastian Kowalski");
        secondUser.setEmail("Seba@wp.pl");
        secondUser.setOrganisation(secondOrganisation);
        secondUser.setRoles(roleSet);

        Mockito.when(userServiceBasic.update(secondUser)).thenReturn(Optional.empty());
        //when
        //then
        mockMvc.perform(put("/api/v1/users/1")
                .content("{\n" +
                        "  \"id\": 2,\n" +
                        "  \"username\": \"Sebastian Kowalski\",\n" +
                        "  \"email\": \"Seba@wp.pl\",\n" +
                        "  \"organisation\": { \"id\": 1, \"name\": \"Updated organisation\", \"users\": [] },\n" +
                        "  \"roles\": [{ \"id\": 1, \"name\": \"New role\", \"users\": [] }]\n" +
                        "}").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }



}