package pe.edu.upc.prime.platform.autorepair.catalog.interfaces.rest.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.prime.platform.autorepair.catalog.domain.model.commands.DeleteAutoRepairCommand;
import pe.edu.upc.prime.platform.autorepair.catalog.domain.model.queries.GetAllAutoRepairsQuery;
import pe.edu.upc.prime.platform.autorepair.catalog.domain.model.queries.GetAutoRepairByIdQuery;
import pe.edu.upc.prime.platform.autorepair.catalog.domain.services.AutoRepairCommandService;
import pe.edu.upc.prime.platform.autorepair.catalog.domain.services.AutoRepairQueryService;
import pe.edu.upc.prime.platform.autorepair.catalog.interfaces.rest.assemblers.AutoRepairAssembler;
import pe.edu.upc.prime.platform.autorepair.catalog.interfaces.rest.resources.AutoRepairResponse;
import pe.edu.upc.prime.platform.autorepair.catalog.interfaces.rest.resources.CreateAutoRepairRequest;
import pe.edu.upc.prime.platform.autorepair.catalog.interfaces.rest.resources.UpdateAutoRepairRequest;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * REST controller for managing AutoRepair entities.
 */
@CrossOrigin(origins = "*", methods = {RequestMethod.DELETE, RequestMethod.POST, RequestMethod.PUT, RequestMethod.PATCH})
@RestController
@RequestMapping(value = "/api/v1/auto_repairs",produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "AutoRepairs", description = "Auto Repair Management Endpoints")
public class AutoRepairController {
    /**
     * Service for handling AutoRepair commands
     */
    private final AutoRepairCommandService autoRepairCommandService;
    /**
     * Service for handling AutoRepair queries
     */
    private final AutoRepairQueryService autoRepairQueryService;

    /**
     * Constructor for AutoRepairController
     *
     * @param autoRepairQueryService the service for handling AutoRepair queries
     * @param autoRepairCommandService the service for handling AutoRepair commands
     */
    public AutoRepairController(AutoRepairQueryService autoRepairQueryService, AutoRepairCommandService autoRepairCommandService) {
        this.autoRepairCommandService = autoRepairCommandService;
        this.autoRepairQueryService = autoRepairQueryService;
    }

    /**
     * Creates a new Auto Repair
     *
     * @param request the CreateAutoRepairRequest containing auto repair details
     * @return a ResponseEntity with the created AutoRepairResponse
     */
    @Operation(summary = "Create a new auto repair",
            description = "Creates a new auto repair with the provided data",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Auto Repair data for creation", required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CreateAutoRepairRequest.class))),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Auto Repair created successfully",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AutoRepairResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request - Invalid input data",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = RuntimeException.class)))
            }
    )
    @PostMapping
    public ResponseEntity<AutoRepairResponse> createAutoRepair(@RequestBody CreateAutoRepairRequest request){
        var createAutoRepairServiceCommand = AutoRepairAssembler.toCommandFromRequest(request);

        var autoRepairId = this.autoRepairCommandService.handle(createAutoRepairServiceCommand);

        if (Objects.isNull(autoRepairId) || autoRepairId.equals(0L)){
            return ResponseEntity.badRequest().build();
        }

        var getAutoRepairByIdQuery = new GetAutoRepairByIdQuery(autoRepairId);
        var autoRepair = this.autoRepairQueryService.handle(getAutoRepairByIdQuery);

        if(autoRepair.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        var autoRepairResponse = AutoRepairAssembler.toResponseFromEntity(autoRepair.get());
        return new ResponseEntity<>(autoRepairResponse, HttpStatus.CREATED);

    }

    /**
     * Retrieves all Auto Repairs
     *
     * @return a ResponseEntity with a list of AutoRepairResponse
     */
    @Operation(summary = "Retrieve all auto repairs",
            description = "Retrieves a list of all auto repairs",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Auto Repairs retrieved successfully",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AutoRepairResponse.class))),
            })
    @GetMapping
    public ResponseEntity<List<AutoRepairResponse>> getAllAutoRepairs(){
        var getAllAutoRepairQuery = new GetAllAutoRepairsQuery();
        var autoRepairs = this.autoRepairQueryService.handle(getAllAutoRepairQuery);

        var autoRepairResponses = autoRepairs.stream()
                .map(AutoRepairAssembler::toResponseFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(autoRepairResponses);
    }


    /**
     * Retrieves an Auto Repair by its ID
     *
     * @param auto_repair_id the ID of the autoRepair to retrieve
     * @return a ResponseEntity with the AutoRepairResponse
     */
    @Operation(summary = "Retrieve a auto repair by its ID",
            description = "Retrieves a auto repair using its unique ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Auto Repair retrieved successfully",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AutoRepairResponse.class))),
            })
    @GetMapping("/{auto_repair_id}")
    public ResponseEntity<AutoRepairResponse> getAutoRepairById(@PathVariable Long auto_repair_id){
        var getAutoRepairByIdQuery = new GetAutoRepairByIdQuery(auto_repair_id);
        var optionalAutoRepair = autoRepairQueryService.handle(getAutoRepairByIdQuery);
        if(optionalAutoRepair.isEmpty()){
            return ResponseEntity.badRequest().build();
        }

        var autoRepairResponse = AutoRepairAssembler.toResponseFromEntity(optionalAutoRepair.get());
        return ResponseEntity.ok(autoRepairResponse);
    }

    /**
     * Updates an existing Auto Repair
     *
     * @param auto_repair_id the ID of the auto repair to update
     * @param request the UpdateAutoRepairRequest containing updated auto repair details
     * @return a ResponseEntity with the updated AutoRepairResponse
     */
    @Operation(summary = "Update an existing auto repair",
            description = "Update an existing auto repair with the provided data",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Auto Repair data for update", required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UpdateAutoRepairRequest.class))),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Auto Repair updated successfully",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AutoRepairResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request - Invalid input data",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = RuntimeException.class)))
            }
    )
    @PutMapping("/{auto_repair_id}")
    public ResponseEntity<AutoRepairResponse> updateAutoRepair(@PathVariable Long auto_repair_id,
                                                               @RequestBody UpdateAutoRepairRequest request){
        var updateAutoRepairCommand = AutoRepairAssembler.toCommandFromRequest(auto_repair_id,request);
        var optionalAutoRepair = this.autoRepairCommandService.handle(updateAutoRepairCommand);

        if(optionalAutoRepair.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        var autoRepairResponse = AutoRepairAssembler.toResponseFromEntity(optionalAutoRepair.get());
        return ResponseEntity.ok(autoRepairResponse);
    }

    /**
     * Deletes an Auto Repair by its ID
     *
     * @param auto_repair_id the ID of the auto repair to delete
     * @return a ResponseEntity with no content
     */
    @Operation(summary = "Delete an auto repair by its ID",
            description = "Deletes an auto repair  using its unique ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Auto Repair deleted successfully"),
                    @ApiResponse(responseCode = "400", description = "Bad request - Invalid auto repair ID",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = RuntimeException.class)))
            }
    )
    @DeleteMapping("/{auto_repair_id}")
    public ResponseEntity<?> deleteAutoRepair(@PathVariable Long auto_repair_id){
        var deleteAutoRepairCommand = new DeleteAutoRepairCommand(auto_repair_id);
        this.autoRepairCommandService.handle(deleteAutoRepairCommand);
        return ResponseEntity.noContent().build();
    }

}
