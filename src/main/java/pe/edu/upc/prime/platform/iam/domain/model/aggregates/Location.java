package pe.edu.upc.prime.platform.iam.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import pe.edu.upc.prime.platform.iam.domain.model.valueobjects.LocationInformation;
import pe.edu.upc.prime.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import pe.edu.upc.prime.platform.iam.domain.model.commands.CreateLocationCommand;
import pe.edu.upc.prime.platform.iam.domain.model.commands.UpdateLocationCommand;


/**
 * Location Aggregate Root
 */
@Entity
@Table(name = "locations")
public class Location extends AuditableAbstractAggregateRoot<Location> {

    @Getter
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "address",
                    column = @Column(name = "address", nullable = false)),
            @AttributeOverride(name = "district",
                    column = @Column(name = "district", nullable = false)),
            @AttributeOverride(name = "department",
                    column = @Column(name = "department", nullable = false))
    })
    private LocationInformation locationInformation;

    /**
     * Default constructor for JPA
     */
    public Location(){}

    /**
     * Constructor with CreateLocationCommand
     *
     * @param command the command to create a location
     */
    public Location(CreateLocationCommand command) {
        this.locationInformation = command.locationInformation();
    }

    /**
     * Update location information
     *
     * @param command the command to update location
     */
    public void updateLocation(UpdateLocationCommand command){
        this.locationInformation = command.locationInformation();
    }
}
