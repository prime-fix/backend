package pe.edu.upc.prime.platform.workshopCatalog.interfaces.rest.transform;

import pe.edu.upc.prime.platform.workshopCatalog.domain.model.aggregates.AutoRepair;
import pe.edu.upc.prime.platform.workshopCatalog.interfaces.rest.resources.AutoRepairResource;

import java.text.SimpleDateFormat;

public class AutoRepairResourceFromEntityAssembler {
    public static AutoRepairResource toResourceFromEntity(AutoRepair entity) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return new AutoRepairResource(
                entity.getId(),
                entity.getCustomerName(),
                sdf.format(entity.getRepairDate()),
                entity.getRepairTime(),
                entity.getDescription(),
                entity.getStatus()
        );
    }
}
