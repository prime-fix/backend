package pe.edu.upc.prime.platform.iam.interfaces.rest.assemblers;

import pe.edu.upc.prime.platform.iam.domain.model.aggregates.UserAccount;
import pe.edu.upc.prime.platform.iam.domain.model.commands.AutoRepairSignUpCommand;
import pe.edu.upc.prime.platform.iam.domain.model.commands.SignInCommand;
import pe.edu.upc.prime.platform.iam.domain.model.commands.VehicleOwnerSignUpCommand;
import pe.edu.upc.prime.platform.iam.domain.model.valueobjects.LocationInformation;
import pe.edu.upc.prime.platform.iam.domain.model.valueobjects.MembershipDescription;
import pe.edu.upc.prime.platform.iam.interfaces.rest.resources.AuthenticatedUserAccountResponse;
import pe.edu.upc.prime.platform.iam.interfaces.rest.resources.AutoRepairSignUpRequest;
import pe.edu.upc.prime.platform.iam.interfaces.rest.resources.SignInRequest;
import pe.edu.upc.prime.platform.iam.interfaces.rest.resources.VehicleOwnerSignUpRequest;
import pe.edu.upc.prime.platform.payment.service.domain.model.valueobjects.CardType;

/**
 * Assembler for authentication-related conversions.
 */
public class AuthenticationAssembler {
    /**
     * Converts a SignInRequest to a SignInCommand.
     *
     * @param request the sign-in request
     * @return the corresponding sign-in command
     */
    public static SignInCommand toCommandFromRequestSignIn(SignInRequest request) {
        return new SignInCommand(
                request.username(),
                request.password()
        );
    }

    /**
     * Converts a VehicleOwnerSignUpRequest to a VehicleOwnerSignUpCommand.
     *
     * @param request the sign-up request for vehicle owner
     * @return the corresponding sign-up command
     */
    public static VehicleOwnerSignUpCommand toCommandFromRequestSignUpVehicleOwner(VehicleOwnerSignUpRequest request) {
        return new VehicleOwnerSignUpCommand(
                request.name(),
                request.lastName(),
                request.dni(),
                request.phoneNumber(),
                request.username(),
                request.email(),
                request.password(),
                new LocationInformation(request.address(), request.district(), request.department()),
                new MembershipDescription(request.membershipDescription()),
                request.started(),
                request.over(),
                request.cardNumber(),
                CardType.valueOf(request.cardType()),
                request.month(),
                request.year(),
                request.cvv()
        );
    }

    /**
     * Converts an AutoRepairSignUpRequest to an AutoRepairSignUpCommand.
     *
     * @param request the sign-up request for auto repair
     * @return the corresponding sign-up command
     */
    public static AutoRepairSignUpCommand toCommandFromRequestSignUpAutoRepair(AutoRepairSignUpRequest request) {
        return new AutoRepairSignUpCommand(
                request.name(),
                request.lastName(),
                request.dni(),
                request.phoneNumber(),
                request.username(),
                request.email(),
                request.password(),
                request.contactEmail(),
                request.ruc(),
                new LocationInformation(request.address(), request.district(), request.department()),
                new MembershipDescription(request.membershipDescription()),
                request.started(),
                request.over(),
                request.cardNumber(),
                CardType.valueOf(request.cardType()),
                request.month(),
                request.year(),
                request.cvv()
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
