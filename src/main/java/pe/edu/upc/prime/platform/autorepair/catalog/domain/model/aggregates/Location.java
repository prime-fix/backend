package pe.edu.upc.prime.platform.autorepair.catalog.domain.model.aggregates;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import pe.edu.upc.prime.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import pe.edu.upc.prime.platform.autorepair.catalog.domain.model.commands.CreateLocationCommand;
import pe.edu.upc.prime.platform.autorepair.catalog.domain.model.commands.UpdateLocationCommand;


@Entity
@Table(name = "locations")
public class Location extends AuditableAbstractAggregateRoot<Location> {

    @Getter
    @Column(name = "address", nullable = false)
    private String address;

    @Getter
    @Column(name = "district", nullable = false)
    private String district;

    @Getter
    @Column(name = "department", nullable = false)
    private String department;

    public Location(){}

    public Location(CreateLocationCommand command) {
        this.address = command.address();
        this.district = command.district();
        this.department = command.department();
    }

    public void updateLocation(UpdateLocationCommand command){
        this.address = command.address();
        this.district = command.district();
        this.department = command.department();
    }
}
