package pe.edu.upc.prime.platform.autorepair.register.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.prime.platform.autorepair.register.domain.model.aggregates.Technician;
import pe.edu.upc.prime.platform.autorepair.register.domain.model.queries.*;
import pe.edu.upc.prime.platform.autorepair.register.domain.services.TechnicianQueryService;
import pe.edu.upc.prime.platform.autorepair.register.infrastructure.persistence.jpa.repositories.TechnicianRepository;
import pe.edu.upc.prime.platform.shared.domain.exceptions.NotFoundIdException;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the TechnicianQueryService interface.
 */
@Service
public class TechnicianQueryServiceImpl implements TechnicianQueryService {
    /**
     * The repository to access technician data.
     */
    private final TechnicianRepository technicianRepository;

    /**
     * Constructor for TechnicianQueryServiceImpl.
     *
     * @param technicianRepository the repository to access technician data
     */
    public TechnicianQueryServiceImpl(TechnicianRepository technicianRepository) {
        this.technicianRepository = technicianRepository;
    }

    /**
     * Handles the query to get all technician schedules.
     *
     * @param query the query to get all technician schedules
     * @return a list of all technicians
     */
    @Override
    public List<Technician> handle(GetAllTechniciansQuery query) {
        return technicianRepository.findAll();
    }

    /**
     * Handles the query to get a technician by ID.
     *
     * @param query the query to get a technician by ID
     * @return an Optional containing the technician if found, or empty if not found
     */
    @Override
    public Optional<Technician> handle(GetTechnicianByIdQuery query) {
        return Optional.ofNullable(
                technicianRepository.findById(query.technicianId())
                        .orElseThrow(() -> new NotFoundIdException(Technician.class, query.technicianId())));
    }

    /**
     * Handles the query to get technicians by auto repair ID.
     *
     * @param query the query to get technicians by auto repair ID
     * @return a list of technicians associated with the specified auto repair ID
     */
    @Override
    public List<Technician> handle(GetTechniciansByAutoRepairIdQuery query) {
        return technicianRepository.findByAutoRepairId(query.autoRepairId());
    }
}
