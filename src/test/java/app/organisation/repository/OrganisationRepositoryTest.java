package app.organisation.repository;


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
class OrganisationRepositoryTest {
    @Autowired
    private OrganisationRepository organisationRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    void when_add_organisation_and_user_no_id_should_add_both() {
        //given
        User user = new User();
        user.setId(null);
        user.setUsername("Jan Kowalski");
        user.setEmail("something@gmail.com");
        Set<User> users = new HashSet<>();
        users.add(user);

        Organisation organisation = new Organisation();
        organisation.setId(null);
        organisation.setName("Some Organisation");
        organisation.setUsers(users);

        organisationRepository.save(organisation);
        //when
        List<Organisation> organisations = organisationRepository.findAll();
        List<User> userList = userRepository.findAll();
        //then
        assertEquals(1, organisations.size());
        assertNotNull(organisations.get(0).getId());
        assertEquals(1, organisations.get(0).getUsers().size());
        assertNotNull(userList.get(0).getId());
        assertEquals(1, userList.size());
    }

    @Test
    void when_delete_organisation_should_delete_all_users_belong() {
        //given
        User user = new User();
        user.setId(null);
        user.setUsername("Jan Kowalski");
        user.setEmail("something@gmail.com");
        Set<User> users = new HashSet<>();
        users.add(user);

        Organisation organisation = new Organisation();
        organisation.setId(null);
        organisation.setName("Some Organisation");
        organisation.setUsers(users);

        organisationRepository.save(organisation);
        organisationRepository.delete(organisation);
        //when
        List<Organisation> organisations = organisationRepository.findAll();
        List<User> userList = userRepository.findAll();
        //then
        assertTrue(organisations.isEmpty());
        assertTrue(userList.isEmpty());
    }
}