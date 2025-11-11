package pe.edu.upc.prime.platform.data.collection.domain.model.aggregates;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import pe.edu.upc.prime.platform.data.collection.domain.model.commands.CreateVisitCommand;
import pe.edu.upc.prime.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

import java.util.Date;

/**
 * Represents a visit in the system
 */
@Entity
@Table(name="visits")
public class Visit extends AuditableAbstractAggregateRoot<Visit>
{
    @Id
    @Getter
    @Column(name="visit_id", nullable = false, unique = true)
    @JsonProperty("visit_id")
    private String visitId;

    @Getter
    @Column(name="failure", nullable = false)
    private String failure;

    @Getter
    @Column(name="vehicle_id", nullable = false)
    private String vehicleId;

    @Getter
    @Temporal(TemporalType.DATE)
    @Column(name = "time_visit", nullable = false)
    private Date timeVisit;

    @Getter
    @Column(name = "auto_repair_id", nullable = false)
    private String autoRepairId;

    @Getter
    @Column(name = "service_id", nullable = false)
    private String serviceId;

    /**
     *  Update the profile with the specified detail
     * @param command the UpdateVisitCommand containing the new visit details
     */
    public Visit(CreateVisitCommand command){
        this.visitId= command.visitId();
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
    public void updateVisit(CreateVisitCommand command){
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
