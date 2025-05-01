package co.edu.uniquindio.ing.soft.pasteleria.application.dto.response;

import co.edu.uniquindio.ing.soft.pasteleria.domain.enums.Status;

import java.time.LocalDateTime;

public record SupplierResponse(
        Long id,
        String name,
        String supplierID,
        String address,
        String phone,
        String email,
        String contactPerson,
        Status status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        // Datos de la última reseña (si existe)
        LocalDateTime lastOrderDate,
        Integer lastReviewRating,
        String lastReviewComment,
        Boolean onTimeDelivery,
        Boolean qualityIssues,
        Long userModify) {
}
