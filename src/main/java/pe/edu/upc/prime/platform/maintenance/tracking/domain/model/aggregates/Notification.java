package pe.edu.upc.prime.platform.maintenance.tracking.domain.model.aggregates;

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

    @Getter
    @Column(name = "message", nullable = false)
    private String message;

    @Getter
    @Column(name = "read", nullable = false)
    private Boolean read;

    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;


    @Getter
    @FutureOrPresent
    @Column(name = "sent", nullable = false)
    private LocalDate sent;


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
    public Notification(CreateNotificationCommand command, Vehicle vehicle) {
        this.message = command.message();
        this.read = command.read();
        this.vehicle = vehicle;
        this.sent = command.sent();
    }

    /**
     * Update notification with UpdateNotificationCommand
     *
     * @param command the update notification command
     */
    public void updateNotification(UpdateNotificationCommand command, Vehicle vehicle) {
        this.message = command.message();
        this.read = command.read();
        this.vehicle = vehicle;
        this.sent = command.sent();
    }
}
