package co.edu.uniquindio.ing.soft.pasteleria.application.dto.response;

import co.edu.uniquindio.ing.soft.pasteleria.domain.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CakeOrderSummaryResponse(
        Long id,
        String customerName,
        LocalDateTime orderDate,
        LocalDateTime deliveryDate,
        OrderStatus orderStatus,
        BigDecimal totalAmount,
        Boolean hasInventoryAlert,
        int totalItems
) {
}