package co.edu.uniquindio.ing.soft.pasteleria.application.dto.request;

import co.edu.uniquindio.ing.soft.pasteleria.domain.enums.OrderStatus;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record UpdateCakeOrderStatusCommand(
        @NotNull(message = "El estado de la orden no puede ser nulo")
        OrderStatus orderStatus,

        @NotNull(message = "La fecha de actualización no puede ser nula")
        LocalDateTime updatedAt,

        @NotNull(message = "El ID del usuario que modifica no puede ser nulo")
        Long modifiedById,

        // Opcional: Un comentario o nota sobre el cambio de estado
        String statusChangeComment,

        // Opcional: Para estados como "RECHAZADA" o "CANCELADA" puede ser útil registrar el motivo
        String rejectionReason,

        // Opcional: Si el estado cambia a "ENTREGADA", podría ser útil registrar quién recibió la orden
        String receivedBy,

        // Opcional: Si deseas permitir actualizar la fecha de entrega al mismo tiempo que el estado
        LocalDateTime newDeliveryDate
) {
    // Constructor secundario para casos básicos donde solo se necesita actualizar el estado
    public UpdateCakeOrderStatusCommand(OrderStatus orderStatus, LocalDateTime updatedAt, Long modifiedById) {
        this(orderStatus, updatedAt, modifiedById, null, null, null, null);
    }
}