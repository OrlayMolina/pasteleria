package co.edu.uniquindio.ing.soft.pasteleria.application.mapper;

import co.edu.uniquindio.ing.soft.pasteleria.application.dto.request.CreateSupplyCommand;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.response.SupplyResponse;
import co.edu.uniquindio.ing.soft.pasteleria.domain.exception.DomainException;
import co.edu.uniquindio.ing.soft.pasteleria.domain.model.Supply;
import org.springframework.stereotype.Component;

@Component
public class SupplyDtoMapper {
    public Supply toModel(CreateSupplyCommand command, Long supplierId) throws DomainException {
        // Usar el minimumStock del comando si está presente, de lo contrario usar valor por defecto
        int minimumStock = command.minimumStock() > 0 ? command.minimumStock() : 20;

        return new Supply(
                command.name(),
                command.supplierID(),
                supplierId,
                command.price(),
                command.entryDate(),
                command.expirationDate(),
                command.quantity(),
                command.unitMeasurement(),
                minimumStock,
                command.createdAt(),
                command.updatedAt(),
                command.userModify()
        );
    }

    public SupplyResponse toResponse(Supply supply) throws DomainException {
        return new SupplyResponse(
                supply.getId(),
                supply.getName(),
                supply.getPrice(),
                supply.getEntryDate(),
                supply.getExpirationDate(),
                supply.getQuantity(),
                supply.getUnitMeasurement(),
                supply.getMinimumStock(),
                supply.getCreatedAt(),
                supply.getUpdatedAt(),
                supply.getUserModify()
        );
    }
}