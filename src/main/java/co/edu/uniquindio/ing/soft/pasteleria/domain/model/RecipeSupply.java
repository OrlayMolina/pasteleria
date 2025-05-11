package co.edu.uniquindio.ing.soft.pasteleria.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class RecipeSupply {
    private Long id;
    private Long recipeId;
    private Long supplyId;
    private Double quantity;
    private String unitOfMeasure;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public RecipeSupply(
            Long recipeId,
            Long supplyId,
            Double quantity,
            String unitOfMeasure,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.recipeId = recipeId;
        this.supplyId = supplyId;
        this.quantity = quantity;
        this.unitOfMeasure = unitOfMeasure;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public RecipeSupply(
            Long id,
            Long recipeId,
            Long supplyId,
            Double quantity,
            String unitOfMeasure,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.recipeId = recipeId;
        this.supplyId = supplyId;
        this.quantity = quantity;
        this.unitOfMeasure = unitOfMeasure;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    /**
     * Calcula la cantidad total necesaria de este insumo para una cantidad específica de recetas.
     * @param recipeQuantity Cantidad de recetas a preparar
     * @return Cantidad total de insumo necesaria
     */
    public Double calculateTotalQuantityNeeded(Integer recipeQuantity) {
        if (recipeQuantity == null || recipeQuantity <= 0) {
            return 0.0;
        }
        return this.quantity * recipeQuantity;
    }

    /**
     * Verifica si la unidad de medida es compatible con la unidad especificada.
     * Este método puede ser extendido para manejar conversiones entre unidades compatibles.
     * @param unit Unidad de medida a verificar
     * @return true si las unidades son compatibles, false en caso contrario
     */
    public boolean isUnitCompatible(String unit) {
        if (unit == null || unit.isEmpty() || this.unitOfMeasure == null) {
            return false;
        }

        // Caso simple: unidades idénticas
        if (unit.equalsIgnoreCase(this.unitOfMeasure)) {
            return true;
        }

        // Ejemplos de compatibilidad entre unidades
        // Estas reglas pueden expandirse según los requisitos específicos
        if (this.unitOfMeasure.equalsIgnoreCase("g") && unit.equalsIgnoreCase("kg")) {
            return true;
        }
        if (this.unitOfMeasure.equalsIgnoreCase("kg") && unit.equalsIgnoreCase("g")) {
            return true;
        }
        if (this.unitOfMeasure.equalsIgnoreCase("ml") && unit.equalsIgnoreCase("l")) {
            return true;
        }
        if (this.unitOfMeasure.equalsIgnoreCase("l") && unit.equalsIgnoreCase("ml")) {
            return true;
        }

        return false;
    }
}