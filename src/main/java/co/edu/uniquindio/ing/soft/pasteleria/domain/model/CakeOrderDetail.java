package co.edu.uniquindio.ing.soft.pasteleria.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class CakeOrderDetail {
    private Long id;
    private Long cakeOrderId;
    private Long recipeId;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal subtotal;
    private String specialInstructions;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public CakeOrderDetail(
            Long cakeOrderId,
            Long recipeId,
            Integer quantity,
            BigDecimal unitPrice,
            BigDecimal subtotal,
            String specialInstructions,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        this.cakeOrderId = cakeOrderId;
        this.recipeId = recipeId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.subtotal = subtotal;
        this.specialInstructions = specialInstructions;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public CakeOrderDetail(
            Long id,
            Long cakeOrderId,
            Long recipeId,
            Integer quantity,
            BigDecimal unitPrice,
            BigDecimal subtotal,
            String specialInstructions,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        this.id = id;
        this.cakeOrderId = cakeOrderId;
        this.recipeId = recipeId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.subtotal = subtotal;
        this.specialInstructions = specialInstructions;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}