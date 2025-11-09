package pe.edu.upc.center.data_collection.data.domain.services;

import pe.edu.upc.center.data_collection.data.domain.model.aggregates.Visit;
import pe.edu.upc.center.data_collection.data.domain.model.commands.CreateVisitCommand;

public interface VisitCommandService {

    Long handle(CreateVisitCommand command);
}
