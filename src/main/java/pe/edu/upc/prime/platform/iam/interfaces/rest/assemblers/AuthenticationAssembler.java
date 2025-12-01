package pe.edu.upc.prime.platform.iam.interfaces.rest.assemblers;

import pe.edu.upc.prime.platform.iam.domain.model.aggregates.UserAccount;
import pe.edu.upc.prime.platform.iam.domain.model.commands.SignInCommand;
import pe.edu.upc.prime.platform.iam.domain.model.commands.SignUpCommand;
import pe.edu.upc.prime.platform.iam.domain.model.valueobjects.LocationInformation;
import pe.edu.upc.prime.platform.iam.domain.model.valueobjects.MembershipDescription;
import pe.edu.upc.prime.platform.iam.interfaces.rest.resources.AuthenticatedUserAccountResponse;
import pe.edu.upc.prime.platform.iam.interfaces.rest.resources.SignInRequest;
import pe.edu.upc.prime.platform.iam.interfaces.rest.resources.SignUpRequest;
import pe.edu.upc.prime.platform.payment.service.domain.model.valueobjects.CardType;

/**
 * Assembler for authentication-related conversions.
 */
public class AuthenticationAssembler {
    /**
     * Converts a SignInRequest to a SignInCommand.
     *
     * @param signInRequest the sign-in request
     * @return the corresponding sign-in command
     */
    public static SignInCommand toCommandFromRequestSignIn(SignInRequest signInRequest) {
        return new SignInCommand(
                signInRequest.username(),
                signInRequest.password()
        );
    }

    /**
     * Converts a SignUpRequest to a SignUpCommand.
     *
     * @param signUpRequest the sign-up request
     * @return the corresponding sign-up command
     */
    public static SignUpCommand toCommandFromRequestSignUp(SignUpRequest signUpRequest) {
        return new SignUpCommand(
                signUpRequest.name(),
                signUpRequest.lastName(),
                signUpRequest.dni(),
                signUpRequest.phoneNumber(),
                signUpRequest.username(),
                signUpRequest.email(),
                signUpRequest.roleId(),
                signUpRequest.password(),
                new LocationInformation(signUpRequest.address(), signUpRequest.district(), signUpRequest.department()),
                new MembershipDescription(signUpRequest.membershipDescription()),
                signUpRequest.started(),
                signUpRequest.over(),
                signUpRequest.cardNumber(),
                CardType.valueOf(signUpRequest.cardType()),
                signUpRequest.month(),
                signUpRequest.year(),
                signUpRequest.cvv()
        );
    }

    /**
     * Converts a UserAccount entity and token to an AuthenticatedUserAccountResponse.
     *
     * @param entity the user account entity
     * @param token the authentication token
     * @return the corresponding authenticated user account response
     */
    public static AuthenticatedUserAccountResponse toResponseFromEntityUserAccount(UserAccount entity, String token) {
        return new AuthenticatedUserAccountResponse(entity.getId(), entity.getUsername(),token);
    }
}
