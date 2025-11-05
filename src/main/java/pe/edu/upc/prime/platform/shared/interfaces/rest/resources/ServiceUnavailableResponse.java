package pe.edu.upc.prime.platform.shared.interfaces.rest.resources;

public record ServiceUnavailableResponse(
        int status, String error, String message
) {
}
