package pe.edu.upc.prime.platform.autorepair.register.domain.services;

import pe.edu.upc.prime.platform.autorepair.register.domain.model.aggregates.TechnicianSchedule;
import pe.edu.upc.prime.platform.autorepair.register.domain.model.queries.GetAllTechnicianSchedulesQuery;
import pe.edu.upc.prime.platform.autorepair.register.domain.model.queries.GetTechnicianScheduleByIdQuery;
import pe.edu.upc.prime.platform.autorepair.register.domain.model.queries.GetSchedulesByTechnicianIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for handling technician schedule-related queries.
 */
public interface TechnicianScheduleQueryService {

    /**
     * Handles the query to get all technician schedules.
     *
     * @param query the query to retrieve all schedules
     * @return a list of all technician schedules
     */
    List<TechnicianSchedule> handle(GetAllTechnicianSchedulesQuery query);

    /**
     * Handles the query to get a technician schedule by its ID.
     *
     * @param query the query containing the schedule ID
     * @return an optional technician schedule matching the ID
     */
    Optional<TechnicianSchedule> handle(GetTechnicianScheduleByIdQuery query);

    /**
     * Handles the query to get all schedules of a specific technician.
     *
     * @param query the query containing the technician ID
     * @return a list of schedules belonging to the technician
     */
    List<TechnicianSchedule> handle(GetSchedulesByTechnicianIdQuery query);
}
