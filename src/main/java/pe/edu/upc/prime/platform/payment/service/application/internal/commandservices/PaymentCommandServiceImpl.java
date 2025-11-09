package pe.edu.upc.prime.platform.payment.service.application.internal.commandservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.prime.platform.payment.service.domain.model.aggregates.Payment;
import pe.edu.upc.prime.platform.payment.service.domain.model.commands.CreatePaymentCommand;
import pe.edu.upc.prime.platform.payment.service.domain.model.commands.DeletePaymentCommand;
import pe.edu.upc.prime.platform.payment.service.domain.model.commands.UpdatePaymentCommand;
import pe.edu.upc.prime.platform.payment.service.domain.services.PaymentCommandService;
import pe.edu.upc.prime.platform.payment.service.infrastructure.persistence.jpa.repositories.PaymentRepository;

import java.util.Optional;

@Service
public class PaymentCommandServiceImpl implements PaymentCommandService {

    private final PaymentRepository paymentRepository;

    public PaymentCommandServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public String handle(CreatePaymentCommand command) {

        // Validate if the payment already exists
        if(this.paymentRepository.existsByIdUserAccount(command.idUserAccount())){
            throw new IllegalArgumentException("[PaymentComandServiceImpl] Payment with user account "
            + command.idUserAccount() + " already exists");
        }

        var payment = new Payment(command);

        try {
            paymentRepository.save(payment);
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "[CreatePaymentCommand] Error while saving payment: " + e.getMessage());
        }

        return payment.getId();
    }


    @Override
    public Optional<Payment> handle(UpdatePaymentCommand command) {
        var paymentId = command.paymentId();

        var existingPayment = paymentRepository.findById(paymentId);
        if (existingPayment.isEmpty()) {
            throw new IllegalArgumentException(
                    "[UpdatePaymentCommand] Payment with id " + paymentId + " does not exist");
        }

        var paymentToUpdate = existingPayment.get();
        paymentToUpdate.updatePayment(command);

        try {
            var updatedPayment = paymentRepository.save(paymentToUpdate);
            return Optional.of(updatedPayment);
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "[UpdatePaymentCommand] Error while updating payment: " + e.getMessage());
        }
    }

    @Override
    public void handle(DeletePaymentCommand command) {
        var paymentId = command.paymentId();

        if (!paymentRepository.existsById(paymentId)) {
            throw new IllegalArgumentException(
                    "[DeletePaymentCommand] Payment with id " + paymentId + " does not exist");
        }

        try {
            paymentRepository.deleteById(paymentId);
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "[DeletePaymentCommand] Error while deleting payment: " + e.getMessage());
        }
    }
}
