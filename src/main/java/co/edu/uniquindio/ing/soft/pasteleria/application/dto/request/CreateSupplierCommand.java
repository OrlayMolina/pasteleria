package co.edu.uniquindio.ing.soft.pasteleria.application.dto.request;

import co.edu.uniquindio.ing.soft.pasteleria.domain.enums.Status;
import co.edu.uniquindio.ing.soft.pasteleria.domain.enums.TypeDocument;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record CreateSupplierCommand(
        @NotBlank(message = "El nombre es requerido")
        String name,
        TypeDocument typeDocument,

        @NotBlank(message = "El ID del proveedor es requerido")
        String supplierID,

        @NotBlank(message = "La dirección es requerida")
        String address,

        @NotBlank(message = "El teléfono es requerido")
        String phone,

        @NotBlank(message = "El Correo electrónico es requerido")
        @Email(message = "El formato del correo es incorrecto")
        String email,

        @NotBlank(message = "La persona de contacto es requerida")
        String contactPerson,

        LocalDateTime lastOrderDate,
        Integer lastReviewRating,
        String lastReviewComment,
        Boolean onTimeDelivery,
        Boolean qualityIssues,

        Status status,

        @NotNull(message = "Fecha de creación del proveedor es requerida")
        LocalDateTime createdAt,

        @NotNull(message = "Fecha de actualización del proveedor es requerida")
        LocalDateTime updatedAt,
        Long userModify) {
}