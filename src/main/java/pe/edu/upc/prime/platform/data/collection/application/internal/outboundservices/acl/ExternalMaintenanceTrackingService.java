package pe.edu.upc.prime.platform.data.collection.application.internal.outboundservices.acl;

import org.springframework.stereotype.Service;
import pe.edu.upc.prime.platform.maintenance.tracking.interfaces.acl.MaintenanceTrackingFacade;

/**
 * Service class for interacting with external maintenance tracking services via MaintenanceTrackingFacade.
 */
@Service
public class ExternalMaintenanceTrackingService {
    /**
     * Constructor for ExternalMaintenanceTrackingService.
     */
    private final MaintenanceTrackingFacade maintenanceTrackingFacade;

    /**
     * Constructor for ExternalMaintenanceTrackingService.
     *
     * @param maintenanceTrackingFacade the MaintenanceTrackingFacade to be used for maintenance tracking operations
     */
    public ExternalMaintenanceTrackingService(MaintenanceTrackingFacade maintenanceTrackingFacade) {
        this.maintenanceTrackingFacade = maintenanceTrackingFacade;
    }

    /**
     * Checks if a maintenance record with the given ID exists.
     *
     * @param recordId the ID of the maintenance record to check
     * @return true if the maintenance record exists, false otherwise
     */
    public boolean existsVehicleById(Long recordId) {
        return this.maintenanceTrackingFacade.existsVehicleById(recordId);
    }
}
