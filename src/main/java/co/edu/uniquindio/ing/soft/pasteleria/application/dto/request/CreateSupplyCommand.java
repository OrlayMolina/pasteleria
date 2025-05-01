package co.edu.uniquindio.ing.soft.pasteleria.application.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record CreateSupplyCommand(
        @NotBlank(message = "El nombre no puede estar vacío")
        String name,

        @NotNull(message = "El precio no puede ser nulo")
        @Min(value = 1, message = "El precio debe ser mayor a 0")
        Double price,

        @NotBlank(message = "El ID del proveedor no puede estar vacío")
        String supplierID,

        @NotNull(message = "La fecha de entrada no puede ser nula")
        @PastOrPresent(message = "La fecha de entrada no puede ser futura")
        LocalDate entryDate,

        LocalDate expirationDate,

        @NotNull(message = "La cantidad no puede ser nula")
        @Min(value = 0, message = "La cantidad no puede ser negativa")
        int quantity,
        String unitMeasurement,

        @Min(value = 1, message = "El stock mínimo debe ser mayor a 0")
        int minimumStock,

        LocalDateTime createdAt,

        LocalDateTime updatedAt,

        Long userModify
) {
}