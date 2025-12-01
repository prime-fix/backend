package pe.edu.upc.prime.platform.maintenance.tracking.domain.model.aggregates;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.commands.CreateVehicleCommand;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.commands.UpdateVehicleCommand;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.valueobjects.MaintenanceStatus;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.valueobjects.UserId;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.valueobjects.VehicleInformation;
import pe.edu.upc.prime.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

/**
 * Vehicle Aggregate Root
 */
@Entity
@Table(name = "vehicles")
public class Vehicle extends AuditableAbstractAggregateRoot<Vehicle> {


    @Getter
    @Column(name = "color", nullable = false, length = 50)
    private String color;


    @Getter
    @Column(name = "model", nullable = false, length = 100)
    private String model;

    @Getter
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "userId", column = @Column(name = "user_id", nullable = false))
    })
    private UserId userId;

    @Getter
    @Embedded
    @AssociationOverrides({
            @AssociationOverride(name = "vehicleBrand",
                    joinColumns = @JoinColumn(name = "vehicle_brand")),
        @AssociationOverride(name = "vehiclePlate",
            joinColumns = @JoinColumn(name = "vehicle_plate")),
        @AssociationOverride(name = "vehicleType",
            joinColumns = @JoinColumn(name = "vehicle_type"))
    })
    private VehicleInformation vehicleInformation;

    @Getter
    @Enumerated(EnumType.STRING)
    @Column(name = "maintenance_status", nullable = false, length = 20)
    private MaintenanceStatus maintenanceStatus;

    /**
     * Default constructor for JPA.
     */
    public Vehicle() {
    }

    /**
     * Default constructor for JPA.
     */
    public Vehicle(CreateVehicleCommand command) {
        this.color = command.color();
        this.model = command.model();
        this.userId = command.userId();
        this.vehicleInformation = command.vehicleInformation();
        this.maintenanceStatus = MaintenanceStatus.NOT_BEING_SERVICED;
    }

    /**
     * Update vehicle with UpdateVehicleCommand
     *
     * @param command the update vehicle command
     */
    public void updateVehicle(UpdateVehicleCommand command) {
        this.color = command.color();
        this.model = command.model();
        this.userId = command.userId();
        this.vehicleInformation = command.vehicleInformation();
        this.maintenanceStatus = command.maintenanceStatus();
    }

    /**
     * Get vehicle details
     *
     * @return the vehicle details
     */
    public String getVehicleDetails() {
        return vehicleInformation.getVehicleDetails();
    }

}
