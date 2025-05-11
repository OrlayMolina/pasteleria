package co.edu.uniquindio.ing.soft.pasteleria.application.dto.response;

public record SupplyAlertDetail(
        String supplyName,
        int requiredQuantity,
        int availableQuantity,
        int minimumStock,
        boolean belowMinimumStock,
        boolean outOfStock
) {
}