package app.organisation.web.resources;

import app.organisation.repository.Organisation;
import app.role.repository.Role;
import app.role.web.resources.RoleDTO;
import app.user.repository.User;

import app.user.web.resources.UserDTO;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class OrganisationTransformerTest {


    private OrganisationMapper organisationMapper = new OrganisationMapperImpl();


    @Test
    void when_use_getDTO_on_organisation_should_returned_organisationDTO() {
        //given
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

        User secondUser = new User();
        secondUser.setId(2L);
        secondUser.setUsername("Sebastian Kowalski");
        secondUser.setEmail("seb@wp.pl");
        secondUser.setRoles(roles);

        Set<User> users = new HashSet<>();
        users.add(user);
        users.add(secondUser);

        Organisation organisation = new Organisation();
        organisation.setId(1L);
        organisation.setName("Some organisation");
        organisation.setUsers(users);

        //when
        OrganisationDTO organisationDTO = organisationMapper.mapToOrganisationDTO(organisation);

        //then
        assertEquals("Some organisation", organisationDTO.getName());
        assertEquals(1L, organisationDTO.getId());
        assertEquals(2, organisationDTO.getUsers().size());

        organisationDTO.getUsers().forEach(userDTO -> {
            if (userDTO.getId() == 1L){
                assertEquals("Jan Kowalski", userDTO.getUsername());
                assertEquals("some@wp.pl", userDTO.getEmail());
                assertNull(userDTO.getRoles());
                assertNull(userDTO.getOrganisation());
            } else if (userDTO.getId() == 2L){
                assertEquals("Sebastian Kowalski", userDTO.getUsername());
                assertEquals("seb@wp.pl", userDTO.getEmail());
                assertNull(userDTO.getRoles());
                assertNull(userDTO.getOrganisation());
            }
        });
    }

    @Test
    void when_use_getModel_on_organisationDTO_should_returned_organisation() {
        //given
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(1L);
        roleDTO.setName("Some role");

        Set<RoleDTO> roleDTOS = new HashSet<>();
        roleDTOS.add(roleDTO);

        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setUsername("Jan Kowalski");
        userDTO.setEmail("some@wp.pl");
        userDTO.setRoles(roleDTOS);

        UserDTO secondUserDTO = new UserDTO();
        secondUserDTO.setId(2L);
        secondUserDTO.setUsername("Sebastian Kowalski");
        secondUserDTO.setEmail("seb@wp.pl");
        secondUserDTO.setRoles(roleDTOS);

        Set<UserDTO> usersDTOS = new HashSet<>();
        usersDTOS.add(userDTO);
        usersDTOS.add(secondUserDTO);

        OrganisationDTO organisationDTO = new OrganisationDTO();
        organisationDTO.setId(1L);
        organisationDTO.setName("Some organisation");
        organisationDTO.setUsers(usersDTOS);

        //when
        Organisation organisation = organisationMapper.mapToOrganisation(organisationDTO);

        //then
        assertEquals("Some organisation", organisation.getName());
        assertEquals(1L, organisation.getId());
        assertEquals(2, organisation.getUsers().size());

        organisation.getUsers().forEach(user -> {
            if (user.getId() == 1L){
                assertEquals("Jan Kowalski", user.getUsername());
                assertEquals("some@wp.pl", user.getEmail());
                assertTrue(user.getRoles().isEmpty());
                assertNull(user.getOrganisation());
            } else if (user.getId() == 2L){
                assertEquals("Sebastian Kowalski", user.getUsername());
                assertEquals("seb@wp.pl", user.getEmail());
                assertTrue(user.getRoles().isEmpty());
                assertNull(user.getOrganisation());
            }
        });
    }

}