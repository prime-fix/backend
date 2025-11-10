package pe.edu.upc.prime.platform.workshop.catalog.domain.model.aggregates;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import pe.edu.upc.prime.platform.workshop.catalog.domain.model.valueObjects.TechnicianSpecialty;
//import pe.edu.upc.prime.platform.workshop.catalog.domain.model.aggregates.AuditableAbstractAggregateRoot;

@Entity
@Table(name = "technicians")
public class Technician extends AuditableAbstractAggregateRoot<Technician> {

    @Getter
    @NotNull
    @NotBlank
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "specialty", column = @Column(name = "specialty", length = 100, nullable = false))
    })
    private TechnicianSpecialty specialty;

    @Getter
    @Column(name = "available", nullable = false)
    private Boolean available;

    public Technician() {}

    public Technician(String name, String specialty, Boolean available) {
        this.name = name;
        this.specialty = new TechnicianSpecialty(specialty);
        this.available = available;
    }

    public String getSpecialty() {
        return specialty.specialty();
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }
}
