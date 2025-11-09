package pe.edu.upc.prime.platform.vehiclediagnosis.interfaces.rest.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.prime.platform.vehiclediagnosis.domain.model.queries.GetAllDiagnosticsByVehicleIdQuery;
import pe.edu.upc.prime.platform.vehiclediagnosis.domain.services.DiagnosisCommandService;
import pe.edu.upc.prime.platform.vehiclediagnosis.domain.services.DiagnosisQueryService;
import pe.edu.upc.prime.platform.vehiclediagnosis.interfaces.rest.resource.DiagnosticResource;

import java.util.List;

/**
 * REST controller for managing VehiclesDiagnosis.
 */
@CrossOrigin(origins = "*", methods = { RequestMethod.POST, RequestMethod.GET,
        RequestMethod.PUT, RequestMethod.DELETE })
@RestController
@RequestMapping(value = "/api/v1/vehicle-diagnosis", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "VehiclesDiagnosis", description = "Vehicles Diagnosis Management Endpoints")
public class VehiclesDiagnosisController {
    private final DiagnosisQueryService diagnosisQueryService;
    private final DiagnosisCommandService diagnosisCommandService;

    public VehiclesDiagnosisController(DiagnosisQueryService diagnosisQueryService,
                                       DiagnosisCommandService diagnosisCommandService) {
        this.diagnosisQueryService = diagnosisQueryService;
        this.diagnosisCommandService = diagnosisCommandService;
    }

    @GetMapping("/vehicle/{vehicleId}")
    public ResponseEntity<List<DiagnosticResource>> getAllDiagnosticsByVehicleId(@PathVariable String vehicleId) {
        var query = new GetAllDiagnosticsByVehicleIdQuery(vehicleId);
        var diagnostics = diagnosisQueryService.handle(query);
        return ResponseEntity.ok(diagnostics);
    }


    @PostMapping
    public ResponseEntity<DiagnosticResource> createDiagnostic(@RequestBody CreateDiagnosticCommand command) {
        var created = diagnosisCommandService.handle(command);
        return ResponseEntity.ok(created);
    }
}
