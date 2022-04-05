package app.role.repository;

import app.organisation.repository.Organisation;
import app.organisation.repository.OrganisationRepository;
import app.user.repository.User;
import app.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class RoleRepositoryTest {
    @Autowired
    private OrganisationRepository organisationRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Test
    void when_add_role_no_id_user_not_gonna_add_no_id() {
        //given
        User user = new User();
        user.setId(null);
        user.setUsername("Jan Kowalski");
        user.setEmail("something@gmail.com");
        Set<User> users = new HashSet<>();
        users.add(user);

        Set<Permission> permissions = new HashSet<>();
        permissions.add(Permission.ORGANISATION_READ);
        permissions.add(Permission.USER_WRITE);

        Role role = new Role();
        role.setId(null);
        role.setName("Permission_1");
        role.setPermissions(permissions);
        role.setUsers(users);

        roleRepository.save(role);
        //when
        List<User> userList = userRepository.findAll();
        List<Role> roleList = roleRepository.findAll();
        //then
        assertEquals(1, roleList.size());
        assertTrue(userList.isEmpty());
    }

    @Test
    void when_add_role_it_should_be_connected_with_permissions() {
        //given
        Set<Permission> permissions = new HashSet<>();
        permissions.add(Permission.ORGANISATION_READ);
        permissions.add(Permission.USER_WRITE);

        Role role = new Role();
        role.setId(null);
        role.setName("Permission_1");
        role.setPermissions(permissions);

        roleRepository.save(role);
        //when
        List<Role> roles = roleRepository.findAll();
        //then
        assertEquals(1, roles.size());
        assertEquals(2, roles.get(0).getPermissions().size());
    }

    @Test
    void when_delete_role_user_should_be_not_deleted() {
        //given
        Set<Permission> permissions = new HashSet<>();
        permissions.add(Permission.ORGANISATION_READ);
        permissions.add(Permission.USER_WRITE);

        Role role = new Role();
        role.setId(null);
        role.setName("Permission_1");
        role.setPermissions(permissions);

        Organisation organisation = new Organisation();
        organisation.setId(null);
        organisation.setName("Some Organisation");
        organisationRepository.save(organisation);

        User user = new User();
        user.setId(null);
        user.setUsername("Jan Kowalski");
        user.setEmail("something@gmail.com");
        user.setOrganisation(organisation);
        user.addRole(role);

        userRepository.save(user);
        roleRepository.delete(role);
        //when
        List<Role> roleList = roleRepository.findAll();
        List<User> userList = userRepository.findAll();
        //then
        assertEquals(1, userList.size());
        assertTrue(roleList.isEmpty());
        assertTrue(userList.get(0).getRoles().isEmpty());

    }

    @Test
    void when_delete_one_role_permissions_should_stay_in_other_role() {
        //given
        Set<Permission> permissions = new HashSet<>();
        permissions.add(Permission.ORGANISATION_READ);
        permissions.add(Permission.USER_WRITE);

        Role role = new Role();
        role.setId(null);
        role.setName("Permission_1");
        role.setPermissions(permissions);

        Role role1 = new Role();
        role1.setId(null);
        role1.setName("Permission_2");
        role1.setPermissions(permissions);

        roleRepository.save(role);
        roleRepository.save(role1);
        roleRepository.delete(role);
        //when
        List<Role> roles = roleRepository.findAll();
        //then
        assertEquals(1, roles.size());
        assertEquals(2, roles.get(0).getPermissions().size());
    }
}