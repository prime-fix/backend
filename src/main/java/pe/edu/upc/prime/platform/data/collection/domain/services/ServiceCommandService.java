package pe.edu.upc.prime.platform.data.collection.domain.services;

import pe.edu.upc.prime.platform.data.collection.domain.model.aggregates.Service;
import pe.edu.upc.prime.platform.data.collection.domain.model.commands.CreateServiceCommand;
import pe.edu.upc.prime.platform.data.collection.domain.model.commands.DeleteServiceCommand;
import pe.edu.upc.prime.platform.data.collection.domain.model.commands.UpdateServiceCommand;

import java.util.Optional;

public interface ServiceCommandService {

    String handle(CreateServiceCommand command);
    Optional<Service> handle(UpdateServiceCommand command);
    void handle(DeleteServiceCommand command);

}
