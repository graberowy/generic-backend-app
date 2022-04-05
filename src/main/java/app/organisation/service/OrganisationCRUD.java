package app.organisation.service;

import app.generic.service.GenericCRUD;
import app.organisation.repository.Organisation;
import app.organisation.repository.OrganisationRepository;
import org.springframework.stereotype.Service;

/**
 * This class is for cover operations on organisation
 *
 * @author pawel.jankowski
 */
@Service
public class OrganisationCRUD extends GenericCRUD<Organisation, Long> {

    /**
     * This is constructor for use operations on organisation
     *
     * @param repository is organisation interface
     */
    public OrganisationCRUD(OrganisationRepository repository) {
        super(repository);
    }
}
