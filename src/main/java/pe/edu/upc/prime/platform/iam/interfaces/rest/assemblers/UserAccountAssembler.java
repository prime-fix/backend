package pe.edu.upc.prime.platform.iam.interfaces.rest.assemblers;

import pe.edu.upc.prime.platform.iam.domain.model.aggregates.UserAccount;
import pe.edu.upc.prime.platform.iam.domain.model.commands.CreateUserAccountCommand;
import pe.edu.upc.prime.platform.iam.domain.model.commands.UpdateUserAccountCommand;
import pe.edu.upc.prime.platform.iam.interfaces.rest.resources.CreateUserAccountRequest;
import pe.edu.upc.prime.platform.iam.interfaces.rest.resources.UpdateUserAccountRequest;
import pe.edu.upc.prime.platform.iam.interfaces.rest.resources.UserAccountResponse;

/**
 * Assembler for converting between UserAccount-related requests, commands, entities, and responses.
 */
public class UserAccountAssembler {

    /**
     * Converts a CreateUserAccountRequest to a CreateUserAccountCommand.
     *
     * @param request the CreateUserAccountRequest to convert
     * @return the corresponding CreateUserAccountCommand
     */
    public static CreateUserAccountCommand toCommandFromRequest(CreateUserAccountRequest request) {
        return new CreateUserAccountCommand( request.username(),
                request.email(), request.idRole(), request.idUser(),
                request.password(), request.isNew());
    }

    /**
     * Converts an UpdateUserAccountRequest to an UpdateUserAccountCommand.
     *
     * @param userAccountId the ID of the user account to update
     * @param request the UpdateUserAccountRequest to convert
     * @return the corresponding UpdateUserAccountCommand
     */
    public static UpdateUserAccountCommand toCommandFromRequest(String userAccountId,
                                                                UpdateUserAccountRequest request) {
        return new UpdateUserAccountCommand(userAccountId, request.username(),
                request.email(), request.idRole(), request.idUser(),
                request.password(), request.isNew());
    }

    /**
     * Converts a UserAccount entity to a UserAccountResponse.
     *
     * @param entity the UserAccount entity to convert
     * @return the corresponding UserAccountResponse
     */
    public static UserAccountResponse toResponseFromEntity(UserAccount entity) {
        return new UserAccountResponse(entity.getId().toString(), entity.getUsername(),
                entity.getEmail(), entity.getIdRole(),
                entity.getIdUser(), entity.getPassword(), entity.isNew());
    }
}
