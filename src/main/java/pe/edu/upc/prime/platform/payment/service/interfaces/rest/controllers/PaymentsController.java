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
            @Valid @RequestBody CreatePaymentRequest request) {

        var createCommand = PaymentAssembler.toCommandFromRequest(request);
        var paymentId = this.paymentCommandService.handle(createCommand);

        if (Objects.isNull(paymentId) || paymentId.isBlank()) {
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
            var getAllPaymentsQuery = new GetAllPaymentsQuery();
            payments = paymentQueryService.handle(getAllPaymentsQuery);
        } else {
            var getPaymentByIdUserAccountQuery = new GetPaymentByIdUserAccountQuery(new IdUserAccount(idUserAccount));
            payments = paymentQueryService.handle(getPaymentByIdUserAccountQuery);
        }

        var paymentResponses = payments.stream()
                .map(PaymentAssembler::toResponseFromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(paymentResponses);
    }

    // GET BY ID
    @Operation(summary = "Retrieve a payment by its ID",
            description = "Retrieves a payment using its unique ID",
            responses = {
            @ApiResponse(responseCode = "200", description = "Payment retrieved successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PaymentResponse.class))),
    })

    @GetMapping("/{id_payment}")
    public ResponseEntity<PaymentResponse> getPaymentById(@PathVariable String id_payment) {
        var getPaymentByIdQuery = new GetPaymentByIdQuery(id_payment);
        var optionalPayment = paymentQueryService.handle(getPaymentByIdQuery);

        if (optionalPayment.isEmpty()) {
            return ResponseEntity.notFound().build();
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
    @PutMapping("/{id_payment}")
    public ResponseEntity<PaymentResponse> updatePayment(
            @PathVariable String id_payment,
            @Valid @RequestBody UpdatePaymentRequest request) {

        var updatePaymentCommand = PaymentAssembler.toCommandFromRequest(id_payment, request);
        var optionalPayment = this.paymentCommandService.handle(updatePaymentCommand);
        if (optionalPayment.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        var response = PaymentAssembler.toResponseFromEntity(optionalPayment.get());
        return ResponseEntity.ok(response);
    }

    // DELETE
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
    @DeleteMapping("/{id_payment}")
    public ResponseEntity<?> deletePayment(@PathVariable String id_payment) {
        var deletePaymentCommand = new DeletePaymentCommand(id_payment);
        paymentCommandService.handle(deletePaymentCommand);
        return ResponseEntity.noContent().build();
    }
}

