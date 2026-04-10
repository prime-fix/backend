package pe.edu.upc.prime.platform.autorepair.register.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * REST response for a Technician resource.
 */
public record TechnicianResponse(
        Long id,
        String name,
        @JsonProperty("last_name") String lastName,
        @JsonProperty("auto_repair_id") Long autoRepairId
) { }
