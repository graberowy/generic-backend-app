package app.user.service;

import app.user.repository.User;
import app.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


class UserServiceTest {

    private final UserRepository userRepository = Mockito.mock(UserRepository.class);
    private final UserServiceBasic userServiceBasic = new UserServiceBasic(userRepository);

    @Test
    void when_send_request_with_valid_id_then_user_with_provided_id_should_be_returned() {
        //given
        User user = new User();
        user.setId(1L);
        user.setUsername("Jan Kowalski");
        user.setEmail("Janek@wp.pl");
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        //when
        Optional<User> userReturned = userServiceBasic.findById(1L);
        //then
        assertEquals("Jan Kowalski", userReturned.get().getUsername());
        assertEquals("Janek@wp.pl", userReturned.get().getEmail());
        assertEquals(1L, userReturned.get().getId());
    }

    @Test
    void when_send_request_with_valid_username_then_user_with_provided_username_should_be_returned() {
        //given
        User user = new User();
        user.setId(1L);
        user.setUsername("Jan Kowalski");
        user.setEmail("Janek@wp.pl");
        Mockito.when(userRepository.findByUsernameIgnoreCase("Jan Kowalski")).thenReturn(Optional.of(user));
        //when
        Optional<User> userReturned = userServiceBasic.findByUsername("Jan Kowalski");
        //then
        assertEquals("Jan Kowalski", userReturned.get().getUsername());
        assertEquals("Janek@wp.pl", userReturned.get().getEmail());
        assertEquals(1L, userReturned.get().getId());
    }

    @Test
    void when_add_new_user_than_user_should_be_added_to_repo() {
        //given
        User firstUser = new User();
        firstUser.setUsername("Jank Kowslaki");
        firstUser.setEmail("Janek@wp.pl");

        User secondUser = new User();
        secondUser.setId(1L);
        secondUser.setUsername("Jan Kowslaki");
        secondUser.setEmail("Janek@wp.pl");

        Mockito.when(userRepository.save(firstUser)).thenReturn(secondUser);
        //when
        User user = userServiceBasic.save(firstUser);
        //then
        assertEquals("Jan Kowslaki", user.getUsername());
        assertEquals("Janek@wp.pl", user.getEmail());
        assertEquals(1L, user.getId());
        Mockito.verify(userRepository).save(firstUser);
    }

    @Test
    void when_update_existing_user_then_data_should_be_updated() {
        //given
        User firstUser = new User();
        firstUser.setId(1L);
        firstUser.setUsername("Jan Kowslaki");
        firstUser.setEmail("Janek@wp.pl");

        User updateUser = new User();
        updateUser.setId(1L);
        updateUser.setUsername("Sebastian Kowslaki");
        updateUser.setEmail("Sebastian@wp.pl");

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(firstUser));
        Mockito.when(userRepository.save(updateUser)).thenReturn(updateUser);
        //when
        Optional<User> userUpdated = userServiceBasic.update(updateUser);
        //then
        assertEquals("Sebastian Kowslaki", userUpdated.get().getUsername());
        assertEquals("Sebastian@wp.pl", userUpdated.get().getEmail());
        assertEquals(1L, userUpdated.get().getId());
    }

    @Test
    void when_request_find_all_than_all_users_should_be_returned() {
        //given
        User firstUser = new User();
        firstUser.setId(1L);
        firstUser.setUsername("Jan Kowslaki");
        firstUser.setEmail("Janek@wp.pl");

        User secondUser = new User();
        secondUser.setId(2L);
        secondUser.setUsername("Sebastian Kowslaki");
        secondUser.setEmail("Sebastian@wp.pl");

        List<User> userList = new ArrayList<>();
        userList.add(firstUser);
        userList.add(secondUser);

        Mockito.when(userRepository.findAll()).thenReturn(userList);
        //when
        List<User> users = userServiceBasic.findAll();
        //then
        assertFalse(users.isEmpty());
        assertEquals(2, users.size());
    }

    @Test
    void when_delete_user_by_id_then_user_should_be_removed() {
        //given
        User firstUser = new User();
        firstUser.setId(1L);
        firstUser.setUsername("Jan Kowslaki");
        firstUser.setEmail("Janek@wp.pl");

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(firstUser));
        //when
        userServiceBasic.delete(1L);
        //then
        Mockito.verify(userRepository).deleteById(1L);
    }
}
