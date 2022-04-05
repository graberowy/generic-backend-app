package app.user.web.resources;

import app.generic.web.resources.GenericMapper;
import app.organisation.repository.Organisation;
import app.organisation.web.resources.OrganisationDTO;
import app.role.repository.Role;
import app.role.web.resources.RoleDTO;
import app.user.repository.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * This interface is for map user to userDTO and reverse
 */
@Mapper(componentModel = "spring")
public interface UserMapper extends GenericMapper<UserDTO, User> {

    /**
     * This method mapping user to userDTO
     *
     * @param user object
     * @return userDTO
     */
    @Mapping(source = "id", target = "id")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "organisation", target = "organisation")
    @Mapping(source = "roles", target = "roles")
    @BeanMapping(ignoreByDefault = true)
    UserDTO mapToUserDTO(User user);

    /**
     * This method mapping organisation to organisationDTO
     *
     * @param organisation object
     * @return organisationDTO
     */
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @BeanMapping(ignoreByDefault = true)
    OrganisationDTO mapToOrganisationDTO(Organisation organisation);

    /**
     * This method mapping role to roleDTO
     *
     * @param role object
     * @return roleDTO
     */
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @BeanMapping(ignoreByDefault = true)
    RoleDTO mapToRoleDTO(Role role);

    /**
     * This method work conversely to mapToUserDTO
     *
     * @param userDTO object
     * @return user
     */
    @InheritInverseConfiguration(name = "mapToUserDTO")
    User mapToUser(UserDTO userDTO);

    /**
     * This method work conversely to mapToOrganisationDTO
     *
     * @param organisationDTO object
     * @return organisation
     */
    @InheritInverseConfiguration(name = "mapToOrganisationDTO")
    Organisation mapToOrganisation(OrganisationDTO organisationDTO);

    /**
     * This method work conversely to mapToRoleDTO
     *
     * @param roleDTO object
     * @return role
     */
    @InheritInverseConfiguration(name = "mapToRoleDTO")
    Role mapToRole(RoleDTO roleDTO);
}
