package pe.edu.upc.prime.platform.payment.service.domain.model.aggregates;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import pe.edu.upc.prime.platform.payment.service.domain.model.commands.CreatePaymentCommand;
import pe.edu.upc.prime.platform.payment.service.domain.model.commands.UpdatePaymentCommand;
import pe.edu.upc.prime.platform.payment.service.domain.model.valueobjects.*;
import pe.edu.upc.prime.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import pe.edu.upc.prime.platform.shared.domain.model.valueobjects.UserAccountId;

/**
 * Payment aggregate root entity.
 */
@Entity
@Table(name="payments")
public class Payment extends AuditableAbstractAggregateRoot<Payment> {

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
    private Integer month;

    @Getter
    @Column(name = "year", nullable = false)
    private Integer year;

    @Getter
    @Min(100)
    @Max(9999)
    @Column(name = "ccv", nullable = false)
    private Integer ccv;

    @Getter
    @Embedded
    @AttributeOverride(
            name = "userAccountId",
            column = @Column(name = "user_account_id", nullable = false, length = 10)
    )
    @JsonProperty("id_user_account")
    private UserAccountId userAccountId;

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
        this.userAccountId = command.userAccountId();
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
        this.userAccountId = command.userAccountId();
    }

    public String getMaskedCardNumber() {
        if (cardNumber == null || cardNumber.length() <= 4) {
            return cardNumber;
        }
        return "**** **** **** " + cardNumber.substring(cardNumber.length() - 4);
    }

}
