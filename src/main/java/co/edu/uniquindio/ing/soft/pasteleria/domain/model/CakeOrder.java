package co.edu.uniquindio.ing.soft.pasteleria.domain.model;

import co.edu.uniquindio.ing.soft.pasteleria.domain.enums.OrderStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class CakeOrder {
    private Long id;
    private LocalDateTime orderDate;
    private LocalDateTime deliveryDate;
    private String customerName;
    private String customerPhone;
    private String customerEmail;
    private OrderStatus orderStatus;
    private BigDecimal totalAmount;
    private Boolean hasInventoryAlert;
    private String inventoryAlertDetails;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long createdById;
    private Long modifiedById;
    private List<CakeOrderDetail> orderDetails = new ArrayList<>();

    public CakeOrder(
            LocalDateTime orderDate,
            LocalDateTime deliveryDate,
            String customerName,
            String customerPhone,
            String customerEmail,
            OrderStatus orderStatus,
            BigDecimal totalAmount,
            Boolean hasInventoryAlert,
            String inventoryAlertDetails,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            Long createdById,
            Long modifiedById) {
        this.orderDate = orderDate;
        this.deliveryDate = deliveryDate;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.customerEmail = customerEmail;
        this.orderStatus = orderStatus;
        this.totalAmount = totalAmount;
        this.hasInventoryAlert = hasInventoryAlert;
        this.inventoryAlertDetails = inventoryAlertDetails;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdById = createdById;
        this.modifiedById = modifiedById;
    }

    public CakeOrder(
            Long id,
            LocalDateTime orderDate,
            LocalDateTime deliveryDate,
            String customerName,
            String customerPhone,
            String customerEmail,
            OrderStatus orderStatus,
            BigDecimal totalAmount,
            Boolean hasInventoryAlert,
            String inventoryAlertDetails,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            Long createdById,
            Long modifiedById) {
        this.id = id;
        this.orderDate = orderDate;
        this.deliveryDate = deliveryDate;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.customerEmail = customerEmail;
        this.orderStatus = orderStatus;
        this.totalAmount = totalAmount;
        this.hasInventoryAlert = hasInventoryAlert;
        this.inventoryAlertDetails = inventoryAlertDetails;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdById = createdById;
        this.modifiedById = modifiedById;
    }
}