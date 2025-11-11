package pe.edu.upc.prime.platform.payment.service.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

import java.util.Objects;

/**
 * Value object representing an IdAutoRepair.
 */
@Embeddable
public record IdAutoRepair(String idAutoRepair) {

    public IdAutoRepair {
        if (Objects.isNull(idAutoRepair) || idAutoRepair.isBlank()) {
            throw new IllegalArgumentException("[IdAutoRepair] IdAutoRepair cannot be null or blank");
        }
    }

    public IdAutoRepair(){this(null);}

    public String getIdAutoRepair() {
        return idAutoRepair;
    }
}
