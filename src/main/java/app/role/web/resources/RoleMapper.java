package app.role.web.resources;

import app.generic.web.resources.GenericMapper;
import app.role.repository.Role;
import app.user.repository.User;
import app.user.web.resources.UserDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * This interface is for map role to roleDTO and reverse
 */
@Mapper(componentModel = "spring")
public interface RoleMapper extends GenericMapper<RoleDTO, Role> {

    /**
     * This method mapping role to roleDTO
     *
     * @param role object
     * @return roleDTO
     */
    RoleDTO mapToRoleDTO(Role role);

    /**
     * This method mapping user to userDTO
     *
     * @param user object
     * @return userDTO
     */
    @Mapping(source = "id", target = "id")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "email", target = "email")
    @BeanMapping(ignoreByDefault = true)
    UserDTO mapToUserDTO(User user);

    /**
     * This method work conversely to mapToRoleDTO
     *
     * @param roleDTO object
     * @return role
     */
    @InheritInverseConfiguration(name = "mapToRoleDTO")
    Role mapToRole(RoleDTO roleDTO);

    /**
     * This method work conversely to mapToUserDTO
     *
     * @param userDTO object
     * @return user
     */
    @InheritInverseConfiguration(name = "mapToUserDTO")
    User mapToUser(UserDTO userDTO);
}
