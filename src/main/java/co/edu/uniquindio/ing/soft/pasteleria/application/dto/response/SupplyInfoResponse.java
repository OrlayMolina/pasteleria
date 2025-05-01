package co.edu.uniquindio.ing.soft.pasteleria.application.dto.response;

import java.time.LocalDateTime;

public record SupplyInfoResponse(
        String name,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Long userModify) {
}
