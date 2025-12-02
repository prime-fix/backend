package pe.edu.upc.prime.platform.maintenance.tracking.interfaces.rest.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.aggregates.Notification;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.commands.DeleteNotificationCommand;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.queries.GetAllNotificationsQuery;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.queries.GetNotificationByIdQuery;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.queries.GetNotificationsByVehicleIdQuery;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.services.NotificationCommandService;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.services.NotificationQueryService;
import pe.edu.upc.prime.platform.maintenance.tracking.interfaces.rest.assemblers.NotificationAssembler;
import pe.edu.upc.prime.platform.maintenance.tracking.interfaces.rest.resources.CreateNotificationRequest;
import pe.edu.upc.prime.platform.maintenance.tracking.interfaces.rest.resources.NotificationResponse;
import pe.edu.upc.prime.platform.maintenance.tracking.interfaces.rest.resources.UpdateNotificationRequest;


import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * REST controller for managing notifications.
 */
@CrossOrigin(origins = "*", methods = { RequestMethod.POST, RequestMethod.GET,
        RequestMethod.PUT, RequestMethod.DELETE })
@RestController
@RequestMapping(value = "/api/v1/notifications", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Notifications", description = "Notification Management Endpoints")
public class NotificationController {
    /**
     * Service for handling notification queries.
     */
    private final NotificationQueryService notificationQueryService;

    /**
     * Service for handling notification commands.
     */
    private final NotificationCommandService notificationCommandService;

    /**
     * Constructor for NotificationController.
     *
     * @param notificationQueryService the notification query service
     * @param notificationCommandService the notification command service
     */
    public NotificationController(NotificationQueryService notificationQueryService,
                                  NotificationCommandService notificationCommandService) {
        this.notificationQueryService = notificationQueryService;
        this.notificationCommandService = notificationCommandService;
    }

    /**
     * Create a new notification.
     *
     * @param request the create notification request
     * @return the response entity with the created notification
     */
    @Operation(summary = "Create a new notification",
            description = "Creates a new notification with the provided data",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Notification data for creation", required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CreateNotificationRequest.class))),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Notification created successfully",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = NotificationResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request - Invalid input data",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = RuntimeException.class)))
            }
    )
    @PostMapping
    public ResponseEntity<NotificationResponse> createNotification(@RequestBody CreateNotificationRequest request) {
        var createNotificationCommand = NotificationAssembler.toCommandFromRequest(request);
        var notificationId = this.notificationCommandService.handle(createNotificationCommand);

        if (Objects.isNull(notificationId) || notificationId.equals(0L)) {
            return ResponseEntity.badRequest().build();
        }

        var getNotificationByIdQuery = new GetNotificationByIdQuery(notificationId);
        var notification = this.notificationQueryService.handle(getNotificationByIdQuery);
        if (notification.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var notificationResponse = NotificationAssembler.toResponseFromEntity(notification.get());
        return new ResponseEntity<>(notificationResponse, HttpStatus.CREATED);
    }

    /**
     * Retrieve all notifications.
     *
     * @return the response entity with the list of notifications
     */
    @Operation(summary = "Retrieve all notifications",
            description = "Retrieves a list of all notifications or filters them by vehicle id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Notifications retrieved successfully",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = NotificationResponse.class))),
            })
    @GetMapping
    public ResponseEntity<List<NotificationResponse>> getAllNotifications(@RequestParam(required = false) Long vehicle_id) {
        List<Notification> notifications;

        if (Objects.isNull(vehicle_id)) {
            var getAllNotificationsQuery = new GetAllNotificationsQuery();
            notifications = this.notificationQueryService.handle(getAllNotificationsQuery);
        } else {
            var getNotificationsByVehicleIdQuery = new GetNotificationsByVehicleIdQuery(vehicle_id);
            notifications = this.notificationQueryService.handle(getNotificationsByVehicleIdQuery);
        }

        var notificationResponse = notifications.stream()
                .map(NotificationAssembler::toResponseFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(notificationResponse);
    }

    /**
     * Retrieve a notification by its ID.
     *
     * @param notification_id the notification ID
     * @return the response entity with the notification
     */
    @Operation(summary = "Retrieve a notification by its ID",
            description = "Retrieves a notification using its unique ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Notification retrieved successfully",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = NotificationResponse.class))),
            })
    @GetMapping("/{notification_id}")
    public ResponseEntity<NotificationResponse> getNotificationById(@PathVariable Long notification_id) {
        var getNotificationByIdQuery = new GetNotificationByIdQuery(notification_id);
        var optionalNotification = this.notificationQueryService.handle(getNotificationByIdQuery);
        if (optionalNotification.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var notificationResponse = NotificationAssembler.toResponseFromEntity(optionalNotification.get());
        return ResponseEntity.ok(notificationResponse);
    }

    /**
     * Update an existing notification.
     *
     * @param notification_id the notification ID
     * @param request the update notification request
     * @return the response entity with the updated notification
     */
    @Operation(summary = "Update an existing notification",
            description = "Update an existing notification with the provided data",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Notification data for update", required = true,
                    content = @Content (
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UpdateNotificationRequest.class))),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Notification updated successfully",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = NotificationResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request - Invalid input data",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = RuntimeException.class)))
            }
    )
    @PutMapping("/{notification_id}")
    public ResponseEntity<NotificationResponse> updateNotification(@PathVariable Long notification_id,
                                                                   @RequestBody UpdateNotificationRequest request) {
        var updateNotificationCommand = NotificationAssembler.toCommandFromRequest(notification_id, request);
        var optionalNotification = this.notificationCommandService.handle(updateNotificationCommand);
        if (optionalNotification.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var notificationResponse = NotificationAssembler.toResponseFromEntity(optionalNotification.get());
        return ResponseEntity.ok(notificationResponse);
    }

    /**
     * Delete a notification by its ID.
     *
     * @param notification_id the notification ID
     * @return the response entity indicating the result of the deletion
     */
    @Operation(summary = "Delete a notification by its ID",
            description = "Deletes a notification using its unique ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Notification deleted successfully"),
                    @ApiResponse(responseCode = "400", description = "Bad request - Invalid notification ID",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = RuntimeException.class)))
            }
    )
    @DeleteMapping("/{notification_id}")
    public ResponseEntity<?> deleteNotification(@PathVariable Long notification_id) {
        var deleteNotificationCommand = new DeleteNotificationCommand(notification_id);
        this.notificationCommandService.handle(deleteNotificationCommand);
        return ResponseEntity.noContent().build();
    }
}
