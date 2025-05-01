package co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.entity;

import co.edu.uniquindio.ing.soft.pasteleria.domain.enums.InvoiceStatus;
import co.edu.uniquindio.ing.soft.pasteleria.domain.enums.PaymentMethod;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "invoice_purchases")
@Getter
@Setter
@NoArgsConstructor
public class InvoicePurchaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "invoice_number", nullable = false)
    private String invoiceNumber;

    // Fecha de la compra o emisión de la factura
    @Column(name = "purchase_date", nullable = false)
    private LocalDateTime purchaseDate;

    // Fecha de recepción de los productos
    @Column(name = "reception_date")
    private LocalDateTime receptionDate;

    // Subtotal (valor de los productos antes de impuestos)
    @NotNull
    @Column(name = "subtotal", nullable = false)
    private BigDecimal subtotal;

    // IVA (impuesto al valor agregado)
    @NotNull
    @Column(name = "iva", nullable = false)
    private BigDecimal iva;

    // Descuento aplicado a la factura
    @Column(name = "discount")
    private BigDecimal discount;

    // Total de la factura (valor de los productos + IVA - descuento)
    @NotNull
    @Column(name = "total", nullable = false)
    private BigDecimal total;

    // Estado de la factura
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private InvoiceStatus status;

    // Notas adicionales sobre la factura
    @Column(name = "notes", length = 500)
    private String notes;

    // Método de pago
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method")
    private PaymentMethod paymentMethod;

    // Relación: cada factura se asocia a un único proveedor
    @ManyToOne
    @JoinColumn(name = "supplier_id", nullable = false)
    private SupplierEntity supplier;

    // Relación: usuario que registró la factura
    @ManyToOne
    @JoinColumn(name = "created_by")
    private UserEntity createdBy;

    // Relación: usuario que modificó la factura
    @ManyToOne
    @JoinColumn(name = "modify_by")
    private UserEntity modifiedBy;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Relación con los insumos incluidos en la factura, a través de una entidad intermedia
    @OneToMany(mappedBy = "invoicePurchase", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InvoicePurchaseDetailEntity> purchaseDetails = new ArrayList<>();

    /**
     * Método para calcular el subtotal de la factura
     */
    public BigDecimal calculateSubtotal() {
        if (purchaseDetails == null || purchaseDetails.isEmpty()) {
            return BigDecimal.ZERO;
        }

        return purchaseDetails.stream()
                .map(InvoicePurchaseDetailEntity::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Método para calcular el IVA de la factura
     */
    public BigDecimal calculateIva(BigDecimal ivaRate) {
        BigDecimal subtotal = calculateSubtotal();
        return subtotal.multiply(ivaRate);
    }

    /**
     * Método para calcular el total de la factura
     */
    public BigDecimal calculateTotal() {
        BigDecimal subtotal = calculateSubtotal();
        BigDecimal discountAmount = discount != null ? discount : BigDecimal.ZERO;

        return subtotal.add(iva).subtract(discountAmount);
    }
}