package pe.edu.upc.prime.platform.autorepair.register.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.prime.platform.autorepair.register.domain.model.aggregates.Technician;
import pe.edu.upc.prime.platform.autorepair.register.domain.model.valueobjects.IdAutoRepair;

import java.util.List;

/**
 * Repository interface for managing Technician entities.
 *
 * <p>This interface extends JpaRepository to provide CRUD operations
 * and custom query methods for the Technician entity.</p>
 */
@Repository
public interface TechnicianRepository extends JpaRepository<Technician, String> {

    /**
     * Custom query method to find all technicians by a specific AutoRepair ID.
     *
     * @param idAutoRepair the ID of the AutoRepair
     * @return a list of technicians working in the specified AutoRepair
     */
    List<Technician> findByIdAutoRepair(IdAutoRepair idAutoRepair);

    /**
     * Custom query method to check if a technician exists by their name and last name.
     *
     * @param name the name of the technician
     * @param lastName the last name of the technician
     * @return true if a technician with the given name and last name exists, false otherwise
     */
    boolean existsByNameAndLastName(String name, String lastName);
}
