package co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.mapper;

import co.edu.uniquindio.ing.soft.pasteleria.domain.exception.DomainException;
import co.edu.uniquindio.ing.soft.pasteleria.domain.model.CakeOrder;
import co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.entity.CakeOrderEntity;
import co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class CakeOrderPersistenceMapper {

    public CakeOrderEntity toEntity(CakeOrder cakeOrder) {
        CakeOrderEntity entity = new CakeOrderEntity();
        entity.setId(cakeOrder.getId());
        entity.setOrderDate(cakeOrder.getOrderDate());
        entity.setDeliveryDate(cakeOrder.getDeliveryDate());
        entity.setCustomerName(cakeOrder.getCustomerName());
        entity.setCustomerPhone(cakeOrder.getCustomerPhone());
        entity.setCustomerEmail(cakeOrder.getCustomerEmail());
        entity.setOrderStatus(cakeOrder.getOrderStatus());
        entity.setTotalAmount(cakeOrder.getTotalAmount());
        entity.setHasInventoryAlert(cakeOrder.getHasInventoryAlert());
        entity.setInventoryAlertDetails(cakeOrder.getInventoryAlertDetails());
        entity.setCreatedAt(cakeOrder.getCreatedAt());
        entity.setUpdatedAt(cakeOrder.getUpdatedAt());

        // Configurar usuario creador
        if (cakeOrder.getCreatedById() != null) {
            UserEntity createdByEntity = new UserEntity();
            createdByEntity.setId(cakeOrder.getCreatedById());
            entity.setCreatedBy(createdByEntity);
        }

        // Configurar usuario modificador
        if (cakeOrder.getModifiedById() != null) {
            UserEntity modifiedByEntity = new UserEntity();
            modifiedByEntity.setId(cakeOrder.getModifiedById());
            entity.setModifiedBy(modifiedByEntity);
        }

        return entity;
    }

    public CakeOrder toDomain(CakeOrderEntity entity) throws DomainException {
        // Manejo seguro para evitar NullPointerException
        Long createdById = null;
        if (entity.getCreatedBy() != null) {
            createdById = entity.getCreatedBy().getId();
        }

        Long modifiedById = null;
        if (entity.getModifiedBy() != null) {
            modifiedById = entity.getModifiedBy().getId();
        }

        return new CakeOrder(
                entity.getId(),
                entity.getOrderDate(),
                entity.getDeliveryDate(),
                entity.getCustomerName(),
                entity.getCustomerPhone(),
                entity.getCustomerEmail(),
                entity.getOrderStatus(),
                entity.getTotalAmount(),
                entity.getHasInventoryAlert(),
                entity.getInventoryAlertDetails(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                createdById,
                modifiedById
        );
    }
}