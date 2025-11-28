package pe.edu.upc.prime.platform.workshopCatalog.interfaces.rest.controllers;


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
import pe.edu.upc.prime.platform.data.collection.interfaces.rest.resources.CreateVisitRequest;
import pe.edu.upc.prime.platform.data.collection.interfaces.rest.resources.VisitResponse;
import pe.edu.upc.prime.platform.shared.interfaces.rest.resources.BadRequestResponse;
import pe.edu.upc.prime.platform.shared.interfaces.rest.resources.NotFoundResponse;
import pe.edu.upc.prime.platform.workshopCatalog.domain.model.commands.DeleteAutoRepairCommand;
import pe.edu.upc.prime.platform.workshopCatalog.domain.model.queries.GetAllAutoRepairsQuery;
import pe.edu.upc.prime.platform.workshopCatalog.domain.model.queries.GetAutoRepairByIdQuery;
import pe.edu.upc.prime.platform.workshopCatalog.domain.services.AutoRepairCommandService;
import pe.edu.upc.prime.platform.workshopCatalog.domain.services.AutoRepairQueryService;
import pe.edu.upc.prime.platform.workshopCatalog.interfaces.rest.assemblers.AutoRepairAssembler;
import pe.edu.upc.prime.platform.workshopCatalog.interfaces.rest.resources.AutoRepairResponse;
import pe.edu.upc.prime.platform.workshopCatalog.interfaces.rest.resources.CreateAutoRepairRequest;
import pe.edu.upc.prime.platform.workshopCatalog.interfaces.rest.resources.UpdateAutoRepairRequest;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", methods = {RequestMethod.DELETE, RequestMethod.POST, RequestMethod.PUT, RequestMethod.PATCH})
@RestController
@RequestMapping(value = "/api/v1/autoRepairs",produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "AutoRepair", description = "Endpoints for managing autoRepairs")
public class AutoRepairController {

    private final AutoRepairCommandService autoRepairCommandService;
    private final AutoRepairQueryService autoRepairQueryService;

    public AutoRepairController(AutoRepairQueryService autoRepairQueryService, AutoRepairCommandService autoRepairCommandService) {
        this.autoRepairCommandService = autoRepairCommandService;
        this.autoRepairQueryService = autoRepairQueryService;
    }

    @Operation(summary = "Create a new Auto Repair",
            description = "Creates a new auto repair  with provided data",
            requestBody =@io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "CreateAutoRepairRequest object containing autoRepair details",
                    required = true,
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CreateAutoRepairRequest.class)))
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",description = "AutoRepair created successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AutoRepairResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BadRequestResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = InternalError.class))),
            @ApiResponse(responseCode = "503", description = "AutoRepair unavailable",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = HttpServerErrorException.InternalServerError.class)
                    ))
    })
    @PostMapping
    public ResponseEntity<AutoRepairResponse> createAutoRepair(@RequestBody CreateAutoRepairRequest request){
        var createAutoRepairServiceCommand = AutoRepairAssembler.toCommandFromRequest(request);

        var autoRepairId = this.autoRepairCommandService.handle(createAutoRepairServiceCommand);

        if (Objects.isNull(autoRepairId) || autoRepairId.isEmpty()){
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

    @Operation(summary = "Retrieves all autoRepairs",
    description = "Fetches a list of all autoRepairs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "AutoRepairs retrieved successfully",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = @ArraySchema(schema = @Schema(implementation = AutoRepairResponse.class))
            ))
    })
    public ResponseEntity<List<AutoRepairResponse>> getAllAutoRepairs(){
        var getAllAutoRepairQuery = new GetAllAutoRepairsQuery();
        var autoRepairs = this.autoRepairQueryService.handle(getAllAutoRepairQuery);

        var autoRepairResponses = autoRepairs.stream()
                .map(AutoRepairAssembler::toResponseFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(autoRepairResponses);
    }


    @Operation(summary = "Retrieve a autoRepair by its ID",
    description = "Fetches a autoRepair using its unique ID",
    parameters = {
            @Parameter(in = ParameterIn.PATH, name= "id",
            description = "ID of the autoRepair to retrieve",
            required = true,
            schema = @Schema(type = "string", format="string"))
    })
    @ApiResponses(value ={
            @ApiResponse(responseCode = "200", description = "Auto Repair retrieved successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AutoRepairResponse.class))
            ),
            @ApiResponse(responseCode="404", description = "Not found - The AutoRepair with the specified ID does not exist",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = NotFoundResponse.class)
                    )),
    })
    @GetMapping("/{id}")
    public ResponseEntity<AutoRepairResponse> getAutoRepairById(@PathVariable String id){
        var getAutoRepairByIdQuery = new GetAutoRepairByIdQuery(id);
        var optionalAutoRepair = autoRepairQueryService.handle(getAutoRepairByIdQuery);
        if(optionalAutoRepair.isEmpty()){
            return ResponseEntity.badRequest().build();
        }

        var autoRepairResponse = AutoRepairAssembler.toResponseFromEntity(optionalAutoRepair.get());
        return ResponseEntity.ok(autoRepairResponse);
    }

    @PutMapping("/{autoRepairId}")
    public ResponseEntity<AutoRepairResponse> updateAutoRepair(@PathVariable String autoRepairId,
                                                               @Valid @RequestBody UpdateAutoRepairRequest request){
        var updateAutoRepairCommand = AutoRepairAssembler.toCommandFromRequest(autoRepairId,request);
        var optionalAutoRepair = this.autoRepairCommandService.handle(updateAutoRepairCommand);

        if(optionalAutoRepair.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        var autoRepairResponse = AutoRepairAssembler.toResponseFromEntity(optionalAutoRepair.get());
        return ResponseEntity.ok(autoRepairResponse);
    }

    @DeleteMapping("/{autoRepairId}")
    public ResponseEntity<?> deleteAutoRepair(@PathVariable String autoRepairId){
        var deleteAutoRepairCommand = new DeleteAutoRepairCommand(autoRepairId);
        this.autoRepairCommandService.handle(deleteAutoRepairCommand);
        return ResponseEntity.noContent().build();
    }



}
