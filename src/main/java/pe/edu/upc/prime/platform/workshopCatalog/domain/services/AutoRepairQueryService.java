package pe.edu.upc.prime.platform.workshopCatalog.domain.services;

import pe.edu.upc.prime.platform.workshopCatalog.domain.model.aggregates.AutoRepair;
import pe.edu.upc.prime.platform.workshopCatalog.domain.model.queries.GetAllAutoRepairsQuery;
import pe.edu.upc.prime.platform.workshopCatalog.domain.model.queries.GetAutoRepairByIdQuery;

import java.util.List;
import java.util.Optional;

public interface AutoRepairQueryService {

    /**
     * Handle the query to get a AutoRepair by its ID
     * @param query the query containing the AutoRepair
     * @return an optional autoRepair matching the ID
     */
    Optional<AutoRepair> handle(GetAutoRepairByIdQuery query);

    /**
     * Handle the query to get all auto repair
     * @param query the query to get all AutoRepair
     * @return a list of all auto repair
     */
    List<AutoRepair> handle(GetAllAutoRepairsQuery query);
}
