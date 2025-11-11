package pe.edu.upc.prime.platform.data.collection.domain.services;

import pe.edu.upc.prime.platform.data.collection.domain.model.aggregates.Service;
import pe.edu.upc.prime.platform.data.collection.domain.model.queries.GetServiceByAutoRepairIdQuery;
import pe.edu.upc.prime.platform.data.collection.domain.model.queries.GetServiceByIdQuery;

import java.util.List;
import java.util.Optional;

public interface ServiceQueryService {
    /*List<Service> handle(GetServiceByAutoRepairIdQuery query);*/
    Optional<Service> handle(GetServiceByIdQuery query);
}
