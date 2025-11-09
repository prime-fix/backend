package pe.edu.upc.prime.platform.payment.service.domain.model.aggregates;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import pe.edu.upc.prime.platform.payment.service.domain.model.commands.CreatePaymentCommand;
import pe.edu.upc.prime.platform.payment.service.domain.model.commands.UpdatePaymentCommand;
import pe.edu.upc.prime.platform.payment.service.domain.model.valueobjects.*;
import pe.edu.upc.prime.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

@Entity
@Table(name="payments")
public class Payment extends AuditableAbstractAggregateRoot<Payment>{

    @Getter
    @Column(name = "card_number", nullable = false, length = 20)
    private String cardNumber;

    @Getter
    @Enumerated(EnumType.STRING)
    @Column(name = "card_type", nullable = false, length = 20)
    private CardType cardType;

    @Getter
    @Min(1)
    @Max(12)
    @Column(name = "month", nullable = false)
    private int month;

    @Getter
    @Column(name = "year", nullable = false)
    private int year;

    @Getter
    @Column(name = "ccv", nullable = false, length = 3)
    private String ccv;

    @Getter
    @Embedded
    @AttributeOverride(
            name = "idUserAccount",
            column = @Column(name = "id_user_account", nullable = false, length = 10)
    )
    private IdUserAccount idUserAccount;

    /**
     * Default constructor for JPA.
     */
    public Payment() {
    }

    /*
      * Constructs a Profile instance from a CreateProfileCommand.
      *
      * @param command createPaymentCommand containing payment details
      */
    public Payment(CreatePaymentCommand command) {
        this.cardNumber = command.cardNumber();
        this.cardType = command.cardType();
        this.month = command.month();
        this.year = command.year();
        this.ccv = command.ccv();
        this.idUserAccount = command.idUserAccount();
    }
    /** Update the profile with the specified details.
   *
     * @param command the UpdateProfileCommand containing the new profile details
     */
    public void updatePayment(UpdatePaymentCommand command) {
        this.cardNumber = command.cardNumber();
        this.cardType = command.cardType();
        this.month = command.month();
        this.year = command.year();
        this.ccv = command.ccv();
    }

    /**
     * Retorna el id del usuario asociado al pago desde el Value Object.
     */
    public String getIdUserAccountValue() {
        return idUserAccount != null ? idUserAccount.getIdUserAccount() : null;
    }

    /**
     * Retorna una versión enmascarada del número de tarjeta.
     */
    public String getMaskedCardNumber() {
        if (cardNumber == null || cardNumber.length() <= 4) {
            return cardNumber;
        }
        return "**** **** **** " + cardNumber.substring(cardNumber.length() - 4);
    }

}
