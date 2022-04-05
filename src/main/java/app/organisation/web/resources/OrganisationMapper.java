package app.organisation.web.resources;

import app.generic.web.resources.GenericMapper;
import app.organisation.repository.Organisation;
import app.user.repository.User;
import app.user.web.resources.UserDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * This interface is for map organisation to organisationDTO and reverse
 */
@Mapper(componentModel = "spring")
public interface OrganisationMapper extends GenericMapper<OrganisationDTO, Organisation> {


    /**
     * This method mapping organisation to organisationDTO
     *
     * @param organisation object
     * @return organisationDTO
     */
    OrganisationDTO mapToOrganisationDTO(Organisation organisation);

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
     * This method work conversely to mapToOrganisationDTO
     *
     * @param organisationDTO object
     * @return organisation
     */
    @InheritInverseConfiguration(name = "mapToOrganisationDTO")
    Organisation mapToOrganisation(OrganisationDTO organisationDTO);

    /**
     * This method work conversely to mapToUserDTO
     *
     * @param userDTO object
     * @return user
     */
    @InheritInverseConfiguration(name = "mapToUserDTO")
    User mapToUser(UserDTO userDTO);
}
