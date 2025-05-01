package co.edu.uniquindio.ing.soft.pasteleria.application.service;

import co.edu.uniquindio.ing.soft.pasteleria.application.dto.MensajeDTO;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.request.CreateSupplyCommand;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.request.UpdateSupplyCommand;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.response.PageResponse;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.response.SupplyInfoResponse;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.response.SupplyResponse;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.response.SupplyStockResponse;
import co.edu.uniquindio.ing.soft.pasteleria.application.mapper.SupplyDtoMapper;
import co.edu.uniquindio.ing.soft.pasteleria.application.ports.input.ManageSupplyUseCase;
import co.edu.uniquindio.ing.soft.pasteleria.application.ports.output.SupplierPort;
import co.edu.uniquindio.ing.soft.pasteleria.application.ports.output.SupplyPort;
import co.edu.uniquindio.ing.soft.pasteleria.domain.exception.DomainException;
import co.edu.uniquindio.ing.soft.pasteleria.domain.model.Supply;
import co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.entity.SupplyEntity;
import co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.repository.SupplyJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class SupplyService implements ManageSupplyUseCase {

    private final SupplyPort supplyPort;
    private final SupplyDtoMapper supplyDtoMapper;
    private final SupplyJpaRepository supplyJpaRepository;
    private final SupplierPort supplierPort;

    @Override
    public MensajeDTO<String> createSupply(CreateSupplyCommand command) throws DomainException {
        try {
            Optional<SupplyEntity> supplyOptional = supplyJpaRepository.findByName(command.name());
            if (supplyOptional.isPresent()) {
                throw new DomainException("Ya existe un insumo con el mismo nombre.");
            }

            // Validar que el proveedor existe y obtener su ID numérico
            Long supplierId = supplierPort.getSupplierIdBySupplierID(command.supplierID());
            if (supplierId == null) {
                return new MensajeDTO<>(true, "El proveedor con ID " + command.supplierID() + " no existe.");
            }
            // Usar el método actualizado, pasando el supplierId
            Supply supply = supplyDtoMapper.toModel(command, supplierId);

            Supply savedSupply = supplyPort.saveSupply(supply);
            return new MensajeDTO<>(false, "Insumo creado con exito");
        } catch (Exception e) {
            return new MensajeDTO<>(true, "Error al crear el insumo: " + e.getMessage());
        }
    }

    @Override
    public MensajeDTO<SupplyResponse> updateSupply(Long id, UpdateSupplyCommand command) throws DomainException {
        try {
            Optional<Supply> optionalSupply = supplyPort.findSupplyById(id);
            if (optionalSupply.isEmpty()) {
                throw new DomainException("El insumo con id " + id + " no existe.");
            }

            Supply existingSupply = optionalSupply.get();
            existingSupply.setName(command.name());
            existingSupply.setPrice(command.price());
            existingSupply.setQuantity(command.quantity());
            existingSupply.setUnitMeasurement(command.unitMeasurement());
            existingSupply.setEntryDate(command.entryDate());
            existingSupply.setExpirationDate(command.expirationDate());
            existingSupply.setMinimumStock(command.minimumStock());
            existingSupply.setUpdatedAt(command.updatedAt());
            existingSupply.setUserModify(command.userModify());

            Supply updatedSupply = supplyPort.updateSupply(existingSupply);
            return new MensajeDTO<>(false, supplyDtoMapper.toResponse(updatedSupply));

        } catch (DomainException e) {
            throw e; // Propaga la excepción de dominio
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar el insumo: " + e.getMessage(), e);
        }
    }

    @Override
    public MensajeDTO<Void> deleteSupply(Long id) {
        supplyPort.deleteSupplyById(id);
        return new MensajeDTO<>(false, null);
    }

    @Override
    public MensajeDTO<SupplyResponse> getSupply(Long id) throws DomainException {
        Optional<Supply> optionalSupply = supplyPort.findSupplyById(id);
        if (optionalSupply.isEmpty()) {
            return new MensajeDTO<>(true, null); // Indicando error si no se encuentra
        }
        return new MensajeDTO<>(false, supplyDtoMapper.toResponse(optionalSupply.get()));
    }

    @Override
    public MensajeDTO<List<SupplyResponse>> searchSupply() {
        try {
            List<Supply> supplies = supplyPort.findAllSupplies();
            List<SupplyResponse> responseList = supplies.stream()
                    .map(supply -> {
                        try {
                            return supplyDtoMapper.toResponse(supply);
                        } catch (DomainException e) {
                            throw new RuntimeException("Error al mapear Supply a SupplyResponse", e);
                        }
                    })
                    .toList();
            return new MensajeDTO<>(false, responseList);
        } catch (Exception e) {
            return new MensajeDTO<>(true, List.of()); // En caso de error, lista vacía
        }
    }

    @Override
    public MensajeDTO<PageResponse<SupplyResponse>> getPagedSupplies(int page, int size) {
        try {
            Page<Supply> suppliesPage = supplyPort.findSuppliesWithPagination(page, size);

            List<SupplyResponse> responses = suppliesPage.getContent().stream()
                    .map(supply -> {
                        try {
                            return supplyDtoMapper.toResponse(supply);
                        } catch (DomainException e) {
                            throw new RuntimeException("Error al mapear Supply a SupplyResponse", e);
                        }
                    }).toList();

            PageResponse<SupplyResponse> pageResponse = new PageResponse<>(
                    responses,
                    suppliesPage.getNumber(),
                    suppliesPage.getSize(),
                    suppliesPage.getTotalElements(),
                    suppliesPage.getTotalPages(),
                    suppliesPage.isLast()
            );

            return new MensajeDTO<>(false, pageResponse);
        } catch (Exception e) {
            return new MensajeDTO<>(true, null);
        }
    }

    @Override
    public MensajeDTO<PageResponse<SupplyResponse>> getPagedSupplies(int page, int size, String sort, String direction, String search) {
        Page<Supply> suppliesPage = supplyPort.findSuppliesWithPaginationAndSorting(page, size, sort, direction, search);

        List<SupplyResponse> items = suppliesPage.getContent().stream()
                .map(supply -> {
                    try {
                        return supplyDtoMapper.toResponse(supply);
                    } catch (DomainException e) {
                        throw new RuntimeException("Error al mapear insumo a DTO", e);
                    }
                })
                .collect(Collectors.toList());

        PageResponse<SupplyResponse> pageResponse = new PageResponse<>(
                items,
                suppliesPage.getNumber(),
                suppliesPage.getSize(),
                suppliesPage.getTotalElements(),
                suppliesPage.getTotalPages(),
                suppliesPage.isLast()
        );

        return new MensajeDTO<>(false, pageResponse);
    }

    @Override
    public MensajeDTO<SupplyInfoResponse> getSupplyInfo(Long id) throws DomainException {
        Optional<Supply> optionalSupply = supplyPort.findSupplyById(id);
        if (optionalSupply.isEmpty()) {
            throw new DomainException("El insumo con id " + id + " no existe.");
        }

        Supply supply = optionalSupply.get();

        SupplyInfoResponse response = new SupplyInfoResponse(
                supply.getName(),
                supply.getCreatedAt(),
                supply.getUpdatedAt(),
                supply.getUserModify()
        );

        return new MensajeDTO<>(false, response);
    }

    @Override
    public MensajeDTO<SupplyStockResponse> getSupplyStock(Long id) throws DomainException {
        Optional<Supply> optionalSupply = supplyPort.findSupplyById(id);
        if (optionalSupply.isEmpty()) {
            throw new DomainException("El insumo con id " + id + " no existe.");
        }

        Supply supply = optionalSupply.get();

        String supplierName = supplierPort.getSupplierNameById(supply.getSupplierId());

        String stockStatus = determineStockStatus(supply.getQuantity(), supply.getMinimumStock());
        boolean aboutToExpire = isAboutToExpire(supply.getExpirationDate());

        SupplyStockResponse response = new SupplyStockResponse(
                supply.getName(),
                supplierName,
                supply.getMinimumStock(),
                supply.getQuantity(),
                supply.getExpirationDate(),
                stockStatus,
                aboutToExpire
        );

        return new MensajeDTO<>(false, response);
    }

    private String determineStockStatus(int quantity, int minimumStock) {
        if (quantity <= 0) {
            return "AGOTADO";
        } else if (quantity < (minimumStock * 0.25)) {
            return "CRÍTICO";
        } else if (quantity < (minimumStock * 0.5)) {
            return "BAJO";
        } else {
            return "NORMAL";
        }
    }

    private boolean isAboutToExpire(LocalDate expirationDate) {
        if (expirationDate == null) {
            return false;
        }

        int daysBeforeWarning = 15;

        LocalDate today = LocalDate.now();
        return today.isAfter(expirationDate.minusDays(daysBeforeWarning)) && today.isBefore(expirationDate);
    }
}