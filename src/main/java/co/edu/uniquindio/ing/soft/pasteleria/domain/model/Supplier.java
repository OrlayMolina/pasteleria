package co.edu.uniquindio.ing.soft.pasteleria.domain.model;

import co.edu.uniquindio.ing.soft.pasteleria.domain.enums.Status;
import co.edu.uniquindio.ing.soft.pasteleria.domain.enums.TypeDocument;
import co.edu.uniquindio.ing.soft.pasteleria.domain.exception.DomainException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import static co.edu.uniquindio.ing.soft.pasteleria.domain.constant.Constants.*;

@NoArgsConstructor
@Getter
@Setter
public class Supplier {
    private Long id;
    private String name;
    private TypeDocument typeDocument;
    private String supplierDocument;
    private String address;
    private String phone;
    private String email;
    private String contactPerson;
    private LocalDateTime lastOrderDate;
    private Integer lastReviewRating;
    private String lastReviewComment;
    private Boolean onTimeDelivery;
    private Boolean qualityIssues;
    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long userModify;

    // Constructor básico
    public Supplier(Long id, String name, String supplierDocument, String address,
                    String phone, String email, String contactPerson,
                    Status status, LocalDateTime createdAt,
                    LocalDateTime updatedAt) throws DomainException {
        validateName(name);
        validateAddress(address);

        this.id = id;
        this.name = name;
        this.supplierDocument = supplierDocument;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.contactPerson = contactPerson;
        this.status = (status == null) ? Status.ACTIVO : status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Constructor completo incluyendo campos de review
    public Supplier(Long id, String name, String supplierDocument, String address,
                    String phone, String email, String contactPerson,
                    LocalDateTime lastOrderDate, Integer lastReviewRating,
                    String lastReviewComment, Boolean onTimeDelivery,
                    Boolean qualityIssues, Status status,
                    LocalDateTime createdAt, LocalDateTime updatedAt) throws DomainException {
        validateName(name);
        validateAddress(address);

        this.id = id;
        this.name = name;
        this.supplierDocument = supplierDocument;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.contactPerson = contactPerson;
        this.lastOrderDate = lastOrderDate;
        this.lastReviewRating = lastReviewRating;
        this.lastReviewComment = lastReviewComment;
        this.onTimeDelivery = onTimeDelivery;
        this.qualityIssues = qualityIssues;
        this.status = (status == null) ? Status.ACTIVO : status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Constructor completo con todos los campos
    public Supplier(Long id, String name, TypeDocument typeDocument, String supplierDocument,
                    String address, String phone, String email, String contactPerson,
                    LocalDateTime lastOrderDate, Integer lastReviewRating,
                    String lastReviewComment, Boolean onTimeDelivery,
                    Boolean qualityIssues, Status status,
                    LocalDateTime createdAt, LocalDateTime updatedAt,
                    Long userModify) throws DomainException {
        validateName(name);
        validateAddress(address);

        this.id = id;
        this.name = name;
        this.typeDocument = typeDocument;
        this.supplierDocument = supplierDocument;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.contactPerson = contactPerson;
        this.lastOrderDate = lastOrderDate;
        this.lastReviewRating = lastReviewRating;
        this.lastReviewComment = lastReviewComment;
        this.onTimeDelivery = onTimeDelivery;
        this.qualityIssues = qualityIssues;
        this.status = (status == null) ? Status.ACTIVO : status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.userModify = userModify;
    }

    public Supplier activate() throws DomainException {
        if (isActive()) {
            return this;
        }
        Supplier activated = new Supplier(id, name, supplierDocument, address, phone, email, contactPerson, Status.ACTIVO, createdAt, updatedAt);
        activated.typeDocument = this.typeDocument;
        activated.lastOrderDate = this.lastOrderDate;
        activated.lastReviewRating = this.lastReviewRating;
        activated.lastReviewComment = this.lastReviewComment;
        activated.onTimeDelivery = this.onTimeDelivery;
        activated.qualityIssues = this.qualityIssues;
        activated.userModify = this.userModify;
        return activated;
    }

    public Supplier deactivate() throws DomainException {
        if (!isActive()) {
            return this;
        }
        Supplier deactivated = new Supplier(id, name, supplierDocument, address, phone, email, contactPerson, Status.INACTIVO, createdAt, updatedAt);
        deactivated.typeDocument = this.typeDocument;
        deactivated.lastOrderDate = this.lastOrderDate;
        deactivated.lastReviewRating = this.lastReviewRating;
        deactivated.lastReviewComment = this.lastReviewComment;
        deactivated.onTimeDelivery = this.onTimeDelivery;
        deactivated.qualityIssues = this.qualityIssues;
        deactivated.userModify = this.userModify;
        return deactivated;
    }

    public boolean isActive() {
        return Status.ACTIVO.equals(status);
    }

    private void validateName(String name) throws DomainException {
        if (name == null || name.trim().isEmpty()) {
            throw new DomainException(SUPPLIER_NAME_CAN_NOT_BE_EMPTY);
        }
        if (name.length() < 3 || name.length() > 100) {
            throw new DomainException(SUPPLIER_NAME_LENGTH);
        }
    }

    private void validateAddress(String address) throws DomainException {
        if (address == null || address.trim().isEmpty()) {
            throw new DomainException(SUPPLIER_ADDRESS_CAN_NOT_BE_EMPTY);
        }
    }
}