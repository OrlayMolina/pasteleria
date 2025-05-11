package co.edu.uniquindio.ing.soft.pasteleria.application.ports.output;

import co.edu.uniquindio.ing.soft.pasteleria.application.dto.response.RecipeResponse;
import co.edu.uniquindio.ing.soft.pasteleria.domain.exception.DomainException;
import co.edu.uniquindio.ing.soft.pasteleria.domain.model.Recipe;
import co.edu.uniquindio.ing.soft.pasteleria.domain.model.RecipeSupply;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface RecipePort {
    Recipe saveRecipe(Recipe Recipe) throws DomainException;

    Page<RecipeResponse> findRecipesWithPaginationAndSorting(int page, int size, String sortField, String sortDirection, String searchTerm);

    void deleteRecipeById(Long id);

    Recipe updateRecipe(Recipe Recipe) throws DomainException;

    Optional<RecipeResponse> findById(Long id);

    Optional<Recipe> findRecipeById(Long id);

    List<RecipeSupply> findSuppliesByRecipeId(Long recipeId);

    String getRecipeNameById(Long recipeId) throws DomainException;

//    List<RecipeResponse> findAllRecipes();
//
//    boolean existsRecipeById(Long id);
//
//    Page<Recipe> findRecipesWithPagination(int page, int size);
//
}
