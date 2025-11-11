package pe.edu.upc.prime.platform.autorepair.register.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.prime.platform.autorepair.register.domain.model.aggregates.TechnicianSchedule;

import java.util.List;

/**
 * Repository interface for managing TechnicianSchedule entities.
 *
 * <p>This interface extends JpaRepository to provide CRUD operations
 * and custom query methods for the TechnicianSchedule entity.</p>
 */
@Repository
public interface TechnicianScheduleRepository extends JpaRepository<TechnicianSchedule, String> {

    /**
     * Custom query method to find all schedules for a specific technician.
     *
     * @param technicianId the ID of the technician
     * @return a list of schedules belonging to the specified technician
     */
    List<TechnicianSchedule> findByTechnician_IdTechnician(String technicianId);

    /**
     * Custom query method to find all active schedules.
     *
     * @return a list of schedules that are currently active
     */
    List<TechnicianSchedule> findByIsActiveTrue();

    /**
     * Custom query method to check if a technician already has a schedule on a given day.
     *
     * @param technicianId the ID of the technician
     * @param dayOfWeek the day of the week
     * @return true if the technician already has a schedule on the specified day, false otherwise
     */
    boolean existsByTechnician_IdTechnicianAndDayOfWeek(String technicianId, String dayOfWeek);
}
