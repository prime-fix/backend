package pe.edu.upc.prime.platform.iam.domain.model.aggregates;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.Getter;
import pe.edu.upc.prime.platform.iam.domain.model.commands.CreateMembershipCommand;
import pe.edu.upc.prime.platform.iam.domain.model.commands.UpdateMembershipCommand;
import pe.edu.upc.prime.platform.iam.domain.model.valueobjects.MembershipDescription;
import pe.edu.upc.prime.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

import java.time.LocalDate;

@Entity
@Table(name = "memberships")
public class Membership extends AuditableAbstractAggregateRoot<Membership> {


    @Getter
    @Embedded
    @AssociationOverrides({
            @AssociationOverride(name = "membershipDescription",
                    joinColumns = @JoinColumn(name = "description"))
    })
    private MembershipDescription membershipDescription;


    @Getter
    @FutureOrPresent
    @Column(name = "started", nullable = false)
    private LocalDate started;

    @Getter
    @FutureOrPresent
    @Column(name = "over", nullable = false)
    private LocalDate over;

    /**
     * Default constructor for JPA.
     */
    public Membership() {
    }

    /**
     * Constructor with parameters.
     *
     * @param command the command to create a membership
     */
    public Membership(CreateMembershipCommand command) {
        this.membershipDescription = command.membershipDescription();
        this.started = command.started();
        this.over = command.over();
    }

    /**
     * Update membership details.
     *
     * @param command the command to update a membership
     */
    public void updateMembership(UpdateMembershipCommand command) {
        this.membershipDescription = command.membershipDescription();
        this.started = command.started();
        this.over = command.over();
    }
}
