package pe.edu.upc.prime.platform.maintenance.tracking.domain.model.aggregates;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.Getter;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.commands.CreateNotificationCommand;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.commands.UpdateNotificationCommand;
import pe.edu.upc.prime.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

import java.time.LocalDate;

/**
 * Notification Aggregate Root
 */
@Entity
@Table(name = "notifications")
public class Notification extends AuditableAbstractAggregateRoot<Notification> {

    @Id
    @Getter
    @Column(name="id_notification", nullable = false, unique = true)
    @JsonProperty("id_notification")
    private String idNotification;

    @Getter
    @Column(name = "message", nullable = false)
    private String message;

    @Getter
    @Column(name = "read", nullable = false)
    private boolean read;

    @Getter
    @Column(name="id_vehicle", nullable = false)
    @JsonProperty("id_vehicle")
    private String idVehicle;


    @Getter
    @FutureOrPresent
    @Column(name = "sent", nullable = false)
    private LocalDate sent;

    @Getter
    @Column(name="id_diagnostic", nullable = false)
    @JsonProperty("id_diagnostic")
    private String idDiagnostic;

    /**
     * Default constructor for JPA.
     */
    public Notification() {
    }

    /**
     * Constructor with CreateNotificationCommand
     *
     * @param command the create notification command
     */
    public Notification(CreateNotificationCommand command) {
        this.idNotification = command.idNotification();
        this.message = command.message();
        this.read = command.read();
        this.idVehicle = command.idVehicle();
        this.sent = command.sent();
        this.idDiagnostic = command.idDiagnostic();
    }

    /**
     * Update notification with UpdateNotificationCommand
     *
     * @param command the update notification command
     */
    public void updateNotification(UpdateNotificationCommand command) {
        this.message = command.message();
        this.read = command.read();
        this.idVehicle = command.idVehicle();
        this.sent = command.sent();
        this.idDiagnostic = command.idDiagnostic();
    }
}
