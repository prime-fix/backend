package pe.edu.upc.prime.platform.autorepair.register.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * REST request for creating a Technician.
 */
public record CreateTechnicianRequest(
        String name,
        @JsonProperty("last_name") String lastName,
        @JsonProperty("auto_repair_id") Long autoRepairId
) { }