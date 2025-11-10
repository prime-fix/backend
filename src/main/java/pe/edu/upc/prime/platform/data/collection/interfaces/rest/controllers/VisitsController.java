package pe.edu.upc.prime.platform.data.collection.interfaces.rest.controllers;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.upc.prime.platform.data.collection.domain.model.aggregates.Visit;
import pe.edu.upc.prime.platform.data.collection.domain.model.commands.DeleteVisitCommand;
import pe.edu.upc.prime.platform.data.collection.domain.model.queries.*;
import pe.edu.upc.prime.platform.data.collection.domain.services.VisitCommandService;
import pe.edu.upc.prime.platform.data.collection.domain.services.VisitQueryService;
import pe.edu.upc.prime.platform.data.collection.interfaces.rest.assemblers.VisitAssembler;
import pe.edu.upc.prime.platform.data.collection.interfaces.rest.resources.CreateVisitRequest;
import pe.edu.upc.prime.platform.data.collection.interfaces.rest.resources.VisitResponse;

import java.awt.*;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins="*", methods ={ RequestMethod.DELETE, RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT})
@RestController
@RequestMapping(value = "/api/v1/visits", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Visits", description = "Endpoints for managing visits")
public class VisitsController {

    private final VisitCommandService visitCommandService;
    private final VisitQueryService visitQueryService;

    public VisitsController(VisitCommandService visitCommandService, VisitQueryService visitQueryService) {
        this.visitCommandService = visitCommandService;
        this.visitQueryService = visitQueryService;
    }

    @PostMapping
    public ResponseEntity<VisitResponse> createVisit(@RequestBody CreateVisitRequest request){
        var createVisitCommand = VisitAssembler.toCommandFromRequest(request);
        var visitId = this.visitCommandService.handle(createVisitCommand);

        if (Objects.isNull(visitId) || visitId.isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        var getVisitByIdQuery = new GetVisitByIdQuery(visitId);
        var visit = this.visitQueryService.handle(getVisitByIdQuery);

        if(visit.isEmpty()){
            return ResponseEntity.badRequest().build();
        }

        var visitResponse = VisitAssembler.toResponseFromEntity(visit.get());
        return new ResponseEntity<>(visitResponse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<VisitResponse>> getAllVisits(){
        var getAllVisitsQuery = new GetAllVisitsQuery();
        var visits = this.visitQueryService.handle(getAllVisitsQuery);

        var visitResponses = visits.stream()
                .map(VisitAssembler::toResponseFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(visitResponses);
    }

    @GetMapping("/auto-repair/{autoRepairId}")
    public ResponseEntity<List<VisitResponse>> getVisitsByAutoRepairId(@PathVariable String autoRepairId) {
        var query = new GetVisitByAutoRepairIdQuery(autoRepairId);
        var visits = visitQueryService.handle(query);

        var visitResponses = visits.stream()
                .map(VisitAssembler::toResponseFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(visitResponses);
    }

    @GetMapping("/{visitId}")
    public ResponseEntity<VisitResponse> getVisitById(@PathVariable String visitId){
        var getVisitByIdQuery = new GetVisitByIdQuery(visitId);
        var optionalVisit = visitQueryService.handle(getVisitByIdQuery);
        if(optionalVisit.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        var visitResponse = VisitAssembler.toResponseFromEntity(optionalVisit.get());
        return ResponseEntity.ok(visitResponse);
    }


    @GetMapping("/search")
    public ResponseEntity<List<VisitResponse>> getVisitByVehicleId(@RequestParam String vehicleId){
        var query = new GetVisitByVehicleIdQuery(vehicleId);
        var visits = this.visitQueryService.handle(query);

        var visitResponses = visits.stream()
                .map(VisitAssembler::toResponseFromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(visitResponses);
    }

    @DeleteMapping("/{visitId}")
    public ResponseEntity<?> deleteVisit(@PathVariable String visitId){
        var deleteVisitCommand = new DeleteVisitCommand(visitId);
        this.visitCommandService.handle(deleteVisitCommand);
        return ResponseEntity.noContent().build();
    }

}
