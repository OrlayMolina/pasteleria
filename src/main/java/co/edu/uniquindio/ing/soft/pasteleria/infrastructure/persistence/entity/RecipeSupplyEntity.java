package co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "recipe_supplies")
@Getter
@Setter
@NoArgsConstructor
public class RecipeSupplyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación con la receta
    @ManyToOne
    @JoinColumn(name = "recipe_id", nullable = false)
    private RecipeEntity recipe;

    // Relación con el insumo
    @ManyToOne
    @JoinColumn(name = "supply_id", nullable = false)
    private SupplyEntity supply;

    // Cantidad requerida del insumo para esta receta
    @NotNull
    @Column(name = "quantity", nullable = false)
    private Double quantity;

    // Unidad de medida (gramos, ml, unidades, etc.)
    @Column(name = "unit_of_measure", nullable = false)
    private String unitOfMeasure;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
