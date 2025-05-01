package co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "supplier_reviews")
@Getter
@Setter
@NoArgsConstructor
public class ReviewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "supplier_id", nullable = false)
    private SupplierEntity supplier;

    @Column(name = "order_date")
    private LocalDateTime orderDate;

    @Column(name = "rating")
    private Integer rating;

    @Column(name = "comment", length = 500)
    private String comment;

    @Column(name = "on_time_delivery")
    private Boolean onTimeDelivery;

    @Column(name = "quality_issues")
    private Boolean qualityIssues;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "modify_by")
    private UserEntity userModify;
}