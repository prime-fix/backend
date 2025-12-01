package pe.edu.upc.prime.platform.payment.service.interfaces.rest.controllers;

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
import pe.edu.upc.prime.platform.payment.service.domain.model.commands.DeleteRatingCommand;
import pe.edu.upc.prime.platform.payment.service.domain.model.queries.GetAllRatingsQuery;
import pe.edu.upc.prime.platform.payment.service.domain.model.queries.GetRatingByIdQuery;
import pe.edu.upc.prime.platform.payment.service.domain.services.RatingCommandService;
import pe.edu.upc.prime.platform.payment.service.domain.services.RatingQueryService;
import pe.edu.upc.prime.platform.payment.service.interfaces.rest.assemblers.RatingAssembler;
import pe.edu.upc.prime.platform.payment.service.interfaces.rest.resources.CreateRatingRequest;
import pe.edu.upc.prime.platform.payment.service.interfaces.rest.resources.RatingResponse;
import pe.edu.upc.prime.platform.payment.service.interfaces.rest.resources.UpdateRatingRequest;

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
                            schema = @Schema(implementation = CreateRatingRequest.class))),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Rating created successfully",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = RatingResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request - Invalid input data",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = RuntimeException.class)))
            }
    )

    @PostMapping
    public ResponseEntity<RatingResponse> createRating(
            @Valid @RequestBody CreateRatingRequest request) {

        var createCommand = RatingAssembler.toCommandFromRequest(request);
        var ratingId = this.ratingCommandService.handle(createCommand);

        if (Objects.isNull(ratingId) || ratingId.equals(0L)) {
            return ResponseEntity.badRequest().build();
        }

        var getRatingByIdQuery = new GetRatingByIdQuery(ratingId);
        var rating = ratingQueryService.handle(getRatingByIdQuery);

        if (rating.isEmpty()) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }

        var response = RatingAssembler.toResponseFromEntity(rating.get());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // GET ALL / FILTER
    @Operation(summary = "Retrieve all ratings",
            description = "Retrieves all ratings or filters by auto repair if provided",
            responses = {
                @ApiResponse(responseCode = "200", description = "Ratings retrieved successfully",
                        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                schema = @Schema(implementation = RatingResponse.class)))
            })
    @GetMapping
    public ResponseEntity<List<RatingResponse>> getAllRatings() {

        var getAllRatingsQuery = new GetAllRatingsQuery();
        var ratings = ratingQueryService.handle(getAllRatingsQuery);

        var responses = ratings.stream()
                .map(RatingAssembler::toResponseFromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    // GET BY ID
    @Operation(summary = "Retrieve a rating by its ID",
            description = "Retrieves a rating using its unique ID",
            responses = {
                @ApiResponse(responseCode = "200", description = "Rating retrieved successfully",
                        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                schema = @Schema(implementation = RatingResponse.class))),
    })
    @GetMapping("/{rating_id}")
    public ResponseEntity<RatingResponse> getRatingById(@PathVariable Long rating_id) {
        var getRatingByIdQuery = new GetRatingByIdQuery(rating_id);
        var optionalRating = ratingQueryService.handle(getRatingByIdQuery);

        if (optionalRating.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var ratingResponse = RatingAssembler.toResponseFromEntity(optionalRating.get());
        return ResponseEntity.ok(ratingResponse);
    }

    // UPDATE
    @Operation(summary = "Update an existing rating",
            description = "Updates an existing rating with the provided data",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Rating data for update", required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UpdateRatingRequest.class))),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Rating updated successfully",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = RatingResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request - Invalid input data",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = RuntimeException.class)))
            }
    )
    @PutMapping("/{id_rating}")
    public ResponseEntity<RatingResponse> updateRating(
            @PathVariable Long id_rating,
            @Valid @RequestBody UpdateRatingRequest request) {

        var updateCommand = RatingAssembler.toCommandFromRequest(id_rating, request);
        var optionalRating = this.ratingCommandService.handle(updateCommand);
        if (optionalRating.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var ratingResponse = RatingAssembler.toResponseFromEntity(optionalRating.get());
        return ResponseEntity.ok(ratingResponse);
    }

    // DELETE
    @Operation(summary = "Delete a rating by its ID",
            description = "Deletes a rating using its unique ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Rating deleted successfully"),
                    @ApiResponse(responseCode = "400", description = "Bad request - Invalid rating ID",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = RuntimeException.class)))
            }
    )
    @DeleteMapping("/{rating_id}")
    public ResponseEntity<?> deleteRating(@PathVariable Long rating_id) {
        var deleteRatingCommand = new DeleteRatingCommand(rating_id);
        this.ratingCommandService.handle(deleteRatingCommand);
        return ResponseEntity.noContent().build();
    }
}
