package pe.edu.upc.prime.platform.autorepair.catalog.application.internal.queryservices;

import pe.edu.upc.prime.platform.autorepair.catalog.domain.model.aggregates.Service;
import pe.edu.upc.prime.platform.autorepair.catalog.domain.model.queries.GetAllServicesQuery;
import pe.edu.upc.prime.platform.autorepair.catalog.domain.model.queries.GetServiceByIdQuery;
import pe.edu.upc.prime.platform.autorepair.catalog.domain.services.ServiceQueryService;
import pe.edu.upc.prime.platform.autorepair.catalog.infrastructure.persistence.jpa.repositories.ServiceRepository;


import java.util.List;
import java.util.Optional;

/**
 * Implementation of ServiceQueryService.
 */
@org.springframework.stereotype.Service
public class ServiceQueryServiceImpl implements ServiceQueryService {

    private final ServiceRepository serviceRepository;

    /**
     * Constructor for ServiceQueryServiceImpl.
     * @param serviceRepository the service repository
     */
    public ServiceQueryServiceImpl(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    /*@Override
    public List<Service> handle(GetServiceByAutoRepairIdQuery query) {
        return this.serviceRepository.findByAutoRepairId(query.autoRepairId());
    }
     */

    @Override
    public Optional<Service> handle(GetServiceByIdQuery query) {
        return this.serviceRepository.findById(query.serviceId());
    }

    @Override
    public List<Service> handle(GetAllServicesQuery query) {
        return this.serviceRepository.findAll();
    }
}
