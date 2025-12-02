package pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.valueobjects;

/**
 * Enumeration representing the various states of a vehicle visit.
 */
public enum StateVisit {
    SCHEDULED_VISIT("Your visit has been scheduled."),
    PENDING_VISIT("Your visit is pending."),
    CANCELLED_VISIT("Your visit has been cancelled.");

    private final String notificationMessage;

    /**
     * Constructor for StateVisit enum.
     *
     * @param notificationMessage the notification message associated with the StateVisit
     */
    StateVisit(String notificationMessage) {
        this.notificationMessage = notificationMessage;
    }

    /**
     * Gets the notification message associated with the StateVisit.
     *
     * @return the notification message
     */
    public String getNotificationMessage() {
        return notificationMessage;
    }
}
