package pe.edu.upc.prime.platform.payment.service.domain.model.aggregates;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import pe.edu.upc.prime.platform.payment.service.domain.model.commands.CreateRatingCommand;
import pe.edu.upc.prime.platform.payment.service.domain.model.commands.UpdateRatingCommand;
import pe.edu.upc.prime.platform.payment.service.domain.model.valueobjects.*;
import pe.edu.upc.prime.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

@Entity
@Table(name = "ratings")
public class Rating extends AuditableAbstractAggregateRoot<Rating> {
    @Getter
    @Min(1)
    @Max(5)
    @Column(name = "star_rating", nullable = false)
    private int starRating;

    @Getter
    @Column(name = "comment", length = 250)
    private String comment;

    @Getter
    @Embedded
    @AttributeOverride(
            name = "idAutoRepair",
            column = @Column(name = "id_auto_repair", nullable = false, length = 10)
    )
    private IdAutoRepair idAutoRepair;

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
    public Rating() {
    }

    /**
     * Constructs a Profile instance from a CreateRatingCommand.
     *
     * @param command createProfileCommand containing rating details
     */
    public Rating(CreateRatingCommand command) {
        this.starRating = command.starRating();
        this.comment = command.comment();
        this.idAutoRepair = command.idAutoRepair();
        this.idUserAccount = command.idUserAccount();
    }
    /** Update the rating with the specified details.
     *
     * @param command the UpdateRatingCommand containing the new rating details
     */
    public void updateRating(UpdateRatingCommand command) {
        this.starRating = command.starRating();
        this.comment = command.comment();
    }

    public String getIdAutoRepairValue() {
        return idAutoRepair != null ? idAutoRepair.getIdAutoRepair() : null;
    }

    public String getIdUserAccountValue() {
        return idUserAccount != null ? idUserAccount.getIdUserAccount() : null;
    }
}
