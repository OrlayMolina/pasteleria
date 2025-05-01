package co.edu.uniquindio.ing.soft.pasteleria.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ValidationCode {
    private String code;
    private LocalDateTime createdAt;

    public ValidationCode(String code, LocalDateTime createdAt) {
        this.code = code;
        this.createdAt = createdAt;
    }
}
