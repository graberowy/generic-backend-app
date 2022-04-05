package app.user.repository;

import app.organisation.repository.Organisation;
import app.organisation.repository.OrganisationRepository;
import app.role.repository.Permission;
import app.role.repository.Role;
import app.role.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private OrganisationRepository organisationRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Test
    void when_delete_user_organisation_should_left() {
        //given
        Organisation organisation = new Organisation();
        organisation.setId(null);
        organisation.setName("Some Organisation");

        User user = new User();
        user.setId(null);
        user.setUsername("Jan Kowalski");
        user.setEmail("something@gmail.com");
        user.setOrganisation(organisation);

        organisationRepository.save(organisation);
        userRepository.save(user);
        userRepository.delete(user);
        //when
        List<Organisation> organisations = organisationRepository.findAll();
        List<User> userList = userRepository.findAll();
        //then
        assertEquals(1, organisations.size());
        assertTrue(userList.isEmpty());
    }

    @Test
    void when_delete_user_not_remove_role() {
        //given
        Set<Permission> permissions = new HashSet<>();
        permissions.add(Permission.ORGANISATION_READ);
        permissions.add(Permission.USER_WRITE);

        Role role = new Role();
        role.setId(null);
        role.setName("Permission_1");
        role.setPermissions(permissions);
        Set<Role> roles = new HashSet<>();
        roles.add(role);

        Organisation organisation = new Organisation();
        organisation.setId(null);
        organisation.setName("Some Organisation");

        User user = new User();
        user.setId(null);
        user.setUsername("Jan Kowalski");
        user.setEmail("something@gmail.com");
        user.setOrganisation(organisation);
        user.setRoles(roles);

        roleRepository.save(role);
        organisationRepository.save(organisation);
        userRepository.save(user);


        userRepository.delete(user);
        //when
        List<User> userList = userRepository.findAll();
        List<Role> roleList = roleRepository.findAll();
        //then
        assertEquals(1, roleList.size());
        assertTrue(userList.isEmpty());
    }

    @Test
    void when_add_user_no_id_it_adds_role_no_id() {
        //given
        Set<Permission> permissions = new HashSet<>();
        permissions.add(Permission.ORGANISATION_READ);
        permissions.add(Permission.USER_WRITE);

        Role role = new Role();
        role.setId(null);
        role.setName("Permission_1");
        role.setPermissions(permissions);
        Set<Role> roles = new HashSet<>();
        roles.add(role);

        Organisation organisation = new Organisation();
        organisation.setId(null);
        organisation.setName("Some Organisation");

        User user = new User();
        user.setId(null);
        user.setUsername("Jan Kowalski");
        user.setEmail("something@gmail.com");
        user.setOrganisation(organisation);
        user.setRoles(roles);

        organisationRepository.save(organisation);
        userRepository.save(user);
        //when
        List<User> userList = userRepository.findAll();
        List<Role> roleList = roleRepository.findAll();
        //then
        assertEquals(1, userList.size());
        assertEquals(1, roleList.size());
    }

    @Test
    void when_add_user_it_doesnt_add_organisation() {
        //given
        Organisation organisation = new Organisation();
        organisation.setId(null);
        organisation.setName("Some Organisation");

        User user = new User();
        user.setId(null);
        user.setUsername("Jan Kowalski");
        user.setEmail("something@gmail.com");
        user.setOrganisation(organisation);

        userRepository.save(user);
        //when
        //then
        assertThrows(InvalidDataAccessApiUsageException.class, () -> {
            organisationRepository.findAll();
        });

    }
}