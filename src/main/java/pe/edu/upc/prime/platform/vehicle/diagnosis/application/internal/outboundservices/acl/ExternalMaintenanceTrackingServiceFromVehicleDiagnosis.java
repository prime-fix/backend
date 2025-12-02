package pe.edu.upc.prime.platform.vehicle.diagnosis.application.internal.outboundservices.acl;

import org.springframework.stereotype.Service;
import pe.edu.upc.prime.platform.maintenance.tracking.interfaces.acl.MaintenanceTrackingContextFacade;

/**
 * Service class for interacting with external Maintenance Tracking services via MaintenanceTrackingContextFacade.
 */
@Service
public class ExternalMaintenanceTrackingServiceFromVehicleDiagnosis {
    /**
     * The MaintenanceTrackingContextFacade for maintenance tracking operations.
     */
    private final MaintenanceTrackingContextFacade maintenanceTrackingContextFacade;

    /**
     * Constructor for ExternalMaintenanceTrackingService.
     *
     * @param maintenanceTrackingContextFacade the MaintenanceTrackingContextFacade to be used for maintenance tracking operations
     */
    public ExternalMaintenanceTrackingServiceFromVehicleDiagnosis(MaintenanceTrackingContextFacade maintenanceTrackingContextFacade) {
        this.maintenanceTrackingContextFacade = maintenanceTrackingContextFacade;
    }

    /**
     * Check if a vehicle exists by its ID.
     *
     * @param vehicleId the ID of the vehicle to check
     * @return true if the vehicle exists, false otherwise
     */
    public boolean existsVehicleById(Long vehicleId) {
        return this.maintenanceTrackingContextFacade.existsVehicleById(vehicleId);
    }
}
