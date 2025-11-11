package pe.edu.upc.prime.platform.autorepair.register.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * REST request for updating a Technician.
 */
public record UpdateTechnicianRequest(
        @JsonProperty("name") String name,
        @JsonProperty("last_name") String lastName,
        @JsonProperty("id_auto_repair") Long idAutoRepair
) { }
