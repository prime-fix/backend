package pe.edu.upc.prime.platform.data.collection.application.internal.queryservices;

import pe.edu.upc.prime.platform.data.collection.domain.model.aggregates.Service;
import pe.edu.upc.prime.platform.data.collection.domain.model.queries.GetServiceByAutoRepairIdQuery;
import pe.edu.upc.prime.platform.data.collection.domain.model.queries.GetServiceByIdQuery;
import pe.edu.upc.prime.platform.data.collection.domain.services.ServiceQueryService;
import pe.edu.upc.prime.platform.data.collection.infrastructure.persistance.jpa.repositories.ServiceRepository;


import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class ServiceQueryServiceImpl implements ServiceQueryService {

    private final ServiceRepository serviceRepository;

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
        return this.serviceRepository.findById(Long.valueOf(query.serviceId()));
    }
}
