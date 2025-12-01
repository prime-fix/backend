package pe.edu.upc.prime.platform.autorepair.register.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Request to update a technician.
 *
 * @param name the technician's first name
 * @param lastName the technician's last name
 * @param autoRepairId the ID of the associated auto repair shop
 */
public record UpdateTechnicianRequest(
        String name,
        @JsonProperty("last_name") String lastName,
        @JsonProperty("auto_repair_id") Long autoRepairId
) { }
