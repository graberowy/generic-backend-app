package app.role.web.controller;

import app.role.repository.Permission;
import app.role.repository.Role;
import app.role.service.RoleCRUD;
import app.role.web.resources.RoleMapperImpl;
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

import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(RoleController.class)
@ContextConfiguration(classes = {RoleMapperImpl.class, RoleController.class})
class RoleControllerTest {

    @MockBean
    private RoleCRUD roleCRUD;

    @Autowired
    MockMvc mockMvc;

    @Test
    void when_send_get_request_by_id_with_existing_role_then_role_should_be_returned() throws Exception {
        //given
        Set<Permission> permissions = new HashSet<>();
        permissions.add(Permission.ORGANISATION_READ);
        permissions.add(Permission.USER_WRITE);



        User user = new User();
        user.setUsername("Jan Kowalski");
        user.setEmail("Jan@wp.pl");
        user.setId(1L);

        User secondUser = new User();
        secondUser.setId(2L);
        secondUser.setUsername("Sebastian Kowalski");
        secondUser.setEmail("Seba@wp.pl");

        Set<User> userSet = new HashSet<>();
        userSet.add(secondUser);
        userSet.add(user);


        Role role = new Role();
        role.setId(1L);
        role.setName("New role");
        role.setPermissions(permissions);
        role.setUsers(userSet);

        Mockito.when(roleCRUD.findById(1L)).thenReturn(Optional.of(role));
        //when
        //then
        mockMvc.perform(get("/api/v1/roles/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.name", equalTo("New role")))
                .andExpect(jsonPath("$.permissions", hasSize(2)))
                .andExpect(jsonPath("$.permissions[*]", containsInAnyOrder("ORGANISATION_READ", "USER_WRITE")))
                .andExpect(jsonPath("$.users", hasSize(2)))
                .andExpect(jsonPath("$.users[?(@.id == 1)].id", equalTo(List.of(1))))
                .andExpect(jsonPath("$.users[?(@.id == 1)].username", equalTo(List.of("Jan Kowalski"))))
                .andExpect(jsonPath("$.users[?(@.id == 1)].email", equalTo(List.of("Jan@wp.pl"))))
                .andExpect(jsonPath("$.users[0].organisation").doesNotExist())
                .andExpect(jsonPath("$.users[0].roles").doesNotExist())
                .andExpect(jsonPath("$.users[?(@.id == 2)].id", equalTo(List.of(2))))
                .andExpect(jsonPath("$.users[?(@.id == 2)].username", equalTo(List.of("Sebastian Kowalski"))))
                .andExpect(jsonPath("$.users[?(@.id == 2)].email", equalTo(List.of("Seba@wp.pl"))))
                .andExpect(jsonPath("$.users[1].organisation").doesNotExist())
                .andExpect(jsonPath("$.users[1].roles").doesNotExist());
    }

    @Test
    void when_send_get_request_by_id_which_is_not_exist_then_not_found_error_should_be_returned() throws Exception {
        //given
        Mockito.when(roleCRUD.findById(1L)).thenReturn(Optional.empty());
        //when
        //then
        mockMvc.perform(get("/api/v1/roles/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    void when_send_post_request_which_has_no_existing_role_then_role_should_be_returned_as_response() throws Exception {
        //given
        Set<Permission> permissions = new HashSet<>();
        permissions.add(Permission.ORGANISATION_READ);
        permissions.add(Permission.USER_WRITE);



        User user = new User();
        user.setUsername("Jan Kowalski");
        user.setEmail("Jan@wp.pl");
        user.setId(1L);

        Set<User> userSet = new HashSet<>();
        userSet.add(user);

        Role role = new Role();
        role.setId(1L);
        role.setName("New role");
        role.setPermissions(permissions);
        role.setUsers(userSet);

        Mockito.when(roleCRUD.save(any())).thenReturn(role);
        //when
        //then
        mockMvc.perform(post("/api/v1/roles/")
                .content("{\n" +
                        "  \"id\": 1,\n" +
                        "  \"name\": \"New role\",\n" +
                        "  \"permissions\": [\"ORGANISATION_READ\", \"USER_WRITE\"],\n" +
                        "  \"users\": [{ \"id\": 1, \"email\": \"Jan@wp.pl\", \"username\": \"Jan Kowalski\", \"roles\": [] }]\n" +
                        "}").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.name", equalTo("New role")))
                .andExpect(jsonPath("$.permissions", hasSize(2)))
                .andExpect(jsonPath("$.permissions[*]", containsInAnyOrder("ORGANISATION_READ", "USER_WRITE")))
                .andExpect(jsonPath("$.users", hasSize(1)))
                .andExpect(jsonPath("$.users[0].id", equalTo(1)))
                .andExpect(jsonPath("$.users[0].username", equalTo("Jan Kowalski")))
                .andExpect(jsonPath("$.users[0].email", equalTo("Jan@wp.pl")))
                .andExpect(jsonPath("$.users[0].organisation").doesNotExist())
                .andExpect(jsonPath("$.users[0].roles").doesNotExist());

    }

    @Test
    void when_send_role_get_request_then_list_roles_should_be_returned() throws Exception {
        //given
        Set<Permission> permissions = new HashSet<>();
        permissions.add(Permission.ORGANISATION_READ);
        permissions.add(Permission.USER_WRITE);



        User user = new User();
        user.setUsername("Jan Kowalski");
        user.setEmail("Jan@wp.pl");
        user.setId(1L);

        Set<User> userSet = new HashSet<>();
        userSet.add(user);

        Role role = new Role();
        role.setId(1L);
        role.setName("New role");
        role.setPermissions(permissions);
        role.setUsers(userSet);

        Role secondRole = new Role();
        secondRole.setId(2L);
        secondRole.setName("Second role");
        secondRole.setPermissions(permissions);
        secondRole.setUsers(userSet);

        Mockito.when(roleCRUD.findAll()).thenReturn(Arrays.asList(role, secondRole));
        //when
        //then
        mockMvc.perform(get("/api/v1/roles/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id", equalTo(1)))
                .andExpect(jsonPath("$[0].name", equalTo("New role")))
                .andExpect(jsonPath("$[0].permissions", hasSize(2)))
                .andExpect(jsonPath("$[0].permissions[*]", containsInAnyOrder("ORGANISATION_READ", "USER_WRITE")))
                .andExpect(jsonPath("$[0].users", hasSize(1)))
                .andExpect(jsonPath("$[0].users[0].id", equalTo(1)))
                .andExpect(jsonPath("$[0].users[0].username", equalTo("Jan Kowalski")))
                .andExpect(jsonPath("$[0].users[0].email", equalTo("Jan@wp.pl")))
                .andExpect(jsonPath("$[0].users[0].organisation").doesNotExist())
                .andExpect(jsonPath("$[0].users[0].roles").doesNotExist())
                .andExpect(jsonPath("$[1].id", equalTo(2)))
                .andExpect(jsonPath("$[1].name", equalTo("Second role")))
                .andExpect(jsonPath("$[1].permissions", hasSize(2)))
                .andExpect(jsonPath("$[1].permissions[*]", containsInAnyOrder("ORGANISATION_READ", "USER_WRITE")))
                .andExpect(jsonPath("$[1].users", hasSize(1)))
                .andExpect(jsonPath("$[1].users[0].id", equalTo(1)))
                .andExpect(jsonPath("$[1].users[0].username", equalTo("Jan Kowalski")))
                .andExpect(jsonPath("$[1].users[0].email", equalTo("Jan@wp.pl")))
                .andExpect(jsonPath("$[1].users[0].organisation").doesNotExist())
                .andExpect(jsonPath("$[1].users[0].roles").doesNotExist());
    }

    @Test
    void when_send_delete_request_which_existing_id_then_record_should_be_removed() throws Exception {
        //given
        Set<Permission> permissions = new HashSet<>();
        permissions.add(Permission.ORGANISATION_READ);
        permissions.add(Permission.USER_WRITE);

        User user = new User();
        user.setUsername("Jan Kowalski");
        user.setEmail("Jan@wp.pl");
        user.setId(1L);

        Set<User> userSet = new HashSet<>();
        userSet.add(user);

        Role role = new Role();
        role.setId(1L);
        role.setName("New role");
        role.setPermissions(permissions);
        role.setUsers(userSet);

        Mockito.when(roleCRUD.findById(1L)).thenReturn(Optional.of(role));

        //when
        roleCRUD.delete(1L);

        //then
        mockMvc.perform(delete("/api/v1/roles/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
    }

    @Test
    void when_send_delete_request_which_is_not_exist_id_then_not_found_error_should_be_returned() throws Exception {
        //given
        //when
        roleCRUD.delete(1L);

        //then
        mockMvc.perform(delete("/api/v1/roles/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    void when_send_put_request_which_existing_id_then_data_should_be_updated() throws Exception {
        //given
        Set<Permission> permissions = new HashSet<>();
        permissions.add(Permission.ORGANISATION_READ);
        permissions.add(Permission.USER_WRITE);

        Set<Permission> newPermissions = new HashSet<>();
        newPermissions.add(Permission.ORGANISATION_WRITE);
        newPermissions.add(Permission.ROLE_READ);



        User user = new User();
        user.setUsername("Jan Kowalski");
        user.setEmail("Jan@wp.pl");
        user.setId(1L);

        Set<User> userSet = new HashSet<>();
        userSet.add(user);

        Role role = new Role();
        role.setId(1L);
        role.setName("New role");
        role.setPermissions(permissions);
        role.setUsers(userSet);

        Role secondRole = new Role();
        secondRole.setId(1L);
        secondRole.setName("Second role");
        secondRole.setPermissions(newPermissions);
        secondRole.setUsers(userSet);

        Mockito.when(roleCRUD.update(any())).thenReturn(Optional.of(secondRole));
        //when
        //then

        mockMvc.perform(put("/api/v1/roles/1")
                        .content("{\n" +
                                "  \"id\": 1,\n" +
                                "  \"name\": \"Second role\",\n" +
                                "  \"permissions\": [\"ORGANISATION_WRITE\", \"ROLE_READ\"],\n" +
                                "  \"users\": [{ \"id\": 1, \"email\": \"Jan@wp.pl\", \"username\": \"Jan Kowalski\", \"roles\": [] }]\n" +
                                "}").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.name", equalTo("Second role")))
                .andExpect(jsonPath("$.permissions", hasSize(2)))
                .andExpect(jsonPath("$.permissions[*]", containsInAnyOrder("ORGANISATION_WRITE", "ROLE_READ")))
                .andExpect(jsonPath("$.users", hasSize(1)))
                .andExpect(jsonPath("$.users[0].id", equalTo(1)))
                .andExpect(jsonPath("$.users[0].username", equalTo("Jan Kowalski")))
                .andExpect(jsonPath("$.users[0].email", equalTo("Jan@wp.pl")))
                .andExpect(jsonPath("$.users[0].organisation").doesNotExist())
                .andExpect(jsonPath("$.users[0].roles").doesNotExist());
    }

    @Test
    void when_send_put_request_which_not_existing_id_then_not_found_error_should_be_returned() throws Exception {
        //given
        Set<Permission> permissions = new HashSet<>();
        permissions.add(Permission.ORGANISATION_READ);
        permissions.add(Permission.USER_WRITE);

        Set<Permission> newPermissions = new HashSet<>();
        newPermissions.add(Permission.ROLE_READ);
        newPermissions.add(Permission.ORGANISATION_WRITE);


        User user = new User();
        user.setUsername("Jan Kowalski");
        user.setEmail("Jan@wp.pl");
        user.setId(1L);

        Set<User> userSet = new HashSet<>();
        userSet.add(user);

        Role role = new Role();
        role.setId(1L);
        role.setName("New role");
        role.setPermissions(permissions);
        role.setUsers(userSet);

        Role secondRole = new Role();
        secondRole.setId(2L);
        secondRole.setName("Second role");
        secondRole.setPermissions(newPermissions);
        secondRole.setUsers(userSet);

        Mockito.when(roleCRUD.update(secondRole)).thenReturn(Optional.empty());
        //when
        //then

        mockMvc.perform(put("/api/v1/roles/1")
                .content("{\n" +
                        "  \"id\": 2,\n" +
                        "  \"name\": \"Second role\",\n" +
                        "  \"permissions\": [\"ORGANISATION_WRITE\", \"ROLE_READ\"],\n" +
                        "  \"users\": [{ \"id\": 1, \"email\": \"Jan@wp.pl\", \"username\": \"Jan Kowalski\", \"roles\": [] }]\n" +
                        "}").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn();

    }



}