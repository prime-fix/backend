package pe.edu.upc.prime.platform.iam.domain.services;


import pe.edu.upc.prime.platform.iam.domain.model.commands.SeedRolesCommand;
import pe.edu.upc.prime.platform.iam.domain.model.entities.Role;

import java.util.Optional;

/**
 * Service interface for handling role-related commands.
 */
public interface RoleCommandService {

    /**
     * Handles the seeding of roles based on the provided command.
     *
     * @param command the command containing the role seeding information
     */
    void handle(SeedRolesCommand command);
}
