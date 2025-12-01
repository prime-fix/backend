package pe.edu.upc.prime.platform.data.collection.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import org.springframework.stereotype.Service;

/**
 * Value object representing a ServiceId
 * @param serviceId the serviceId
 */

@Embeddable
public record ServiceId(Long serviceId) {
    public ServiceId {
        if(serviceId <0)
        {
            throw new IllegalArgumentException("[CreateVisitCommand] serviceId must not be negative");
        }
    }

    public ServiceId(){this(0L);}
}
