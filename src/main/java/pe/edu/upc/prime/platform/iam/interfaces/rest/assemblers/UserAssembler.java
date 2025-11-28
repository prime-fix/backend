package pe.edu.upc.prime.platform.iam.interfaces.rest.assemblers;

import pe.edu.upc.prime.platform.iam.domain.model.aggregates.User;
import pe.edu.upc.prime.platform.iam.domain.model.commands.CreateUserCommand;
import pe.edu.upc.prime.platform.iam.domain.model.commands.UpdateUserCommand;
import pe.edu.upc.prime.platform.iam.interfaces.rest.resources.CreateUserRequest;
import pe.edu.upc.prime.platform.iam.interfaces.rest.resources.UpdateUserRequest;
import pe.edu.upc.prime.platform.iam.interfaces.rest.resources.UserResponse;

/**
 * Assembler for converting between User-related requests, commands, entities, and responses.
 */
public class UserAssembler {

    /**
     * Converts a CreateUserRequest to a CreateUserCommand.
     *
     * @param request the CreateUserRequest to convert
     * @return the corresponding CreateUserCommand
     */
    public static CreateUserCommand toCommandFromRequest(CreateUserRequest request) {
        return new CreateUserCommand( request.name(),
                request.lastName(), request.dni(), request.phoneNumber(), request.idLocation());
    }

    /**
     * Converts an UpdateUserRequest to an UpdateUserCommand.
     *
     * @param userId the ID of the user to update
     * @param request the UpdateUserRequest to convert
     * @return the corresponding UpdateUserCommand
     */
    public static UpdateUserCommand toCommandFromRequest(String userId,
                                                            UpdateUserRequest request) {
        return new UpdateUserCommand(userId, request.name(),
                request.lastName(), request.dni(), request.phoneNumber(), request.idLocation());
    }

    /**
     * Converts a User entity to a UserResponse.
     *
     * @param entity the User entity to convert
     * @return the corresponding UserResponse
     */
    public static UserResponse toResponseFromEntity(User entity) {

        return new UserResponse(entity.getId().toString(), entity.getName(),
                entity.getLastName(), entity.getDni(),
                entity.getPhoneNumber(), entity.getIdLocation());
    }
}
