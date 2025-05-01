package co.edu.uniquindio.ing.soft.pasteleria.domain.model;

import co.edu.uniquindio.ing.soft.pasteleria.domain.enums.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class Recipe {

    private Long id;

    private String name;
    private String description;

    private Integer portions;
    private Integer preparationTimeMinutes;

    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Long createdBy;
    private Long userModify;

    public Recipe(String name, String description, Integer portions, Integer preparationTimeMinutes,
                  Status status, LocalDateTime createdAt, LocalDateTime updatedAt,
                  Long createdBy, Long userModify) {
        this.name = name;
        this.description = description;
        this.portions = portions;
        this.preparationTimeMinutes = preparationTimeMinutes;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdBy = createdBy;
        this.userModify = userModify;
    }
}
