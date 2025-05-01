package co.edu.uniquindio.ing.soft.pasteleria.application.dto.response;

import co.edu.uniquindio.ing.soft.pasteleria.domain.enums.Status;

import java.time.LocalDateTime;

public record RecipeResponse(
        Long id,
        String name,
        String description,
        Integer preparationTimeMinutes,
        Integer portions,
        Status status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Long createdBy,
        Long userModify) {
}
