package pe.edu.upc.prime.platform.autorepair.register.domain.model.aggregates;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import pe.edu.upc.prime.platform.autorepair.register.domain.model.commands.CreateTechnicianScheduleCommand;
import pe.edu.upc.prime.platform.autorepair.register.domain.model.commands.UpdateTechnicianScheduleCommand;
import pe.edu.upc.prime.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Aggregate root representing a Technician's work schedule.
 */
@Entity
@Table(name = "technician_schedules")
public class TechnicianSchedule extends AuditableAbstractAggregateRoot<TechnicianSchedule> {


    @ManyToOne(optional = false)
    @JoinColumn(name = "technician_id")
    @Getter
    private Technician technician;

    @Getter
    @Column(name = "day_of_week", nullable = false, length = 20)
    private String dayOfWeek;

    @Getter
    @Column(name = "start_time", nullable = false)
    private String startTime;

    @Getter
    @Column(name = "end_time", nullable = false)
    private String endTime;

    @Getter
    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    /**
     * Default constructor for JPA.
     */
    protected TechnicianSchedule() { }

    /**
     * Constructor for CreateTechnicianScheduleCommand.
     */
    public TechnicianSchedule(CreateTechnicianScheduleCommand command, Technician technician) {

        if (Objects.isNull(technician)) {
            throw new IllegalArgumentException("[TechnicianSchedule] Technician cannot be null");
        }

        if (Objects.isNull(command.dayOfWeek()) || command.dayOfWeek().isBlank()) {
            throw new IllegalArgumentException("[TechnicianSchedule] Day of week cannot be null or blank");
        }

        if (Objects.isNull(command.startTime()) || Objects.isNull(command.endTime())) {
            throw new IllegalArgumentException("[TechnicianSchedule] Start and end time cannot be null");
        }


        if (Objects.isNull(command.isActive())) {
            throw new IllegalArgumentException("[TechnicianSchedule] Active status cannot be null");
        }

        this.technician = technician;
        this.dayOfWeek = command.dayOfWeek();
        this.startTime = command.startTime();
        this.endTime = command.endTime();
        this.isActive = command.isActive();
    }

    /**
     * Update TechnicianSchedule with UpdateTechnicianScheduleCommand.
     */
    public void updateTechnicianSchedule(UpdateTechnicianScheduleCommand command) {
        if (Objects.nonNull(command.dayOfWeek()) && !command.dayOfWeek().isBlank()) {
            this.dayOfWeek = command.dayOfWeek();
        }

        if (Objects.nonNull(command.startTime()) && Objects.nonNull(command.endTime())) {
            this.startTime = command.startTime();
            this.endTime = command.endTime();
        }

        if (Objects.nonNull(command.isActive())) {
            this.isActive = command.isActive();
        }
    }
}
