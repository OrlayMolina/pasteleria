package co.edu.uniquindio.ing.soft.pasteleria.infrastructure.controller;

import co.edu.uniquindio.ing.soft.pasteleria.application.dto.MensajeDTO;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.request.CreateSupplierCommand;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.request.UpdateSupplierCommand;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.response.PageResponse;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.response.SupplierResponse;
import co.edu.uniquindio.ing.soft.pasteleria.application.ports.input.ManageSupplierUseCase;
import co.edu.uniquindio.ing.soft.pasteleria.domain.exception.DomainException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
@AllArgsConstructor
public class SupplierController {
    private final ManageSupplierUseCase supplierUseCase;

    @PostMapping
    public ResponseEntity<MensajeDTO<SupplierResponse>> createSupplier(@RequestBody @Valid CreateSupplierCommand command) throws DomainException {
        MensajeDTO<SupplierResponse> response = supplierUseCase.createSupplier(command);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MensajeDTO<SupplierResponse>> updateSupplier(
            @PathVariable Long id,
            @RequestBody @Valid UpdateSupplierCommand command) throws DomainException {
        MensajeDTO<SupplierResponse> response = supplierUseCase.updateSupplier(id, command);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MensajeDTO<SupplierResponse>> getSupplier(@PathVariable Long id) throws DomainException {
        return ResponseEntity.ok(supplierUseCase.getSupplier(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MensajeDTO<Void>> deleteSupplier(@PathVariable Long id) {
        supplierUseCase.deleteSupplier(id);
        return ResponseEntity.ok(new MensajeDTO<>(false, null));
    }

    @GetMapping
    public ResponseEntity<MensajeDTO<List<SupplierResponse>>> getAllSuppliers() {
        return ResponseEntity.ok(supplierUseCase.searchSupplier());
    }

    @GetMapping("/paged")
    public ResponseEntity<MensajeDTO<PageResponse<SupplierResponse>>> getPagedSuppliers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sort,
            @RequestParam(defaultValue = "asc") String direction,
            @RequestParam(required = false) String search) {
        return ResponseEntity.ok(supplierUseCase.getPagedSuppliers(page, size, sort, direction, search));
    }
}