package co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.mapper;

import co.edu.uniquindio.ing.soft.pasteleria.domain.model.Review;
import co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.entity.ReviewEntity;
import co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.entity.SupplierEntity;
import co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.entity.UserEntity;
import co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ReviewPersistenceMapper {

    private final UserJpaRepository userJpaRepository;

    /**
     * Convierte un objeto Review del dominio a una entidad ReviewEntity con el proveedor proporcionado
     *
     * @param review         El objeto Review del dominio
     * @param supplierEntity La entidad SupplierEntity ya obtenida
     * @return Una entidad ReviewEntity
     */
    public ReviewEntity toEntity(Review review, SupplierEntity supplierEntity) {
        ReviewEntity entity = new ReviewEntity();
        entity.setId(review.getId());
        entity.setSupplier(supplierEntity);

        entity.setOrderDate(review.getOrderDate());
        entity.setRating(review.getRating());
        entity.setComment(review.getComment());
        entity.setOnTimeDelivery(review.getOnTimeDelivery());
        entity.setQualityIssues(review.getQualityIssues());
        entity.setCreatedAt(review.getCreatedAt());
        entity.setUpdatedAt(review.getUpdatedAt());

        // Establecer el usuario modificador si existe
        if (review.getUserModify() != null) {
            Optional<UserEntity> userEntity = userJpaRepository.findById(review.getUserModify());
            userEntity.ifPresent(entity::setUserModify);
        }

        return entity;
    }

    /**
     * Convierte una entidad ReviewEntity a un objeto Review del dominio
     *
     * @param entity La entidad ReviewEntity
     * @return Un objeto Review del dominio
     */
    public Review toDomain(ReviewEntity entity) {
        Review review = new Review(
                entity.getId(),
                entity.getSupplier().getId(),
                entity.getOrderDate(),
                entity.getRating(),
                entity.getComment(),
                entity.getOnTimeDelivery(),
                entity.getQualityIssues(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );

        // Establecer usuario modificador si existe
        if (entity.getUserModify() != null) {
            review.setUserModify(entity.getUserModify().getId());
        }

        return review;
    }
}