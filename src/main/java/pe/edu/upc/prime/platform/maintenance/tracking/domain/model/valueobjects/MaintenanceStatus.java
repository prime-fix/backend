package pe.edu.upc.prime.platform.maintenance.tracking.domain.model.valueobjects;

import java.util.Arrays;

/**
 * Enumeration representing the various statuses of vehicle maintenance.
 */
public enum MaintenanceStatus {
    NOT_BEING_SERVICED(0),
    WAITING(1),
    IN_DIAGNOSIS(2),
    IN_REPAIR(3),
    TESTING(4),
    READY_FOR_PICKUP(5),
    COLLECTED(6);

    private final int value;

    /**
     * Constructor for MaintenanceStatus enum.
     *
     * @param value the integer value associated with the MaintenanceStatus
     */
    MaintenanceStatus(int value) {
        this.value = value;
    }

    /**
     * Gets the integer value associated with the MaintenanceStatus.
     *
     * @return the integer value of the MaintenanceStatus
     */
    public int getValue() {
        return value;
    }

    /**
     * Converts an integer value to its corresponding MaintenanceStatus enum.
     *
     * @param value the integer value representing a MaintenanceStatus
     * @return the corresponding MaintenanceStatus enum
     */
    public static MaintenanceStatus fromValue(int value) {
        return Arrays.stream(MaintenanceStatus.values())
                .filter(mt -> mt.value == value)
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException("[MaintenanceStatus] Invalid value for MaintenanceStatus: "
                                + value));
    }
}
