package pe.edu.upc.prime.platform.data.collection.domain.model.aggregates;


import jakarta.persistence.*;
import lombok.Getter;
import pe.edu.upc.prime.platform.data.collection.domain.model.commands.CreateVisitCommand;
import pe.edu.upc.prime.platform.data.collection.domain.model.commands.UpdateVisitCommand;
import pe.edu.upc.prime.platform.data.collection.domain.model.valueobjects.ServiceId;
import pe.edu.upc.prime.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import pe.edu.upc.prime.platform.shared.domain.model.valueobjects.AutoRepairId;
import pe.edu.upc.prime.platform.shared.domain.model.valueobjects.VehicleId;

import java.time.LocalDateTime;

@Entity
@Table(name="visits")
public class Visit extends AuditableAbstractAggregateRoot<Visit>
{

    @Getter
    @Column(name="failure", nullable = false)
    private String failure;

    @Getter
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "vehicleId", column = @Column(name = "vehicle_id", nullable = false))
    })
    private VehicleId vehicleId;

    @Getter
    @Column(name = "time_visit", nullable = false)
    private LocalDateTime timeVisit;


    @Getter
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "autoRepairId", column = @Column(name = "auto_repair_id", nullable = false))
    })private AutoRepairId autoRepairId;


    @Getter
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "serviceId", column = @Column(name = "service_id", nullable = false))
    })private ServiceId serviceId;

    /**
     *  Update the profile with the specified detail
     * @param command the UpdateVisitCommand containing the new visit details
     */
    public Visit(CreateVisitCommand command){
        this.failure=command.failure();
        this.vehicleId= command.vehicleId();
        this.timeVisit=command.timeVisit();
        this.autoRepairId= command.autoRepairId();
        this.serviceId= command.serviceId();
    }

    /**
     *Update the visit instance from a CreateVisitCommand
     * @param command createVisitCommand containing visit details.
     */
    public void updateVisit(UpdateVisitCommand command){
        this.failure=command.failure();
        this.vehicleId= command.vehicleId();
        this.timeVisit=command.timeVisit();
        this.autoRepairId= command.autoRepairId();
        this.serviceId= command.serviceId();
    }

    /**
     *Default constructor for JPA
     */
    public Visit() {}
}
