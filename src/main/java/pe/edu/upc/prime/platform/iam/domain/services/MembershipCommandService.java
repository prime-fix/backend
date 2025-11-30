package pe.edu.upc.prime.platform.iam.domain.services;

import pe.edu.upc.prime.platform.iam.domain.model.entities.Membership;
import pe.edu.upc.prime.platform.iam.domain.model.commands.CreateMembershipCommand;
import pe.edu.upc.prime.platform.iam.domain.model.commands.DeleteMembershipCommand;
import pe.edu.upc.prime.platform.iam.domain.model.commands.UpdateMembershipCommand;

import java.util.Optional;

/**
 * Service interface for handling membership-related commands.
 */
public interface MembershipCommandService {
    /**
     * Handles the creation of a new membership based on the provided command.
     *
     * @param command the command containing the membership information
     * @return the ID of the newly created membership
     */
    Optional<Membership> handle(CreateMembershipCommand command);

    /**
     * Handles the update of a membership based on the provided command.
     *
     * @param command the command containing the updated membership information
     * @return an Optional containing the updated Membership if successful, or empty if not found
     */
    Optional<Membership> handle(UpdateMembershipCommand command);

    /**
     * Handles the deletion of a membership based on the provided command.
     *
     * @param command the command containing the ID of the membership to be deleted
     */
    void handle(DeleteMembershipCommand command);
}
