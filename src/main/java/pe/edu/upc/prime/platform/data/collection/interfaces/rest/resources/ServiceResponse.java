package pe.edu.upc.prime.platform.data.collection.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ServiceResponse(
        @JsonProperty("service_id")
        String serviceId, String name, String description) {
}
