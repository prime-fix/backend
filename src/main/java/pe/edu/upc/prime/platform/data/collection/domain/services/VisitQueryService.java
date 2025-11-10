package pe.edu.upc.prime.platform.data.collection.domain.services;

import pe.edu.upc.prime.platform.data.collection.domain.model.aggregates.Visit;
import pe.edu.upc.prime.platform.data.collection.domain.model.queries.*;

import java.util.List;
import java.util.Optional;

public interface VisitQueryService {

    List<Visit> handle(GetAllVisitsQuery query);
    Optional<Visit> handle(GetVisitByIdQuery query);
    List<Visit> handle(GetVisitByVehicleIdQuery query);
    List<Visit> handle(GetVisitByAutoRepairIdQuery query);

}
