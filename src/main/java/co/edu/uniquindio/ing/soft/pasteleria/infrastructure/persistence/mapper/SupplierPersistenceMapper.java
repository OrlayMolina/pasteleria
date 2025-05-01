package co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.mapper;

import co.edu.uniquindio.ing.soft.pasteleria.domain.exception.DomainException;
import co.edu.uniquindio.ing.soft.pasteleria.domain.model.Supplier;
import co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.entity.SupplierEntity;
import co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.entity.UserEntity;
import co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SupplierPersistenceMapper {

    private final UserJpaRepository userJpaRepository;

    public SupplierEntity toEntity(Supplier supplier) throws DomainException {
        SupplierEntity entity = new SupplierEntity();
        entity.setId(supplier.getId());
        entity.setName(supplier.getName());
        entity.setSupplierID(supplier.getSupplierDocument());
        entity.setAddress(supplier.getAddress());
        entity.setPhone(supplier.getPhone());
        entity.setEmail(supplier.getEmail());
        entity.setContactPerson(supplier.getContactPerson());
        entity.setStatus(supplier.getStatus());
        entity.setCreatedAt(supplier.getCreatedAt());
        entity.setUpdatedAt(supplier.getUpdatedAt());

        // Si hay usuario modificador, establecerlo
        if (supplier.getUserModify() != null) {
            Optional<UserEntity> userEntity = userJpaRepository.findById(supplier.getUserModify());
            userEntity.ifPresent(entity::setUserModify);
        }

        // Establecer el tipo de documento si existe
        if (supplier.getTypeDocument() != null) {
            entity.setTypeDocument(supplier.getTypeDocument());
        }

        return entity;
    }

    public Supplier toDomain(SupplierEntity entity) throws DomainException {
        Supplier supplier = new Supplier(
                entity.getId(),
                entity.getName(),
                entity.getSupplierID(),
                entity.getAddress(),
                entity.getPhone(),
                entity.getEmail(),
                entity.getContactPerson(),
                entity.getStatus(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );

        // Establecer tipo de documento
        supplier.setTypeDocument(entity.getTypeDocument());

        // Establecer usuario modificador si existe
        if (entity.getUserModify() != null) {
            supplier.setUserModify(entity.getUserModify().getId());
        }

        // Establecer campos relacionados con la revisión más reciente
        if (!entity.getReviews().isEmpty()) {
            // Obtener la revisión más reciente
            entity.getReviews().stream()
                    .max((r1, r2) -> r1.getOrderDate().compareTo(r2.getOrderDate()))
                    .ifPresent(latestReview -> {
                        supplier.setLastOrderDate(latestReview.getOrderDate());
                        supplier.setLastReviewRating(latestReview.getRating());
                        supplier.setLastReviewComment(latestReview.getComment());
                        supplier.setOnTimeDelivery(latestReview.getOnTimeDelivery());
                        supplier.setQualityIssues(latestReview.getQualityIssues());
                    });
        }

        return supplier;
    }
}