package pe.edu.upc.prime.platform.autorepair.catalog.interfaces.rest.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.prime.platform.autorepair.catalog.domain.model.commands.AddServiceToAutoRepairServiceCatalogCommand;
import pe.edu.upc.prime.platform.autorepair.catalog.domain.model.queries.GetServiceOfferByServiceIdAndAutoRepairIdQuery;
import pe.edu.upc.prime.platform.autorepair.catalog.domain.services.AutoRepairCommandService;
import pe.edu.upc.prime.platform.autorepair.catalog.domain.services.AutoRepairQueryService;
import pe.edu.upc.prime.platform.autorepair.catalog.interfaces.rest.assemblers.ServiceOfferAssembler;
import pe.edu.upc.prime.platform.autorepair.catalog.interfaces.rest.resources.RegisterServiceOfferRequest;
import pe.edu.upc.prime.platform.autorepair.catalog.interfaces.rest.resources.ServiceOfferResource;

@CrossOrigin(origins = "*", methods = {RequestMethod.DELETE, RequestMethod.PUT, RequestMethod.POST, RequestMethod.GET})
@RestController
@RequestMapping(value = "/api/v1/serviceOffer", produces= MediaType.APPLICATION_JSON_VALUE)
@Tag(name="ServiceOffer", description="Endpoints for managing serviceOffer")
public class ServiceOfferController {

    private final AutoRepairCommandService autoRepairCommandService;
    private final AutoRepairQueryService autoRepairQueryService;

    public ServiceOfferController(AutoRepairCommandService autoRepairCommandService, AutoRepairQueryService autoRepairQueryService) {
        this.autoRepairCommandService = autoRepairCommandService;
        this.autoRepairQueryService = autoRepairQueryService;
    }


    @PostMapping("/{autoRepairId}")
    @Operation(summary = "Add a service to the Service Catalog")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Service added to the Catalog of teh AutoRepair"),
            @ApiResponse(responseCode = "404", description = "Auto Repair not found not found")
    })
    public ResponseEntity<ServiceOfferResource> addServiceToServiceCatalog(@PathVariable Long autoRepairId, @RequestBody RegisterServiceOfferRequest request){
        var command = new AddServiceToAutoRepairServiceCatalogCommand(
                autoRepairId,
                request.serviceId(),
                request.price()
        );

        autoRepairCommandService.handle(command);
        var getOfferByServiceIdAndAutoRepairIdQuery = new GetServiceOfferByServiceIdAndAutoRepairIdQuery(request.serviceId(),autoRepairId);
        var serviceOffer = autoRepairQueryService.handle(getOfferByServiceIdAndAutoRepairIdQuery);
        if(serviceOffer.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        var serviceOfferEntity = serviceOffer.get();
        var serviceOfferResource = ServiceOfferAssembler.toResourceFromEntity(serviceOfferEntity);
        return new ResponseEntity<>(serviceOfferResource, HttpStatus.CREATED);
    }
}
