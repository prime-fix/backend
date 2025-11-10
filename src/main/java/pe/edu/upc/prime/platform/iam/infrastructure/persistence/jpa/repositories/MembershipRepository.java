package pe.edu.upc.prime.platform.iam.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.prime.platform.iam.domain.model.aggregates.Membership;
import pe.edu.upc.prime.platform.iam.domain.model.valueobjects.MembershipDescription;

/**
 * Repository interface for Membership entities.
 */
@Repository
public interface MembershipRepository extends JpaRepository<Membership, String> {
    /**
     * Check if a membership exists by its description.
     *
     * @param membershipDescription the description of the membership
     * @return true if a membership with the given description exists, false otherwise
     */
    boolean existsByMembershipDescription(MembershipDescription membershipDescription);

    /**
     * Check if a membership exists by its description, excluding a specific membership ID.
     * @param membershipDescription the description of the membership
     * @param idMembership the ID of the membership to exclude
     * @return true if a membership with the given description exists excluding the specified membership ID, false otherwise
     */
    boolean existsByMembershipDescriptionAndIdMembershipIsNot(MembershipDescription membershipDescription, String idMembership);
}
