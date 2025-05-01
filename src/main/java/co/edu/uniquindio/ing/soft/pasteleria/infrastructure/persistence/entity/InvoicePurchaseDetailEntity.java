package co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "invoice_purchase_details")
@Getter
@Setter
@NoArgsConstructor
public class InvoicePurchaseDetailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación con la factura
    @ManyToOne
    @JoinColumn(name = "invoice_purchase_id", nullable = false)
    private InvoicePurchaseEntity invoicePurchase;

    // Relación con el insumo
    @ManyToOne
    @JoinColumn(name = "supply_id", nullable = false)
    private SupplyEntity supply;

    // Cantidad de este insumo en la factura
    @NotNull
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    // Precio unitario de este insumo en la factura
    @NotNull
    @Column(name = "unit_price", nullable = false)
    private BigDecimal unitPrice;

    // Subtotal (precio * cantidad)
    @NotNull
    @Column(name = "subtotal", nullable = false)
    private BigDecimal subtotal;

    // Fecha de vencimiento específica para este lote de insumos
    @Column(name = "expiration_date")
    private LocalDate expirationDate;

    // Número de lote del fabricante
    @Column(name = "batch_number")
    private String batchNumber;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * Método para calcular el subtotal de este detalle
     */
    public BigDecimal calculateSubtotal() {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }

    /**
     * Método para actualizar el inventario al confirmar la factura
     */
    public void updateInventory() {
        SupplyEntity supply = this.getSupply();
        supply.setQuantity(supply.getQuantity() + this.quantity);
        // Actualizar otros campos como la fecha de expiración si es la más reciente
        if (this.expirationDate != null) {
            if (supply.getExpirationDate() == null || this.expirationDate.isAfter(supply.getExpirationDate())) {
                supply.setExpirationDate(this.expirationDate);
            }
        }
    }
}