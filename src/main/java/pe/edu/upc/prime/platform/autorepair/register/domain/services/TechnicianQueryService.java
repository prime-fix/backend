package pe.edu.upc.prime.platform.autorepair.register.domain.services;

import pe.edu.upc.prime.platform.autorepair.register.domain.model.aggregates.Technician;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for handling Technician-related queries.
 */
public interface TechnicianQueryService {

    /**
     * Retrieves all technicians.
     *
     * @return A list of all technicians.
     */
    List<Technician> handleGetAll();

    /**
     * Retrieves a technician by ID.
     *
     * @param idTechnician The ID of the technician.
     * @return An Optional containing the technician if found.
     */
    Optional<Technician> handleGetById(String idTechnician);

    /**
     * Retrieves all technicians belonging to a specific AutoRepair.
     *
     * @param idAutoRepair The AutoRepair ID.
     * @return A list of technicians belonging to that AutoRepair.
     */
    List<Technician> handleGetByAutoRepairId(long idAutoRepair);
}
