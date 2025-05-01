package co.edu.uniquindio.ing.soft.pasteleria.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class Review {
    private Long id;
    private Long supplierId;
    private LocalDateTime orderDate;
    private Integer rating;
    private String comment;
    private Boolean onTimeDelivery;
    private Boolean qualityIssues;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long userModify;

    public Review(Long id, Long supplierId, LocalDateTime orderDate, Integer rating,
                  String comment, Boolean onTimeDelivery, Boolean qualityIssues,
                  LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.supplierId = supplierId;
        this.orderDate = orderDate;
        this.rating = rating;
        this.comment = comment;
        this.onTimeDelivery = onTimeDelivery;
        this.qualityIssues = qualityIssues;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Review(Long id, Long supplierId, LocalDateTime orderDate, Integer rating,
                  String comment, Boolean onTimeDelivery, Boolean qualityIssues,
                  LocalDateTime createdAt, LocalDateTime updatedAt, Long userModify) {
        this.id = id;
        this.supplierId = supplierId;
        this.orderDate = orderDate;
        this.rating = rating;
        this.comment = comment;
        this.onTimeDelivery = onTimeDelivery;
        this.qualityIssues = qualityIssues;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.userModify = userModify;
    }
}