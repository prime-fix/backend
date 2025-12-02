package pe.edu.upc.prime.platform.autorepair.catalog.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Response record for service information.
 *
 * @param name the name of the service
 * @param description  the description of the service
 */
public record ServiceResponse(
        Long id, String name, String description) {
}
