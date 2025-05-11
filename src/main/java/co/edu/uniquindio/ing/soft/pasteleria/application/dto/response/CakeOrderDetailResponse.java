package co.edu.uniquindio.ing.soft.pasteleria.application.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CakeOrderDetailResponse(
        Long id,
        Long recipeId,
        String recipeName,
        Integer quantity,
        BigDecimal unitPrice,
        BigDecimal subtotal,
        String specialInstructions,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}