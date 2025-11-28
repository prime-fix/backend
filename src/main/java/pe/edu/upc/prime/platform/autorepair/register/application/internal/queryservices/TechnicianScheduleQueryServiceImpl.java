package pe.edu.upc.prime.platform.autorepair.register.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.prime.platform.autorepair.register.domain.model.aggregates.TechnicianSchedule;
import pe.edu.upc.prime.platform.autorepair.register.domain.model.queries.GetAllTechnicianSchedulesQuery;
import pe.edu.upc.prime.platform.autorepair.register.domain.model.queries.GetTechnicianScheduleByIdQuery;
import pe.edu.upc.prime.platform.autorepair.register.domain.model.queries.GetSchedulesByTechnicianIdQuery;
import pe.edu.upc.prime.platform.autorepair.register.domain.services.TechnicianScheduleQueryService;
import pe.edu.upc.prime.platform.autorepair.register.infrastructure.persistence.jpa.repositories.TechnicianScheduleRepository;
import pe.edu.upc.prime.platform.shared.domain.exceptions.NotFoundIdException;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the TechnicianScheduleQueryService interface.
 */
@Service
public class TechnicianScheduleQueryServiceImpl implements TechnicianScheduleQueryService {

    private final TechnicianScheduleRepository technicianScheduleRepository;

    /**
     * Constructor for TechnicianScheduleQueryServiceImpl.
     *
     * @param technicianScheduleRepository the repository used to access schedule data
     */
    public TechnicianScheduleQueryServiceImpl(TechnicianScheduleRepository technicianScheduleRepository) {
        this.technicianScheduleRepository = technicianScheduleRepository;
    }

    /**
     * Handles the GetAllTechnicianSchedulesQuery to retrieve all technician schedules.
     *
     * @param query the query to get all schedules
     * @return a list of all technician schedules
     */
    @Override
    public List<TechnicianSchedule> handle(GetAllTechnicianSchedulesQuery query) {
        return technicianScheduleRepository.findAll();
    }

    /**
     * Handles the GetTechnicianScheduleByIdQuery to retrieve a schedule by its ID.
     *
     * @param query the query containing the schedule ID
     * @return an Optional containing the schedule if found
     */
    @Override
    public Optional<TechnicianSchedule> handle(GetTechnicianScheduleByIdQuery query) {
        return Optional.ofNullable(
                technicianScheduleRepository.findById(query.idTechnicianSchedule())
                        .orElseThrow(() -> new NotFoundIdException(TechnicianSchedule.class, query.idTechnicianSchedule()))
        );
    }

    /**
     * Handles the GetSchedulesByTechnicianIdQuery to retrieve all schedules for a given technician.
     *
     * @param query the query containing the technician ID
     * @return a list of schedules belonging to the specified technician
     */
    @Override
    public List<TechnicianSchedule> handle(GetSchedulesByTechnicianIdQuery query) {
        return technicianScheduleRepository.findByTechnician_Id(Long.valueOf(query.idTechnician()));
    }
}
