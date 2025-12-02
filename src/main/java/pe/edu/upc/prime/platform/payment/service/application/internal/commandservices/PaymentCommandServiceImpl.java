package pe.edu.upc.prime.platform.payment.service.application.internal.commandservices;

import jakarta.persistence.PersistenceException;
import org.springframework.stereotype.Service;
import pe.edu.upc.prime.platform.payment.service.application.internal.outboundservices.acl.ExternalIamServiceFromPaymentService;
import pe.edu.upc.prime.platform.payment.service.domain.model.aggregates.Payment;
import pe.edu.upc.prime.platform.payment.service.domain.model.commands.CreatePaymentCommand;
import pe.edu.upc.prime.platform.payment.service.domain.model.commands.DeletePaymentCommand;
import pe.edu.upc.prime.platform.payment.service.domain.model.commands.UpdatePaymentCommand;
import pe.edu.upc.prime.platform.payment.service.domain.services.PaymentCommandService;
import pe.edu.upc.prime.platform.payment.service.infrastructure.persistence.jpa.repositories.PaymentRepository;
import pe.edu.upc.prime.platform.shared.domain.exceptions.NotFoundArgumentException;
import pe.edu.upc.prime.platform.shared.domain.exceptions.NotFoundIdException;

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
     * Service for interacting with external IAM services.
     */
    private final ExternalIamServiceFromPaymentService externalIamServiceFromPaymentService;

    /**
     * Constructor for PaymentCommandServiceImpl.
     *
     * @param paymentRepository the repository to access user data
     * @param externalIamServiceFromPaymentService service for interacting with external IAM services
     */
    public PaymentCommandServiceImpl(PaymentRepository paymentRepository,
                                     ExternalIamServiceFromPaymentService externalIamServiceFromPaymentService) {
        this.paymentRepository = paymentRepository;
        this.externalIamServiceFromPaymentService = externalIamServiceFromPaymentService;
    }

    /**
     * Handles the creation of a new payment based on the provided command.
     *
     * @param command the command containing the user information
     * @return the ID of the newly created user
     */
    @Override
    public Long handle(CreatePaymentCommand command) {
        // Validate if user account ID exists in external IAM service
        if (!this.externalIamServiceFromPaymentService.existsUserAccountById(command.userAccountId().userAccountId())) {
            throw new NotFoundArgumentException(
                    String.format("[PaymentCommandServiceImpl User Account ID: %s not found in the external IAM service",
                            command.userAccountId().userAccountId()));
        }

        // Create a new Payment aggregate using the command data
        var payment = new Payment(command);

        // Save the new payment to the repository
        try {
            this.paymentRepository.save(payment);
        } catch (Exception e) {
            throw new PersistenceException(
                    "[PaymentCommandServiceImpl] Error while saving payment: " + e.getMessage());
        }
        return payment.getId();
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

        // Check if the payment exists
        if (!this.paymentRepository.existsById(paymentId)) {
            throw new NotFoundIdException(Payment.class, paymentId);
        }

        // Validate if user account ID exists in external IAM service
        if (!this.externalIamServiceFromPaymentService.existsUserAccountById(command.userAccountId().userAccountId())) {
            throw new NotFoundArgumentException(
                    String.format("[PaymentCommandServiceImpl] User Account ID: %s not found in the external IAM service",
                            command.userAccountId().userAccountId()));
        }

        // Retrieve the existing payment and update its details
        var paymentToUpdate = this.paymentRepository.findById(paymentId).get();
        paymentToUpdate.updatePayment(command);

        // Save the updated payment to the repository
        try {
            this.paymentRepository.save(paymentToUpdate);
            return Optional.of(paymentToUpdate);
        } catch (Exception e) {
            throw new PersistenceException(
                    "[PaymentCommandServiceImpl] Error while updating payment: " + e.getMessage());
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

        // Check if the payment exists
        if (!this.paymentRepository.existsById(paymentId)) {
            throw new NotFoundIdException(Payment.class, paymentId);
        }

        // Delete the payment from the repository
        try {
            this.paymentRepository.deleteById(paymentId);
        } catch (Exception e) {
            throw new PersistenceException("[PaymentCommandServiceImpl] Error while deleting service:" + e.getMessage());
        }
    }
}
