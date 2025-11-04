package pe.edu.upc.prime.platform.iam.domain.exceptions;

/**
 * Exception thrown when a user is not found in the system.
 */
public class UserNotfoundException extends RuntimeException {

    /**
     * Constructor for UserNotfoundException.
     *
     * @param userId The ID of the user that was not found.
     */
    public UserNotfoundException(String userId) {
        super(String.format("User with id %s does not exist.", userId));
    }

    /**
     * Constructor for UserNotfoundException with cause.
     *
     * @param userId The ID of the user that was not found.
     * @param cause The cause of the exception.
     */
    public UserNotfoundException(String userId, Throwable cause) {
        super(String.format("User with id %s does not exist", userId), cause);
    }
}
