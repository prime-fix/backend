package pe.edu.upc.prime.platform.payment.service.domain.model.aggregates;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    @Id
    @Getter
    @Column(name="id_rating", nullable = false, unique = true)
    @JsonProperty("id_rating")
    private String idRating;

    @Getter
    @Min(1)
    @Max(5)
    @Column(name = "star_rating", nullable = false)
    @JsonProperty("star_rating")
    private int starRating;

    @Getter
    @Column(name = "comment", length = 250)
    private String comment;

    @Getter
    @Embedded
    @AttributeOverride(
            name = "id_auto_repair",
            column = @Column(name = "id_auto_repair", nullable = false, length = 10)
    )
    @JsonProperty("id_auto_repair")
    private IdAutoRepair idAutoRepair;

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
    public Rating() {
    }

    /**
     * Constructs a Profile instance from a CreateRatingCommand.
     *
     * @param command createProfileCommand containing rating details
     */
    public Rating(CreateRatingCommand command) {
        this.idRating = command.idRating();
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
        this.idAutoRepair = command.idAutoRepair();
        this.idUserAccount = command.idUserAccount();
    }

    public String getIdAutoRepairValue() {
        return idAutoRepair != null ? idAutoRepair.getIdAutoRepair() : null;
    }

    public String getIdUserAccountValue() {
        return idUserAccount != null ? idUserAccount.getIdUserAccount() : null;
    }
}
