package pe.edu.upc.prime.platform.shared.domain.model.aggregates;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * AuditableAbstractAggregateRoot class.
 * This class is used to represent an abstract aggregate root that is auditable.
 * @param <T> the type of the aggregate root
 */
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
public abstract class AuditableAbstractAggregateRoot<T extends AbstractAggregateRoot<T>>
        extends AbstractAggregateRoot<T> {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false, length = 36)
    private String id;

    /**
     * The date and time when the entity was created.
     */
    @CreatedDate
    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    /**
     * The date and time when the entity was last updated.
     */
    @LastModifiedDate
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime updatedAt;
}
