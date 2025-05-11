package co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.mapper;

import co.edu.uniquindio.ing.soft.pasteleria.domain.exception.DomainException;
import co.edu.uniquindio.ing.soft.pasteleria.domain.model.RecipeSupply;
import co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.entity.RecipeEntity;
import co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.entity.RecipeSupplyEntity;
import co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.entity.SupplyEntity;
import co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.repository.RecipeRepository;
import co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.repository.SupplyJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RecipeSupplyPersistenceMapper {

    private final RecipeRepository recipeJpaRepository;
    private final SupplyJpaRepository supplyJpaRepository;

    public RecipeSupply toDomain(RecipeSupplyEntity entity) throws DomainException {
        if (entity == null) {
            return null;
        }

        Long recipeId = null;
        if (entity.getRecipe() != null) {
            recipeId = entity.getRecipe().getId();
        }

        Long supplyId = null;
        if (entity.getSupply() != null) {
            supplyId = entity.getSupply().getId();
        }

        return new RecipeSupply(
                entity.getId(),
                recipeId,
                supplyId,
                entity.getQuantity(),
                entity.getUnitOfMeasure(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public RecipeSupplyEntity toEntity(RecipeSupply domain) throws DomainException {
        if (domain == null) {
            return null;
        }

        RecipeSupplyEntity entity = new RecipeSupplyEntity();
        entity.setId(domain.getId());
        entity.setQuantity(domain.getQuantity());
        entity.setUnitOfMeasure(domain.getUnitOfMeasure());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());

        // Establecer la receta
        if (domain.getRecipeId() != null) {
            Optional<RecipeEntity> recipeOptional = recipeJpaRepository.findById(domain.getRecipeId());
            if (recipeOptional.isEmpty()) {
                throw new DomainException("La receta con ID " + domain.getRecipeId() + " no existe");
            }
            entity.setRecipe(recipeOptional.get());
        }

        // Establecer el insumo
        if (domain.getSupplyId() != null) {
            Optional<SupplyEntity> supplyOptional = supplyJpaRepository.findById(domain.getSupplyId());
            if (supplyOptional.isEmpty()) {
                throw new DomainException("El insumo con ID " + domain.getSupplyId() + " no existe");
            }
            entity.setSupply(supplyOptional.get());
        }

        return entity;
    }
}