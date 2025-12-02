package pe.edu.upc.prime.platform.maintenance.tracking.application.internal.eventhandlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.commands.CreateNotificationCommand;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.commands.CreateVehicleCommand;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.commands.UpdateVehicleCommand;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.queries.GetVehicleByIdQuery;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.valueobjects.MaintenanceStatus;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.services.NotificationCommandService;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.services.VehicleCommandService;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.services.VehicleQueryService;
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
     * Service for handling notification commands.
     */
    private final NotificationCommandService notificationCommandService;

    /**
     * Service for handling vehicle commands.
     */
    private final VehicleCommandService vehicleCommandService;

    /**
     * Service for querying vehicle information.
     */
    private final VehicleQueryService vehicleQueryService;

    /**
     * Logger for logging events and errors.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ChangeStateVisitEventHandler.class);

    /**
     * Constructor for ChangeStateVisitEventHandler.
     *
     * @param notificationCommandService the notification command service
     * @param vehicleCommandService      the vehicle command service
     */
    public ChangeStateVisitEventHandler(NotificationCommandService notificationCommandService,
                                        VehicleCommandService vehicleCommandService,
                                        VehicleQueryService vehicleQueryService) {
        this.notificationCommandService = notificationCommandService;
        this.vehicleCommandService = vehicleCommandService;
        this.vehicleQueryService = vehicleQueryService;
    }

    /**
     * Handles the ChangeStateVisitEvent.
     *
     * @param event the change state visit event
     */
    @EventListener
    public void on(ChangeStateVisitEvent event) {
        var optionalVehicle = vehicleQueryService.handle(new GetVehicleByIdQuery(event.getVehicleId()));

        if (optionalVehicle.isEmpty()) {
            LOGGER.error("Vehicle with ID: {} not found.", event.getVehicleId());
            return;
        } else {
            var vehicle = optionalVehicle.get();
            if (event.getStateVisit() == StateVisit.SCHEDULED_VISIT) {
                var updateVehicleCommand = new UpdateVehicleCommand(vehicle.getId(), vehicle.getColor(), vehicle.getModel(),
                        vehicle.getUserId(), vehicle.getVehicleInformation(), MaintenanceStatus.WAITING);
                vehicleCommandService.handle(updateVehicleCommand);
            } else if (event.getStateVisit() == StateVisit.CANCELLED_VISIT) {
                var updateVehicleCommand = new UpdateVehicleCommand(vehicle.getId(), vehicle.getColor(), vehicle.getModel(),
                        vehicle.getUserId(), vehicle.getVehicleInformation(), MaintenanceStatus.NOT_BEING_SERVICED);
                vehicleCommandService.handle(updateVehicleCommand);
            }
            LOGGER.info("Vehicle with ID: {} has been updated.", vehicle.getId());
        }

        // Create a notification when state visit changes
        var createNotificationCommand = new CreateNotificationCommand(event.getStateVisit().getNotificationMessage(),
                event.getVehicleId(), LocalDate.now());
        LOGGER.info("Handling ChangeStateVisitEvent for Vehicle ID: {}", event.getVehicleId());

        // Invoke the command service to create the notification
        var notificationId = notificationCommandService.handle(createNotificationCommand);

        // Log the result of the notification creation
        if (Objects.isNull(notificationId)) {
            LOGGER.error("Failed to create notification for Vehicle ID: {}", event.getVehicleId());
        } else {
            LOGGER.info("Notification created with ID: {} for Vehicle ID: {}", notificationId, event.getVehicleId());
        }
    }
}
