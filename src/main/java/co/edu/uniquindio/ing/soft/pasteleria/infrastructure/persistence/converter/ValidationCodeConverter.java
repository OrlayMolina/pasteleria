package co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import co.edu.uniquindio.ing.soft.pasteleria.domain.model.ValidationCode;

import java.time.LocalDateTime;

@Converter
public class ValidationCodeConverter implements AttributeConverter<ValidationCode, String> {

    @Override
    public String convertToDatabaseColumn(ValidationCode validationCode) {
        if (validationCode == null) {
            return null;
        }

        return validationCode.getCode() + ":" + validationCode.getCreatedAt();
    }

    @Override
    public ValidationCode convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return null;
        }
        String[] parts = dbData.split(":");
        if (parts.length != 2) {
            return null;
        }
        String code = parts[0];
        return new ValidationCode(code, LocalDateTime.parse(parts[1]));
    }
}