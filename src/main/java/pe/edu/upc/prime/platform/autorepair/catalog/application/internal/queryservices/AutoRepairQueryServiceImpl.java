package pe.edu.upc.prime.platform.autorepair.catalog.application.internal.queryservices;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import pe.edu.upc.prime.platform.autorepair.catalog.domain.model.aggregates.AutoRepair;
import pe.edu.upc.prime.platform.autorepair.catalog.domain.model.entities.ServiceOffer;
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

    private final AutoRepairRepository autoRepairRepository;

    /**
     * Constructor for AutoRepairQueryServiceImpl
     * @param autoRepairRepository the autoRepair repository
     */
    public AutoRepairQueryServiceImpl(AutoRepairRepository autoRepairRepository) {
        this.autoRepairRepository = autoRepairRepository;
    }

    @Override
    public Optional<AutoRepair> handle(GetAutoRepairByIdQuery query) {
        return this.autoRepairRepository.findById(query.repairId());
    }

    @Override
    public List<AutoRepair> handle(GetAllAutoRepairsQuery query) {
        return this.autoRepairRepository.findAll();
    }

    @Override
    public Optional<ServiceOffer> handle(GetServiceOfferByServiceIdAndAutoRepairIdQuery query) {
        if(!autoRepairRepository.existsById(query.autoRepairId())) throw new EntityNotFoundException("AutoRepair Not Found");
        return autoRepairRepository.findById(query.autoRepairId())
                .map(autoRepair -> autoRepair.getServiceCatalog().getOfferByAutoRepairId(query.autoRepairId()));
    }
}
