package app.organisation.web.controller;

import app.organisation.repository.Organisation;
import app.organisation.service.OrganisationCRUD;
import app.organisation.web.resources.OrganisationMapperImpl;
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

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(OrganisationController.class)
@ContextConfiguration(classes = {OrganisationMapperImpl.class, OrganisationController.class})
class OrganisationControllerTest {


    @MockBean
    private OrganisationCRUD organisationCRUD;

    @Autowired
    MockMvc mockMvc;

    @Test
    void when_send_get_request_by_id_with_existing_organisation_then_organisation_should_be_returned() throws Exception {
        //given
        User user = new User();
        user.setUsername("Jan Kowalski");
        user.setEmail("Jan@wp.pl");
        user.setId(1L);

        Set<User> userSet = new HashSet<>();
        userSet.add(user);

        Organisation organisation = new Organisation();
        organisation.setId(1L);
        organisation.setName("Some organisation");
        organisation.setUsers(userSet);

        Mockito.when(organisationCRUD.findById(1L)).thenReturn(Optional.of(organisation));
        //when
        //then
        mockMvc.perform(get("/api/v1/organisations/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.name", equalTo("Some organisation")))
                .andExpect(jsonPath("$.users", hasSize(1)))
                .andExpect(jsonPath("$.users[0].id", equalTo(1)))
                .andExpect(jsonPath("$.users[0].username", equalTo("Jan Kowalski")))
                .andExpect(jsonPath("$.users[0].email", equalTo("Jan@wp.pl")))
                .andExpect(jsonPath("$.users[0].roles").doesNotExist())
                .andExpect(jsonPath("$.users[0].organisation").doesNotExist());
    }

    @Test
    void when_send_get_request_by_id_which_is_not_exist_then_not_found_error_should_be_returned() throws Exception {
        //given
        Mockito.when(organisationCRUD.findById(1L)).thenReturn(Optional.empty());
        //when
        //then
        mockMvc.perform(get("/api/v1/organisations/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn();

    }

    @Test
    void when_send_post_request_which_has_no_existing_organisation_then_organisation_should_be_returned_as_response() throws Exception {
        //given
        User user = new User();
        user.setUsername("Jan Kowalski");
        user.setEmail("Jan@wp.pl");
        user.setId(1L);

        Set<User> userSet = new HashSet<>();
        userSet.add(user);

        Organisation organisation = new Organisation();
        organisation.setId(1L);
        organisation.setName("Some organisation");
        organisation.setUsers(userSet);

        Mockito.when(organisationCRUD.save(any())).thenReturn(organisation);
        //when
        //then
        mockMvc.perform(post("/api/v1/organisations/")
                        .content("{\n" +
                                "  \"id\": 1,\n" +
                                "  \"name\": \"Some organisation\",\n" +
                                "  \"users\": [{ \"id\": 1, \"email\": \"Jan@wp.pl\", \"username\": \"Jan Kowalski\", \"roles\": [] }]\n" +
                                "}").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.name", equalTo("Some organisation")))
                .andExpect(jsonPath("$.users", hasSize(1)))
                .andExpect(jsonPath("$.users[0].id", equalTo(1)))
                .andExpect(jsonPath("$.users[0].username", equalTo("Jan Kowalski")))
                .andExpect(jsonPath("$.users[0].email", equalTo("Jan@wp.pl")))
                .andExpect(jsonPath("$.users[0].roles").doesNotExist())
                .andExpect(jsonPath("$.users[0].organisation").doesNotExist());
    }

    @Test
    void when_send_organisation_get_request_then_list_organisations_should_be_returned() throws Exception {
        //given
        User user = new User();
        user.setUsername("Jan Kowalski");
        user.setEmail("Jan@wp.pl");
        user.setId(1L);

        Set<User> userSet = new HashSet<>();
        userSet.add(user);

        Organisation organisation = new Organisation();
        organisation.setId(1L);
        organisation.setName("Some organisation");
        organisation.setUsers(userSet);

        Organisation secondOrganisation = new Organisation();
        secondOrganisation.setId(2L);
        secondOrganisation.setName("Second organisation");

        Mockito.when(organisationCRUD.findAll()).thenReturn(Arrays.asList(organisation, secondOrganisation));

        //when
        //then
        mockMvc.perform(get("/api/v1/organisations/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", equalTo(1)))
                .andExpect(jsonPath("$[1].id", equalTo(2)))
                .andExpect(jsonPath("$[0].name", equalTo("Some organisation")))
                .andExpect(jsonPath("$[1].name", equalTo("Second organisation")))
                .andExpect(jsonPath("$[0].users", hasSize(1)))
                .andExpect(jsonPath("$[1].users", hasSize(0)))
                .andExpect(jsonPath("$[0].users[0].id", equalTo(1)))
                .andExpect(jsonPath("$[0].users[0].username", equalTo("Jan Kowalski")))
                .andExpect(jsonPath("$[0].users[0].email", equalTo("Jan@wp.pl")))
                .andExpect(jsonPath("$[0].users[0].organisation").doesNotExist())
                .andExpect(jsonPath("$[0].users[0].roles").doesNotExist());

    }

    @Test
    void when_send_delete_request_which_existing_id_then_record_should_be_removed()  throws Exception {
        //given
        Organisation organisation = new Organisation();
        organisation.setId(1L);
        organisation.setName("Some organisation");

        Mockito.when(organisationCRUD.findById(1L)).thenReturn(Optional.of(organisation));

        //when
        organisationCRUD.delete(1L);

        //then
        mockMvc.perform(delete("/api/v1/organisations/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
    }

    @Test
    void when_send_delete_request_which_is_not_exist_id_then_not_found_error_should_be_returned()  throws Exception {
        //given
        //when
        organisationCRUD.delete(1L);

        //then
        mockMvc.perform(delete("/api/v1/organisations/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    void when_send_put_request_which_existing_id_then_data_should_be_updated() throws Exception {
        //given
        User user = new User();
        user.setUsername("Jan Kowalski");
        user.setEmail("Jan@wp.pl");
        user.setId(1L);

        User updatedUser = new User();
        updatedUser.setUsername("Sebastian Kowalski");
        updatedUser.setEmail("Sebastian@wp.pl");
        updatedUser.setId(1L);

        Set<User> userSet = new HashSet<>();
        userSet.add(user);

        Set<User> updatedUserSet = new HashSet<>();
        updatedUserSet.add(updatedUser);

        Organisation organisation = new Organisation();
        organisation.setId(1L);
        organisation.setName("Some organisation");
        organisation.setUsers(userSet);

        Organisation updateOrganisation = new Organisation();
        updateOrganisation.setId(1L);
        updateOrganisation.setName("updated organisation!");
        updateOrganisation.setUsers(updatedUserSet);

        Mockito.when(organisationCRUD.update(any())).thenReturn(Optional.of(updateOrganisation));
        //when
        //then
        mockMvc.perform(put("/api/v1/organisations/1")
                        .content("{\n" +
                                "  \"id\": 1,\n" +
                                "  \"name\": \"updated organisation!\",\n" +
                                "  \"users\": [{ \"id\": 1, \"email\": \"Sebastian@wp.pl\", \"username\": \"Sebastian Kowalski\", \"roles\": [] }]\n" +
                                "}").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.name", equalTo("updated organisation!")))
                .andExpect(jsonPath("$.users", hasSize(1)))
                .andExpect(jsonPath("$.users[0].id", equalTo(1)))
                .andExpect(jsonPath("$.users[0].username", equalTo("Sebastian Kowalski")))
                .andExpect(jsonPath("$.users[0].email", equalTo("Sebastian@wp.pl")))
                .andExpect(jsonPath("$.users[0].organisation").doesNotExist())
                .andExpect(jsonPath("$.users[0].roles").doesNotExist());
    }

    @Test
    void when_send_put_request_which_not_existing_id_then_not_found_error_should_be_returned() throws Exception {
        //given
        User user = new User();
        user.setUsername("Jan Kowalski");
        user.setEmail("Jan@wp.pl");
        user.setId(1L);

        User updatedUser = new User();
        updatedUser.setUsername("Sebastian Kowalski");
        updatedUser.setEmail("Sebastian@wp.pl");
        updatedUser.setId(1L);

        Set<User> userSet = new HashSet<>();
        userSet.add(user);

        Set<User> updatedUserSet = new HashSet<>();
        updatedUserSet.add(updatedUser);

        Organisation organisation = new Organisation();
        organisation.setId(1L);
        organisation.setName("Some organisation");
        organisation.setUsers(userSet);

        Organisation updateOrganisation = new Organisation();
        updateOrganisation.setId(2L);
        updateOrganisation.setName("updated organisation!");
        updateOrganisation.setUsers(updatedUserSet);

        Mockito.when(organisationCRUD.update(updateOrganisation)).thenReturn(Optional.empty());
        //when
        //then
        mockMvc.perform(put("/api/v1/organisations/2")
                .content("{\n" +
                        "  \"id\": 2,\n" +
                        "  \"name\": \"updated organisation!\",\n" +
                        "  \"users\": [{ \"id\": 1, \"email\": \"Sebastian@wp.pl\", \"username\": \"Sebastian Kowalski\", \"roles\": [] }]\n" +
                        "}").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }
}