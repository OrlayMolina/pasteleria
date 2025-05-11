package co.edu.uniquindio.ing.soft.pasteleria.application.dto.response;

import co.edu.uniquindio.ing.soft.pasteleria.domain.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record CakeOrderResponse(
        Long id,
        LocalDateTime orderDate,
        LocalDateTime deliveryDate,
        String customerName,
        String customerPhone,
        String customerEmail,
        OrderStatus orderStatus,
        BigDecimal totalAmount,
        Boolean hasInventoryAlert,
        String inventoryAlertDetails,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Long createdById,
        Long modifiedById,
        List<CakeOrderDetailResponse> orderDetails
) {
}