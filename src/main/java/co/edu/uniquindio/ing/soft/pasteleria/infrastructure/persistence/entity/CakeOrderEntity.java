package co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.entity;

import co.edu.uniquindio.ing.soft.pasteleria.domain.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cake_orders")
@Getter
@Setter
@NoArgsConstructor
public class CakeOrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_date", nullable = false)
    private LocalDateTime orderDate;

    @Column(name = "delivery_date")
    private LocalDateTime deliveryDate;

    @Column(name = "customer_name", nullable = false)
    private String customerName;

    @Column(name = "customer_phone")
    private String customerPhone;

    @Column(name = "customer_email")
    private String customerEmail;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", nullable = false)
    private OrderStatus orderStatus;

    @Column(name = "total_amount", nullable = false)
    private BigDecimal totalAmount;

    @Column(name = "has_inventory_alert", nullable = false)
    private Boolean hasInventoryAlert;

    @Column(name = "inventory_alert_details")
    private String inventoryAlertDetails;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private UserEntity createdBy;

    @ManyToOne
    @JoinColumn(name = "modify_by")
    private UserEntity modifiedBy;

    // Relación con los detalles de la orden
    @OneToMany(mappedBy = "cakeOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CakeOrderDetailEntity> orderDetails = new ArrayList<>();
}