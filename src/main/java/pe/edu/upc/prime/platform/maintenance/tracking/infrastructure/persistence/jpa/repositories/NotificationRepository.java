package pe.edu.upc.prime.platform.maintenance.tracking.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.aggregates.Notification;


/**
 * Repository interface for managing Notification entities.
 *
 * <p>This interface extends JpaRepository to provide CRUD operations for the Notification entity.</p>
 */
@Repository
public interface NotificationRepository extends JpaRepository<Notification, String> {
}
