package pe.edu.upc.prime.platform.autorepair.catalog.domain.model.commands;

import java.math.BigDecimal;

public record AddServiceToAutoRepairServiceCatalogCommand( Long autoRepairId,Long serviceId, BigDecimal price) {
}
