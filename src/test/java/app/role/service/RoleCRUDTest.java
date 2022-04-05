package app.role.service;

import app.role.repository.Role;
import app.role.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class RoleCRUDTest {

    private final RoleRepository roleRepository = Mockito.mock(RoleRepository.class);
    private final RoleCRUD roleCRUD = new RoleCRUD(roleRepository);

    @Test
    void when_send_request_with_valid_id_then_role_with_provided_id_should_be_returned() {
        //given
        Role role = new Role();
        role.setId(1L);
        role.setName("Some role");
        Mockito.when(roleRepository.findById(1L)).thenReturn(Optional.of(role));
        //when
        Optional<Role> roleReturned = roleCRUD.findById(1L);
        //then
        assertEquals("Some role", roleReturned.get().getName());
        assertEquals(1L, roleReturned.get().getId());
    }

    @Test
    void when_add_new_role_than_role_should_be_added_to_repo() {
        //given
        Role role = new Role();
        role.setName("Some role");

        Role secondRole = new Role();
        secondRole.setId(1L);
        secondRole.setName("Some role");

        Mockito.when(roleRepository.save(role)).thenReturn(secondRole);
        //when
        Role roleReturned = roleCRUD.save(role);
        //then
        assertEquals("Some role", roleReturned.getName());
        assertEquals(1L, roleReturned.getId());
        Mockito.verify(roleRepository).save(role);
    }

    @Test
    void when_update_existing_role_then_data_should_be_updated() {
        //given
        Role role = new Role();
        role.setId(1L);
        role.setName("Some role");

        Role secondRole = new Role();
        secondRole.setId(1L);
        secondRole.setName("updated role");

        Mockito.when(roleRepository.findById(1L)).thenReturn(Optional.of(role));
        Mockito.when(roleRepository.save(secondRole)).thenReturn(secondRole);

        //when
        Optional<Role> roleUpdated = roleCRUD.update(secondRole);
        //then
        assertEquals("updated role", roleUpdated.get().getName());
        assertEquals(1L, roleUpdated.get().getId());
    }

    @Test
    void when_request_find_all_than_all_roles_should_be_returned() {
        //given
        Role firstRole = new Role();
        firstRole.setId(1L);
        firstRole.setName("Some role");

        Role secondRole = new Role();
        secondRole.setId(2L);
        secondRole.setName("second role");

        List<Role> roleList = new ArrayList<>();
        roleList.add(firstRole);
        roleList.add(secondRole);

        Mockito.when(roleRepository.findAll()).thenReturn(roleList);
        //when
        List<Role> roles = roleCRUD.findAll();
        //then
        assertFalse(roles.isEmpty());
        assertEquals(2, roles.size());
    }

    @Test
    void when_delete_role_by_id_then_role_should_be_removed() {
        //given
        Role role = new Role();
        role.setId(1L);
        role.setName("Some role");
        Mockito.when(roleRepository.findById(1L)).thenReturn(Optional.of(role));
        //when
        roleCRUD.delete(1L);
        //then
        Mockito.verify(roleRepository).deleteById(1L);
    }
}