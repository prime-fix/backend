package pe.edu.upc.prime.platform.autorepair.catalog.domain.model.commands;

/**
 *  Command to update an existing auto repair
 * @param locationID the ID of the location to update
 * @param address the address
 * @param district the district
 * @param department the department
 */
public record UpdateLocationCommand(String locationID, String address, String district, String department) {
}
