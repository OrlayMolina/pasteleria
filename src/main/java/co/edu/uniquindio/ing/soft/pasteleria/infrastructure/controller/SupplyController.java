package co.edu.uniquindio.ing.soft.pasteleria.infrastructure.controller;

import co.edu.uniquindio.ing.soft.pasteleria.application.dto.MensajeDTO;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.request.CreateSupplyCommand;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.request.UpdateSupplyCommand;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.response.PageResponse;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.response.SupplyInfoResponse;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.response.SupplyResponse;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.response.SupplyStockResponse;
import co.edu.uniquindio.ing.soft.pasteleria.application.ports.input.ManageSupplyUseCase;
import co.edu.uniquindio.ing.soft.pasteleria.domain.exception.DomainException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/supplies")
@AllArgsConstructor
public class SupplyController {
    private final ManageSupplyUseCase supplyUseCase;

    @PostMapping
    public ResponseEntity<MensajeDTO<String>> createSupply(@RequestBody @Valid CreateSupplyCommand command) throws DomainException {
        MensajeDTO<String> response = supplyUseCase.createSupply(command);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MensajeDTO<SupplyResponse>> updateSupply(
            @PathVariable Long id,
            @RequestBody @Valid UpdateSupplyCommand command) throws DomainException {
        MensajeDTO<SupplyResponse> response = supplyUseCase.updateSupply(id, command);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MensajeDTO<SupplyResponse>> getSupply(@PathVariable Long id) throws DomainException {
        return ResponseEntity.ok(supplyUseCase.getSupply(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MensajeDTO<Void>> deleteSupply(@PathVariable Long id) {
        supplyUseCase.deleteSupply(id);
        return ResponseEntity.ok(new MensajeDTO<>(false, null));
    }

    @GetMapping
    public ResponseEntity<MensajeDTO<List<SupplyResponse>>> getAllSupplies() {
        return ResponseEntity.ok(supplyUseCase.searchSupply());
    }

    @GetMapping("/{id}/info")
    public ResponseEntity<MensajeDTO<SupplyInfoResponse>> getSupplyInfo(@PathVariable Long id) throws DomainException {
        return ResponseEntity.ok(supplyUseCase.getSupplyInfo(id));
    }

    @GetMapping("/{id}/stock")
    public ResponseEntity<MensajeDTO<SupplyStockResponse>> getSupplyStock(@PathVariable Long id) throws DomainException {
        return ResponseEntity.ok(supplyUseCase.getSupplyStock(id));
    }

    @GetMapping("/paged")
    public ResponseEntity<MensajeDTO<PageResponse<SupplyResponse>>> getPagedSupplies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sort,
            @RequestParam(defaultValue = "asc") String direction,
            @RequestParam(required = false) String search) {
        return ResponseEntity.ok(supplyUseCase.getPagedSupplies(page, size, sort, direction, search));
    }
}