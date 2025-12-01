package pe.edu.upc.prime.platform.autorepair.catalog.domain.services;

import pe.edu.upc.prime.platform.autorepair.catalog.domain.model.aggregates.AutoRepair;
import pe.edu.upc.prime.platform.autorepair.catalog.domain.model.entities.ServiceOffer;
import pe.edu.upc.prime.platform.autorepair.catalog.domain.model.queries.ExistsAutoRepairByIdQuery;
import pe.edu.upc.prime.platform.autorepair.catalog.domain.model.queries.GetAllAutoRepairsQuery;
import pe.edu.upc.prime.platform.autorepair.catalog.domain.model.queries.GetAutoRepairByIdQuery;
import pe.edu.upc.prime.platform.autorepair.catalog.domain.model.queries.GetServiceOfferByServiceIdAndAutoRepairIdQuery;

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

    /**
     * Handle the query to get a ServiceOffer by serviceId and autoRepairId
     *
     * @param query the query containing the serviceId and autoRepairId
     * @return an optional ServiceOffer matching the serviceId and autoRepairId
     */
    Optional<ServiceOffer> handle(GetServiceOfferByServiceIdAndAutoRepairIdQuery query);

    /**
     * Handle the query to check if an AutoRepair exists by its ID
     *
     * @param query the query containing the AutoRepair ID
     * @return true if the AutoRepair exists, false otherwise
     */
    boolean handle(ExistsAutoRepairByIdQuery query);
}
