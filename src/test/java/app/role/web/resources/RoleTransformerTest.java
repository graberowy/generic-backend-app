package app.role.web.resources;

import app.organisation.repository.Organisation;
import app.organisation.web.resources.OrganisationDTO;
import app.role.repository.Permission;
import app.role.repository.Role;
import app.user.repository.User;
import app.user.web.resources.UserDTO;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static app.role.repository.Permission.USER_WRITE;
import static org.junit.jupiter.api.Assertions.*;

class RoleTransformerTest {


    private RoleMapper roleMapper = new RoleMapperImpl();

    @Test
    void when_use_getDTO_on_role_should_returned_roleDTO() {
        //given
        Organisation organisation = new Organisation();
        organisation.setId(1L);
        organisation.setName("Some organisation");

        Organisation secondOrganisation = new Organisation();
        secondOrganisation.setId(2L);
        secondOrganisation.setName("Other organisation");

        Set<Permission> permissionSet = new HashSet<>();
        permissionSet.add(Permission.ROLE_READ);
        permissionSet.add(USER_WRITE);

        User user = new User();
        user.setId(1L);
        user.setUsername("Jan Kowalski");
        user.setEmail("some@wp.pl");
        user.setOrganisation(organisation);

        User secondUser = new User();
        secondUser.setId(2L);
        secondUser.setUsername("Sebastian Kowalski");
        secondUser.setEmail("other@wp.pl");
        secondUser.setOrganisation(secondOrganisation);

        Set<User> users = new HashSet<>();
        users.add(user);
        users.add(secondUser);

        Role role = new Role();
        role.setId(1L);
        role.setPermissions(permissionSet);
        role.setName("Some role");
        role.setUsers(users);

        //when
        RoleDTO roleDTO = roleMapper.mapToRoleDTO(role);

        //then
        assertEquals("Some role", roleDTO.getName());
        assertEquals(1L, roleDTO.getId());
        assertEquals(2, roleDTO.getUsers().size());
        assertEquals(2, roleDTO.getPermissions().size());

        assertTrue(roleDTO.getPermissions().stream().anyMatch(permission -> permission.equals(Permission.ROLE_READ)));
        assertTrue(roleDTO.getPermissions().stream().anyMatch(permission -> permission.equals(Permission.USER_WRITE)));

        roleDTO.getUsers().forEach(userDTO -> {
            if(userDTO.getId() == 1L) {
                assertEquals("Jan Kowalski", userDTO.getUsername());
                assertEquals("some@wp.pl", userDTO.getEmail());
                assertNull(userDTO.getRoles());
                assertNull(userDTO.getOrganisation());

            } else if(userDTO.getId() == 2L) {
                assertEquals("Sebastian Kowalski", userDTO.getUsername());
                assertEquals("other@wp.pl", userDTO.getEmail());
                assertNull(userDTO.getRoles());
                assertNull(userDTO.getOrganisation());

            }
        });
    }

    @Test
    void when_use_getModel_on_roleDTO_should_returned_role() {
        //given
        OrganisationDTO organisationDTO = new OrganisationDTO();
        organisationDTO.setId(1L);
        organisationDTO.setName("Some organisation");

        OrganisationDTO secondOrganisationDTO = new OrganisationDTO();
        secondOrganisationDTO.setId(2L);
        secondOrganisationDTO.setName("Other organisation");

        Set<Permission> permissionSet = new HashSet<>();
        permissionSet.add(Permission.ROLE_READ);
        permissionSet.add(USER_WRITE);

        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setUsername("Jan Kowalski");
        userDTO.setEmail("some@wp.pl");
        userDTO.setOrganisation(organisationDTO);

        UserDTO secondUserDTO = new UserDTO();
        secondUserDTO.setId(2L);
        secondUserDTO.setUsername("Sebastian Kowalski");
        secondUserDTO.setEmail("other@wp.pl");
        secondUserDTO.setOrganisation(secondOrganisationDTO);

        Set<UserDTO> usersDTOS = new HashSet<>();
        usersDTOS.add(userDTO);
        usersDTOS.add(secondUserDTO);

        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(1L);
        roleDTO.setPermissions(permissionSet);
        roleDTO.setName("Some role");
        roleDTO.setUsers(usersDTOS);

        //when
        Role role = roleMapper.mapToRole(roleDTO);

        //then
        assertEquals("Some role", role.getName());
        assertEquals(1L, role.getId());
        assertEquals(2, role.getUsers().size());
        assertEquals(2, role.getPermissions().size());

        assertTrue(role.getPermissions().stream().anyMatch(permission -> permission.equals(Permission.ROLE_READ)));
        assertTrue(role.getPermissions().stream().anyMatch(permission -> permission.equals(Permission.USER_WRITE)));

        role.getUsers().forEach(user -> {
            if(user.getId() == 1L) {
                assertEquals("Jan Kowalski", user.getUsername());
                assertEquals("some@wp.pl", user.getEmail());
                assertTrue(user.getRoles().isEmpty());
                assertNull(user.getOrganisation());

            } else if(user.getId() == 2L) {
                assertEquals("Sebastian Kowalski", user.getUsername());
                assertEquals("other@wp.pl", user.getEmail());
                assertTrue(user.getRoles().isEmpty());
                assertNull(user.getOrganisation());

            }
        });
    }

}