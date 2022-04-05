package app.organisation.web.controller;

import app.generic.web.controller.GenericControllerImpl;
import app.generic.web.resources.GenericMapper;
import app.organisation.repository.Organisation;
import app.organisation.service.OrganisationCRUD;
import app.organisation.web.resources.OrganisationDTO;
import org.springframework.web.bind.annotation.*;

/**
 * This class if for cover CRUD operations on organisations
 *
 * @author pawel.jankowski
 */

@RestController
@RequestMapping("/api/v1/organisations")
public class OrganisationController extends GenericControllerImpl<OrganisationDTO, Organisation, Long> {

    /**
     * This is constructor for use generic operations on specific type
     *
     * @param organisationCRUD organisation service
     * @param genericMapper    generic object mapper
     */
    public OrganisationController(OrganisationCRUD organisationCRUD, GenericMapper<OrganisationDTO, Organisation> genericMapper) {
        super(organisationCRUD, genericMapper);
    }
}
