package pe.edu.upc.prime.platform.payment.service.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.prime.platform.payment.service.domain.model.aggregates.Payment;
import pe.edu.upc.prime.platform.payment.service.domain.model.queries.GetAllPaymentsQuery;
import pe.edu.upc.prime.platform.payment.service.domain.model.queries.GetPaymentByIdQuery;
import pe.edu.upc.prime.platform.payment.service.domain.model.queries.GetPaymentByIdUserAccountQuery;
import pe.edu.upc.prime.platform.payment.service.domain.services.PaymentQueryService;
import pe.edu.upc.prime.platform.payment.service.infrastructure.persistence.jpa.repositories.PaymentRepository;
import pe.edu.upc.prime.platform.shared.domain.exceptions.NotFoundIdException;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentQueryServiceImpl implements PaymentQueryService {

    private final PaymentRepository paymentRepository;

    public PaymentQueryServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public List<Payment> handle(GetAllPaymentsQuery query) {
        return paymentRepository.findAll();
    }

    @Override
    public Optional<Payment> handle(GetPaymentByIdQuery query) {
        return Optional.ofNullable(this.paymentRepository.findById(query.paymentId())
                .orElseThrow(() -> new NotFoundIdException(Payment.class,query.paymentId())));
    }

    @Override
    public List<Payment> handle(GetPaymentByIdUserAccountQuery query) {
        return paymentRepository.findByIdUserAccount(query.idUserAccount());
    }
}