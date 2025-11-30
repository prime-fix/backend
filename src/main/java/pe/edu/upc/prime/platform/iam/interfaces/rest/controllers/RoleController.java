package pe.edu.upc.prime.platform.iam.interfaces.rest.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.prime.platform.iam.domain.model.queries.GetAllRolesQuery;
import pe.edu.upc.prime.platform.iam.domain.services.RoleQueryService;
import pe.edu.upc.prime.platform.iam.interfaces.rest.assemblers.RoleAssembler;
import pe.edu.upc.prime.platform.iam.interfaces.rest.resources.RoleResponse;

import java.util.List;

/**
 * REST controller for managing roles.
 */
@CrossOrigin(origins = "*", methods = { RequestMethod.POST, RequestMethod.GET,
        RequestMethod.PUT, RequestMethod.DELETE })
@RestController
@RequestMapping(value = "/api/v1/roles", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Roles", description = "Role Management Endpoints")
public class RoleController {
    /**
     * Service for handling role queries.
     */
    private final RoleQueryService roleQueryService;


    /**
     * Constructor for RoleController.
     *
     * @param roleQueryService the role query service
     */
    public RoleController(RoleQueryService roleQueryService) {
        this.roleQueryService = roleQueryService;
    }

    /**
     * Get all roles.
     *
     * @return List of role resources
     */
    @GetMapping
    public ResponseEntity<List<RoleResponse>> getAllRoles() {
        var getAllRolesQuery = new GetAllRolesQuery();
        var roles = roleQueryService.handle(getAllRolesQuery);
        var roleResources = roles.stream()
                .map(RoleAssembler::toResponseFromEntity)
                .toList();

        return ResponseEntity.ok(roleResources);
    }
}
