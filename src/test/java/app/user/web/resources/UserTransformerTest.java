package app.user.web.resources;

import app.organisation.repository.Organisation;
import app.organisation.web.resources.OrganisationDTO;
import app.role.repository.Permission;
import app.role.repository.Role;
import app.role.web.resources.RoleDTO;
import app.user.repository.User;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserTransformerTest {

    private UserMapper userMapper = new UserMapperImpl();


    @Test
    void when_use_getDTO_on_user_should_returned_userDTO() {
        //given
        Organisation organisation = new Organisation();
        organisation.setId(1L);
        organisation.setName("Some organisation");

        Set<Permission> permissionSet = new HashSet<>();
        permissionSet.add(Permission.ROLE_READ);
        permissionSet.add(Permission.USER_WRITE);

        Role role = new Role();
        role.setId(1L);
        role.setPermissions(permissionSet);
        role.setName("Some role");

        Role secondRole = new Role();
        secondRole.setId(2L);
        secondRole.setPermissions(permissionSet);
        secondRole.setName("Other role");

        Set<Role> roles = new HashSet<>();
        roles.add(role);
        roles.add(secondRole);

        User user = new User();
        user.setId(1L);
        user.setUsername("Jan Kowalski");
        user.setEmail("some@wp.pl");
        user.setRoles(roles);
        user.setOrganisation(organisation);

        //when
        UserDTO userDTO = userMapper.mapToUserDTO(user);

        //then
        assertEquals("Jan Kowalski", userDTO.getUsername());
        assertEquals("some@wp.pl", userDTO.getEmail());
        assertEquals(1L, userDTO.getId());
        assertEquals(2, userDTO.getRoles().size());
        assertNotNull(userDTO.getOrganisation());

        assertEquals("Some organisation", userDTO.getOrganisation().getName());
        assertEquals(1L, userDTO.getOrganisation().getId());
        assertNull(userDTO.getOrganisation().getUsers());

        userDTO.getRoles().forEach(roleDTO -> {
            if(roleDTO.getId() == 1L) {
                assertEquals("Some role", roleDTO.getName());
                assertNull(roleDTO.getPermissions());
                assertNull(roleDTO.getUsers());
            } else if(roleDTO.getId() == 2L) {
                assertEquals("Other role", roleDTO.getName());
                assertNull(roleDTO.getPermissions());
                assertNull(roleDTO.getUsers());
            }
        });
    }

    @Test
    void when_use_getModel_on_userDTO_should_returned_user() {
        //given
        OrganisationDTO organisationDTO = new OrganisationDTO();
        organisationDTO.setId(1L);
        organisationDTO.setName("Some organisation");

        Set<Permission> permissionSet = new HashSet<>();
        permissionSet.add(Permission.ROLE_READ);
        permissionSet.add(Permission.USER_WRITE);

        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(1L);
        roleDTO.setPermissions(permissionSet);
        roleDTO.setName("Some role");

        RoleDTO secondRoleDTO = new RoleDTO();
        secondRoleDTO.setId(2L);
        secondRoleDTO.setPermissions(permissionSet);
        secondRoleDTO.setName("Other role");

        Set<RoleDTO> roleDTOS = new HashSet<>();
        roleDTOS.add(roleDTO);
        roleDTOS.add(secondRoleDTO);

        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setUsername("Jan Kowalski");
        userDTO.setEmail("some@wp.pl");
        userDTO.setRoles(roleDTOS);
        userDTO.setOrganisation(organisationDTO);

        //when
        User user = userMapper.mapToUser(userDTO);

        //then
        assertEquals("Jan Kowalski", user.getUsername());
        assertEquals("some@wp.pl", user.getEmail());
        assertEquals(1L, user.getId());
        assertEquals(2, user.getRoles().size());
        assertNotNull(user.getOrganisation());

        assertEquals("Some organisation", user.getOrganisation().getName());
        assertEquals(1L, user.getOrganisation().getId());
        assertTrue(user.getOrganisation().getUsers().isEmpty());

        user.getRoles().forEach(role -> {
            if(role.getId() == 1L) {
                assertEquals("Some role", role.getName());
                assertTrue(role.getPermissions().isEmpty());
                assertTrue(role.getUsers().isEmpty());
            } else if(role.getId() == 2L) {
                assertEquals("Other role", role.getName());
                assertTrue(role.getPermissions().isEmpty());
                assertTrue(role.getUsers().isEmpty());
            }
        });
    }

}