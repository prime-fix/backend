package pe.edu.upc.prime.platform.autorepair.catalog.interfaces.rest.controllers;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;
import pe.edu.upc.prime.platform.autorepair.catalog.domain.model.commands.DeleteServiceCommand;
import pe.edu.upc.prime.platform.autorepair.catalog.domain.model.queries.GetAllServicesQuery;
import pe.edu.upc.prime.platform.autorepair.catalog.domain.model.queries.GetServiceByIdQuery;
import pe.edu.upc.prime.platform.autorepair.catalog.domain.services.ServiceCommandService;
import pe.edu.upc.prime.platform.autorepair.catalog.domain.services.ServiceQueryService;
import pe.edu.upc.prime.platform.autorepair.catalog.interfaces.rest.assemblers.ServiceAssembler;
import pe.edu.upc.prime.platform.autorepair.catalog.interfaces.rest.resources.CreateServiceRequest;
import pe.edu.upc.prime.platform.autorepair.catalog.interfaces.rest.resources.ServiceResponse;
import pe.edu.upc.prime.platform.autorepair.catalog.interfaces.rest.resources.UpdateServiceRequest;
import pe.edu.upc.prime.platform.shared.interfaces.rest.resources.BadRequestResponse;
import pe.edu.upc.prime.platform.shared.interfaces.rest.resources.InternalServerErrorResponse;
import pe.edu.upc.prime.platform.shared.interfaces.rest.resources.NotFoundResponse;
import pe.edu.upc.prime.platform.shared.interfaces.rest.resources.ServiceUnavailableResponse;


import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * REST controller for managing services.
 */
@CrossOrigin(origins = "*", methods = {RequestMethod.DELETE, RequestMethod.PUT, RequestMethod.POST, RequestMethod.GET})
@RestController
@RequestMapping(value = "/api/v1/services", produces= MediaType.APPLICATION_JSON_VALUE)
@Tag(name="Services", description="Endpoints for managing services")
public class ServicesController {

    private final ServiceQueryService serviceQueryService;
    private final ServiceCommandService serviceCommandService;

    /**
     * Constructor for ServicesController.
     * @param serviceCommandService the service for handling service commands
     * @param serviceQueryService the service for handling service queries
     */
    public ServicesController(ServiceCommandService serviceCommandService, ServiceQueryService serviceQueryService) {
        this.serviceQueryService = serviceQueryService;
        this.serviceCommandService = serviceCommandService;
    }


    /**
     * Endpoint to create a new service.
     * @param request the service data to be created
     * @return a ResponseEntity containing the created service resource or a bas request status if creations fails
     */
    @Operation(summary = "Creates a new Service",
            description = "Creates a new service with the provided data",
    requestBody =  @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Service data for creation",
            required = true,
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = CreateServiceRequest.class)))
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Visit created successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ServiceResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request - Invalid input data",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BadRequestResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error - Unexpected error",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = HttpServerErrorException.InternalServerError.class))),
            @ApiResponse(responseCode = "503", description = "Service unavailable - Persistence error",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = InternalServerErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<ServiceResponse> createService(@RequestBody CreateServiceRequest request){
        var createServiceCommand = ServiceAssembler.toCommandFromRequest(request);
        var serviceId = this.serviceCommandService.handle(createServiceCommand);

        if (Objects.isNull(serviceId) || serviceId.equals(0L)) {
            return ResponseEntity.badRequest().build();
        }

        var getVisitByIdQuery = new GetServiceByIdQuery(serviceId);
        var service = this.serviceQueryService.handle(getVisitByIdQuery);

        if(service.isEmpty()){
            return ResponseEntity.badRequest().build();
        }

        var serviceResponse = ServiceAssembler.toResponseFromEntity(service.get());
        return new ResponseEntity<>(serviceResponse, HttpStatus.CREATED);
    }

    /**
     * Endpoint to retrieve all services.
     * @return a ResponseEntity containing a list of all service resources
     */
    @Operation(summary = "Retrieves all services",
    description = "Fetches a list of all services")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Services retrieved successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = ServiceResponse.class))))
    })
    @GetMapping
    public ResponseEntity<List<ServiceResponse>> getAllServices(){
        var getAllServicesQuery = new GetAllServicesQuery();
        var services = this.serviceQueryService.handle(getAllServicesQuery);

        var serviceResponses = services.stream()
                .map(ServiceAssembler::toResponseFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(serviceResponses);
    }

    /**
     * Endpoint to update an existing service.
     * @param serviceId the ID of the service to be updated
     * @param request the updated service data
     * @return a ResponseEntity containing the updated service resource or a not found status if the service does not exist
     */
    @Operation(summary = "Update an existing Service",
        description = "Updates the details of an existing service identified by its ID",
            parameters = {
              @Parameter(in = ParameterIn.PATH, name = "visitId",
                description = "The ID of the service to be updated", required = true,
                      schema = @Schema(type = "string"))
            },
            requestBody =  @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Updated service data",
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UpdateServiceRequest.class))
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profile updated successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ServiceResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request - Invalid input data",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BadRequestResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not found - Related resource not found",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = NotFoundResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error - Unexpected error",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = InternalServerErrorResponse.class))),
            @ApiResponse(responseCode = "503", description = "Service unavailable - Persistence error",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ServiceUnavailableResponse.class)))
    })

    @PutMapping("/{serviceId}")
    public ResponseEntity<ServiceResponse> updateService(@PathVariable String serviceId,
    @Valid @RequestBody UpdateServiceRequest request){

        var updateServiceCommand = ServiceAssembler.toCommandFromRequest(serviceId, request);
        var optionalService = this.serviceCommandService.handle(updateServiceCommand);

        if (optionalService.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var serviceResponse = ServiceAssembler.toResponseFromEntity(optionalService.get());
        return ResponseEntity.ok(serviceResponse);
    }

    /*@GetMapping("/auto-repair/{autoRepairId}")
    public ResponseEntity<List<ServiceResponse>> getServicesByAutoRepairId(@PathVariable String autoRepairId){
        var query = new GetServiceByAutoRepairIdQuery(autoRepairId);
        var services = serviceQueryService.handle(query);

        var serviceResponses = services.stream()
                .map(ServiceAssembler::toResponseFromEntity).toList();
        return ResponseEntity.ok(serviceResponses);
    }
     */

    /**
     * Endpoint to delete a service by its ID.
     * @param serviceId the ID of the service to be deleted
     * @return a ResponseEntity with no content if deletion is successful
     */
    @Operation(summary = "Delete a service by its ID",
            description = "Deletes a service using its unique ID",
            parameters = {
                    @Parameter(in = ParameterIn.PATH, name = "serviceId",
                            description = "ID of the profile to retrieve",
                            required = true,
                            schema = @Schema(type = "string", format = "string"))
            }
    )
    @ApiResponses(value ={
            @ApiResponse(responseCode = "204", description = "Service deleted successfully"),
            @ApiResponse(responseCode="404", description = "Not found - The service with the specified ID does not exist",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = InternalServerErrorResponse.class)
                    )),
    })
    @DeleteMapping("/{serviceId}")
    public ResponseEntity<?> deleteService(@PathVariable String serviceId){
        var deleteServiceCommand = new DeleteServiceCommand(serviceId);
        this.serviceCommandService.handle(deleteServiceCommand);
        return ResponseEntity.noContent().build();
    }
}
