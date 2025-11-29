package pe.edu.upc.prime.platform.workshopCatalog.domain.model.commands;

import pe.edu.upc.prime.platform.workshopCatalog.domain.model.aggregates.AutoRepair;
import pe.edu.upc.prime.platform.workshopCatalog.domain.model.aggregates.Service;

import java.math.BigDecimal;

public record AddServiceToAutoRepairServiceCatalogCommand( Long autoRepairId,Long serviceId, BigDecimal price) {
}
