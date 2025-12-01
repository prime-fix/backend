package pe.edu.upc.prime.platform.maintenance.tracking.interfaces.acl;

import org.springframework.stereotype.Service;
import pe.edu.upc.prime.platform.maintenance.tracking.application.internal.outboundservices.acl.ExternalIamService;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.queries.ExistsNotificationByIdQuery;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.queries.ExistsVehicleByIdQuery;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.services.NotificationCommandService;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.services.NotificationQueryService;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.services.VehicleCommandService;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.services.VehicleQueryService;
import pe.edu.upc.prime.platform.maintenance.tracking.interfaces.rest.assemblers.NotificationAssembler;
import pe.edu.upc.prime.platform.maintenance.tracking.interfaces.rest.assemblers.VehicleAssembler;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Facade for maintenance tracking operations, providing methods to manage vehicles and notifications.
 */
@Service
public class MaintenanceTrackingFacade {
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
    public MaintenanceTrackingFacade(VehicleCommandService vehicleCommandService,
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
     * Updates an existing vehicle with the provided details.
     *
     * @param vehicleId the ID of the vehicle to update
     * @param color the color of the vehicle
     * @param model the model of the vehicle
     * @param userId the ID of the user associated with the vehicle
     * @param vehicleBrand the brand of the vehicle
     * @param vehiclePlate the plate number of the vehicle
     * @param vehicleType the type of the vehicle
     * @param maintenanceStatus the maintenance status of the vehicle
     * @return the ID of the updated vehicle, or 0L if the update failed
     */
    public Long updateVehicle(Long vehicleId, String color, String model, Long userId,
                              String vehicleBrand, String vehiclePlate,
                              String vehicleType, Integer maintenanceStatus) {
        var updateVehicleCommand = VehicleAssembler.toCommandFromValues(
                vehicleId, color, model, userId,
                vehicleBrand, vehiclePlate,
                vehicleType, maintenanceStatus);

        var optionalVehicle = this.vehicleCommandService.handle(updateVehicleCommand);
        if (optionalVehicle.isEmpty()) {
            return 0L;
        }
        return optionalVehicle.get().getId();
    }

    /**
     * Creates a new notification with the provided details.
     *
     * @param message the notification message
     * @param read the read status
     * @param vehicleId the associated vehicle ID
     * @param sent the date the notification was sent
     * @return the ID of the created notification, or 0L if creation failed
     */
    public Long createNotification(String message, Boolean read,
                                   Long vehicleId, LocalDate sent) {
        var createNotificationCommand = NotificationAssembler.toCommandFromValues(
                message, read, vehicleId, sent);

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
