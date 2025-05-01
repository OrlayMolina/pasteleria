package co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.adapter;

import co.edu.uniquindio.ing.soft.pasteleria.application.dto.response.RecipeResponse;
import co.edu.uniquindio.ing.soft.pasteleria.application.ports.output.RecipePort;
import co.edu.uniquindio.ing.soft.pasteleria.domain.exception.DomainException;
import co.edu.uniquindio.ing.soft.pasteleria.domain.model.Recipe;
import co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.entity.RecipeEntity;
import co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.entity.SupplierEntity;
import co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.entity.UserEntity;
import co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.mapper.RecipePersistenceMapper;
import co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.repository.RecipeRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;

@Component
@AllArgsConstructor
public class RecipePersistenceAdapter implements RecipePort {

    private final RecipeRepository recipeJpaRepository;
    private final RecipePersistenceMapper persistenceMapper;

    @Override
    public Recipe saveRecipe(Recipe recipe) throws DomainException {
        try {
            RecipeEntity recipeEntity = persistenceMapper.toEntity(recipe);
            System.out.println("RecipeEntity a guardar: " + recipeEntity);

            RecipeEntity savedEntity = recipeJpaRepository.save(recipeEntity);
            return persistenceMapper.toDomain(savedEntity);
        } catch (Exception e) {
            e.printStackTrace(); // importante para ver el error real
            throw new DomainException("Error al guardar receta: " + e.getMessage());
        }
    }

    @Override
    public Page<RecipeResponse> findRecipesWithPaginationAndSorting(int page, int size, String sortField, String sortDirection, String searchTerm) {
        Sort sort = Sort.unsorted();

        if (sortField != null && !sortField.isEmpty()) {
            Sort.Direction direction = sortDirection.equalsIgnoreCase("asc") ?
                    Sort.Direction.ASC : Sort.Direction.DESC;

            String fieldName = mapSortField(sortField);
            sort = Sort.by(direction, fieldName);
        }

        Pageable pageable = PageRequest.of(page, size, sort);

        if (searchTerm != null && !searchTerm.isEmpty()) {
            Specification<RecipeEntity> spec = createSearchSpecification(searchTerm);
            return recipeJpaRepository.findAll(spec, pageable)
                    .map(entity -> {
                        try {
                            return persistenceMapper.toDomainResponse(entity);
                        } catch (DomainException e) {
                            throw new RuntimeException("Error mapping user entity to domain", e);
                        }
                    });
        }

        return recipeJpaRepository.findAll(pageable)
                .map(entity -> {
                    try {
                        return persistenceMapper.toDomainResponse(entity);
                    } catch (DomainException e) {
                        throw new RuntimeException("Error mapping user entity to domain", e);
                    }
                });
    }

    // Metodo auxiliar para mapear campos de ordenamiento desde la UI a la entidad
    private String mapSortField(String uiField) {
        return switch (uiField.toLowerCase()) {
            case "id" -> "id";
            case "name", "nombre" -> "name";
            default -> "id";
        };
    }

    // Método para crear la especificación de búsqueda
    private Specification<RecipeEntity> createSearchSpecification(String searchTerm) {
        String term = "%" + searchTerm.toLowerCase() + "%";

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), term),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), term),
                        criteriaBuilder.like(root.get("id").as(String.class), term)
                );
    }

    @Override
    public void deleteRecipeById(Long id) {
        recipeJpaRepository.deleteById(id);
    }

    public Recipe updateRecipe(Recipe recipe) throws DomainException {
        RecipeEntity existingEntity = recipeJpaRepository.findById(recipe.getId())
                .orElseThrow(() -> new DomainException("Receta no encontrado"));
        try {
            existingEntity.setName(recipe.getName());
            existingEntity.setPreparationTimeMinutes(recipe.getPreparationTimeMinutes());
            existingEntity.setPortions(recipe.getPortions());
            existingEntity.setDescription(recipe.getDescription());
            existingEntity.setStatus(recipe.getStatus());
            existingEntity.setUpdatedAt(recipe.getUpdatedAt());


            if (recipe.getUserModify() != null) {
                UserEntity userEntity = new UserEntity();
                userEntity.setId(recipe.getUserModify());
                existingEntity.setModifiedBy(userEntity);
            }

            RecipeEntity updatedEntity = recipeJpaRepository.save(existingEntity);
            return persistenceMapper.toDomain(updatedEntity);
        } catch (Exception e) {
            e.printStackTrace(); // importante para ver el error real
            throw new DomainException("Error al guardar la receta: " + e.getMessage());
        }
    }

    @Override
    public Optional<Recipe> findRecipeById(Long id) {
        Optional<RecipeEntity> optionalEntity = recipeJpaRepository.findById(id);
        if (optionalEntity.isEmpty()) {
            return Optional.empty();
        }

        try {
            return Optional.of(persistenceMapper.toDomain(optionalEntity.get()));
        } catch (DomainException e) {
            throw new RuntimeException("Error al convertir UserEntity a dominio User", e);
        }
    }

    @Override
    public Optional<RecipeResponse> findById(Long id) {
        Optional<RecipeEntity> optionalEntity = recipeJpaRepository.findById(id);
        if (optionalEntity.isEmpty()) {
            return Optional.empty();
        }
        try {
            return Optional.of(persistenceMapper.toDomainResponse(optionalEntity.get()));
        } catch (DomainException e) {
            throw new RuntimeException("Error al convertir UserEntity a dominio User", e);
        }
    }

//    @Override
//    public List<UserResponse> findAllRecipes() {
//        List<UserEntity> entities = userJpaRepository.findAll();
//        return entities.stream()
//                .map(entity -> {
//                    try {
//                        return persistenceMapper.toDomainResponse(entity);
//                    } catch (DomainException e) {
//                        throw new RuntimeException("Error al convertir lista de UserEntity a dominio User", e);
//                    }
//                })
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public boolean existsUserById(Long id) {
//        return userJpaRepository.existsById(id);
//    }
//
//    @Override
//    public Page<User> findRecipesWithPagination(int page, int size) {
//        Pageable pageable = PageRequest.of(page, size);
//        Page<UserEntity> RecipesPage = userJpaRepository.findAll(pageable);
//        return RecipesPage.map(entity -> {
//            try {
//                return persistenceMapper.toDomain(entity);
//            } catch (DomainException e) {
//                throw new RuntimeException("Error mapping user entity to domain", e);
//            }
//        });
//    }
//
}
