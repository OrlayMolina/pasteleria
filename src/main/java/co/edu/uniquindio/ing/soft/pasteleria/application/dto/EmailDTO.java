package co.edu.uniquindio.ing.soft.pasteleria.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record EmailDTO(
        @NotBlank String asunto, // No puede ser nulo ni vacío
        @NotBlank String cuerpo, // No puede ser nulo ni vacío
        @NotBlank @Email String destinatario // Debe ser un correo electrónico válido
) {
}
