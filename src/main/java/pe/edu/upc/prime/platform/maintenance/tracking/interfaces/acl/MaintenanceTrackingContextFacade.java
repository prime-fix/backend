package pe.edu.upc.prime.platform.maintenance.tracking.interfaces.acl;

import org.springframework.stereotype.Service;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.commands.UpdateVehicleCommand;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.queries.ExistsNotificationByIdQuery;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.queries.ExistsVehicleByIdQuery;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.queries.GetVehicleByIdQuery;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.services.NotificationCommandService;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.services.NotificationQueryService;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.services.VehicleCommandService;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.services.VehicleQueryService;
import pe.edu.upc.prime.platform.maintenance.tracking.interfaces.rest.assemblers.NotificationAssembler;
import pe.edu.upc.prime.platform.maintenance.tracking.interfaces.rest.assemblers.VehicleAssembler;
import pe.edu.upc.prime.platform.shared.domain.model.valueobjects.MaintenanceStatus;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Facade for maintenance tracking operations, providing methods to manage vehicles and notifications.
 */
@Service
public class MaintenanceTrackingContextFacade {
    /**
     * The vehicle command service for handling vehicle-related commands.
     */
    private final VehicleCommandService vehicleCommandService;
    /**
     * The vehicle query service for handling vehicle-related queries.
     */
    private final VehicleQueryService vehicleQueryService;
    /**
     * The notification command service for handling notification-related commands.
     */
    private final NotificationCommandService notificationCommandService;
    /**
     * The notification query service for handling notification-related queries.
     */
    private final NotificationQueryService notificationQueryService;

    /**
     * Constructs a MaintenanceTrackingFacade with the specified command and query services.
     *
     * @param vehicleCommandService the service for handling vehicle commands
     * @param vehicleQueryService  the service for handling vehicle queries
     * @param notificationCommandService the service for handling notification commands
     * @param notificationQueryService  the service for handling notification queries
     */
    public MaintenanceTrackingContextFacade(VehicleCommandService vehicleCommandService,
                                            VehicleQueryService vehicleQueryService,
                                            NotificationCommandService notificationCommandService,
                                            NotificationQueryService notificationQueryService) {
        this.vehicleCommandService = vehicleCommandService;
        this.vehicleQueryService = vehicleQueryService;
        this.notificationCommandService = notificationCommandService;
        this.notificationQueryService = notificationQueryService;
    }

    /**
     * Checks if a vehicle with the given ID exists.
     *
     * @param vehicleId the ID of the vehicle to check
     * @return true if the vehicle exists, false otherwise
     */
    public boolean existsVehicleById(Long vehicleId) {
        var existsVehicleByIdQuery = new ExistsVehicleByIdQuery(vehicleId);
        return this.vehicleQueryService.handle(existsVehicleByIdQuery);
    }

    /**
     * Checks if a notification with the given ID exists.
     *
     * @param notificationId the ID of the notification to check
     * @return true if the notification exists, false otherwise
     */
    public boolean existsNotificationById(Long notificationId) {
        var existsNotificationByIdQuery = new ExistsNotificationByIdQuery(notificationId);
        return this.notificationQueryService.handle(existsNotificationByIdQuery);
    }

    /**
     * Updates the MaintenanceStatus of an existing vehicle.
     *
     * @param vehicleId the ID of the vehicle to update
     * @param maintenanceStatus the maintenance status of the vehicle
     * @return true if the update was successful, false otherwise
     */
    public boolean updateVehicleByMaintenanceStatus(Long vehicleId, MaintenanceStatus maintenanceStatus) {
        var getVehicleByIdQuery = new GetVehicleByIdQuery(vehicleId);

        var optionalVehicle = this.vehicleQueryService.handle(getVehicleByIdQuery);
        if (optionalVehicle.isPresent()) {
            var vehicle = optionalVehicle.get();
            var updateVehicleCommand = new UpdateVehicleCommand(vehicle.getId(), vehicle.getColor(), vehicle.getModel(),
                    vehicle.getUserId(), vehicle.getVehicleInformation(), maintenanceStatus);
            return vehicleCommandService.handle(updateVehicleCommand).isPresent();
        }
        return false;
    }

    /**
     * Creates a new notification with the provided details.
     *
     * @param message the notification message
     * @param vehicleId the associated vehicle ID
     * @param sent the date the notification was sent
     * @return the ID of the created notification, or 0L if creation failed
     */
    public Long createNotification(String message, Long vehicleId, LocalDate sent) {
        var createNotificationCommand = NotificationAssembler.toCommandFromValues(
                message, vehicleId, sent);

        var notificationId = this.notificationCommandService.handle(createNotificationCommand);
        if (Objects.isNull(notificationId)) {
            return 0L;
        }
        return notificationId;
    }

    /**
     * Updates an existing notification with the provided details.
     *
     * @param notificationId the ID of the notification to update
     * @param message the notification message
     * @param read the read status
     * @param vehicleId the associated vehicle ID
     * @param sent the date the notification was sent
     * @return the ID of the updated notification, or 0L if the update failed
     */
    public Long updateNotification(Long notificationId, String message, Boolean read,
                                   Long vehicleId, LocalDate sent) {
        var updateNotificationCommand = NotificationAssembler.toCommandFromValues(
                notificationId, message, read, vehicleId, sent);

        var optionalNotification = this.notificationCommandService.handle(updateNotificationCommand);
        if (optionalNotification.isEmpty()) {
            return 0L;
        }
        return optionalNotification.get().getId();
    }


}
