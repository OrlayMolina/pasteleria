package co.edu.uniquindio.ing.soft.pasteleria.infrastructure.controller;

import co.edu.uniquindio.ing.soft.pasteleria.application.dto.MensajeDTO;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.request.CreateRecipeCommand;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.request.UpdateRecipeCommand;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.response.PageResponse;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.response.RecipeResponse;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.response.UserResponse;
import co.edu.uniquindio.ing.soft.pasteleria.application.ports.input.ManageRecipeUseCase;
import co.edu.uniquindio.ing.soft.pasteleria.application.ports.input.ManageUserUseCase;
import co.edu.uniquindio.ing.soft.pasteleria.domain.exception.DomainException;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recipes")
@AllArgsConstructor
public class RecipeController {

    private final ManageRecipeUseCase manageRecipeUseCase;

    @PostMapping
    public ResponseEntity<MensajeDTO<String>> createRecipe(@Valid @RequestBody CreateRecipeCommand command) {
        try {
            MensajeDTO<String> response = manageRecipeUseCase.createRecipe(command);
            return ResponseEntity.ok(response);
        } catch (DomainException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MensajeDTO<>(true, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MensajeDTO<>(true, e.getMessage()));
        }
    }

    @GetMapping("/paged")
    public ResponseEntity<MensajeDTO<PageResponse<RecipeResponse>>> getPagedRecipes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sort,
            @RequestParam(defaultValue = "asc") String direction,
            @RequestParam(required = false) String search) {
        return ResponseEntity.ok(manageRecipeUseCase.getPagedRecipes(page, size, sort, direction, search));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MensajeDTO<Void>> deleteRecipe(@PathVariable Long id) {
        try {
            MensajeDTO<Void> response = manageRecipeUseCase.deleteRecipe(id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MensajeDTO<>(true, null));
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<MensajeDTO<String>> updateRecipe(@PathVariable Long id, @Valid @RequestBody UpdateRecipeCommand command) {
        try {
            MensajeDTO<String> response = manageRecipeUseCase.updateRecipe(id, command);
            if (response.error() || response.respuesta() == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            return ResponseEntity.ok(response);
        } catch (ConstraintViolationException e) {
            return ResponseEntity.badRequest().body(new MensajeDTO<>(true, "Datos inválidos: " + e.getMessage()));
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new MensajeDTO<>(true, "Conflicto de integridad de datos: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MensajeDTO<>(true, "Error al actualizar la receta: " + e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<MensajeDTO<RecipeResponse>> getRecipe(@PathVariable Long id) {
        try {
            MensajeDTO<RecipeResponse> response = manageRecipeUseCase.getRecipe(id);
            if (response.error() || response.respuesta() == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            return ResponseEntity.ok(response);
        } catch (DomainException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MensajeDTO<>(true, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MensajeDTO<>(true, null));
        }
    }
}