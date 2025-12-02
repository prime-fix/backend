package pe.edu.upc.prime.platform.iam.domain.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.Getter;
import pe.edu.upc.prime.platform.iam.domain.model.commands.CreateMembershipCommand;
import pe.edu.upc.prime.platform.iam.domain.model.commands.UpdateMembershipCommand;
import pe.edu.upc.prime.platform.iam.domain.model.valueobjects.MembershipDescription;
import pe.edu.upc.prime.platform.shared.domain.model.entities.AuditableModel;

import java.time.LocalDate;

/**
 * Membership entity representing a user's membership details.
 */
@Getter
@Entity
@Table(name = "memberships")
public class Membership extends AuditableModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @AssociationOverrides({
            @AssociationOverride(name = "membershipDescription",
                    joinColumns = @JoinColumn(name = "description"))
    })
    private MembershipDescription membershipDescription;


    @FutureOrPresent
    @Column(name = "started", nullable = false)
    private LocalDate started;

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
