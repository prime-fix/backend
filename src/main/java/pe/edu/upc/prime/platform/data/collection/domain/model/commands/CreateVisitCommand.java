package pe.edu.upc.center.data_collection.data.domain.model.commands;

import pe.edu.upc.center.data_collection.data.domain.model.valueobjects.Service;
import pe.edu.upc.center.data_collection.data.domain.model.valueobjects.IdAutoRepair;
import pe.edu.upc.center.data_collection.data.domain.model.valueobjects.IdVehicle;

import java.util.Date;

public record CreateVisitCommand(VehicleId vehicleId, Service service, String failure, Date timeVisit, AutoRepairId autoRepairId) {

}
