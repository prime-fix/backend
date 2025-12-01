package pe.edu.upc.prime.platform.autorepair.register.domain.services;

import pe.edu.upc.prime.platform.autorepair.register.domain.model.aggregates.Technician;
import pe.edu.upc.prime.platform.autorepair.register.domain.model.queries.*;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for handling Technician-related queries.
 */
public interface TechnicianQueryService {
    /**
     * Handle the query to get all technician schedules.
     *
     * @param query the query to get all technician schedules
     * @return a list of all technician schedules
     */
    List<Technician> handle(GetAllTechniciansQuery query);

    /**
     * Handle the query to get a technician by ID.
     *
     * @param query the query to get a technician by ID
     * @return an Optional containing the technician if found, or empty if not found
     */
    Optional<Technician> handle(GetTechnicianByIdQuery query);

    /**
     * Handle the query to get technicians by auto repair ID.
     *
     * @param query the query to get technicians by auto repair ID
     * @return a list of technicians associated with the specified auto repair ID
     */
    List<Technician> handle(GetTechniciansByAutoRepairIdQuery query);
}
