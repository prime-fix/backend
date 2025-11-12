package pe.edu.upc.prime.platform.workshopCatalog.domain.services;

import pe.edu.upc.prime.platform.workshopCatalog.domain.model.aggregates.AutoRepair;
import pe.edu.upc.prime.platform.workshopCatalog.domain.model.aggregates.Location;
import pe.edu.upc.prime.platform.workshopCatalog.domain.model.aggregates.Technician;
import pe.edu.upc.prime.platform.workshopCatalog.domain.model.commands.*;

import java.util.Optional;

public interface WorkshopCatalogCommandService {
    Long handle(CreateAutoRepairCommand command);
    Optional<AutoRepair> handle(UpdateAutoRepairStatusCommand command);
    Long handle(CreateLocationCommand command);
    Long handle(CreateTechnicianCommand command);
}
