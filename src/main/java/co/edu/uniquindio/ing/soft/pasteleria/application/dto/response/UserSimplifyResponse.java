package co.edu.uniquindio.ing.soft.pasteleria.application.dto.response;

import co.edu.uniquindio.ing.soft.pasteleria.domain.enums.TypeDocument;

public record UserSimplifyResponse(
        TypeDocument typeDocument,
        String documentNumber,
        String first_name,
        String second_name,
        String last_name,
        String second_last_name) {
}
