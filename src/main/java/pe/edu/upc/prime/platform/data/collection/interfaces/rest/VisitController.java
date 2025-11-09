package pe.edu.upc.center.data_collection.data.interfaces.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.upc.center.data_collection.data.domain.model.aggregates.Visit;
import pe.edu.upc.center.data_collection.data.domain.model.queries.GetAllVisitsQuery;
import pe.edu.upc.center.data_collection.data.domain.model.queries.GetVisitByIdQuery;
import pe.edu.upc.center.data_collection.data.domain.services.VisitCommandService;
import pe.edu.upc.center.data_collection.data.domain.services.VisitQueryService;
import pe.edu.upc.center.data_collection.data.interfaces.rest.resources.CreateVisitResource;
import pe.edu.upc.center.data_collection.data.interfaces.rest.resources.VisitResource;
import pe.edu.upc.center.data_collection.data.interfaces.rest.transforms.CreateVisitCommandFromResourceAssembler;
import pe.edu.upc.center.data_collection.data.interfaces.rest.transforms.VisitResourceFromEntityAssembler;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST})
@RestController
@RequestMapping(value = "/api/v1/visits", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name="Visits", description = "Visit Management Endpoint")
public class VisitController {

    private final VisitQueryService visitQueryService;
    private final VisitCommandService visitCommandService;

    public VisitController(VisitQueryService visitQueryService, VisitCommandService visitCommandService) {
        this.visitCommandService = visitCommandService;
        this.visitQueryService = visitQueryService;
    }

    @PostMapping
    public ResponseEntity<VisitResource> createVisit(@RequestBody CreateVisitResource resource) {
        var createVisitCommand = CreateVisitCommandFromResourceAssembler
                .toCommandFromResource(resource);
        var visitId = this.visitCommandService.handle(createVisitCommand);

        if(visitId.equals(0L)){
            return ResponseEntity.badRequest().build();
        }
        var getVisitBiIdQuery = new GetVisitByIdQuery(visitId);
        var optionalVisit = this.visitQueryService.handle(getVisitBiIdQuery);

        var visitResource = VisitResourceFromEntityAssembler.toResourceFromEntity(optionalVisit.get());
        return new ResponseEntity<>(visitResource, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<VisitResource>> getAllVisits() {
        var getAllVisitEntity = new GetAllVisitsQuery();
        var visits = this.visitQueryService.handle(getAllVisitEntity);
        var profileResources = visits.stream().map(VisitResourceFromEntityAssembler::toResourceFromEntity).collect(Collectors.toList());
        return ResponseEntity.ok(profileResources);
    }

    @GetMapping("/{visitId}")
    public ResponseEntity<VisitResource> getVisitById(@PathVariable Long visitId){
        var getVisitByIdQuery = new GetVisitByIdQuery(visitId);
        var optionalVisit = this.visitQueryService.handle(getVisitByIdQuery);
        if(optionalVisit.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        var visitResource = VisitResourceFromEntityAssembler.toResourceFromEntity(optionalVisit.get());
        return ResponseEntity.ok(visitResource);
    }
}
