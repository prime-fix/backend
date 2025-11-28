package pe.edu.upc.prime.platform.iam.application.internal.commandservices;

import jakarta.persistence.PersistenceException;
import org.springframework.stereotype.Service;
import pe.edu.upc.prime.platform.iam.domain.model.aggregates.Membership;
import pe.edu.upc.prime.platform.iam.domain.model.commands.CreateMembershipCommand;
import pe.edu.upc.prime.platform.iam.domain.model.commands.DeleteMembershipCommand;
import pe.edu.upc.prime.platform.iam.domain.model.commands.UpdateMembershipCommand;
import pe.edu.upc.prime.platform.iam.domain.services.MembershipCommandService;
import pe.edu.upc.prime.platform.iam.infrastructure.persistence.jpa.repositories.MembershipRepository;
import pe.edu.upc.prime.platform.shared.domain.exceptions.NotFoundIdException;

import java.util.Optional;

/**
 * Implementation of the MembershipCommandService interface.
 */
@Service
public class MembershipCommandServiceImpl implements MembershipCommandService {
    /**
     * The repository to access membership data.
     */
    private final MembershipRepository membershipRepository;

    /**
     * Constructor for MembershipCommandServiceImpl.
     *
     * @param membershipRepository the repository to access membership data
     */
    public MembershipCommandServiceImpl(MembershipRepository membershipRepository) {
        this.membershipRepository = membershipRepository;
    }

    /**
     * Handles the creation of a new membership based on the provided command.
     *
     * @param command the command containing the membership information
     * @return the ID of the newly created membership
     */
    @Override
    public String handle(CreateMembershipCommand command) {
       /* var membershipId = command.idMembership();
        var membershipDescription = command.membershipDescription();

        if (this.membershipRepository.existsById(membershipId)) {
            throw new IllegalArgumentException("[MembershipCommandServiceImpl] Membership with the same id "
                    + membershipId + " already exists.");
        }

        if (this.membershipRepository.existsByMembershipDescription(membershipDescription)) {
            throw new IllegalArgumentException("[MembershipCommandServiceImpl] Membership with the same description " +
                    membershipDescription.description() + " already exists.");
        }

        var membership = new Membership(command);
        try {
            this.membershipRepository.save(membership);
        } catch (Exception e) {
            throw new PersistenceException("[MembershipCommandServiceImpl] Error while saving membership: "
                    + e.getMessage());
        }
        return membership.getIdMembership();
    */


        var membership = new Membership(command);
        try{
            this.membershipRepository.save(membership);
        } catch (Exception e){
            throw new IllegalStateException(e);
        }
        return membership.getId().toString();
    }


    /**
     * Handles the update of an existing membership based on the provided command.
     *
     * @param command the command containing the updated membership information
     * @return an Optional containing the updated membership if successful
     */
    @Override
    public Optional<Membership> handle(UpdateMembershipCommand command) {
        var membershipId = command.idMembership();
        var membershipDescription = command.membershipDescription();

        if (!this.membershipRepository.existsById(membershipId)) {
            throw new NotFoundIdException(Membership.class, membershipId);
        }

        if (this.membershipRepository.existsByMembershipDescriptionAndIdIsNot(membershipDescription, Long.valueOf(membershipId))) {
            throw new IllegalArgumentException("[MembershipCommandServiceImpl] Membership with the same description " +
                    membershipDescription.description() + " already exists.");
        }

        var membershipToUpdate = this.membershipRepository.findById(membershipId).get();
        membershipToUpdate.updateMembership(command);

        try {
            this.membershipRepository.save(membershipToUpdate);
            return Optional.of(membershipToUpdate);
        } catch (Exception e) {
            throw new PersistenceException("[MembershipCommandServiceImpl] Error while updating membership: "
                    + e.getMessage());
        }
    }

    /**
     * Handles the deletion of an existing membership based on the provided command.
     *
     * @param command the command containing the ID of the membership to be deleted
     */
    @Override
    public void handle(DeleteMembershipCommand command) {
        if (!this.membershipRepository.existsById(command.idMembership())) {
            throw new NotFoundIdException(Membership.class, command.idMembership());
        }

        try {
            this.membershipRepository.deleteById(command.idMembership());
        } catch (Exception e) {
            throw new PersistenceException("[MembershipCommandServiceImpl] Error while deleting membership: "
                    + e.getMessage());
        }
    }
}
