package co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.mapper;

import co.edu.uniquindio.ing.soft.pasteleria.domain.exception.DomainException;
import co.edu.uniquindio.ing.soft.pasteleria.domain.model.Supply;
import co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.entity.SupplierEntity;
import co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.entity.SupplyEntity;
import co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class SupplyPersistenceMapper {

    public SupplyEntity toEntity(Supply supply) {
        SupplyEntity entity = new SupplyEntity();
        entity.setId(supply.getId());
        entity.setName(supply.getName());
        entity.setPrice(supply.getPrice());
        entity.setSupplierDocument(supply.getSupplierDocument());
        entity.setEntryDate(supply.getEntryDate());
        entity.setExpirationDate(supply.getExpirationDate());
        entity.setQuantity(supply.getQuantity());
        entity.setUnitMeasurement(supply.getUnitMeasurement());
        entity.setMinimumStock(supply.getMinimumStock());
        entity.setCreatedAt(supply.getCreatedAt());
        entity.setUpdatedAt(supply.getUpdatedAt());

        // Configura el proveedor
        if (supply.getSupplierId() != null) {
            SupplierEntity supplierEntity = new SupplierEntity();
            supplierEntity.setId(supply.getSupplierId());
            entity.setSupplier(supplierEntity);
        }

        // Configura el usuario que modificó
        if (supply.getUserModify() != null) {
            UserEntity userEntity = new UserEntity();
            userEntity.setId(supply.getUserModify());
            entity.setUserModify(userEntity);
        }

        return entity;
    }

    public Supply toDomain(SupplyEntity entity) throws DomainException {
        // Manejo seguro para evitar NullPointerException
        Long supplierId = null;
        if (entity.getSupplier() != null) {
            supplierId = entity.getSupplier().getId();
        }

        Long userModifyId = null;
        if (entity.getUserModify() != null) {
            userModifyId = entity.getUserModify().getId();
        }

        return new Supply(
                entity.getId(),
                entity.getName(),
                entity.getSupplierDocument(),
                supplierId,
                entity.getPrice(),
                entity.getEntryDate(),
                entity.getExpirationDate(),
                entity.getQuantity(),
                entity.getUnitMeasurement(),
                entity.getMinimumStock(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                userModifyId
        );
    }
}