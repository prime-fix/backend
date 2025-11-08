package pe.edu.upc.prime.platform.iam.interfaces.rest.assemblers;

import pe.edu.upc.prime.platform.iam.domain.model.aggregates.Membership;
import pe.edu.upc.prime.platform.iam.domain.model.commands.CreateMembershipCommand;
import pe.edu.upc.prime.platform.iam.domain.model.commands.UpdateMembershipCommand;
import pe.edu.upc.prime.platform.iam.domain.model.valueobjects.MembershipDescription;
import pe.edu.upc.prime.platform.iam.interfaces.rest.resources.CreateMembershipRequest;
import pe.edu.upc.prime.platform.iam.interfaces.rest.resources.MembershipResponse;
import pe.edu.upc.prime.platform.iam.interfaces.rest.resources.UpdateMembershipRequest;

/**
 * Assembler for Membership-related conversions between REST requests/responses and domain commands/entities.
 */
public class MembershipAssembler {
    /**
     * Converts a CreateMembershipRequest to a CreateMembershipCommand.
     *
     * @param request the CreateMembershipRequest object
     * @return the corresponding CreateMembershipCommand object
     */
    public static CreateMembershipCommand toCommandFromRequest(CreateMembershipRequest request) {
        return new CreateMembershipCommand(request.idMembership(),
                new MembershipDescription(request.description()), request.started(), request.over());
    }

    /**
     * Converts an UpdateMembershipRequest to an UpdateMembershipCommand.
     *
     * @param idMembership the ID of the membership to be updated
     * @param request the UpdateMembershipRequest object
     * @return the corresponding UpdateMembershipCommand object
     */
    public static UpdateMembershipCommand toCommandFromRequest(String idMembership,UpdateMembershipRequest request) {
        return new UpdateMembershipCommand(idMembership,
                new MembershipDescription(request.description()), request.started(), request.over());
    }

    /**
     * Converts a Membership entity to a MembershipResponse.
     *
     * @param entity the Membership entity
     * @return the corresponding MembershipResponse object
     */
    public static MembershipResponse toResponseFromEntity(Membership entity) {
        return new MembershipResponse(entity.getIdMembership(), entity.getMembershipDescription().description(),
                entity.getStarted(), entity.getOver());
    }
}
