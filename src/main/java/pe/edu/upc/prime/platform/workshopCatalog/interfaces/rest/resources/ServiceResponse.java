package pe.edu.upc.prime.platform.workshopCatalog.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Response record for service information.
 * @param serviceId the ID of the service
 * @param name the name of the service
 * @param description  the description of the service
 */
public record ServiceResponse(
        @JsonProperty("service_id")
        String serviceId, String name, String description) {
}
