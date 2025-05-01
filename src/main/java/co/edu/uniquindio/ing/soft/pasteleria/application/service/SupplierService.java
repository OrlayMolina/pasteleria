package co.edu.uniquindio.ing.soft.pasteleria.application.service;

import co.edu.uniquindio.ing.soft.pasteleria.application.dto.MensajeDTO;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.request.CreateSupplierCommand;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.request.UpdateSupplierCommand;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.response.PageResponse;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.response.SupplierResponse;
import co.edu.uniquindio.ing.soft.pasteleria.application.mapper.SupplierDtoMapper;
import co.edu.uniquindio.ing.soft.pasteleria.application.ports.input.ManageSupplierUseCase;
import co.edu.uniquindio.ing.soft.pasteleria.application.ports.output.ReviewPort;
import co.edu.uniquindio.ing.soft.pasteleria.application.ports.output.SupplierPort;
import co.edu.uniquindio.ing.soft.pasteleria.domain.exception.DomainException;
import co.edu.uniquindio.ing.soft.pasteleria.domain.model.Review;
import co.edu.uniquindio.ing.soft.pasteleria.domain.model.Supplier;
import co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.entity.SupplierEntity;
import co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.repository.SupplierJpaRepository;
import co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class SupplierService implements ManageSupplierUseCase {

    private final SupplierPort supplierPort;
    private final ReviewPort reviewPort;
    private final SupplierDtoMapper supplierDtoMapper;
    private final SupplierJpaRepository supplierJpaRepository;
    private final UserJpaRepository userJpaRepository;

    @Override
    public MensajeDTO<SupplierResponse> createSupplier(CreateSupplierCommand command) throws DomainException {
        try {
            // Verificar si ya existe un proveedor con el mismo ID
            Optional<SupplierEntity> supplierOptional = supplierJpaRepository.findBySupplierID(command.supplierID());
            if (supplierOptional.isPresent()) {
                throw new DomainException("Ya existe un proveedor con el mismo número de ID.");
            }

            // Verificar si el usuario existe
            if (command.userModify() != null) {
                boolean userExists = userJpaRepository.existsById(command.userModify());
                if (!userExists) {
                    throw new DomainException("El usuario especificado no existe.");
                }
            }

            // Crear y guardar el proveedor
            Supplier supplier = new Supplier(
                    null,
                    command.name(),
                    command.supplierID(),
                    command.address(),
                    command.phone(),
                    command.email(),
                    command.contactPerson(),
                    command.lastOrderDate(),
                    command.lastReviewRating(),
                    command.lastReviewComment(),
                    command.onTimeDelivery(),
                    command.qualityIssues(),
                    command.status(),
                    command.createdAt(),
                    command.updatedAt()
            );

            // Si hay un tipo de documento, establecerlo
            if (command.typeDocument() != null) {
                supplier.setTypeDocument(command.typeDocument());
            }

            // Guardar el proveedor primero para obtener su ID
            Supplier savedSupplier = supplierPort.saveSupplier(supplier);

            // Si hay datos de reseña, crear y guardar la reseña
            Review review = null;
            if (command.lastOrderDate() != null && command.lastReviewRating() != null) {
                review = new Review(
                        null,
                        savedSupplier.getId(),
                        command.lastOrderDate(),
                        command.lastReviewRating(),
                        command.lastReviewComment(),
                        command.onTimeDelivery(),
                        command.qualityIssues(),
                        command.createdAt(),
                        command.updatedAt()
                );

                // Si hay un usuario modificador, establecerlo en la reseña
                if (command.userModify() != null) {
                    review.setUserModify(command.userModify());
                }

                reviewPort.saveReview(review);
            }

            // Crear la respuesta con los datos combinados
            SupplierResponse response = createEnrichedResponse(savedSupplier, review);

            return new MensajeDTO<>(false, response);
        } catch (DomainException e) {
            return new MensajeDTO<>(true, null);
        }
    }

    @Override
    public MensajeDTO<SupplierResponse> updateSupplier(Long id, UpdateSupplierCommand command) {
        try {
            Optional<Supplier> optionalSupplier = supplierPort.findSupplierById(id);
            if (optionalSupplier.isEmpty()) {
                return new MensajeDTO<>(true, null);
            }

            // Verificar si el usuario existe
            if (command.userModify() != null) {
                boolean userExists = userJpaRepository.existsById(command.userModify());
                if (!userExists) {
                    throw new DomainException("El usuario especificado no existe.");
                }
            }

            Supplier existingSupplier = optionalSupplier.get();
            existingSupplier.setName(command.name());

            // Si el supplierID no es nulo, actualizarlo
            if (command.supplierID() != null) {
                // Verificar que no exista otro proveedor con ese ID
                Optional<SupplierEntity> supplierWithSameID = supplierJpaRepository.findBySupplierID(command.supplierID());
                if (supplierWithSameID.isPresent() && !supplierWithSameID.get().getId().equals(id)) {
                    throw new DomainException("Ya existe otro proveedor con el mismo número de ID.");
                }
                existingSupplier.setSupplierDocument(command.supplierID());
            }

            existingSupplier.setAddress(command.address());
            existingSupplier.setPhone(command.phone());
            existingSupplier.setEmail(command.email());
            existingSupplier.setContactPerson(command.contactPerson());
            existingSupplier.setStatus(command.status());
            existingSupplier.setUpdatedAt(command.updatedAt());

            Supplier updatedSupplier = supplierPort.saveSupplier(existingSupplier);

            // Manejar la actualización o creación de la reseña
            Review review = null;
            if (command.lastOrderDate() != null && command.lastReviewRating() != null) {
                // Buscar si ya existe una reseña para la misma fecha
                Optional<Review> existingReview = reviewPort.findLatestReviewBySupplier(id);

                if (existingReview.isPresent() &&
                        existingReview.get().getOrderDate().equals(command.lastOrderDate())) {
                    // Actualizar reseña existente
                    review = existingReview.get();
                    review.setRating(command.lastReviewRating());
                    review.setComment(command.lastReviewComment());
                    review.setOnTimeDelivery(command.onTimeDelivery());
                    review.setQualityIssues(command.qualityIssues());
                    review.setUpdatedAt(command.updatedAt());

                    // Actualizar usuario modificador si está presente
                    if (command.userModify() != null) {
                        review.setUserModify(command.userModify());
                    }
                } else {
                    // Crear nueva reseña
                    review = new Review(
                            null,
                            id,
                            command.lastOrderDate(),
                            command.lastReviewRating(),
                            command.lastReviewComment(),
                            command.onTimeDelivery(),
                            command.qualityIssues(),
                            LocalDateTime.now(),
                            command.updatedAt() != null ? command.updatedAt() : LocalDateTime.now()
                    );

                    // Establecer usuario modificador si está presente
                    if (command.userModify() != null) {
                        review.setUserModify(command.userModify());
                    }
                }

                reviewPort.saveReview(review);
            }

            // Obtener la última reseña (podría ser la que acabamos de crear/actualizar u otra anterior)
            Optional<Review> latestReview = reviewPort.findLatestReviewBySupplier(id);

            // Crear respuesta enriquecida
            SupplierResponse response = createEnrichedResponse(updatedSupplier, latestReview.orElse(null));

            return new MensajeDTO<>(false, response);
        } catch (DomainException e) {
            return new MensajeDTO<>(true, null);
        }
    }

    @Override
    public MensajeDTO<Void> deleteSupplier(Long id) {
        try {
            supplierPort.deleteSupplierById(id);
            return new MensajeDTO<>(false, null);
        } catch (Exception e) {
            return new MensajeDTO<>(true, null);
        }
    }

    @Override
    public MensajeDTO<SupplierResponse> getSupplier(Long id) throws DomainException {
        Optional<Supplier> optionalSupplier = supplierPort.findSupplierById(id);
        if (optionalSupplier.isEmpty()) {
            return new MensajeDTO<>(true, null);
        }

        Supplier supplier = optionalSupplier.get();
        Optional<Review> latestReview = reviewPort.findLatestReviewBySupplier(id);

        SupplierResponse response = createEnrichedResponse(supplier, latestReview.orElse(null));
        return new MensajeDTO<>(false, response);
    }

    @Override
    public MensajeDTO<List<SupplierResponse>> searchSupplier() {
        try {
            List<Supplier> suppliers = supplierPort.findAllSuppliers();
            List<SupplierResponse> responses = suppliers.stream().map(supplier -> {
                try {
                    Optional<Review> latestReview = reviewPort.findLatestReviewBySupplier(supplier.getId());
                    return createEnrichedResponse(supplier, latestReview.orElse(null));
                } catch (DomainException e) {
                    throw new RuntimeException("Error al mapear Supplier a SupplierResponse", e);
                }
            }).collect(Collectors.toList());

            return new MensajeDTO<>(false, responses);
        } catch (Exception e) {
            return new MensajeDTO<>(true, null);
        }
    }

    @Override
    public MensajeDTO<PageResponse<SupplierResponse>> getPagedSuppliersByPageAndSize(int page, int size) {
        try {
            Page<Supplier> suppliersPage = supplierPort.findSuppliersWithPagination(page, size);

            List<SupplierResponse> responses = suppliersPage.getContent().stream()
                    .map(supplier -> {
                        try {
                            Optional<Review> latestReview = reviewPort.findLatestReviewBySupplier(supplier.getId());
                            return createEnrichedResponse(supplier, latestReview.orElse(null));
                        } catch (DomainException e) {
                            throw new RuntimeException("Error al mapear Supplier a SupplierResponse", e);
                        }
                    }).collect(Collectors.toList());

            PageResponse<SupplierResponse> pageResponse = new PageResponse<>(
                    responses,
                    suppliersPage.getNumber(),
                    suppliersPage.getSize(),
                    suppliersPage.getTotalElements(),
                    suppliersPage.getTotalPages(),
                    suppliersPage.isLast()
            );

            return new MensajeDTO<>(false, pageResponse);
        } catch (Exception e) {
            return new MensajeDTO<>(true, null);
        }
    }

    @Override
    public MensajeDTO<PageResponse<SupplierResponse>> getPagedSuppliers(int page, int size, String sort, String direction, String search) {
        try {
            // Validar dirección de ordenamiento
            String sortDirection = "asc".equalsIgnoreCase(direction) ? "asc" : "desc";

            // Obtener página de proveedores con ordenamiento y búsqueda
            Page<Supplier> suppliersPage = supplierPort.findSuppliersWithPaginationAndSorting(page, size, sort, sortDirection, search);

            List<SupplierResponse> responses = suppliersPage.getContent().stream()
                    .map(supplier -> {
                        try {
                            Optional<Review> latestReview = reviewPort.findLatestReviewBySupplier(supplier.getId());
                            return createEnrichedResponse(supplier, latestReview.orElse(null));
                        } catch (DomainException e) {
                            throw new RuntimeException("Error al mapear Supplier a SupplierResponse", e);
                        }
                    }).collect(Collectors.toList());

            PageResponse<SupplierResponse> pageResponse = new PageResponse<>(
                    responses,
                    suppliersPage.getNumber(),
                    suppliersPage.getSize(),
                    suppliersPage.getTotalElements(),
                    suppliersPage.getTotalPages(),
                    suppliersPage.isLast()
            );

            return new MensajeDTO<>(false, pageResponse);
        } catch (Exception e) {
            return new MensajeDTO<>(true, null);
        }
    }

    // Método auxiliar para crear una respuesta con datos enriquecidos
    private SupplierResponse createEnrichedResponse(Supplier supplier, Review review) throws DomainException {
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
                review != null ? review.getOrderDate() : null,
                review != null ? review.getRating() : null,
                review != null ? review.getComment() : null,
                review != null ? review.getOnTimeDelivery() : null,
                review != null ? review.getQualityIssues() : null,
                supplier.getUserModify()
        );
    }
}