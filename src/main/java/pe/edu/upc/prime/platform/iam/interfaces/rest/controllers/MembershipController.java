package pe.edu.upc.prime.platform.iam.interfaces.rest.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.prime.platform.iam.domain.model.commands.DeleteMembershipCommand;
import pe.edu.upc.prime.platform.iam.domain.model.queries.GetAllMembershipsQuery;
import pe.edu.upc.prime.platform.iam.domain.model.queries.GetMembershipByIdQuery;
import pe.edu.upc.prime.platform.iam.domain.services.MembershipCommandService;
import pe.edu.upc.prime.platform.iam.domain.services.MembershipQueryService;
import pe.edu.upc.prime.platform.iam.interfaces.rest.assemblers.MembershipAssembler;
import pe.edu.upc.prime.platform.iam.interfaces.rest.resources.CreateMembershipRequest;
import pe.edu.upc.prime.platform.iam.interfaces.rest.resources.MembershipResponse;
import pe.edu.upc.prime.platform.iam.interfaces.rest.resources.UpdateMembershipRequest;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * REST controller for managing memberships.
 */
@CrossOrigin(origins = "*", methods = { RequestMethod.POST, RequestMethod.GET,
        RequestMethod.PUT, RequestMethod.DELETE })
@RestController
@RequestMapping(value = "/api/v1/memberships", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Memberships", description = "Membership Management Endpoints")
public class MembershipController {
    /**
     * Service for handling membership queries.
     */
    private final MembershipQueryService membershipQueryService;
    /**
     * Service for handling membership commands.
     */
    private final MembershipCommandService membershipCommandService;

    /**
     * Constructor for MembershipController.
     *
     * @param membershipQueryService the membership query service
     * @param membershipCommandService the membership command service
     */
    public MembershipController(MembershipQueryService membershipQueryService,
                                MembershipCommandService membershipCommandService) {
        this.membershipQueryService = membershipQueryService;
        this.membershipCommandService = membershipCommandService;
    }

    /**
     * Create a new membership.
     *
     * @param request the membership creation request
     * @return ResponseEntity containing the created membership response
     */
    @Operation(summary = "Create a new membership",
            description = "Creates a new membership with the provided data",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Membership data for creation", required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CreateMembershipRequest.class))),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Membership created successfully",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = MembershipResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request - Invalid input data",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = RuntimeException.class)))
            }
    )
    @PostMapping
    public ResponseEntity<MembershipResponse> createMembership(@RequestBody CreateMembershipRequest request) {
        var createMembershipCommand = MembershipAssembler.toCommandFromRequest(request);
        var membershipId = this.membershipCommandService.handle(createMembershipCommand);

        if (Objects.isNull(membershipId) || membershipId.equals(0L)) {
            return ResponseEntity.badRequest().build();
        }

        var getMembershipByIdQuery = new GetMembershipByIdQuery(membershipId);
        var membership = this.membershipQueryService.handle(getMembershipByIdQuery);
        if (membership.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var membershipResponse = MembershipAssembler.toResponseFromEntity(membership.get());
        return new ResponseEntity<>(membershipResponse, HttpStatus.CREATED);
    }

    /**
     * Retrieve all memberships.
     *
     * @return ResponseEntity containing the list of all membership responses
     */
    @Operation(summary = "Retrieve all memberships",
            description = "Retrieves a list of all memberships",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Memberships retrieved successfully",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = MembershipResponse.class))),
            })
    @GetMapping
    public ResponseEntity<List<MembershipResponse>> getAllMemberships() {
        var getAllMembershipsQuery = new GetAllMembershipsQuery();
        var memberships = this.membershipQueryService.handle(getAllMembershipsQuery);

        var membershipResponses = memberships.stream()
                .map(MembershipAssembler::toResponseFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(membershipResponses);
    }

    /**
     * Retrieve a membership by its ID.
     *
     * @param membership_id the ID of the membership to retrieve
     * @return ResponseEntity containing the membership response
     */
    @Operation(summary = "Retrieve a membership by its ID",
            description = "Retrieves a membership using its unique ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Membership retrieved successfully",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = MembershipResponse.class))),
            })
    @GetMapping("/{membership_id}")
    public ResponseEntity<MembershipResponse> getMembershipById(@PathVariable Long membership_id) {
        var getMembershipByIdQuery = new GetMembershipByIdQuery(membership_id);
        var optionalMembership = this.membershipQueryService.handle(getMembershipByIdQuery);
        if (optionalMembership.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var membershipResponse = MembershipAssembler.toResponseFromEntity(optionalMembership.get());
        return ResponseEntity.ok(membershipResponse);
    }

    /**
     * Update an existing membership.
     *
     * @param membership_id the ID of the membership to update
     * @param request the membership update request
     * @return ResponseEntity containing the updated membership response
     */
    @Operation(summary = "Update an existing membership",
            description = "Update an existing membership with the provided data",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Membership data for update", required = true,
                    content = @Content (
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UpdateMembershipRequest.class))),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Membership updated successfully",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = MembershipResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request - Invalid input data",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = RuntimeException.class)))
            }
    )
    @PutMapping("/{membership_id}")
    public ResponseEntity<MembershipResponse> updateMembership(@PathVariable Long membership_id,
                                                               @RequestBody UpdateMembershipRequest request) {
        var updateMembershipCommand = MembershipAssembler.toCommandFromRequest(membership_id, request);
        var optionalMembership = this.membershipCommandService.handle(updateMembershipCommand);
        if (optionalMembership.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var membershipResponse = MembershipAssembler.toResponseFromEntity(optionalMembership.get());
        return ResponseEntity.ok(membershipResponse);
    }

    /**
     * Delete a membership by its ID.
     *
     * @param membership_id the ID of the membership to delete
     * @return ResponseEntity with no content
     */
    @Operation(summary = "Delete a membership by its ID",
            description = "Deletes a membership using its unique ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Membership deleted successfully"),
                    @ApiResponse(responseCode = "400", description = "Bad request - Invalid membership ID",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = RuntimeException.class)))
            }
    )
    @DeleteMapping("/{membership_id}")
    public ResponseEntity<?> deleteMembership(@PathVariable Long membership_id) {
        var deleteMembershipCommand = new DeleteMembershipCommand(membership_id);
        this.membershipCommandService.handle(deleteMembershipCommand);
        return ResponseEntity.noContent().build();
    }
}
