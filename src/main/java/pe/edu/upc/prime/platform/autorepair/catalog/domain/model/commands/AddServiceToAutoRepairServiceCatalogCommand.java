package pe.edu.upc.prime.platform.autorepair.catalog.domain.model.commands;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Command to add a service to the auto repair service catalog
 *
 * @param autoRepairId the id of the auto repair
 * @param serviceId the id of the service
 * @param price the price of the service
 */
public record AddServiceToAutoRepairServiceCatalogCommand(Long autoRepairId,Long serviceId, BigDecimal price) {

    /**
     * Constructor with validation.
     *
     * @param autoRepairId the id of the auto repair
     * @param serviceId the id of the service
     * @param price the price of the service
     */
    public AddServiceToAutoRepairServiceCatalogCommand {
        Objects.requireNonNull(autoRepairId, "[AddServiceToAutoRepairServiceCatalogCommand] auto repair id must not be null");
        Objects.requireNonNull(serviceId, "[AddServiceToAutoRepairServiceCatalogCommand] service id must not be null");
        Objects.requireNonNull(price, "[AddServiceToAutoRepairServiceCatalogCommand] price must not be null");
    }
}
