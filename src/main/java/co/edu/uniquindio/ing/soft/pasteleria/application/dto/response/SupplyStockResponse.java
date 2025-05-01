package co.edu.uniquindio.ing.soft.pasteleria.application.dto.response;

import java.time.LocalDate;

public record SupplyStockResponse(
        String product,
        String supplier,
        int minimumStock,
        int currentStock,
        LocalDate expirationDate,
        String stockStatus,
        boolean aboutToExpire) {
}
