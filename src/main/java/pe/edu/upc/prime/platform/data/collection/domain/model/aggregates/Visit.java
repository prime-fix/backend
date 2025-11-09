package pe.edu.upc.center.data_collection.data.domain.model.aggregates;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import pe.edu.upc.center.data_collection.data.domain.model.commands.CreateVisitCommand;
import pe.edu.upc.center.data_collection.data.domain.model.valueobjects.VehicleId;
import pe.edu.upc.center.data_collection.data.domain.model.valueobjects.AutoRepairId;

import pe.edu.upc.center.data_collection.data.domain.model.valueobjects.Service;
import pe.edu.upc.center.data_collection.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

import java.util.Date;

@Entity
@Table(name = "visit")
public class Visit extends AuditableAbstractAggregateRoot<Visit>{

    @Getter
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="vehicle_id",column = @Column(name = "id", nullable = false))
    })
    private VehicleId idVehicle;

    @Getter
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="type", column = @Column(name="service_type",nullable = false))
    })
    private Service service;

    @Getter
    @NotNull
    @NotBlank
    @Column(name="failure", length = 100, nullable = false)
    private String failure;

    @Getter
    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(name="time_visit", nullable = false)
    private Date timeVisit;

    /*@Getter
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="id_expected_visit", column = @Column(name="id_expected_visit", nullable = false))
    })
    private IdExpectedVisit idExpectedVisit;
    */
    @Getter
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="auto_repair_id", column = @Column(name = "auto_repair_id", nullable = false))
    })
    private AutoRepairId idAutoRepair;


    public Visit(CreateVisitCommand command){
        this.idVehicle = command.idVehicle();
        this.failure=command.failure();
        this.timeVisit=command.timeVisit();
        this.idAutoRepair= command.idAutoRepair();
        this.service= command.service();
        /*this.idExpectedVisit= command.idExpectedVisit();*/
    }

    public Visit(){};

}
