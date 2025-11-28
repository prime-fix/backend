package pe.edu.upc.prime.platform.data.collection.interfaces.acl;

import org.springframework.stereotype.Service;
import pe.edu.upc.prime.platform.data.collection.domain.model.queries.GetVisitByAutoRepairIdQuery;
import pe.edu.upc.prime.platform.data.collection.domain.model.queries.GetVisitByIdQuery;
import pe.edu.upc.prime.platform.data.collection.domain.model.queries.GetVisitByVehicleIdQuery;
import pe.edu.upc.prime.platform.data.collection.domain.services.VisitCommandService;
import pe.edu.upc.prime.platform.data.collection.domain.services.VisitQueryService;
import pe.edu.upc.prime.platform.data.collection.interfaces.rest.assemblers.VisitAssembler;
import pe.edu.upc.prime.platform.data.collection.interfaces.rest.resources.VisitResponse;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.queries.GetVehicleByIdQuery;

import java.util.List;
import java.util.Optional;

@Service
public class VisitContextFacade {
    private final VisitCommandService visitCommandService;
    private final VisitQueryService visitQueryService;

    /**
     * Constructs a VisitsContextFacade with the specified command and query services
     * @param visitCommandService the service for handling visit commands
     * @param visitQueryService the service for handling visit queries
     */
    public VisitContextFacade(VisitCommandService visitCommandService, VisitQueryService visitQueryService) {
        this.visitCommandService = visitCommandService;
        this.visitQueryService = visitQueryService;
    }

    /**
     * Retrieves a single visit by its unique identifier
     * @param visitId the ID of the visit to retrieve
     * @return an Optional containing the visitResponse if found
     */
    public Optional<VisitResponse> fetchVisitById(String visitId){
        var getVisitByIdQuery = new GetVisitByIdQuery(visitId);
        var optionalVisit = visitQueryService.handle(getVisitByIdQuery);
        if(optionalVisit.isEmpty()){
            return Optional.empty();
        }
        var visitResponse = VisitAssembler.toResponseFromEntity(optionalVisit.get());
        return Optional.of(visitResponse);
    }

    /**
     * Retrieves all visits associated with a specific vehicle
     * @param vehicleId the ID of the vehicle whose visits are being requested
     * @return a list of VisitResponse objects matching the given vehicle ID
     */
    public List<VisitResponse> fetchVisitByVehicleId(String vehicleId){
        var visitQuery = new GetVisitByVehicleIdQuery(vehicleId);
        var visits = visitQueryService.handle(visitQuery);

        return visits.stream()
                .map(VisitAssembler::toResponseFromEntity).toList();
    }

    /**
     * Retrieves all visits associated with a specific auto repair ID
     * @param autoRepairId  the ID of the auto repair
     * @return  a list of VisitResponse objects associated with the auto repair ID
     */
    public List<VisitResponse> fetchVisitsByAutoRepairId(String autoRepairId){
            var visitQuery = new GetVisitByAutoRepairIdQuery(autoRepairId);
            var visits = visitQueryService.handle(visitQuery);

            return visits.stream()
                    .map(VisitAssembler::toResponseFromEntity).toList();
    }

}
