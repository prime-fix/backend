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

@Entity
@Table(name="payments")
public class Payment extends AuditableAbstractAggregateRoot<Payment>{

    @Id
    @Getter
    @Column(name = "id_payment", nullable = false, unique = true)
    @JsonProperty("id_payment")
    private String idPayment;

    @Getter
    @Column(name = "card_number", nullable = false, length = 20)
    @JsonProperty("card_number")
    private String cardNumber;

    @Getter
    @Enumerated(EnumType.STRING)
    @Column(name = "card_type", nullable = false, length = 20)
    @JsonProperty("card_type")
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
            name = "id_user_account",
            column = @Column(name = "id_user_account", nullable = false, length = 10)
    )
    @JsonProperty("id_user_account")
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
        this.idPayment = command.idPayment();
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


    public String getIdUserAccountValue() {
        return idUserAccount != null ? idUserAccount.getIdUserAccount() : null;
    }


    public String getMaskedCardNumber() {
        if (cardNumber == null || cardNumber.length() <= 4) {
            return cardNumber;
        }
        return "**** **** **** " + cardNumber.substring(cardNumber.length() - 4);
    }

}
