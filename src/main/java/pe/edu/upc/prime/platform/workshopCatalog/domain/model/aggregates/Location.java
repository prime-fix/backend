package pe.edu.upc.prime.platform.workshopCatalog.domain.model.aggregates;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
//import pe.edu.upc.prime.platform.workshop.catalog.domain.model.aggregates.AuditableAbstractAggregateRoot;

@Entity
@Table(name = "locations")
public class Location extends AuditableAbstractAggregateRoot<Location> {

    @Getter
    @NotNull
    @NotBlank
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Getter
    @NotNull
    @NotBlank
    @Column(name = "address", length = 200, nullable = false)
    private String address;

    @Getter
    @Column(name = "phone", length = 20)
    private String phone;

    @Getter
    @Column(name = "opening_hours", length = 50)
    private String openingHours;

    public Location() {}

    public Location(String name, String address, String phone, String openingHours) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.openingHours = openingHours;
    }
}
