package pe.edu.upc.prime.platform.data.collection.application.internal.outboundservices.acl;

import org.springframework.stereotype.Service;
import pe.edu.upc.prime.platform.maintenance.tracking.interfaces.acl.MaintenanceTrackingContextFacade;

/**
 * Service class for interacting with external maintenance tracking services via MaintenanceTrackingFacade.
 */
@Service
public class ExternalMaintenanceTrackingServiceFromDataCollection {
    /**
     * Constructor for ExternalMaintenanceTrackingService.
     */
    private final MaintenanceTrackingContextFacade maintenanceTrackingContextFacade;

    /**
     * Constructor for ExternalMaintenanceTrackingService.
     *
     * @param maintenanceTrackingContextFacade the MaintenanceTrackingFacade to be used for maintenance tracking operations
     */
    public ExternalMaintenanceTrackingServiceFromDataCollection(MaintenanceTrackingContextFacade maintenanceTrackingContextFacade) {
        this.maintenanceTrackingContextFacade = maintenanceTrackingContextFacade;
    }

    /**
     * Checks if a maintenance record with the given ID exists.
     *
     * @param recordId the ID of the maintenance record to check
     * @return true if the maintenance record exists, false otherwise
     */
    public boolean existsVehicleById(Long recordId) {
        return this.maintenanceTrackingContextFacade.existsVehicleById(recordId);
    }
}
