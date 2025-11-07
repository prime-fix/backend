package pe.edu.upc.prime.platform.maintenance.tracking.domain.model.aggregates;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.commands.CreateVehicleCommand;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.commands.UpdateVehicleCommand;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.valueobjects.VehicleInformation;
import pe.edu.upc.prime.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

/**
 * Vehicle Aggregate Root
 */
@Entity
@Table(name = "vehicles")
public class Vehicle extends AuditableAbstractAggregateRoot<Vehicle> {

    @Id
    @Getter
    @Column(name="id_vehicle", nullable = false, unique = true)
    @JsonProperty("id_vehicle")
    private String idVehicle;

    @Getter
    @Column(name = "color", nullable = false, length = 50)
    private String color;


    @Getter
    @Column(name = "model", nullable = false, length = 100)
    private String model;

    @Getter
    @Column(name="id_user", nullable = false)
    @JsonProperty("id_user")
    private String idUser;

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
    @Column(name = "maintenance_status", nullable = false)
    @JsonProperty("maintenance_status")
    private int maintenanceStatus;

    /**
     * Default constructor for JPA.
     */
    public Vehicle() {
    }

    /**
     * Default constructor for JPA.
     */
    public Vehicle(CreateVehicleCommand command) {
        this.idVehicle = command.idVehicle();
        this.color = command.color();
        this.model = command.model();
        this.idUser = command.idUser();
        this.vehicleInformation = command.vehicleInformation();
        this.maintenanceStatus = command.maintenanceStatus();
    }

    /**
     * Update vehicle with UpdateVehicleCommand
     *
     * @param command the update vehicle command
     */
    public void updateVehicle(UpdateVehicleCommand command) {
        this.idVehicle = command.idVehicle();
        this.color = command.color();
        this.model = command.model();
        this.idUser = command.idUser();
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
