package pe.edu.upc.prime.platform.autorepair.catalog.application.internal.queryservices;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import pe.edu.upc.prime.platform.autorepair.catalog.domain.model.aggregates.AutoRepair;
import pe.edu.upc.prime.platform.autorepair.catalog.domain.model.entities.ServiceOffer;
import pe.edu.upc.prime.platform.autorepair.catalog.domain.model.queries.ExistsAutoRepairByIdQuery;
import pe.edu.upc.prime.platform.autorepair.catalog.domain.model.queries.GetAllAutoRepairsQuery;
import pe.edu.upc.prime.platform.autorepair.catalog.domain.model.queries.GetAutoRepairByIdQuery;
import pe.edu.upc.prime.platform.autorepair.catalog.domain.model.queries.GetServiceOfferByServiceIdAndAutoRepairIdQuery;
import pe.edu.upc.prime.platform.autorepair.catalog.domain.services.AutoRepairQueryService;
import pe.edu.upc.prime.platform.autorepair.catalog.infrastructure.persistence.jpa.repositories.AutoRepairRepository;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of AutoRepairQuery Service
 */
@Service
public class AutoRepairQueryServiceImpl implements AutoRepairQueryService {
    /**
     * The autoRepair repository
     */
    private final AutoRepairRepository autoRepairRepository;

    /**
     * Constructor for AutoRepairQueryServiceImpl
     * @param autoRepairRepository the autoRepair repository
     */
    public AutoRepairQueryServiceImpl(AutoRepairRepository autoRepairRepository) {
        this.autoRepairRepository = autoRepairRepository;
    }

    /**
     * Handle the query to get a AutoRepair by its ID
     *
     * @param query the query containing the AutoRepair
     * @return an optional autoRepair matching the ID
     */
    @Override
    public Optional<AutoRepair> handle(GetAutoRepairByIdQuery query) {
        return this.autoRepairRepository.findById(query.repairId());
    }

    /**
     * Handle the query to get all auto repair
     *
     * @param query the query to get all AutoRepair
     * @return a list of all auto repair
     */
    @Override
    public List<AutoRepair> handle(GetAllAutoRepairsQuery query) {
        return this.autoRepairRepository.findAll();
    }

    /**
     * Handle the query to get a ServiceOffer by serviceId and autoRepairId
     *
     * @param query the query containing the serviceId and autoRepairId
     * @return an optional ServiceOffer matching the serviceId and autoRepairId
     */
    @Override
    public Optional<ServiceOffer> handle(GetServiceOfferByServiceIdAndAutoRepairIdQuery query) {
        if(!autoRepairRepository.existsById(query.autoRepairId())) throw new EntityNotFoundException("AutoRepair Not Found");
        return autoRepairRepository.findById(query.autoRepairId())
                .map(autoRepair -> autoRepair.getServiceCatalog().getOfferByAutoRepairId(query.autoRepairId()));
    }

    /**
     * Handle the query to check if an AutoRepair exists by its ID
     *
     * @param query the query containing the AutoRepair ID
     * @return true if the AutoRepair exists, false otherwise
     */
    @Override
    public boolean handle(ExistsAutoRepairByIdQuery query) {
        return this.autoRepairRepository.existsById(query.autoRepairId());
    }
}
