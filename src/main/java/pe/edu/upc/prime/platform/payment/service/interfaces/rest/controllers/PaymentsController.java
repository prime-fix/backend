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
import pe.edu.upc.prime.platform.payment.service.domain.model.aggregates.Payment;
import pe.edu.upc.prime.platform.payment.service.domain.model.commands.DeletePaymentCommand;
import pe.edu.upc.prime.platform.payment.service.domain.model.queries.GetAllPaymentsQuery;
import pe.edu.upc.prime.platform.payment.service.domain.model.queries.GetPaymentByIdQuery;
import pe.edu.upc.prime.platform.payment.service.domain.model.queries.GetPaymentByIdUserAccountQuery;
import pe.edu.upc.prime.platform.payment.service.domain.model.valueobjects.IdUserAccount;
import pe.edu.upc.prime.platform.payment.service.domain.services.PaymentCommandService;
import pe.edu.upc.prime.platform.payment.service.domain.services.PaymentQueryService;
import pe.edu.upc.prime.platform.payment.service.interfaces.rest.assemblers.PaymentAssembler;
import pe.edu.upc.prime.platform.payment.service.interfaces.rest.resources.CreatePaymentRequest;
import pe.edu.upc.prime.platform.payment.service.interfaces.rest.resources.PaymentResponse;
import pe.edu.upc.prime.platform.payment.service.interfaces.rest.resources.UpdatePaymentRequest;
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
@RequestMapping(value = "/api/v1/payments", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Payments", description = "Payment Management Endpoints")
public class PaymentsController {

    private final PaymentQueryService paymentQueryService;
    private final PaymentCommandService paymentCommandService;

    public PaymentsController(PaymentQueryService paymentQueryService,
                              PaymentCommandService paymentCommandService) {
        this.paymentQueryService = paymentQueryService;
        this.paymentCommandService = paymentCommandService;
    }

    // CREATE PAYMENT
    @Operation(summary = "Create a new payment",
            description = "Creates a new payment method for a user",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Payment data for creation", required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CreatePaymentRequest.class))))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Payment created successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PaymentResponse.class))),
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
    public ResponseEntity<PaymentResponse> createPayment(
            @Valid @RequestBody CreatePaymentRequest request) {

        var createCommand = PaymentAssembler.toCommandFromRequest(request);
        var paymentId = paymentCommandService.handle(createCommand);

        if (Objects.isNull(paymentId) || paymentId.isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        var query = new GetPaymentByIdQuery(paymentId);
        var optionalPayment = paymentQueryService.handle(query);


        var response = PaymentAssembler.toResponseFromEntity(optionalPayment.get());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // GET ALL / BY USER ACCOUNT
    @Operation(summary = "Retrieve all payments",
            description = "Retrieves all payments or filters by user account if provided")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payments retrieved successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = PaymentResponse.class))))
    })
    @GetMapping
    public ResponseEntity<List<PaymentResponse>> getAllPayments(
            @RequestParam(required = false) String idUserAccount) {

        List<Payment> payments;

        if (Objects.isNull(idUserAccount)) {
            var query = new GetAllPaymentsQuery();
            payments = paymentQueryService.handle(query);
        } else {
            var query = new GetPaymentByIdUserAccountQuery(new IdUserAccount(idUserAccount));
            payments = paymentQueryService.handle(query);
        }

        var responses = payments.stream()
                .map(PaymentAssembler::toResponseFromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    // GET BY ID
    @Operation(summary = "Retrieve a payment by its ID",
            description = "Retrieves a payment using its unique ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payment retrieved successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PaymentResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = NotFoundResponse.class)))
    })
    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentResponse> getPaymentById(@PathVariable String paymentId) {
        var query = new GetPaymentByIdQuery(paymentId);
        var optionalPayment = paymentQueryService.handle(query);

        if (optionalPayment.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        var response = PaymentAssembler.toResponseFromEntity(optionalPayment.get());
        return ResponseEntity.ok(response);
    }

    // UPDATE
    @Operation(summary = "Update an existing payment",
            description = "Updates an existing payment with the provided data",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Payment data for update", required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UpdatePaymentRequest.class))))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payment updated successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PaymentResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request - Invalid input data",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BadRequestResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = NotFoundResponse.class)))
    })
    @PutMapping("/{paymentId}")
    public ResponseEntity<PaymentResponse> updatePayment(
            @PathVariable String paymentId,
            @Valid @RequestBody UpdatePaymentRequest request) {

        var updateCommand = PaymentAssembler.toCommandFromRequest(
                new UpdatePaymentRequest(
                        paymentId,
                        request.cardNumber(),
                        request.cardType(),
                        request.month(),
                        request.year(),
                        request.ccv(),
                        request.idUserAccount()
                )
        );

        var optionalPayment = paymentCommandService.handle(updateCommand);
        if (optionalPayment.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        var response = PaymentAssembler.toResponseFromEntity(optionalPayment.get());
        return ResponseEntity.ok(response);
    }

    // DELETE
    @Operation(summary = "Delete a payment by its ID",
            description = "Deletes a payment using its unique ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Payment deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = NotFoundResponse.class)))
    })
    @DeleteMapping("/{paymentId}")
    public ResponseEntity<?> deletePayment(@PathVariable String paymentId) {
        var deleteCommand = new DeletePaymentCommand(paymentId);
        paymentCommandService.handle(deleteCommand);
        return ResponseEntity.noContent().build();
    }
}

