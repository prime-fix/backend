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
import pe.edu.upc.prime.platform.shared.domain.model.valueobjects.UserAccountId;
import pe.edu.upc.prime.platform.payment.service.domain.services.PaymentCommandService;
import pe.edu.upc.prime.platform.payment.service.domain.services.PaymentQueryService;
import pe.edu.upc.prime.platform.payment.service.interfaces.rest.assemblers.PaymentAssembler;
import pe.edu.upc.prime.platform.payment.service.interfaces.rest.resources.CreatePaymentRequest;
import pe.edu.upc.prime.platform.payment.service.interfaces.rest.resources.PaymentResponse;
import pe.edu.upc.prime.platform.payment.service.interfaces.rest.resources.UpdatePaymentRequest;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * REST controller for managing payments.
 */
@CrossOrigin(origins = "*",
        methods = { RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE })
@RestController
@RequestMapping(value = "/api/v1/payments", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Payments", description = "Payment Management Endpoints")
public class PaymentsController {
    /**
     * Service for handling payment queries.
     */
    private final PaymentQueryService paymentQueryService;
    /**
     * Service for handling payment commands.
     */
    private final PaymentCommandService paymentCommandService;

    /**
     * Constructor for PaymentsController.
     *
     * @param paymentQueryService   the payment query service
     * @param paymentCommandService the payment command service
     */
    public PaymentsController(PaymentQueryService paymentQueryService,
                              PaymentCommandService paymentCommandService) {
        this.paymentQueryService = paymentQueryService;
        this.paymentCommandService = paymentCommandService;
    }

    /**
     * Create a new payment.
     *
     * @param request the create payment request
     * @return the response entity with the created payment
     */
    @Operation(summary = "Create a new payment",
            description = "Creates a new payment method for a user",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Payment data for creation", required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CreatePaymentRequest.class))),
            responses = {
                @ApiResponse(responseCode = "201", description = "Payment created successfully",
                        content = @Content(
                                mediaType = MediaType.APPLICATION_JSON_VALUE,
                                schema = @Schema(implementation = PaymentResponse.class))),
                @ApiResponse(responseCode = "400", description = "Bad request - Invalid input data",
                        content = @Content(
                                mediaType = MediaType.APPLICATION_JSON_VALUE,
                                schema = @Schema(implementation = RuntimeException.class)))
            }
    )
    @PostMapping
    public ResponseEntity<PaymentResponse> createPayment(
            @RequestBody CreatePaymentRequest request) {

        var createCommand = PaymentAssembler.toCommandFromRequest(request);
        var paymentId = this.paymentCommandService.handle(createCommand);

        if (Objects.isNull(paymentId) || paymentId.equals(0L)) {
            return ResponseEntity.badRequest().build();
        }

        var getPaymentByIdQuery = new GetPaymentByIdQuery(paymentId);
        var payment = paymentQueryService.handle(getPaymentByIdQuery);
        if (payment.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var paymentResponse = PaymentAssembler.toResponseFromEntity(payment.get());
        return new ResponseEntity<>(paymentResponse, HttpStatus.CREATED);
    }

    /**
     * Get all payments, optionally filtered by user account ID.
     *
     * @param user_account_id the user account ID to filter payments (optional)
     * @return the response entity with the list of payments
     */
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
            @RequestParam(required = false) Long user_account_id) {

        List<Payment> payments;

        if (Objects.isNull(user_account_id)) {
            var getAllPaymentsQuery = new GetAllPaymentsQuery();
            payments = paymentQueryService.handle(getAllPaymentsQuery);
        } else {
            var getPaymentByIdUserAccountQuery = new GetPaymentByIdUserAccountQuery(new UserAccountId(user_account_id));
            payments = paymentQueryService.handle(getPaymentByIdUserAccountQuery);
        }

        var paymentResponses = payments.stream()
                .map(PaymentAssembler::toResponseFromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(paymentResponses);
    }

    /**
     * Get a payment by its ID.
     *
     * @param payment_id the payment ID
     * @return the response entity with the payment
     */
    @Operation(summary = "Retrieve a payment by its ID",
            description = "Retrieves a payment using its unique ID",
            responses = {
            @ApiResponse(responseCode = "200", description = "Payment retrieved successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PaymentResponse.class))),
    })

    @GetMapping("/{payment_id}")
    public ResponseEntity<PaymentResponse> getPaymentById(@PathVariable Long payment_id) {
        var getPaymentByIdQuery = new GetPaymentByIdQuery(payment_id);
        var optionalPayment = paymentQueryService.handle(getPaymentByIdQuery);

        if (optionalPayment.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var response = PaymentAssembler.toResponseFromEntity(optionalPayment.get());
        return ResponseEntity.ok(response);
    }

    /**
     * Update an existing payment.
     *
     * @param payment_id the payment ID
     * @param request   the update payment request
     * @return the response entity with the updated payment
     */
    @Operation(summary = "Update an existing payment",
            description = "Updates an existing payment with the provided data",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Payment data for update", required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UpdatePaymentRequest.class))),
            responses = {
                @ApiResponse(responseCode = "200", description = "Payment updated successfully",
                        content = @Content(
                                mediaType = MediaType.APPLICATION_JSON_VALUE,
                                schema = @Schema(implementation = PaymentResponse.class))),
                @ApiResponse(responseCode = "400", description = "Bad request - Invalid input data",
                        content = @Content(
                                mediaType = MediaType.APPLICATION_JSON_VALUE,
                                schema = @Schema(implementation = RuntimeException.class)))
            }
    )
    @PutMapping("/{payment_id}")
    public ResponseEntity<PaymentResponse> updatePayment(
            @PathVariable Long payment_id,
            @RequestBody UpdatePaymentRequest request) {

        var updatePaymentCommand = PaymentAssembler.toCommandFromRequest(payment_id, request);
        var optionalPayment = this.paymentCommandService.handle(updatePaymentCommand);
        if (optionalPayment.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        var response = PaymentAssembler.toResponseFromEntity(optionalPayment.get());
        return ResponseEntity.ok(response);
    }

    /**
     * Delete a payment by its ID.
     *
     * @param payment_id the payment ID
     * @return the response entity indicating the result of the deletion
     */
    @Operation(summary = "Delete a payment by its ID",
            description = "Deletes a payment using its unique ID",
            responses = {
                @ApiResponse(responseCode = "204", description = "Payment deleted successfully"),
                @ApiResponse(responseCode = "400", description = "Bad request - Invalid payment ID",
                        content = @Content(
                                mediaType = MediaType.APPLICATION_JSON_VALUE,
                                schema = @Schema(implementation = RuntimeException.class)))
            }
    )
    @DeleteMapping("/{payment_id}")
    public ResponseEntity<?> deletePayment(@PathVariable Long payment_id) {
        var deletePaymentCommand = new DeletePaymentCommand(payment_id);
        paymentCommandService.handle(deletePaymentCommand);
        return ResponseEntity.noContent().build();
    }
}

