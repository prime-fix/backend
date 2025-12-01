package pe.edu.upc.prime.platform.payment.service.domain.model.aggregates;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import pe.edu.upc.prime.platform.payment.service.domain.model.commands.CreateRatingCommand;
import pe.edu.upc.prime.platform.payment.service.domain.model.commands.UpdateRatingCommand;
import pe.edu.upc.prime.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import pe.edu.upc.prime.platform.shared.domain.model.valueobjects.AutoRepairId;
import pe.edu.upc.prime.platform.shared.domain.model.valueobjects.UserAccountId;

/**
 * Rating aggregate root entity.
 */
@Entity
@Table(name = "ratings")
public class Rating extends AuditableAbstractAggregateRoot<Rating> {
    @Getter
    @Min(1)
    @Max(5)
    @Column(name = "star_rating", nullable = false)
    private Integer starRating;

    @Getter
    @Column(name = "comment", length = 250)
    private String comment;

    @Getter
    @Embedded
    @AttributeOverride(
            name = "autoRepairId",
            column = @Column(name = "auto_repair_id", nullable = false, length = 10)
    )
    private AutoRepairId autoRepairId;

    @Getter
    @Embedded
    @AttributeOverride(
            name = "userAccountId",
            column = @Column(name = "user_account_id", nullable = false, length = 10)
    )
    private UserAccountId userAccountId;

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
        this.autoRepairId = command.autoRepairId();
        this.userAccountId = command.userAccountId();
    }
    /** Update the rating with the specified details.
     *
     * @param command the UpdateRatingCommand containing the new rating details
     */
    public void updateRating(UpdateRatingCommand command) {
        this.starRating = command.starRating();
        this.comment = command.comment();
        this.autoRepairId = command.autoRepairId();
        this.userAccountId = command.userAccountId();
    }
}
