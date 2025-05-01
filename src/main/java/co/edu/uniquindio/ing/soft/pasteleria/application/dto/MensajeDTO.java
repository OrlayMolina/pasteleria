package co.edu.uniquindio.ing.soft.pasteleria.application.dto;

public record MensajeDTO<T>(
        boolean error,
        T respuesta) {
}