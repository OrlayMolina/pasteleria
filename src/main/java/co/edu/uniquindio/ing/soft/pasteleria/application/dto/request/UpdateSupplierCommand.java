package co.edu.uniquindio.ing.soft.pasteleria.application.dto.request;

import co.edu.uniquindio.ing.soft.pasteleria.domain.enums.Status;

import java.time.LocalDateTime;

public record UpdateSupplierCommand(
        String name,
        String supplierID,
        String address,
        String phone,
        String email,
        String contactPerson,
        Status status,
        LocalDateTime updatedAt,
        Long userModify,
        // Campos opcionales para actualizar reseña
        LocalDateTime lastOrderDate,
        Integer lastReviewRating,
        String lastReviewComment,
        Boolean onTimeDelivery,
        Boolean qualityIssues) {
}
