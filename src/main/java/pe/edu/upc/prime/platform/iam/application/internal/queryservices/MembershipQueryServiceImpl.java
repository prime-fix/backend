package pe.edu.upc.prime.platform.iam.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.prime.platform.iam.domain.model.entities.Membership;
import pe.edu.upc.prime.platform.iam.domain.model.queries.GetAllMembershipsQuery;
import pe.edu.upc.prime.platform.iam.domain.model.queries.GetMembershipByIdQuery;
import pe.edu.upc.prime.platform.iam.domain.services.MembershipQueryService;
import pe.edu.upc.prime.platform.iam.infrastructure.persistence.jpa.repositories.MembershipRepository;
import pe.edu.upc.prime.platform.shared.domain.exceptions.NotFoundIdException;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the MembershipQueryService interface.
 */
@Service
public class MembershipQueryServiceImpl implements MembershipQueryService {
    /**
     * The repository to access membership data.
     */
    private final MembershipRepository membershipRepository;

    /**
     * Constructor for MembershipQueryServiceImpl.
     * @param membershipRepository the repository to access membership data
     */
    public MembershipQueryServiceImpl(MembershipRepository membershipRepository) {
        this.membershipRepository = membershipRepository;
    }

    /**
     * Get all memberships.
     *
     * @param query the query to get all memberships
     * @return a list of all memberships
     */
    @Override
    public List<Membership> handle(GetAllMembershipsQuery query) {
        return this.membershipRepository.findAll();
    }

    /**
     * Get a membership by its ID.
     *
     * @param query the query containing the membership ID
     * @return an optional containing the membership if found
     */
    @Override
    public Optional<Membership> handle(GetMembershipByIdQuery query) {
        return Optional.ofNullable(this.membershipRepository.findById(query.membershipId())
        .orElseThrow(() -> new NotFoundIdException(Membership.class, query.membershipId())));
    }
}
