package pe.edu.upc.prime.platform.iam.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.prime.platform.iam.domain.model.entities.Membership;
import pe.edu.upc.prime.platform.iam.domain.model.valueobjects.MembershipDescription;

/**
 * Repository interface for Membership entities.
 */
@Repository
public interface MembershipRepository extends JpaRepository<Membership, Long> {
}
