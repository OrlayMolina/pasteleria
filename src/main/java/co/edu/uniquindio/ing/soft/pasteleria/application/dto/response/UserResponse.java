package co.edu.uniquindio.ing.soft.pasteleria.application.dto.response;

import co.edu.uniquindio.ing.soft.pasteleria.domain.enums.Status;
import co.edu.uniquindio.ing.soft.pasteleria.domain.enums.TypeDocument;

import java.time.LocalDateTime;

public record UserResponse(
        Long id,
        TypeDocument typeDocument,
        String documentNumber,
        String phone,
        String position,
        Float salary,
        String first_name,
        String second_name,
        String last_name,
        String second_last_name,
        String email,
        String password,
        Status status,
        Boolean isAdmin,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {
}
