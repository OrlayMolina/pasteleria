package co.edu.uniquindio.ing.soft.pasteleria.application.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateCakeOrderDetailCommand(
        @NotNull(message = "El ID de la receta no puede ser nulo")
        Long recipeId,

        @NotNull(message = "La cantidad no puede ser nula")
        @Min(value = 1, message = "La cantidad debe ser al menos 1")
        Integer quantity,

        @NotNull(message = "El precio unitario no puede ser nulo")
        @Min(value = 0, message = "El precio unitario no puede ser negativo")
        BigDecimal unitPrice,

        String specialInstructions
) {
}