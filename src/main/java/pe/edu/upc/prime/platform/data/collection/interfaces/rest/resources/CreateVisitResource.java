package pe.edu.upc.center.data_collection.data.interfaces.rest.resources;

import pe.edu.upc.center.data_collection.data.domain.model.valueobjects.Service;
import pe.edu.upc.center.data_collection.data.domain.model.valueobjects.IdAutoRepair;
import pe.edu.upc.center.data_collection.data.domain.model.valueobjects.IdVehicle;

import java.util.Date;

public record CreateVisitResource( IdVehicle idVehicle, Service service,
                                  String failure, Date timeVisit,
                                  IdAutoRepair idAutoRepair) {
}
