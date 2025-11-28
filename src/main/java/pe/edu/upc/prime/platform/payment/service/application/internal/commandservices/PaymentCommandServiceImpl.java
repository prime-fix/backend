package pe.edu.upc.prime.platform.payment.service.application.internal.commandservices;

import jakarta.persistence.PersistenceException;
import org.springframework.stereotype.Service;
import pe.edu.upc.prime.platform.payment.service.domain.model.aggregates.Payment;
import pe.edu.upc.prime.platform.payment.service.domain.model.commands.CreatePaymentCommand;
import pe.edu.upc.prime.platform.payment.service.domain.model.commands.DeletePaymentCommand;
import pe.edu.upc.prime.platform.payment.service.domain.model.commands.UpdatePaymentCommand;
import pe.edu.upc.prime.platform.payment.service.domain.services.PaymentCommandService;
import pe.edu.upc.prime.platform.payment.service.infrastructure.persistence.jpa.repositories.PaymentRepository;
import pe.edu.upc.prime.platform.shared.domain.exceptions.NotFoundArgumentException;

import java.util.Optional;

/**
 * Implementation of the PaymentCommandService interface.
 */
@Service
public class PaymentCommandServiceImpl implements PaymentCommandService {

    /**
     * The repository to access user data.
     */
    private final PaymentRepository paymentRepository;

    /**
     * Constructor for PaymentCommandServiceImpl.
     *
     * @param paymentRepository the repository to access user data
     */
    public PaymentCommandServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    /**
     * Handles the creation of a new payment based on the provided command.
     *
     * @param command the command containing the user information
     * @return the ID of the newly created user
     */
    @Override
    public String handle(CreatePaymentCommand command) {

        var userAccountId = command.idUserAccount();

        var payment = new Payment(command);

        try {
            this.paymentRepository.save(payment);
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "[CreatePaymentCommand] Error while saving payment: " + e.getMessage());
        }
        return payment.getId().toString();
    }

    /**
     * Handles the update of an existing payment based on the provided command.
     *
     * @param command the command containing the updated payment information
     * @return an Optional containing the updated Payment if successful
     */
    @Override
    public Optional<Payment> handle(UpdatePaymentCommand command) {
        var paymentId = command.paymentId();

        if (!this.paymentRepository.existsById(Long.valueOf(paymentId))) {
            throw new NotFoundArgumentException(
                    String.format("Payment with the same id %s does not exist.", paymentId)
            );
        }

        var paymentToUpdate = this.paymentRepository.findById(Long.valueOf(paymentId)).get();
        paymentToUpdate.updatePayment(command);

        try {
            this.paymentRepository.save(paymentToUpdate);
            return Optional.of(paymentToUpdate);
        } catch (Exception e) {
            throw new PersistenceException(
                    "[UpdatePaymentCommand] Error while updating payment: " + e.getMessage());
        }
    }

    /**
     * Handles the deletion of a payment based on the provided command.
     *
     * @param command the command containing the ID of the payment to be deleted
     */
    @Override
    public void handle(DeletePaymentCommand command) {
        var paymentId = command.paymentId();

        if (!this.paymentRepository.existsById(Long.valueOf(paymentId))) {
            throw new NotFoundArgumentException(
                    String.format("Payment with the same id %s does not exist.", paymentId));
        }

       /* this.paymentRepository.findById(paymentId).ifPresent(optionalPayment -> {
            this.paymentRepository.deleteById(optionalPayment.getIdPayment());


        });
    */
        try {
            this.paymentRepository.deleteById(Long.valueOf(paymentId));
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while deleting service:" + e.getMessage());
        }
    }
}
