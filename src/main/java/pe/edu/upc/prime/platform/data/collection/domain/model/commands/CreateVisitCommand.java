package pe.edu.upc.prime.platform.data.collection.domain.model.commands;

import java.util.Date;

public record CreateVisitCommand(String visitId, String failure, String vehicleId,
Date timeVisit, String autoRepairId, String serviceId) {



}
