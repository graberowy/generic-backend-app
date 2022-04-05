package app.role.web.controller;

import app.generic.web.controller.GenericControllerImpl;
import app.generic.web.resources.GenericMapper;
import app.role.repository.Role;
import app.role.service.RoleCRUD;
import app.role.web.resources.RoleDTO;
import org.springframework.web.bind.annotation.*;

/**
 * This class if for cover CRUD operations on role
 *
 * @author pawel.jankowski
 */

@RestController
@RequestMapping("/api/v1/roles")
public class RoleController extends GenericControllerImpl<RoleDTO, Role, Long> {

    /**
     * This is constructor for use generic operations on specific type
     *
     * @param roleCRUD      role service
     * @param genericMapper generic object mapper
     */
    public RoleController(RoleCRUD roleCRUD, GenericMapper<RoleDTO, Role> genericMapper) {
        super(roleCRUD, genericMapper);
    }
}
