package pe.edu.upc.prime.platform.iam.interfaces.rest.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.prime.platform.iam.domain.model.commands.DeleteRoleCommand;
import pe.edu.upc.prime.platform.iam.domain.model.queries.GetAllRolesQuery;
import pe.edu.upc.prime.platform.iam.domain.model.queries.GetRoleByIdQuery;
import pe.edu.upc.prime.platform.iam.domain.services.RoleCommandService;
import pe.edu.upc.prime.platform.iam.domain.services.RoleQueryService;
import pe.edu.upc.prime.platform.iam.interfaces.rest.assemblers.RoleAssembler;
import pe.edu.upc.prime.platform.iam.interfaces.rest.resources.CreateRoleRequest;
import pe.edu.upc.prime.platform.iam.interfaces.rest.resources.RoleResponse;
import pe.edu.upc.prime.platform.iam.interfaces.rest.resources.UpdateRoleRequest;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
     * Service for handling role commands.
     */
    private final RoleCommandService roleCommandService;

    /**
     * Constructor for RoleController.
     *
     * @param roleQueryService the role query service
     * @param roleCommandService the role command service
     */
    public RoleController(RoleQueryService roleQueryService,
                          RoleCommandService roleCommandService) {
        this.roleQueryService = roleQueryService;
        this.roleCommandService = roleCommandService;
    }

    /**
     * Create a new role.
     *
     * @param request the create role request
     * @return the response entity with the created role
     */
    @Operation(summary = "Create a new role",
            description = "Creates a new role with the provided data",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Role data for creation", required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CreateRoleRequest.class))),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Role created successfully",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = RoleResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request - Invalid input data",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = RuntimeException.class)))
            }
    )
    @PostMapping
    public ResponseEntity<RoleResponse> createRole(@RequestBody CreateRoleRequest request) {
        var createRoleCommand = RoleAssembler.toCommandFromRequest(request);
        var roleId = this.roleCommandService.handle(createRoleCommand);

        if (Objects.isNull(roleId) || roleId.isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        var getRoleByIdQuery = new GetRoleByIdQuery(roleId);
        var role = this.roleQueryService.handle(getRoleByIdQuery);
        if (role.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var roleResponse = RoleAssembler.toResponseFromEntity(role.get());
        return new ResponseEntity<>(roleResponse, HttpStatus.CREATED);
    }

    /**
     * Retrieve all roles.
     *
     * @return the response entity with the list of roles
     */
    @Operation(summary = "Retrieve all roles",
            description = "Retrieves a list of all roles",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Roles retrieved successfully",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = RoleResponse.class))),
            })
    @GetMapping
    public ResponseEntity<List<RoleResponse>> getAllRoles() {
        var getAllRolesQuery = new GetAllRolesQuery();
        var roles = this.roleQueryService.handle(getAllRolesQuery);

        var roleResponses = roles.stream()
                .map(RoleAssembler::toResponseFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(roleResponses);
    }

    /**
     * Retrieve a role by its ID.
     *
     * @param id_role the ID of the role to retrieve
     * @return the response entity with the role
     */
    @Operation(summary = "Retrieve a role by its ID",
            description = "Retrieves a role using its unique ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Role retrieved successfully",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = RoleResponse.class))),
            })
    @GetMapping("/{id_role}")
    public ResponseEntity<RoleResponse> getRoleById(@PathVariable String id_role) {
        var getRoleByIdQuery = new GetRoleByIdQuery(id_role);
        var optionalRole = this.roleQueryService.handle(getRoleByIdQuery);
        if (optionalRole.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var roleResponse = RoleAssembler.toResponseFromEntity(optionalRole.get());
        return ResponseEntity.ok(roleResponse);
    }

    /**
     * Update an existing role.
     *
     * @param id_role the ID of the role to update
     * @param request the update role request
     * @return the response entity with the updated role
     */
    @Operation(summary = "Update an existing role",
            description = "Update an existing role with the provided data",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Role data for update", required = true,
                    content = @Content (
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UpdateRoleRequest.class))),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Role updated successfully",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = RoleResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request - Invalid input data",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = RuntimeException.class)))
            }
    )
    @PutMapping("/{id_role}")
    public ResponseEntity<RoleResponse> updateRole(@PathVariable String id_role,
                                                   @RequestBody UpdateRoleRequest request) {
        var updateRoleCommand = RoleAssembler.toCommandFromRequest(id_role, request);
        var optionalRole = this.roleCommandService.handle(updateRoleCommand);
        if (optionalRole.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var roleResponse = RoleAssembler.toResponseFromEntity(optionalRole.get());
        return ResponseEntity.ok(roleResponse);
    }

    /**
     * Delete a role by its ID.
     *
     * @param id_role the ID of the role to delete
     * @return the response entity with no content
     */
    @Operation(summary = "Delete a role by its ID",
            description = "Deletes a role using its unique ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Role deleted successfully"),
                    @ApiResponse(responseCode = "400", description = "Bad request - Invalid role ID",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = RuntimeException.class)))
            }
    )
    @DeleteMapping("/{id_role}")
    public ResponseEntity<?> deleteRole(@PathVariable String id_role) {
        var deleteRoleCommand = new DeleteRoleCommand(id_role);
        this.roleCommandService.handle(deleteRoleCommand);
        return ResponseEntity.noContent().build();
    }
}
