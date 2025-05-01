package co.edu.uniquindio.ing.soft.pasteleria.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class Supply {
    private Long id;
    private String name;
    private String supplierDocument;
    private Long supplierId;
    private Double price;
    private LocalDate entryDate;
    private LocalDate expirationDate;
    private int quantity;
    private String unitMeasurement;
    private int minimumStock;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long userModify;

    public Supply(String name, String supplierDocument, Long supplierId, Double price, LocalDate entryDate,
                  LocalDate expirationDate, int quantity, String unitMeasurement, int minimumStock, LocalDateTime createdAt,
                  LocalDateTime updatedAt, Long userModify) {
        this.name = name;
        this.supplierDocument = supplierDocument;
        this.supplierId = supplierId;
        this.price = price;
        this.entryDate = entryDate;
        this.expirationDate = expirationDate;
        this.quantity = quantity;
        this.unitMeasurement = unitMeasurement;
        this.minimumStock = minimumStock;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.userModify = userModify;
    }

    public Supply(Long id, String name, String supplierDocument, Long supplierId, Double price,
                  LocalDate entryDate, LocalDate expirationDate, int quantity, String unitMeasurement, int minimumStock,
                  LocalDateTime createdAt, LocalDateTime updatedAt, Long userModify) {
        this.id = id;
        this.name = name;
        this.supplierDocument = supplierDocument;
        this.supplierId = supplierId;
        this.price = price;
        this.entryDate = entryDate;
        this.expirationDate = expirationDate;
        this.quantity = quantity;
        this.unitMeasurement = unitMeasurement;
        this.minimumStock = minimumStock;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.userModify = userModify;
    }
}
