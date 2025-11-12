package pe.edu.upc.prime.platform.workshopCatalog.domain.services;

import pe.edu.upc.prime.platform.workshopCatalog.domain.model.aggregates.AutoRepair;
import pe.edu.upc.prime.platform.workshopCatalog.domain.model.aggregates.Location;
import pe.edu.upc.prime.platform.workshopCatalog.domain.model.aggregates.Technician;
import pe.edu.upc.prime.platform.workshopCatalog.domain.model.queries.*;

import java.util.List;
import java.util.Optional;

public interface WorkshopCatalogQueryService {
    List<AutoRepair> handle(GetAllAutoRepairsQuery query);
    Optional<AutoRepair> handle(GetAutoRepairByIdQuery query);
    List<Location> handle(GetAllLocationsQuery query);
    List<Technician> handle(GetAllTechniciansQuery query);
}
