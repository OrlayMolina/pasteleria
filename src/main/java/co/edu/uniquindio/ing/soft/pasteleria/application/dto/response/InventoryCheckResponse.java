package co.edu.uniquindio.ing.soft.pasteleria.application.dto.response;

import java.util.List;

public record InventoryCheckResponse(
        boolean hasInventoryAlert,
        String alertMessage,
        List<SupplyAlertDetail> alertDetails
) {
}