package pe.edu.upc.prime.platform.iam.domain.services;

import pe.edu.upc.prime.platform.iam.domain.model.entities.Membership;
import pe.edu.upc.prime.platform.iam.domain.model.queries.GetAllMembershipsQuery;
import pe.edu.upc.prime.platform.iam.domain.model.queries.GetMembershipByIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for handling membership-related queries.
 */
public interface MembershipQueryService {
    /**
     * Handle the query to get all memberships.
     *
     * @param query the query to get all memberships
     * @return a list of all memberships
     */
    List<Membership> handle(GetAllMembershipsQuery query);

    /**
     * Handle the query to get a membership by its ID.
     *
     * @param query the query containing the membership ID
     * @return an optional membership matching the ID
     */
    Optional<Membership> handle(GetMembershipByIdQuery query);
}
