package pe.edu.upc.prime.platform.data.collection.domain.model.commands;

public record CreateServiceCommand(String serviceId, String name, String description) {
}
