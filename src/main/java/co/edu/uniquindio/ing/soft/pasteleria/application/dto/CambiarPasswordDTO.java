package co.edu.uniquindio.ing.soft.pasteleria.application.dto;

public record CambiarPasswordDTO(
        String email,
        String codigoVerificacion,
        String passwordNueva) {
}
