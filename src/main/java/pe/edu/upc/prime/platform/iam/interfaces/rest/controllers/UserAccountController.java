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
import pe.edu.upc.prime.platform.iam.domain.model.commands.DeleteUserAccountCommand;
import pe.edu.upc.prime.platform.iam.domain.model.queries.GetAllUserAccountsQuery;
import pe.edu.upc.prime.platform.iam.domain.model.queries.GetUserAccountByIdQuery;
import pe.edu.upc.prime.platform.iam.domain.services.UserAccountCommandService;
import pe.edu.upc.prime.platform.iam.domain.services.UserAccountQueryService;
import pe.edu.upc.prime.platform.iam.interfaces.rest.assemblers.UserAccountAssembler;
import pe.edu.upc.prime.platform.iam.interfaces.rest.resources.CreateUserAccountRequest;
import pe.edu.upc.prime.platform.iam.interfaces.rest.resources.UpdateUserAccountRequest;
import pe.edu.upc.prime.platform.iam.interfaces.rest.resources.UserAccountResponse;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * REST controller for managing user accounts
 */
@CrossOrigin(origins = "*", methods = { RequestMethod.POST, RequestMethod.GET,
        RequestMethod.PUT, RequestMethod.DELETE })
@RestController
@RequestMapping(value = "/api/v1/user_accounts", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "UserAccounts", description = "User Account Management Endpoints")
public class UserAccountController {
    /**
     * Service for handling user account queries
     */
    private final UserAccountQueryService userAccountQueryService;

    /**
     * Service for handling user account commands
     */
    private final UserAccountCommandService userAccountCommandService;

    /**
     * Constructor for UserAccountController
     *
     * @param userAccountQueryService   the user account query service
     * @param userAccountCommandService the user account command service
     */
    public UserAccountController(UserAccountQueryService userAccountQueryService,
                                 UserAccountCommandService userAccountCommandService) {
        this.userAccountQueryService = userAccountQueryService;
        this.userAccountCommandService = userAccountCommandService;
    }

    /**
     * Create a new user account
     *
     * @param request The request body containing user account data
     * @return ResponseEntity with the created user account data
     */
    @Operation(summary = "Create a new user account",
            description = "Creates a new user account with the provided data",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "User Account data for creation", required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CreateUserAccountRequest.class))),
            responses = {
                    @ApiResponse(responseCode = "201", description = "User Account created successfully",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserAccountResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request - Invalid input data",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = RuntimeException.class)))
            }
    )
    @PostMapping
    public ResponseEntity<UserAccountResponse> createUserAccount(@RequestBody CreateUserAccountRequest request) {
        var createUserAccountCommand = UserAccountAssembler.toCommandFromRequest(request);
        var userAccountId = this.userAccountCommandService.handle(createUserAccountCommand);

        if (Objects.isNull(userAccountId) || userAccountId.equals(0L)) {
            return ResponseEntity.badRequest().build();
        }

        var getUserAccountByIdQuery = new GetUserAccountByIdQuery(userAccountId);
        var userAccount = userAccountQueryService.handle(getUserAccountByIdQuery);
        if (userAccount.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var userAccountResponse = UserAccountAssembler.toResponseFromEntity(userAccount.get());
        return new ResponseEntity<>(userAccountResponse, HttpStatus.CREATED);
    }

    /**
     * Retrieve all user accounts
     *
     * @return ResponseEntity with the list of all user accounts
     */
    @Operation(summary = "Retrieve all user accounts",
            description = "Retrieves a list of all user accounts",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User Accounts retrieved successfully",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserAccountResponse.class))),
            })
    @GetMapping
    public ResponseEntity<List<UserAccountResponse>> getAllUserAccounts() {
        var getAllUserAccountsQuery = new GetAllUserAccountsQuery();
        var userAccounts = userAccountQueryService.handle(getAllUserAccountsQuery);

        var userAccountResponses = userAccounts.stream()
                .map(UserAccountAssembler::toResponseFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userAccountResponses);
    }

    /**
     * Retrieve a user account by its ID
     *
     * @param id_user_account the unique ID of the user account
     * @return ResponseEntity with the user account data
     */
    @Operation(summary = "Retrieve a user account by its ID",
            description = "Retrieves a user account using its unique ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User Account retrieved successfully",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserAccountResponse.class))),
            })
    @GetMapping("/{id_user_account}")
    public ResponseEntity<UserAccountResponse> getUserAccountById(@PathVariable Long id_user_account) {
        var getUserAccountByIdQuery = new GetUserAccountByIdQuery(id_user_account);
        var optionalUserAccount = userAccountQueryService.handle(getUserAccountByIdQuery);
        if (optionalUserAccount.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var userAccountResponse = UserAccountAssembler.toResponseFromEntity(optionalUserAccount.get());
        return ResponseEntity.ok(userAccountResponse);
    }

    /**
     * Update an existing user account
     *
     * @param user_account_id The unique ID of the user account to be updated
     * @param request         The request body containing updated user account data
     * @return ResponseEntity with the updated user account data
     */
    @Operation(summary = "Update an existing user account",
            description = "Update an existing user account with the provided data",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "User Account data for update", required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UpdateUserAccountRequest.class))),
            responses = {
                    @ApiResponse(responseCode = "200", description = "User Account updated successfully",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserAccountResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request - Invalid input data",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = RuntimeException.class)))
            }
    )
    @PutMapping("/{user_account_id}")
    public ResponseEntity<UserAccountResponse> updateUserAccount(@PathVariable Long user_account_id,
                                                                 @RequestBody UpdateUserAccountRequest request) {
        var updateUserAccountCommand = UserAccountAssembler.toCommandFromRequest(user_account_id, request);
        var optionalUserAccount = this.userAccountCommandService.handle(updateUserAccountCommand);
        if (optionalUserAccount.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var userAccountResponse = UserAccountAssembler.toResponseFromEntity(optionalUserAccount.get());
        return ResponseEntity.ok(userAccountResponse);
    }

    /**
     * Delete a user account by its ID
     *
     * @param user_account_id The unique ID of the user account to be deleted
     * @return ResponseEntity with no content
     */
    @Operation(summary = "Delete a user account by its ID",
            description = "Deletes a user account using its unique ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "User Account deleted successfully"),
                    @ApiResponse(responseCode = "400", description = "Bad request - Invalid user account ID",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = RuntimeException.class)))
            }
    )
    @DeleteMapping("/{user_account_id}")
    public ResponseEntity<?> deleteUserAccount(@PathVariable Long user_account_id) {
        var deleteUserAccountCommand = new DeleteUserAccountCommand(user_account_id);
        this.userAccountCommandService.handle(deleteUserAccountCommand);
        return ResponseEntity.noContent().build();
    }

}
