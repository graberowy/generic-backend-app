package app.role.service;

import app.generic.service.GenericCRUD;
import app.role.repository.Role;
import app.role.repository.RoleRepository;
import org.springframework.stereotype.Service;

/**
 * This class is for cover operations on role
 *
 * @author pawel.jankowski
 */
@Service
public class RoleCRUD extends GenericCRUD<Role, Long> {
    /**
     * This is constructor for use operations on role
     *
     * @param repository is role interface
     */
    public RoleCRUD(RoleRepository repository) {
        super(repository);
    }
}
