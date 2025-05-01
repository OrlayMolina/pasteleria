package co.edu.uniquindio.ing.soft.pasteleria.application.dto.request;

import co.edu.uniquindio.ing.soft.pasteleria.domain.enums.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDateTime;

public record CreateRecipeCommand(

        @NotBlank(message = "El nombre de la receta es requerido")
        String name,

        String description,

        @PositiveOrZero(message = "El tiempo de preparación debe ser mayor o igual a cero")
        Integer preparationTimeMinutes,

        @Positive(message = "La cantidad de porciones debe ser mayor que cero")
        Integer portions,

        Status status,

        @NotNull(message = "La fecha de creación es requerida")
        LocalDateTime createdAt,

        @NotNull(message = "La fecha de actualización es requerida")
        LocalDateTime updatedAt,

        Long createdBy,

        Long userModify
) {
}
