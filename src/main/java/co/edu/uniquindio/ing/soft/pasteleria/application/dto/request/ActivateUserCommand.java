package co.edu.uniquindio.ing.soft.pasteleria.application.dto.request;

public record ActivateUserCommand(
        String email,
        String codigo) {
}
