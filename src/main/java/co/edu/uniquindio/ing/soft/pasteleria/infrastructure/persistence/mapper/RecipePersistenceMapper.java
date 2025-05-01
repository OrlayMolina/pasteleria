package co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.mapper;

import co.edu.uniquindio.ing.soft.pasteleria.application.dto.response.RecipeResponse;
import co.edu.uniquindio.ing.soft.pasteleria.domain.exception.DomainException;
import co.edu.uniquindio.ing.soft.pasteleria.domain.model.Recipe;
import co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.entity.RecipeEntity;
import co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.entity.UserEntity;
import co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RecipePersistenceMapper {

    private final UserJpaRepository userJpaRepository;

    public RecipeEntity toEntity(Recipe recipe) throws DomainException {
        if (recipe == null) {
            return null;
        }

        RecipeEntity entity = new RecipeEntity();
        entity.setId(recipe.getId());
        entity.setName(recipe.getName());
        entity.setDescription(recipe.getDescription());
        entity.setPortions(recipe.getPortions());
        entity.setPreparationTimeMinutes(recipe.getPreparationTimeMinutes());
        entity.setStatus(recipe.getStatus());
        entity.setCreatedAt(recipe.getCreatedAt());
        entity.setUpdatedAt(recipe.getUpdatedAt());

        // Si hay usuario modificador, establecerlo
        if (recipe.getUserModify() != null) {
            Optional<UserEntity> userEntity = userJpaRepository.findById(recipe.getUserModify());
            userEntity.ifPresent(entity::setModifiedBy);
        }
        if (recipe.getCreatedBy() != null) {
            Optional<UserEntity> userEntity = userJpaRepository.findById(recipe.getCreatedBy());
            userEntity.ifPresent(entity::setCreatedBy);
        }

        return entity;
    }

    public Recipe toDomain(RecipeEntity entity) throws DomainException {
        if (entity == null) {
            return null;
        }

        Recipe recipe = new Recipe(
                entity.getName(),
                entity.getDescription(),
                entity.getPortions(),
                entity.getPreparationTimeMinutes(),
                entity.getStatus(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                null,
                null);

        // Establecer usuario modificador si existe
        if (entity.getModifiedBy() != null) {
            recipe.setUserModify(entity.getModifiedBy().getId());
        }

        // Establecer usuario modificador si existe
        if (entity.getCreatedBy() != null) {
            recipe.setCreatedBy(entity.getCreatedBy().getId());
        }

        return recipe;


    }

    public RecipeResponse toDomainResponse(RecipeEntity entity) throws DomainException {
        if (entity == null) {
            return null;
        }
        RecipeResponse recipeResponse = new RecipeResponse(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getPortions(),
                entity.getPreparationTimeMinutes(),
                entity.getStatus(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getModifiedBy() != null ? entity.getModifiedBy().getId() : null,
                entity.getCreatedBy() != null ? entity.getCreatedBy().getId() : null
        );

        return recipeResponse;
    }
}
