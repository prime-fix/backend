package pe.edu.upc.prime.platform.autorepair.register.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import java.util.Objects;

/**
 * Value object representing an IdAutoRepair.
 */
@Embeddable
public record IdAutoRepair(Long idAutoRepair) {

    public IdAutoRepair {
        if (Objects.isNull(idAutoRepair)) {
            throw new IllegalArgumentException("[IdAutoRepair] idAutoRepair cannot be null");
        }
        if (idAutoRepair < 0) {
            throw new IllegalArgumentException("[IdAutoRepair] idAutoRepair cannot be negative");
        }
    }

    public IdAutoRepair() {
        this(0L);
    }

    public Long getIdAutoRepair() {
        return idAutoRepair;
    }
}
