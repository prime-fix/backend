package pe.edu.upc.prime.platform.maintenance.tracking.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.aggregates.Vehicle;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.queries.GetAllVehiclesQuery;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.queries.GetVehicleByIdQuery;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.queries.GetVehicleByMaintenanceStatusQuery;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.services.VehicleQueryService;
import pe.edu.upc.prime.platform.maintenance.tracking.infrastructure.persistence.jpa.repositories.VehicleRepository;
import pe.edu.upc.prime.platform.shared.domain.exceptions.NotFoundIdException;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the VehicleQueryService interface.
 */
@Service
public class VehicleQueryServiceImpl implements VehicleQueryService {
    /**
     * The repository to access vehicle data.
     */
    private final VehicleRepository vehicleRepository;

    /**
     * Constructor for VehicleQueryServiceImpl.
     *
     * @param vehicleRepository the repository to access vehicle data
     */
    public VehicleQueryServiceImpl(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    /**
     * Handles the GetAllVehiclesQuery to retrieve all vehicles.
     *
     * @param query the query to get all vehicles
     * @return a list of all vehicles
     */
    @Override
    public List<Vehicle> handle(GetAllVehiclesQuery query) {
        return this.vehicleRepository.findAll();
    }

    /**
     * Handles the GetVehicleByIdQuery to retrieve a vehicle by its ID.
     *
     * @param query the query containing the vehicle ID
     * @return an Optional containing the vehicle if found
     */
    @Override
    public Optional<Vehicle> handle(GetVehicleByIdQuery query) {
        return Optional.ofNullable(this.vehicleRepository.findById(query.idVehicle())
        .orElseThrow(() -> new NotFoundIdException(Vehicle.class, query.idVehicle())));
    }

    /**
     * Handles the GetVehicleByMaintenanceStatusQuery to retrieve vehicles by their maintenance status.
     *
     * @param query the query containing the maintenance status
     * @return a list of vehicles with the specified maintenance status
     */
    @Override
    public List<Vehicle> handle(GetVehicleByMaintenanceStatusQuery query) {
        return this.vehicleRepository.findByMaintenanceStatus(query.maintenanceStatus());
    }
}
