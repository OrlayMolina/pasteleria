package co.edu.uniquindio.ing.soft.pasteleria.application.ports.input;

import co.edu.uniquindio.ing.soft.pasteleria.application.dto.MensajeDTO;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.request.CreateCakeOrderCommand;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.request.UpdateCakeOrderStatusCommand;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.response.CakeOrderResponse;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.response.CakeOrderSummaryResponse;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.response.InventoryCheckResponse;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.response.PageResponse;
import co.edu.uniquindio.ing.soft.pasteleria.domain.exception.DomainException;

import java.util.List;

public interface ManageCakeOrderUseCase {
    MensajeDTO<CakeOrderResponse> createCakeOrder(CreateCakeOrderCommand command) throws DomainException;

    MensajeDTO<CakeOrderResponse> updateCakeOrderStatus(Long id, UpdateCakeOrderStatusCommand command) throws DomainException;

    MensajeDTO<Void> deleteCakeOrder(Long id) throws DomainException;

    MensajeDTO<CakeOrderResponse> getCakeOrder(Long id) throws DomainException;

    MensajeDTO<List<CakeOrderSummaryResponse>> getAllCakeOrders();

    MensajeDTO<PageResponse<CakeOrderSummaryResponse>> getPagedCakeOrders(int page, int size, String sort, String direction, String search);

    MensajeDTO<InventoryCheckResponse> checkInventoryForOrder(CreateCakeOrderCommand command) throws DomainException;
}