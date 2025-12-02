package pe.edu.upc.prime.platform.payment.service.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.prime.platform.payment.service.domain.model.aggregates.Payment;
import pe.edu.upc.prime.platform.payment.service.domain.model.queries.ExistsPaymentByIdQuery;
import pe.edu.upc.prime.platform.payment.service.domain.model.queries.GetAllPaymentsQuery;
import pe.edu.upc.prime.platform.payment.service.domain.model.queries.GetPaymentByIdQuery;
import pe.edu.upc.prime.platform.payment.service.domain.model.queries.GetPaymentByIdUserAccountQuery;
import pe.edu.upc.prime.platform.payment.service.domain.services.PaymentQueryService;
import pe.edu.upc.prime.platform.payment.service.infrastructure.persistence.jpa.repositories.PaymentRepository;
import pe.edu.upc.prime.platform.shared.domain.exceptions.NotFoundIdException;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the PaymentQueryService interface.
 */
@Service
public class PaymentQueryServiceImpl implements PaymentQueryService {
    /**
     * Repository for managing Payment entities.
     */
    private final PaymentRepository paymentRepository;

    /**
     * Constructor for PaymentQueryServiceImpl.
     *
     * @param paymentRepository The repository for managing Payment entities.
     */
    public PaymentQueryServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    /**
     * Handle the query to get all payments.
     *
     * @param query the query to get all payments
     * @return a list of all payments
     */
    @Override
    public List<Payment> handle(GetAllPaymentsQuery query) {
        return paymentRepository.findAll();
    }

    /**
     * Handle the query to get a payment by its ID.
     *
     * @param query the query to get a payment by ID
     * @return an Optional containing the Payment if found, or empty if not found
     */
    @Override
    public Optional<Payment> handle(GetPaymentByIdQuery query) {
        return Optional.ofNullable(this.paymentRepository.findById(query.paymentId())
                .orElseThrow(() -> new NotFoundIdException(Payment.class,query.paymentId())));
    }

    /**
     * Handle the query to get payments by user account ID.
     *
     * @param query the query to get payments by user account ID
     * @return a list of payments associated with the specified user account ID
     */
    @Override
    public List<Payment> handle(GetPaymentByIdUserAccountQuery query) {
        return paymentRepository.findByUserAccountId(query.idUserAccount());
    }

    /**
     * Handle the query to check if a payment exists by its ID.
     *
     * @param query the query containing the payment ID to check
     * @return true if the payment exists, false otherwise
     */
    @Override
    public boolean handle(ExistsPaymentByIdQuery query) {
        return paymentRepository.existsById(query.paymentId());
    }
}