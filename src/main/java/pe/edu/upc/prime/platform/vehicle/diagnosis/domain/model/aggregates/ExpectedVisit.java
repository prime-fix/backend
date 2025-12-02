package pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import pe.edu.upc.prime.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import pe.edu.upc.prime.platform.shared.domain.model.valueobjects.VehicleId;
import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.commands.CreateExpectedVisitCommand;
import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.commands.UpdateExpectedVisitCommand;
import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.events.ChangeStateVisitEvent;
import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.valueobjects.StateVisit;
import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.valueobjects.VisitId;

import javax.swing.plaf.nimbus.State;

/**
 * ExpectedVisit Aggregate Root
 */
@Entity
@Table(name = "expected_visit")
public class ExpectedVisit extends AuditableAbstractAggregateRoot<ExpectedVisit> {

    @Getter
    @Enumerated(EnumType.STRING)
    @Column(name = "state_visit", nullable = false, length = 30)
    private StateVisit stateVisit;

    @Getter
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "visitId",
                    column = @Column(name = "visit_id", nullable = false))
    })
    private VisitId visitId;

    @Getter
    @Column(name = "is_scheduled", length = 2, nullable = false)
    private Boolean isScheduled;

    @Getter
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "vehicleId",
                    column = @Column(name = "vehicle_id", nullable = false))
    })
    private VehicleId vehicleId;

    /*
     * Default constructor for JPA.
     */
    public ExpectedVisit() {  }

    public ExpectedVisit(CreateExpectedVisitCommand command) {
        this.stateVisit = StateVisit.PENDING_VISIT;
        this.visitId = command.visitId();
        this.isScheduled = false;
        this.vehicleId = command.vehicleId();
    }

    /**
     * Updates the Expected Visit with the provided command.
     *
     * @param command the command containing the updated information
     */
    public void updateExpectedVisit(UpdateExpectedVisitCommand command) {
        this.stateVisit = command.stateVisit();
        this.visitId = command.visitId();
        this.isScheduled = command.isScheduled();
        this.vehicleId = command.vehicleId();
        this.registerEvent(new ChangeStateVisitEvent(this, command.vehicleId().value() ,command.stateVisit()));
    }
}
