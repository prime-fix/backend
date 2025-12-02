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
import pe.edu.upc.prime.platform.autorepair.catalog.domain.model.commands.DeleteServiceToAutoRepairServiceCatalogCommand;
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


    /**
     * Adds a new service offer to the specified AutoRepair service catalog.
     * This endpoint receives a request with service details (serviceId, price, duration)
     * and registers a new Service Offer associated with the given AutoRepair.
     *
     * @param autoRepairId the ID of the AutoRepair to which the service will be added
     * @param request the request body containing the service offer information
     * @return a {@link ResponseEntity} containing the created {@link ServiceOfferResource}
     */
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
                request.price(),
                true,
                request.duration_hour()
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

    /**
     * Removes an existing service offer from the specified AutoRepair catalog.
     * This endpoint deletes a service offer using its identifier and the
     * associated AutoRepair ID.
     *
     * @param autoRepairId the ID of the AutoRepair that owns the service offer
     * @param serviceOfferId the ID of the Service Offer to remove
     * @return a {@link ResponseEntity} with no content when deletion is successful
     */
    @DeleteMapping("/{autoRepairId}/{serviceOfferId}")
    @Operation(summary = "Remove a service from the service Catalog ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Service removed from the catalog"),
            @ApiResponse(responseCode = "404", description = "Service or Auto Repair not found"),
            @ApiResponse(responseCode = "400", description = "Domain error while removing the service")
    })
    public ResponseEntity<?> deleteServiceOffer(@PathVariable Long autoRepairId,
                                                @PathVariable Long serviceOfferId){

        var command = new DeleteServiceToAutoRepairServiceCatalogCommand(serviceOfferId,autoRepairId);
        autoRepairCommandService.handle(command);
        return ResponseEntity.noContent().build();
    }
}
