package pe.edu.upc.prime.platform.data.collection.domain.services;

import pe.edu.upc.prime.platform.data.collection.domain.model.commands.CreateVisitCommand;
import pe.edu.upc.prime.platform.data.collection.domain.model.commands.DeleteVisitCommand;

public interface VisitCommandService {

    String handle(CreateVisitCommand command);
    void handle(DeleteVisitCommand command);
}
