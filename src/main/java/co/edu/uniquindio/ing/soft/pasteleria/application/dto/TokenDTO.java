package co.edu.uniquindio.ing.soft.pasteleria.application.dto;

import jakarta.validation.constraints.NotNull;

public record TokenDTO(
        @NotNull String token) {
}