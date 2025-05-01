package co.edu.uniquindio.ing.soft.pasteleria.application.ports.input;

import co.edu.uniquindio.ing.soft.pasteleria.application.dto.MensajeDTO;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.request.CreateRecipeCommand;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.request.UpdateRecipeCommand;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.response.PageResponse;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.response.RecipeResponse;
import co.edu.uniquindio.ing.soft.pasteleria.domain.exception.DomainException;

public interface ManageRecipeUseCase {
    MensajeDTO<String> createRecipe(CreateRecipeCommand command) throws DomainException;

    MensajeDTO<PageResponse<RecipeResponse>> getPagedRecipes(int page, int size, String sort, String direction, String search);

    MensajeDTO<Void> deleteRecipe(Long id);

    MensajeDTO<String> updateRecipe(Long id, UpdateRecipeCommand command);

    MensajeDTO<RecipeResponse> getRecipe(Long id) throws DomainException;

//    MensajeDTO<List<RecipeResponse>> searchRecipe();
//
//    MensajeDTO<RecipeSimplifyResponse> getRecipeBasicInfo(Long id) throws DomainException;

}