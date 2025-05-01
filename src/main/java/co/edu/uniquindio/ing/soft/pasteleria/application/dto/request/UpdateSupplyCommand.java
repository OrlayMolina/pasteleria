package co.edu.uniquindio.ing.soft.pasteleria.application.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record UpdateSupplyCommand(
        Long id,

        @NotBlank(message = "El nombre del insumo requerido")
        String name,

        @Positive(message = "El precio debe ser un valor positivo")
        Double price,

        @NotNull(message = "La fecha de entrada del insumo es requerido")
        LocalDate entryDate,

        @NotNull(message = "La fecha de salida del insumo es requerido")
        LocalDate expirationDate,

        @Positive(message = "La cantidad debe ser un valor positivo")
        int quantity,
        String unitMeasurement,

        @Min(value = 1, message = "El stock mínimo debe ser mayor a 0")
        int minimumStock,

        @NotNull(message = "Fecha de actualización de un insumo es requerida")
        LocalDateTime updatedAt,

        Long userModify
) {
}