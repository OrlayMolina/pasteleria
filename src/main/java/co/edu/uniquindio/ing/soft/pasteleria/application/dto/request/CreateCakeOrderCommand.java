package co.edu.uniquindio.ing.soft.pasteleria.application.dto.request;

import co.edu.uniquindio.ing.soft.pasteleria.domain.enums.OrderStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.List;

public record CreateCakeOrderCommand(
        @NotBlank(message = "El nombre del cliente no puede estar vacío")
        String customerName,

        @NotBlank(message = "El teléfono del cliente no puede estar vacío")
        String customerPhone,

        @Email(message = "El correo electrónico debe ser válido")
        String customerEmail,

        @Future(message = "La fecha de entrega debe ser futura")
        LocalDateTime deliveryDate,

        @NotNull(message = "El estado de la orden no puede ser nulo")
        OrderStatus orderStatus,

        @NotNull(message = "El ID del creador no puede ser nulo")
        Long createdById,

        @NotEmpty(message = "La orden debe tener al menos un detalle")
        @Valid
        List<CreateCakeOrderDetailCommand> orderDetails
) {
}