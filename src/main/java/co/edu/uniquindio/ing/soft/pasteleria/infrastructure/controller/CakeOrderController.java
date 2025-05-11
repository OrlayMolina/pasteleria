package co.edu.uniquindio.ing.soft.pasteleria.infrastructure.controller;

import co.edu.uniquindio.ing.soft.pasteleria.application.dto.MensajeDTO;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.request.CreateCakeOrderCommand;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.request.UpdateCakeOrderStatusCommand;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.response.CakeOrderResponse;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.response.CakeOrderSummaryResponse;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.response.InventoryCheckResponse;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.response.PageResponse;
import co.edu.uniquindio.ing.soft.pasteleria.application.ports.input.ManageCakeOrderUseCase;
import co.edu.uniquindio.ing.soft.pasteleria.domain.exception.DomainException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cake-orders")
@AllArgsConstructor
public class CakeOrderController {

    private final ManageCakeOrderUseCase cakeOrderUseCase;

    @PostMapping
    public ResponseEntity<MensajeDTO<CakeOrderResponse>> createCakeOrder(@RequestBody @Valid CreateCakeOrderCommand command) throws DomainException {
        MensajeDTO<CakeOrderResponse> response = cakeOrderUseCase.createCakeOrder(command);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<MensajeDTO<CakeOrderResponse>> updateCakeOrderStatus(
            @PathVariable Long id,
            @RequestBody @Valid UpdateCakeOrderStatusCommand command) throws DomainException {
        MensajeDTO<CakeOrderResponse> response = cakeOrderUseCase.updateCakeOrderStatus(id, command);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MensajeDTO<CakeOrderResponse>> getCakeOrder(@PathVariable Long id) throws DomainException {
        return ResponseEntity.ok(cakeOrderUseCase.getCakeOrder(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MensajeDTO<Void>> deleteCakeOrder(@PathVariable Long id) throws DomainException {
        return ResponseEntity.ok(cakeOrderUseCase.deleteCakeOrder(id));
    }

    @GetMapping
    public ResponseEntity<MensajeDTO<List<CakeOrderSummaryResponse>>> getAllCakeOrders() {
        return ResponseEntity.ok(cakeOrderUseCase.getAllCakeOrders());
    }

    @GetMapping("/paged")
    public ResponseEntity<MensajeDTO<PageResponse<CakeOrderSummaryResponse>>> getPagedCakeOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sort,
            @RequestParam(defaultValue = "asc") String direction,
            @RequestParam(required = false) String search) {
        return ResponseEntity.ok(cakeOrderUseCase.getPagedCakeOrders(page, size, sort, direction, search));
    }

    @PostMapping("/check-inventory")
    public ResponseEntity<MensajeDTO<InventoryCheckResponse>> checkInventoryForOrder(
            @RequestBody @Valid CreateCakeOrderCommand command) throws DomainException {
        return ResponseEntity.ok(cakeOrderUseCase.checkInventoryForOrder(command));
    }
}