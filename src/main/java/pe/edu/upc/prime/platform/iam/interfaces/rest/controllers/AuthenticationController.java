package pe.edu.upc.prime.platform.iam.interfaces.rest.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.upc.prime.platform.iam.domain.services.UserAccountCommandService;
import pe.edu.upc.prime.platform.iam.interfaces.rest.assemblers.AuthenticationAssembler;
import pe.edu.upc.prime.platform.iam.interfaces.rest.assemblers.UserAccountAssembler;
import pe.edu.upc.prime.platform.iam.interfaces.rest.resources.*;

@RestController
@RequestMapping(value = "/api/v1/authentication", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Authentication", description = "Authentication Endpoints")
public class AuthenticationController {
    /**
     * The user account command service.
     */
    private final UserAccountCommandService userAccountCommandService;

    /**
     * Constructor for AuthenticationController.
     *
     * @param userAccountCommandService the user account command service
     */
    public AuthenticationController(UserAccountCommandService userAccountCommandService) {
        this.userAccountCommandService = userAccountCommandService;
    }

    /**
     *  Handle sign-in requests.
     *
     * @param request the sign-in request
     * @return the authenticated user account response
     */
    @PostMapping("/sign-in")
    public ResponseEntity<AuthenticatedUserAccountResponse> signIn(
            @RequestBody SignInRequest request) {

        var signInCommand = AuthenticationAssembler
                .toCommandFromRequestSignIn(request);

        var authenticatedUserAccount = userAccountCommandService.handle(signInCommand);
        if (authenticatedUserAccount.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var authenticatedUserAccountResponse = AuthenticationAssembler
                .toResponseFromEntityUserAccount(
                        authenticatedUserAccount.get().getLeft(), authenticatedUserAccount.get().getRight());

        return ResponseEntity.ok(authenticatedUserAccountResponse);
    }

    /**
     * Handle sign-up requests for vehicle owners.
     *
     * @param request the sign-up request
     * @return the created user account response
     */
    @PostMapping("/sign-up/vehicle-owner")
    public ResponseEntity<UserAccountResponse> signUp(@RequestBody VehicleOwnerSignUpRequest request) {
        var signUpCommand = AuthenticationAssembler
                .toCommandFromRequestSignUpVehicleOwner(request);
        var userAccount = userAccountCommandService.handle(signUpCommand);
        if (userAccount.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var userAccountResponse = UserAccountAssembler.toResponseFromEntity(userAccount.get());
        return new ResponseEntity<>(userAccountResponse, HttpStatus.CREATED);
    }

    /**
     * Handle sign-up requests for auto repair.
     *
     * @param request the sign-up request
     * @return the created user account response
     */
    @PostMapping("/sign-up/auto-repair")
    public ResponseEntity<UserAccountResponse> signUp(@RequestBody AutoRepairSignUpRequest request) {
        var signUpCommand = AuthenticationAssembler.toCommandFromRequestSignUpAutoRepair(request);
        var userAccount = userAccountCommandService.handle(signUpCommand);
        if (userAccount.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var userAccountResponse = UserAccountAssembler.toResponseFromEntity(userAccount.get());
        return new ResponseEntity<>(userAccountResponse, HttpStatus.CREATED);
    }
}
