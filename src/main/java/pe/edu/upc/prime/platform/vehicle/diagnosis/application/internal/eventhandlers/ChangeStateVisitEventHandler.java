package pe.edu.upc.prime.platform.vehicle.diagnosis.application.internal.eventhandlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import pe.edu.upc.prime.platform.shared.domain.model.valueobjects.MaintenanceStatus;
import pe.edu.upc.prime.platform.vehicle.diagnosis.application.internal.outboundservices.acl.ExternalMaintenanceTrackingServiceFromVehicleDiagnosis;
import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.events.ChangeStateVisitEvent;
import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.valueobjects.StateVisit;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Event handler for ChangeStateVisitEvent.
 */
@Service
public class ChangeStateVisitEventHandler {
    /**
     * External service to interact with Maintenance Tracking
     */
    private final ExternalMaintenanceTrackingServiceFromVehicleDiagnosis externalMaintenanceTrackingService;

    /**
     * Logger for logging events and errors.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ChangeStateVisitEventHandler.class);

    /**
     * Constructor for ChangeStateVisitEventHandler.
     *
     * @param externalMaintenanceTrackingService the external maintenance tracking service from vehicle diagnosis
     */
    public ChangeStateVisitEventHandler(ExternalMaintenanceTrackingServiceFromVehicleDiagnosis externalMaintenanceTrackingService) {
        this.externalMaintenanceTrackingService = externalMaintenanceTrackingService;
    }

    /**
     * Handles the ChangeStateVisitEvent.
     *
     * @param event the change state visit event
     */
    @EventListener
    public void on(ChangeStateVisitEvent event) {
        var vehicleId = event.getVehicleId();
        var updatedState = false;

        if (!externalMaintenanceTrackingService.existsVehicleById(vehicleId)) {
            LOGGER.error("Vehicle with ID: {} not found.", event.getVehicleId());
            return;
        } else {
            if (event.getStateVisit() == StateVisit.SCHEDULED_VISIT) {
                updatedState = externalMaintenanceTrackingService.updateVehicleByMaintenanceStatus(vehicleId, MaintenanceStatus.WAITING);
            } else if (event.getStateVisit() == StateVisit.CANCELLED_VISIT) {
                updatedState = externalMaintenanceTrackingService.updateVehicleByMaintenanceStatus(vehicleId, MaintenanceStatus.NOT_BEING_SERVICED);
            }
            if (updatedState) {
                LOGGER.info("Vehicle with ID: {} has been updated to maintenance status based on state visit: {}", vehicleId, event.getStateVisit());
            } else {
                LOGGER.error("Failed to update Vehicle with ID: {} for state visit: {}", vehicleId, event.getStateVisit());
            }
        }

        // Create a notification when state visit changes
        LOGGER.info("Handling ChangeStateVisitEvent for Vehicle ID: {}", vehicleId);
        var notificationId = externalMaintenanceTrackingService
                .createNotification("State visit changed to: " + event.getStateVisit(), vehicleId, LocalDate.now());

        // Log the result of the notification creation
        if (Objects.isNull(notificationId)) {
            LOGGER.error("Failed to create notification for Vehicle ID: {}", vehicleId);
        } else {
            LOGGER.info("Notification created with ID: {} for Vehicle ID: {}", notificationId, vehicleId);
        }
    }
}
