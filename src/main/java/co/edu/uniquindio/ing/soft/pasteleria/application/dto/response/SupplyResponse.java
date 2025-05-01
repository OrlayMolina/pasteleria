package co.edu.uniquindio.ing.soft.pasteleria.application.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record SupplyResponse(
        Long id,
        String name,
        Double price,
        LocalDate entryDate,
        LocalDate expirationDate,
        int quantity,
        String unitMeasurement,
        int minimumStock,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Long userModify) {
}
