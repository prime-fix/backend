package pe.edu.upc.prime.platform.iam.domain.model.aggregates;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import pe.edu.upc.prime.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

import java.time.LocalDate;

@Entity
@Table(name = "memberships")
public class Membership extends AuditableAbstractAggregateRoot<Membership> {

    @Id
    @Getter
    @Column(name="id_membership", nullable = false, unique = true)
    @JsonProperty("id_membership")
    private String idMembership;

    @Getter
    @Column(name = "description", nullable = false)
    private String description;


    @Getter
    @Column(name = "started", nullable = false)
    private LocalDate started;

    @Getter
    @Column(name = "over", nullable = false)
    private LocalDate over;

    /**
     * Default constructor for JPA.
     */
    public Membership() {
    }
}
