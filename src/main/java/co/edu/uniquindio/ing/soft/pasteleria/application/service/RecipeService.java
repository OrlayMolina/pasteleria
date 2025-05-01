package co.edu.uniquindio.ing.soft.pasteleria.application.service;

import co.edu.uniquindio.ing.soft.pasteleria.application.dto.MensajeDTO;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.request.CreateRecipeCommand;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.request.UpdateRecipeCommand;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.response.PageResponse;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.response.RecipeResponse;
import co.edu.uniquindio.ing.soft.pasteleria.application.mapper.UserDtoMapper;
import co.edu.uniquindio.ing.soft.pasteleria.application.ports.input.ManageRecipeUseCase;
import co.edu.uniquindio.ing.soft.pasteleria.application.ports.output.RecipePort;
import co.edu.uniquindio.ing.soft.pasteleria.domain.exception.DomainException;
import co.edu.uniquindio.ing.soft.pasteleria.domain.model.Recipe;
import co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
@Transactional
@RequiredArgsConstructor
public class RecipeService implements ManageRecipeUseCase {
    private final RecipePort recipePort;
    private final RecipeRepository recipeJpaRepository;

    @Override
    public MensajeDTO<String> createRecipe(CreateRecipeCommand command) throws DomainException {
        try {
            Recipe recipe = new Recipe(
                    command.name(),
                    command.description(),
                    command.portions(),
                    command.preparationTimeMinutes(),
                    command.status(),
                    command.createdAt(),
                    command.updatedAt(),
                    command.createdBy(),
                    command.userModify()
            );

            recipePort.saveRecipe(recipe);
            return new MensajeDTO<>(false, "Receta creada con exito");
        } catch (Exception e) {
            return new MensajeDTO<>(true, "Error al crear la receta: " + e.getMessage());
        }
    }

    @Override
    public MensajeDTO<PageResponse<RecipeResponse>> getPagedRecipes(int page, int size, String sort, String direction, String search) {
        Page<RecipeResponse> recipesPage = recipePort.findRecipesWithPaginationAndSorting(page, size, sort, direction, search);

        List<RecipeResponse> items = recipesPage.getContent().stream().toList();

        PageResponse<RecipeResponse> pageResponse = new PageResponse<>(
                items,
                recipesPage.getNumber(),
                recipesPage.getSize(),
                recipesPage.getTotalElements(),
                recipesPage.getTotalPages(),
                recipesPage.isLast()
        );

        return new MensajeDTO<>(false, pageResponse);
    }

    @Override
    public MensajeDTO<Void> deleteRecipe(Long id) {
        try {
            recipePort.deleteRecipeById(id);
            return new MensajeDTO<>(false, null);
        } catch (Exception e) {
            return new MensajeDTO<>(true, null);
        }
    }

    @Override
    public MensajeDTO<String> updateRecipe(Long id, UpdateRecipeCommand command) {
        try {
            Optional<Recipe> optionalUser = recipePort.findRecipeById(id);
            if (optionalUser.isEmpty()) {
                return new MensajeDTO<>(true, "Receta no encontrado");
            }

            Recipe existingRecipe = optionalUser.get();
            existingRecipe.setId(command.id());
            existingRecipe.setName(command.name());
            existingRecipe.setPreparationTimeMinutes(command.preparationTimeMinutes());
            existingRecipe.setPortions(command.portions());
            existingRecipe.setDescription(command.description());
            existingRecipe.setStatus(command.status());
            existingRecipe.setUpdatedAt(command.updatedAt());
            existingRecipe.setUserModify(command.userModify());

            Recipe updatedUser = recipePort.updateRecipe(existingRecipe);

            return new MensajeDTO<>(false, "Usuario actualizado con exito");
        } catch (RuntimeException e) {
            throw e; // Importante: relanzar para que se haga rollback real
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar el usuario", e);
        }
    }


    @Override
    public MensajeDTO<RecipeResponse> getRecipe(Long id) throws DomainException {
        try {
            Optional<RecipeResponse> optionalRecipe = recipePort.findById(id);
            if (optionalRecipe.isEmpty()) {
                return new MensajeDTO<>(true, null);
            }
            return new MensajeDTO<>(false, optionalRecipe.get());
        } catch (Exception e) {
            return new MensajeDTO<>(true, null);
        }
    }

//    @Override
//    public MensajeDTO<List<UserResponse>> searchUser() {
//        try {
//            List<UserResponse> Recipes = userPort.findAllRecipes();
////            List<UserResponse> responses = Recipes.stream().map(user -> {
////                try {
////                    return userDtoMapper.toResponse(user);
////                } catch (DomainException e) {
////                    throw new RuntimeException("Error al mapear User a UserResponse", e);
////                }
////            }).toList();
//            return new MensajeDTO<>(false, Recipes);
//        } catch (Exception e) {
//            return new MensajeDTO<>(true, null);
//        }
//    }
//
//    @Override
//    public MensajeDTO<RecipesimplifyResponse> getUserBasicInfo(Long id) throws DomainException {
//        try {
//            Optional<User> optionalUser = userPort.findUserById(id);
//            if (optionalUser.isEmpty()) {
//                return new MensajeDTO<>(true, null);
//            }
//
//            User user = optionalUser.get();
//            RecipesimplifyResponse response = new RecipesimplifyResponse(
//                    user.getTypeDocument(),
//                    user.getDocumentNumber(),
//                    user.getFirstName(),
//                    user.getSecondName(),
//                    user.getLastName(),
//                    user.getSecondLastName()
//            );
//
//            return new MensajeDTO<>(false, response);
//        } catch (Exception e) {
//            return new MensajeDTO<>(true, null);
//        }
//    }
}