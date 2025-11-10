package pe.edu.upc.prime.platform.iam.domain.services;


import pe.edu.upc.prime.platform.iam.domain.model.aggregates.Role;
import pe.edu.upc.prime.platform.iam.domain.model.commands.CreateRoleCommand;
import pe.edu.upc.prime.platform.iam.domain.model.commands.DeleteRoleCommand;
import pe.edu.upc.prime.platform.iam.domain.model.commands.UpdateRoleCommand;

import java.util.Optional;

/**
 * Service interface for handling role-related commands.
 */
public interface RoleCommandService {
    /**
     * Handles the creation of a new role based on the provided command.
     *
     * @param command the command containing the role information
     * @return the ID of the newly created role
     */
    String handle(CreateRoleCommand command);

    /**
     * Handles the update of a role based on the provided command.
     *
     * @param command the command containing the updated role information
     * @return an Optional containing the updated Role if successful, or empty if not found
     */
    Optional<Role> handle(UpdateRoleCommand command);

    /**
     * Handles the deletion of a role based on the provided command.
     * @param command the command containing the ID of the role to be deleted
     */
    void handle(DeleteRoleCommand command);
}
