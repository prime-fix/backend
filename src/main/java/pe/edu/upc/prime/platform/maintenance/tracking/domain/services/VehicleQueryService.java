package pe.edu.upc.prime.platform.maintenance.tracking.domain.services;

import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.aggregates.Vehicle;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.queries.ExistsVehicleByIdQuery;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.queries.GetAllVehiclesQuery;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.queries.GetVehicleByIdQuery;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.queries.GetVehiclesByMaintenanceStatusQuery;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for handling vehicle-related queries.
 */
public interface VehicleQueryService {
    /**
     * Handle the query to get all vehicles.
     *
     * @param query the query to get all vehicles
     * @return a list of all vehicles
     */
    List<Vehicle> handle(GetAllVehiclesQuery query);

    /**
     * Handle the query to get a vehicle by its ID.
     *
     * @param query the query containing the vehicle ID
     * @return an optional vehicle matching the ID
     */
    Optional<Vehicle> handle(GetVehicleByIdQuery query);

    /**
     * Handle the query to get vehicles by their maintenance status.
     *
     * @param query the query containing the maintenance status
     * @return a list of vehicles matching the maintenance status
     */
    List<Vehicle> handle(GetVehiclesByMaintenanceStatusQuery query);

    /**
     * Handle the query to check if a vehicle exists by its ID.
     *
     * @param query the query containing the vehicle ID
     * @return true if the vehicle exists, false otherwise
     */
    boolean handle(ExistsVehicleByIdQuery query);
}
