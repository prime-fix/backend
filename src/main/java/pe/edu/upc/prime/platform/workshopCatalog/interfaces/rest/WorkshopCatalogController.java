package pe.edu.upc.prime.platform.workshopCatalog.interfaces.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.prime.platform.workshopCatalog.domain.model.commands.*;
import pe.edu.upc.prime.platform.workshopCatalog.domain.model.queries.*;
import pe.edu.upc.prime.platform.workshopCatalog.domain.services.WorkshopCatalogCommandService;
import pe.edu.upc.prime.platform.workshopCatalog.domain.services.WorkshopCatalogQueryService;
import pe.edu.upc.prime.platform.workshopCatalog.interfaces.rest.resources.*;
import pe.edu.upc.prime.platform.workshopCatalog.interfaces.rest.transform.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE})
@RestController
@RequestMapping(value = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Workshop Catalog", description = "Workshop Catalog Management Endpoints")
public class WorkshopCatalogController {

    private final WorkshopCatalogQueryService queryService;
    private final WorkshopCatalogCommandService commandService;

    public WorkshopCatalogController(WorkshopCatalogQueryService queryService, WorkshopCatalogCommandService commandService) {
        this.queryService = queryService;
        this.commandService = commandService;
    }

    // AUTO REPAIRS ENDPOINTS
    @PostMapping("/autoRepairs")
    public ResponseEntity<AutoRepairResource> createAutoRepair(@RequestBody CreateAutoRepairResource resource) {
        var command = CreateAutoRepairCommandFromResourceAssembler.toCommandFromResource(resource);
        var repairId = this.commandService.handle(command);
        var repair = this.queryService.handle(new GetAutoRepairByIdQuery(repairId)).orElseThrow();
        return new ResponseEntity<>(AutoRepairResourceFromEntityAssembler.toResourceFromEntity(repair), HttpStatus.CREATED);
    }

    @GetMapping("/autoRepairs")
    public ResponseEntity<List<AutoRepairResource>> getAllAutoRepairs() {
        var repairs = this.queryService.handle(new GetAllAutoRepairsQuery());
        var resources = repairs.stream().map(AutoRepairResourceFromEntityAssembler::toResourceFromEntity).collect(Collectors.toList());
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/autoRepairs/{repairId}")
    public ResponseEntity<AutoRepairResource> getAutoRepairById(@PathVariable Long repairId) {
        var repair = this.queryService.handle(new GetAutoRepairByIdQuery(repairId));
        return repair.map(r -> ResponseEntity.ok(AutoRepairResourceFromEntityAssembler.toResourceFromEntity(r)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/autoRepairs/{repairId}/status")
    public ResponseEntity<AutoRepairResource> updateAutoRepairStatus(@PathVariable Long repairId, @RequestBody UpdateAutoRepairStatusResource resource) {
        var command = new UpdateAutoRepairStatusCommand(repairId, resource.status());
        var repair = this.commandService.handle(command);
        return repair.map(r -> ResponseEntity.ok(AutoRepairResourceFromEntityAssembler.toResourceFromEntity(r)))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    // LOCATIONS ENDPOINTS
    @PostMapping("/locations")
    public ResponseEntity<LocationResource> createLocation(@RequestBody LocationResource resource) {
        var command = new CreateLocationCommand(resource.name(), resource.address(), resource.phone(), resource.openingHours());
        var locationId = this.commandService.handle(command);
        return new ResponseEntity<>(resource, HttpStatus.CREATED);
    }

    @GetMapping("/locations")
    public ResponseEntity<List<LocationResource>> getAllLocations() {
        var locations = this.queryService.handle(new GetAllLocationsQuery());
        var resources = locations.stream().map(LocationResourceFromEntityAssembler::toResourceFromEntity).collect(Collectors.toList());
        return ResponseEntity.ok(resources);
    }

    // TECHNICIANS ENDPOINTS
    @PostMapping("/technicians")
    public ResponseEntity<TechnicianResource> createTechnician(@RequestBody TechnicianResource resource) {
        var command = new CreateTechnicianCommand(resource.name(), resource.specialty(), resource.available());
        var technicianId = this.commandService.handle(command);
        return new ResponseEntity<>(resource, HttpStatus.CREATED);
    }

    @GetMapping("/technicians")
    public ResponseEntity<List<TechnicianResource>> getAllTechnicians() {
        var technicians = this.queryService.handle(new GetAllTechniciansQuery());
        var resources = technicians.stream().map(TechnicianResourceFromEntityAssembler::toResourceFromEntity).collect(Collectors.toList());
        return ResponseEntity.ok(resources);
    }
}
