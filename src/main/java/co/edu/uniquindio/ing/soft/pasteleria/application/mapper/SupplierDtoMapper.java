package co.edu.uniquindio.ing.soft.pasteleria.application.mapper;

import co.edu.uniquindio.ing.soft.pasteleria.application.dto.request.CreateSupplierCommand;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.request.UpdateSupplierCommand;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.response.SupplierResponse;
import co.edu.uniquindio.ing.soft.pasteleria.domain.enums.Status;
import co.edu.uniquindio.ing.soft.pasteleria.domain.exception.DomainException;
import co.edu.uniquindio.ing.soft.pasteleria.domain.model.Review;
import co.edu.uniquindio.ing.soft.pasteleria.domain.model.Supplier;
import org.springframework.stereotype.Component;

@Component
public class SupplierDtoMapper {

    /**
     * Convierte un CreateSupplierCommand en un modelo Supplier
     */
    public Supplier toModel(CreateSupplierCommand command) throws DomainException {
        Supplier supplier = new Supplier(
                null,
                command.name(),
                command.supplierID(),
                command.address(),
                command.phone(),
                command.email(),
                command.contactPerson(),
                command.status() != null ? command.status() : Status.ACTIVO,
                command.createdAt(),
                command.updatedAt()
        );

        // Establecer campos adicionales
        supplier.setTypeDocument(command.typeDocument());
        supplier.setLastOrderDate(command.lastOrderDate());
        supplier.setLastReviewRating(command.lastReviewRating());
        supplier.setLastReviewComment(command.lastReviewComment());
        supplier.setOnTimeDelivery(command.onTimeDelivery());
        supplier.setQualityIssues(command.qualityIssues());
        supplier.setUserModify(command.userModify());

        return supplier;
    }

    /**
     * Actualiza un modelo Supplier existente con los datos de un UpdateSupplierCommand
     */
    public Supplier updateModel(Supplier supplier, UpdateSupplierCommand command) throws DomainException {
        if (command.name() != null) {
            supplier.setName(command.name());
        }
        if (command.supplierID() != null) {
            supplier.setSupplierDocument(command.supplierID());
        }
        if (command.address() != null) {
            supplier.setAddress(command.address());
        }
        if (command.phone() != null) {
            supplier.setPhone(command.phone());
        }
        if (command.email() != null) {
            supplier.setEmail(command.email());
        }
        if (command.contactPerson() != null) {
            supplier.setContactPerson(command.contactPerson());
        }
        if (command.status() != null) {
            supplier.setStatus(command.status());
        }
        if (command.updatedAt() != null) {
            supplier.setUpdatedAt(command.updatedAt());
        }
        if (command.userModify() != null) {
            supplier.setUserModify(command.userModify());
        }

        // Actualizar campos relacionados con la revisión
        if (command.lastOrderDate() != null) {
            supplier.setLastOrderDate(command.lastOrderDate());
        }
        if (command.lastReviewRating() != null) {
            supplier.setLastReviewRating(command.lastReviewRating());
        }
        if (command.lastReviewComment() != null) {
            supplier.setLastReviewComment(command.lastReviewComment());
        }
        if (command.onTimeDelivery() != null) {
            supplier.setOnTimeDelivery(command.onTimeDelivery());
        }
        if (command.qualityIssues() != null) {
            supplier.setQualityIssues(command.qualityIssues());
        }

        return supplier;
    }

    /**
     * Convierte un modelo Supplier en un SupplierResponse
     */
    public SupplierResponse toResponse(Supplier supplier) throws DomainException {
        return new SupplierResponse(
                supplier.getId(),
                supplier.getName(),
                supplier.getSupplierDocument(),
                supplier.getAddress(),
                supplier.getPhone(),
                supplier.getEmail(),
                supplier.getContactPerson(),
                supplier.getStatus(),
                supplier.getCreatedAt(),
                supplier.getUpdatedAt(),
                supplier.getLastOrderDate(),
                supplier.getLastReviewRating(),
                supplier.getLastReviewComment(),
                supplier.getOnTimeDelivery(),
                supplier.getQualityIssues(),
                supplier.getUserModify()
        );
    }

    /**
     * Convierte un modelo Supplier en un SupplierResponse enriquecido con datos de una revisión específica
     */
    public SupplierResponse toEnrichedResponse(Supplier supplier, Review review) throws DomainException {
        return new SupplierResponse(
                supplier.getId(),
                supplier.getName(),
                supplier.getSupplierDocument(),
                supplier.getAddress(),
                supplier.getPhone(),
                supplier.getEmail(),
                supplier.getContactPerson(),
                supplier.getStatus(),
                supplier.getCreatedAt(),
                supplier.getUpdatedAt(),
                review != null ? review.getOrderDate() : supplier.getLastOrderDate(),
                review != null ? review.getRating() : supplier.getLastReviewRating(),
                review != null ? review.getComment() : supplier.getLastReviewComment(),
                review != null ? review.getOnTimeDelivery() : supplier.getOnTimeDelivery(),
                review != null ? review.getQualityIssues() : supplier.getQualityIssues(),
                supplier.getUserModify()
        );
    }
}