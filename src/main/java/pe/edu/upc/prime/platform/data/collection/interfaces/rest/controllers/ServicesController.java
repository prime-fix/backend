package pe.edu.upc.prime.platform.data.collection.interfaces.rest.controllers;


import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.prime.platform.data.collection.domain.model.queries.GetServiceByAutoRepairIdQuery;
import pe.edu.upc.prime.platform.data.collection.domain.model.queries.GetServiceByIdQuery;
import pe.edu.upc.prime.platform.data.collection.domain.services.ServiceCommandService;
import pe.edu.upc.prime.platform.data.collection.domain.services.ServiceQueryService;
import pe.edu.upc.prime.platform.data.collection.interfaces.rest.assemblers.ServiceAssembler;
import pe.edu.upc.prime.platform.data.collection.interfaces.rest.resources.CreateServiceRequest;
import pe.edu.upc.prime.platform.data.collection.interfaces.rest.resources.ServiceResponse;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collector;

@CrossOrigin(origins = "*", methods = {RequestMethod.DELETE, RequestMethod.PUT, RequestMethod.POST, RequestMethod.GET})
@RestController
@RequestMapping(value = "/api/v1/services", produces= MediaType.APPLICATION_JSON_VALUE)
@Tag(name="Services", description="Endpoints for managing services")
public class ServicesController {

    private final ServiceQueryService serviceQueryService;
    private final ServiceCommandService serviceCommandService;

    public ServicesController(ServiceCommandService serviceCommandService, ServiceQueryService serviceQueryService) {
        this.serviceQueryService = serviceQueryService;
        this.serviceCommandService = serviceCommandService;
    }

    @PostMapping
    public ResponseEntity<ServiceResponse> createService(@RequestBody CreateServiceRequest request){
        var createServiceCommand = ServiceAssembler.toCommandFromRequest(request);
        var serviceId = this.serviceCommandService.handle(createServiceCommand);

        if (Objects.isNull(serviceId) || serviceId.isEmpty()) {
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

    /*@GetMapping("/auto-repair/{autoRepairId}")
    public ResponseEntity<List<ServiceResponse>> getServicesByAutoRepairId(@PathVariable String autoRepairId){
        var query = new GetServiceByAutoRepairIdQuery(autoRepairId);
        var services = serviceQueryService.handle(query);

        var serviceResponses = services.stream()
                .map(ServiceAssembler::toResponseFromEntity).toList();
        return ResponseEntity.ok(serviceResponses);
    }
     */

}
