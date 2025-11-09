package pe.edu.upc.center.data_collection.data.domain.services;

import pe.edu.upc.center.data_collection.data.domain.model.aggregates.Visit;
import pe.edu.upc.center.data_collection.data.domain.model.queries.GetAllVisitsQuery;
import pe.edu.upc.center.data_collection.data.domain.model.queries.GetVisitByIdAutoRepair;
import pe.edu.upc.center.data_collection.data.domain.model.queries.GetVisitByIdQuery;
import pe.edu.upc.center.data_collection.data.domain.model.queries.GetVisitByIdVehicle;

import java.util.List;
import java.util.Optional;

public interface VisitQueryService {

    List<Visit> handle (GetAllVisitsQuery query);
    Optional<Visit> handle (GetVisitByIdQuery query);
    Optional<Visit> handle (GetVisitByIdAutoRepair query);
    Optional<Visit> handle (GetVisitByIdVehicle query);

}
