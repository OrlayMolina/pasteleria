package co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.entity;

import co.edu.uniquindio.ing.soft.pasteleria.domain.enums.Status;
import co.edu.uniquindio.ing.soft.pasteleria.domain.enums.TypeDocument;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "suppliers")
@Getter
@Setter
@NoArgsConstructor
public class SupplierEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "name", nullable = false)
    @Size(min = 3, max = 50)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_document")
    private TypeDocument typeDocument;

    @NotBlank
    @Column(name = "supplier_document", nullable = false)
    @Size(min = 3, max = 50)
    private String supplierID;

    @NotBlank
    @Column(name = "address", nullable = false)
    @Size(max = 100)
    private String address;

    @NotBlank
    @Pattern(regexp = "^\\+?[1-9]\\d{7,14}$")
    @Column(name = "phone")
    private String phone;

    @Email
    @Column(name = "email")
    private String email;

    @Column(name = "status")
    private Status status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "contact_person")
    private String contactPerson;

    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SupplyEntity> supplies = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "modify_by")
    private UserEntity userModify;

    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewEntity> reviews = new ArrayList<>();
}