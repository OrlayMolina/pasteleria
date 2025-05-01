package co.edu.uniquindio.ing.soft.pasteleria.application.dto.request;

import co.edu.uniquindio.ing.soft.pasteleria.domain.enums.Status;
import co.edu.uniquindio.ing.soft.pasteleria.domain.enums.TypeDocument;
import jakarta.annotation.Nullable;

import java.time.LocalDateTime;

public record UpdateUserCommand(
        Long id,
        TypeDocument typeDocument,
        String documentNumber,
        String phone,
        @Nullable String position,
        @Nullable Float salary,
        String firstName,
        String secondName,
        String lastName,
        String secondLastName,
        String email,
        @Nullable String password,
        @Nullable Status status,
        @Nullable Boolean isAdmin,
        @Nullable LocalDateTime createdAt,
        LocalDateTime updatedAt) {
}
