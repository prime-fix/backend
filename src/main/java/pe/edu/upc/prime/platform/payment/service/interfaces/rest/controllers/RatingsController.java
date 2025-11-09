package pe.edu.upc.prime.platform.payment.service.interfaces.rest.controllers;

import io.swagger.v3.oas.annotations.Operation;
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
import pe.edu.upc.prime.platform.payment.service.domain.model.aggregates.Rating;
import pe.edu.upc.prime.platform.payment.service.domain.model.commands.DeleteRatingCommand;
import pe.edu.upc.prime.platform.payment.service.domain.model.queries.GetAllRatingsQuery;
import pe.edu.upc.prime.platform.payment.service.domain.model.queries.GetRatingByIdAutoRepairQuery;
import pe.edu.upc.prime.platform.payment.service.domain.model.queries.GetRatingByIdQuery;
import pe.edu.upc.prime.platform.payment.service.domain.model.valueobjects.IdAutoRepair;
import pe.edu.upc.prime.platform.payment.service.domain.services.RatingCommandService;
import pe.edu.upc.prime.platform.payment.service.domain.services.RatingQueryService;
import pe.edu.upc.prime.platform.payment.service.interfaces.rest.assemblers.RatingAssembler;
import pe.edu.upc.prime.platform.payment.service.interfaces.rest.resources.CreateRatingRequest;
import pe.edu.upc.prime.platform.payment.service.interfaces.rest.resources.RatingResponse;
import pe.edu.upc.prime.platform.payment.service.interfaces.rest.resources.UpdateRatingRequest;
import pe.edu.upc.prime.platform.shared.interfaces.rest.resources.BadRequestResponse;
import pe.edu.upc.prime.platform.shared.interfaces.rest.resources.InternalServerErrorResponse;
import pe.edu.upc.prime.platform.shared.interfaces.rest.resources.NotFoundResponse;
import pe.edu.upc.prime.platform.shared.interfaces.rest.resources.ServiceUnavailableResponse;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*",
        methods = { RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE })
@RestController
@RequestMapping(value = "/api/v1/ratings", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Ratings", description = "Ratings Management Endpoints")
public class RatingsController {

    private final RatingQueryService ratingQueryService;
    private final RatingCommandService ratingCommandService;

    public RatingsController(RatingQueryService ratingQueryService,
                             RatingCommandService ratingCommandService) {
        this.ratingQueryService = ratingQueryService;
        this.ratingCommandService = ratingCommandService;
    }

    // CREATE
    @Operation(summary = "Create a new rating",
            description = "Creates a new rating for an auto repair",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Rating data for creation", required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CreateRatingRequest.class))))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Rating created successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = RatingResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request - Invalid input data",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BadRequestResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = InternalServerErrorResponse.class))),
            @ApiResponse(responseCode = "503", description = "Service unavailable - Persistence error",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ServiceUnavailableResponse.class)))
    })
    @PostMapping
    public ResponseEntity<RatingResponse> createRating(
            @Valid @RequestBody CreateRatingRequest request) {

        var createCommand = RatingAssembler.toCommandFromRequest(request);
        var ratingId = ratingCommandService.handle(createCommand);

        if (Objects.isNull(ratingId) || ratingId.isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        var query = new GetRatingByIdQuery(ratingId);
        var optionalRating = ratingQueryService.handle(query);

        if (optionalRating.isEmpty()) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }

        var response = RatingAssembler.toResponseFromEntity(optionalRating.get());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // GET ALL / FILTER
    @Operation(summary = "Retrieve all ratings",
            description = "Retrieves all ratings or filters by auto repair if provided")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ratings retrieved successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = RatingResponse.class))))
    })
    @GetMapping
    public ResponseEntity<List<RatingResponse>> getAllRatings(
            @RequestParam(required = false) IdAutoRepair idAutoRepair) {

        List<Rating> ratings;

        if (Objects.nonNull(idAutoRepair)) {
            var query = new GetRatingByIdAutoRepairQuery(idAutoRepair);
            ratings = ratingQueryService.handle(query);
        } else {
            var query = new GetAllRatingsQuery();
            ratings = ratingQueryService.handle(query);
        }

        var responses = ratings.stream()
                .map(RatingAssembler::toResponseFromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    // GET BY ID
    @Operation(summary = "Retrieve a rating by its ID",
            description = "Retrieves a rating using its unique ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rating retrieved successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = RatingResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = NotFoundResponse.class)))
    })
    @GetMapping("/{ratingId}")
    public ResponseEntity<RatingResponse> getRatingById(@PathVariable String ratingId) {
        var query = new GetRatingByIdQuery(ratingId);
        var optionalRating = ratingQueryService.handle(query);

        if (optionalRating.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        var response = RatingAssembler.toResponseFromEntity(optionalRating.get());
        return ResponseEntity.ok(response);
    }

    // UPDATE
    @Operation(summary = "Update an existing rating",
            description = "Updates an existing rating with the provided data",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Rating data for update", required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UpdateRatingRequest.class))))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rating updated successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = RatingResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request - Invalid input data",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BadRequestResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = NotFoundResponse.class)))
    })
    @PutMapping("/{ratingId}")
    public ResponseEntity<RatingResponse> updateRating(
            @PathVariable String ratingId,
            @Valid @RequestBody UpdateRatingRequest request) {

        var updateCommand = RatingAssembler.toCommandFromRequest(
                new UpdateRatingRequest(
                        ratingId,
                        request.starRating(),
                        request.comment()
                )
        );

        var optionalRating = ratingCommandService.handle(updateCommand);
        if (optionalRating.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        var response = RatingAssembler.toResponseFromEntity(optionalRating.get());
        return ResponseEntity.ok(response);
    }

    // DELETE
    @Operation(summary = "Delete a rating by its ID",
            description = "Deletes a rating using its unique ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Rating deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = NotFoundResponse.class)))
    })
    @DeleteMapping("/{ratingId}")
    public ResponseEntity<?> deleteRating(@PathVariable String ratingId) {
        var deleteCommand = new DeleteRatingCommand(ratingId);
        ratingCommandService.handle(deleteCommand);
        return ResponseEntity.noContent().build();
    }
}
