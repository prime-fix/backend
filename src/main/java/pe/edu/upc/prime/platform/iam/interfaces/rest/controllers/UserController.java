package pe.edu.upc.prime.platform.iam.interfaces.rest.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.prime.platform.iam.domain.model.commands.DeleteUserCommand;
import pe.edu.upc.prime.platform.iam.domain.model.queries.GetAllUsersQuery;
import pe.edu.upc.prime.platform.iam.domain.model.queries.GetUserByIdQuery;
import pe.edu.upc.prime.platform.iam.domain.services.UserCommandService;
import pe.edu.upc.prime.platform.iam.domain.services.UserQueryService;
import pe.edu.upc.prime.platform.iam.interfaces.rest.assemblers.UserAssembler;
import pe.edu.upc.prime.platform.iam.interfaces.rest.resources.CreateUserRequest;
import pe.edu.upc.prime.platform.iam.interfaces.rest.resources.UpdateUserRequest;
import pe.edu.upc.prime.platform.iam.interfaces.rest.resources.UserResponse;
import pe.edu.upc.prime.platform.shared.interfaces.rest.resources.ValidationExceptionResponse;

import java.util.List;
import java.util.Objects;

/**
 * REST controller for managing users
 */
@CrossOrigin(origins = "*", methods = { RequestMethod.POST, RequestMethod.GET,
        RequestMethod.PUT, RequestMethod.DELETE })
@RestController
@RequestMapping(value = "/api/v1/users", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Users", description = "User Management Endpoints")
public class UserController {

    /**
     * Service for handling user queries
     */
    private final UserQueryService userQueryService;
    /**
     * Service for handling user commands
     */
    private final UserCommandService userCommandService;

    /**
     * Constructor for UserController
     */
    public UserController(UserQueryService userQueryService, UserCommandService userCommandService) {
        this.userQueryService = userQueryService;
        this.userCommandService = userCommandService;
    }

    /**
     * Create a new user
     *
     * @param request The request body containing user data
     * @return ResponseEntity with the created user data
     */
    @Operation(summary = "Create a new user",
            description = "Creates a new user with the provided data",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "User data for creation", required = true,
                    content = @Content (
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CreateUserRequest.class))),
            responses = {
                    @ApiResponse(responseCode = "201", description = "User created successfully",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request - Invalid input data",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ValidationExceptionResponse.class)))
            }
    )
    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody CreateUserRequest request) {
        var createUserCommand = UserAssembler.toCommandFromRequest(request);
        var userId = this.userCommandService.handle(createUserCommand);

        if (Objects.isNull(userId) || userId.isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        var getUserByIdQuery = new GetUserByIdQuery(userId);
        var optionalUser = userQueryService.handle(getUserByIdQuery);

        var userResponse = UserAssembler.toResponseFromEntity(optionalUser.get());
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    /**
     * Retrieve all users
     *
     * @return ResponseEntity with the list of all users
     */
    @Operation(summary = "Retrieve all users",
            description = "Retrieves a list of all users",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Users retrieved successfully",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserResponse.class))),
            })
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        var getAllUsersQuery = new GetAllUsersQuery();
        var users = userQueryService.handle(getAllUsersQuery);

        var userResponses = users.stream()
                .map(UserAssembler::toResponseFromEntity)
                .toList();
        return ResponseEntity.ok(userResponses);
    }

    /**
     * Retrieve a user by its ID
     *
     * @param id_user The unique ID of the user
     * @return ResponseEntity with the user data
     */
    @Operation(summary = "Retrieve a user by its ID",
            description = "Retrieves a user using its unique ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User retrieved successfully",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserResponse.class))),
            })
    @GetMapping("/{id_user}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable String id_user) {
        var getUserByIdQuery = new GetUserByIdQuery(id_user);
        var optionalUser = userQueryService.handle(getUserByIdQuery);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var userResponse = UserAssembler.toResponseFromEntity(optionalUser.get());
        return ResponseEntity.ok(userResponse);
    }

    /**
     * Update an existing user
     *
     * @param id_user The unique ID of the user to be updated
     * @param request The request body containing updated user data
     * @return ResponseEntity with the updated user data
     */
    @Operation(summary = "Update an existing user",
            description = "Update an existing user with the provided data",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "User data for update", required = true,
                    content = @Content (
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UpdateUserRequest.class))),
            responses = {
                    @ApiResponse(responseCode = "200", description = "User updated successfully",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request - Invalid input data",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = RuntimeException.class)))
            }
    )
    @PutMapping("/{id_user}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable String id_user,
                                                   @Valid @RequestBody UpdateUserRequest request) {
        var updateUserCommand = UserAssembler.toCommandFromRequest(id_user, request);
        var optionalUser = this.userCommandService.handle(updateUserCommand);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var userResponse = UserAssembler.toResponseFromEntity(optionalUser.get());
        return ResponseEntity.ok(userResponse);
    }

    /**
     * Delete a user by its ID
     *
     * @param id_user The unique ID of the user to be deleted
     * @return ResponseEntity with no content
     */
    @Operation(summary = "Delete a user by its ID",
            description = "Deletes a user using its unique ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "User deleted successfully"),
                    @ApiResponse(responseCode = "400", description = "Bad request - Invalid user ID",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = RuntimeException.class)))
            }
    )
    @DeleteMapping("/{id_user}")
    public ResponseEntity<?> deleteUser(@PathVariable String id_user) {
        var deleteUserCommand = new DeleteUserCommand(id_user);
        this.userCommandService.handle(deleteUserCommand);
        return ResponseEntity.noContent().build();
    }
}
