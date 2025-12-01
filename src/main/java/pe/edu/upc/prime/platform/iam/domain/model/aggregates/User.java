package pe.edu.upc.prime.platform.iam.domain.model.aggregates;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import pe.edu.upc.prime.platform.iam.domain.model.commands.CreateUserCommand;
import pe.edu.upc.prime.platform.iam.domain.model.commands.UpdateUserCommand;
import pe.edu.upc.prime.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

/**
 * User aggregate root entity.
 */
@Entity
@Table(name = "users")
public class User extends AuditableAbstractAggregateRoot<User> {

    @Getter
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Getter
    @Column(name = "last_name", nullable = false, length = 100)
    @JsonProperty("last_name")
    private String lastName;

    @Getter
    @Column(name = "dni", nullable = false, length = 8)
    private String dni;

    @Getter
    @Column(name = "phone_number", nullable = false, length = 15)
    @JsonProperty("phone_number")
    private String phoneNumber;

    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private Location location;

    /**
     * Default constructor for JPA.
     */
    public User() {
    }

    /**
     * Constructor to create a User from a CreateUserCommand.
     *
     * @param command the command containing user details
     */
    public User(CreateUserCommand command, Location location) {
        this.name = command.name();
        this.lastName = command.lastName();
        this.dni = command.dni();
        this.phoneNumber = command.phoneNumber();
        this.location = location;
    }

    /**
     * Updates the User with details from an UpdateUserCommand.
     *
     * @param command the command containing updated user details
     */
    public void updateUser(UpdateUserCommand command, Location location) {
        this.name = command.name();
        this.lastName = command.lastName();
        this.dni = command.dni();
        this.phoneNumber = command.phoneNumber();
        this.location = location;
    }

}
