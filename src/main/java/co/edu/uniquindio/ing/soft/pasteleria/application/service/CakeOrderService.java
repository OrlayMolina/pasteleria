package co.edu.uniquindio.ing.soft.pasteleria.application.service;

import co.edu.uniquindio.ing.soft.pasteleria.application.dto.EmailDTO;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.MensajeDTO;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.request.CreateCakeOrderCommand;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.request.CreateCakeOrderDetailCommand;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.request.UpdateCakeOrderStatusCommand;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.response.*;
import co.edu.uniquindio.ing.soft.pasteleria.application.mapper.CakeOrderDtoMapper;
import co.edu.uniquindio.ing.soft.pasteleria.application.ports.input.ManageCakeOrderUseCase;
import co.edu.uniquindio.ing.soft.pasteleria.application.ports.input.ManageEmailUseCase;
import co.edu.uniquindio.ing.soft.pasteleria.application.ports.output.CakeOrderPort;
import co.edu.uniquindio.ing.soft.pasteleria.application.ports.output.InventoryCheckPort;
import co.edu.uniquindio.ing.soft.pasteleria.application.ports.output.RecipePort;
import co.edu.uniquindio.ing.soft.pasteleria.domain.enums.OrderStatus;
import co.edu.uniquindio.ing.soft.pasteleria.domain.exception.DomainException;
import co.edu.uniquindio.ing.soft.pasteleria.domain.model.CakeOrder;
import co.edu.uniquindio.ing.soft.pasteleria.domain.model.CakeOrderDetail;
import co.edu.uniquindio.ing.soft.pasteleria.domain.model.Recipe;
import co.edu.uniquindio.ing.soft.pasteleria.domain.model.RecipeSupply;
import co.edu.uniquindio.ing.soft.pasteleria.domain.model.Supply;
import co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.adapter.config.EmailTemplateConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CakeOrderService implements ManageCakeOrderUseCase {

    private final CakeOrderPort cakeOrderPort;
    private final CakeOrderDtoMapper cakeOrderDtoMapper;
    private final RecipePort recipePort;
    private final InventoryCheckPort inventoryCheckPort;
    private final ManageEmailUseCase emailService;

    @Override
    public MensajeDTO<CakeOrderResponse> createCakeOrder(CreateCakeOrderCommand command) throws DomainException {
        try {
            // Verificar inventario antes de crear la orden
            InventoryCheckResponse inventoryCheck = checkInventoryForOrder(command).respuesta();

            // Convertir el comando a modelo de dominio
            CakeOrder cakeOrder = cakeOrderDtoMapper.toModel(command);

            // Configurar alertas de inventario si existen
            cakeOrder.setHasInventoryAlert(inventoryCheck.hasInventoryAlert());
            cakeOrder.setInventoryAlertDetails(inventoryCheck.alertMessage());

            // Si está en estado PENDIENTE, verificar si hay suficiente inventario
            if (command.orderStatus() == OrderStatus.PENDING && !inventoryCheck.hasInventoryAlert()) {
                cakeOrder.setOrderStatus(OrderStatus.CONFIRMED);
            } else {
                cakeOrder.setOrderStatus(command.orderStatus());
            }

            // Guardar la orden
            CakeOrder savedOrder = cakeOrderPort.saveCakeOrder(cakeOrder);

            // Crear y guardar los detalles de la orden
            List<CakeOrderDetail> details = cakeOrderDtoMapper.toOrderDetailModels(command.orderDetails(), savedOrder.getId());

            // Si la orden está confirmada, reducir el inventario
            if (savedOrder.getOrderStatus() == OrderStatus.CONFIRMED) {
                reduceInventory(command);
            }

            // Si hay alertas de inventario, enviar correo de notificación
            if (inventoryCheck.hasInventoryAlert()) {
                sendInventoryAlert(savedOrder, inventoryCheck);
            }

            // Mapear la respuesta
            CakeOrderResponse response = cakeOrderDtoMapper.toResponse(savedOrder, details);
            return new MensajeDTO<>(false, response);

        } catch (Exception e) {
            return new MensajeDTO<>(true, null);
        }
    }

    @Override
    public MensajeDTO<CakeOrderResponse> updateCakeOrderStatus(Long id, UpdateCakeOrderStatusCommand command) throws DomainException {
        try {
            Optional<CakeOrder> optionalOrder = cakeOrderPort.findCakeOrderById(id);
            if (optionalOrder.isEmpty()) {
                throw new DomainException("La orden con id " + id + " no existe.");
            }

            CakeOrder existingOrder = optionalOrder.get();
            OrderStatus oldStatus = existingOrder.getOrderStatus();
            existingOrder.setOrderStatus(command.orderStatus());
            existingOrder.setUpdatedAt(command.updatedAt());
            existingOrder.setModifiedById(command.modifiedById());

            // Si la orden pasa de PENDIENTE a CONFIRMADA, verificar el inventario
            if (oldStatus == OrderStatus.PENDING && command.orderStatus() == OrderStatus.CONFIRMED) {
                // Obtener los detalles de la orden y verificar inventario
                // Este código es simplificado, necesitarías obtener los detalles reales
                if (existingOrder.getHasInventoryAlert()) {
                    throw new DomainException("No se puede confirmar la orden debido a alertas de inventario.");
                }

                // Reducir el inventario (implementación simplificada)
                // Necesitarías implementar cómo obtener los detalles y recetas
            }

            CakeOrder updatedOrder = cakeOrderPort.updateCakeOrder(existingOrder);

            // Mapear la respuesta (implementación simplificada)
            List<CakeOrderDetail> details = new ArrayList<>(); // Deberías obtener los detalles reales
            CakeOrderResponse response = cakeOrderDtoMapper.toResponse(updatedOrder, details);
            return new MensajeDTO<>(false, response);

        } catch (DomainException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar el estado de la orden: " + e.getMessage(), e);
        }
    }

    @Override
    public MensajeDTO<Void> deleteCakeOrder(Long id) throws DomainException {
        if (!cakeOrderPort.existsCakeOrderById(id)) {
            throw new DomainException("La orden con id " + id + " no existe.");
        }

        try {
            cakeOrderPort.deleteCakeOrderById(id);
            return new MensajeDTO<>(false, null);
        } catch (Exception e) {
            throw new DomainException("Error al eliminar la orden: " + e.getMessage());
        }
    }

    @Override
    public MensajeDTO<CakeOrderResponse> getCakeOrder(Long id) throws DomainException {
        Optional<CakeOrder> optionalOrder = cakeOrderPort.findCakeOrderById(id);
        if (optionalOrder.isEmpty()) {
            throw new DomainException("La orden con id " + id + " no existe.");
        }

        CakeOrder order = optionalOrder.get();

        // Obtener los detalles de la orden (esta implementación depende de tu estructura)
        // Aquí asumo que necesitarías obtener los detalles desde otro puerto
        List<CakeOrderDetail> details = new ArrayList<>(); // Placeholder, implementa según tu estructura

        CakeOrderResponse response = cakeOrderDtoMapper.toResponse(order, details);
        return new MensajeDTO<>(false, response);
    }

    @Override
    public MensajeDTO<List<CakeOrderSummaryResponse>> getAllCakeOrders() {
        try {
            List<CakeOrder> orders = cakeOrderPort.findAllCakeOrders();
            List<CakeOrderSummaryResponse> responseList = orders.stream()
                    .map(order -> {
                        // Asumiendo que necesitas contar los detalles de cada orden
                        int totalItems = countOrderItems(order.getId());
                        return cakeOrderDtoMapper.toSummaryResponse(order, totalItems);
                    })
                    .collect(Collectors.toList());

            return new MensajeDTO<>(false, responseList);
        } catch (Exception e) {
            return new MensajeDTO<>(true, List.of());
        }
    }

    @Override
    public MensajeDTO<PageResponse<CakeOrderSummaryResponse>> getPagedCakeOrders(int page, int size, String sort, String direction, String search) {
        try {
            Page<CakeOrder> ordersPage = cakeOrderPort.findCakeOrdersWithPaginationAndSorting(page, size, sort, direction, search);

            List<CakeOrderSummaryResponse> items = ordersPage.getContent().stream()
                    .map(order -> {
                        int totalItems = countOrderItems(order.getId());
                        return cakeOrderDtoMapper.toSummaryResponse(order, totalItems);
                    })
                    .collect(Collectors.toList());

            PageResponse<CakeOrderSummaryResponse> pageResponse = new PageResponse<>(
                    items,
                    ordersPage.getNumber(),
                    ordersPage.getSize(),
                    ordersPage.getTotalElements(),
                    ordersPage.getTotalPages(),
                    ordersPage.isLast()
            );

            return new MensajeDTO<>(false, pageResponse);
        } catch (Exception e) {
            return new MensajeDTO<>(true, null);
        }
    }

    @Override
    public MensajeDTO<InventoryCheckResponse> checkInventoryForOrder(CreateCakeOrderCommand command) throws DomainException {
        List<SupplyAlertDetail> alertDetails = new ArrayList<>();
        StringBuilder alertMessage = new StringBuilder();
        boolean hasAlert = false;

        Map<Long, Double> totalSupplyRequirements = calculateTotalSupplyRequirements(command);

        for (Map.Entry<Long, Double> entry : totalSupplyRequirements.entrySet()) {
            Long supplyId = entry.getKey();
            Double requiredQuantity = entry.getValue();

            try {
                Supply supply = inventoryCheckPort.getSupplyDetails(supplyId);
                boolean hasEnough = inventoryCheckPort.hasEnoughQuantity(supplyId, requiredQuantity);
                boolean belowMinimum = (supply.getQuantity() - requiredQuantity) < supply.getMinimumStock();

                if (!hasEnough || belowMinimum) {
                    hasAlert = true;

                    SupplyAlertDetail detail = new SupplyAlertDetail(
                            supply.getName(),
                            requiredQuantity.intValue(),
                            supply.getQuantity(),
                            supply.getMinimumStock(),
                            belowMinimum,
                            !hasEnough
                    );

                    alertDetails.add(detail);

                    if (!hasEnough) {
                        alertMessage.append("• ").append(supply.getName())
                                .append(": Inventario insuficiente (Requerido: ")
                                .append(requiredQuantity.intValue())
                                .append(", Disponible: ")
                                .append(supply.getQuantity())
                                .append(")\n");
                    } else if (belowMinimum) {
                        alertMessage.append("• ").append(supply.getName())
                                .append(": Stock por debajo del mínimo después de la orden (Mínimo: ")
                                .append(supply.getMinimumStock())
                                .append(", Quedará: ")
                                .append(supply.getQuantity() - requiredQuantity.intValue())
                                .append(")\n");
                    }
                }
            } catch (DomainException e) {
                alertMessage.append("• Error verificando insumo ID ").append(supplyId).append(": ")
                        .append(e.getMessage()).append("\n");
                hasAlert = true;
            }
        }

        String finalAlertMessage = hasAlert ?
                "Se detectaron alertas de inventario en la orden:\n" + alertMessage.toString() :
                null;

        InventoryCheckResponse response = new InventoryCheckResponse(
                hasAlert,
                finalAlertMessage,
                alertDetails
        );

        return new MensajeDTO<>(false, response);
    }

    private Map<Long, Double> calculateTotalSupplyRequirements(CreateCakeOrderCommand command) throws DomainException {
        Map<Long, Double> totalSuppliesRequired = new HashMap<>();

        for (CreateCakeOrderDetailCommand detail : command.orderDetails()) {
            Optional<Recipe> optionalRecipe = recipePort.findRecipeById(detail.recipeId());
            if (optionalRecipe.isEmpty()) {
                throw new DomainException("La receta con id " + detail.recipeId() + " no existe.");
            }

            List<RecipeSupply> recipeSupplies = recipePort.findSuppliesByRecipeId(detail.recipeId());

            for (RecipeSupply recipeSupply : recipeSupplies) {
                Long supplyId = recipeSupply.getSupplyId();
                Double quantityPerRecipe = recipeSupply.getQuantity();
                Double totalQuantityRequired = quantityPerRecipe * detail.quantity();

                totalSuppliesRequired.merge(supplyId, totalQuantityRequired, Double::sum);
            }
        }

        return totalSuppliesRequired;
    }

    private void reduceInventory(CreateCakeOrderCommand command) throws DomainException {
        Map<Long, Double> totalSupplyRequirements = calculateTotalSupplyRequirements(command);

        for (Map.Entry<Long, Double> entry : totalSupplyRequirements.entrySet()) {
            Long supplyId = entry.getKey();
            Double requiredQuantity = entry.getValue();

            inventoryCheckPort.reduceSupplyQuantity(supplyId, requiredQuantity.intValue());
        }
    }

    private void sendInventoryAlert(CakeOrder order, InventoryCheckResponse inventoryCheck) {
        try {
            // Crear tabla de detalles para el correo
            StringBuilder detallesAlerta = new StringBuilder();
            for (SupplyAlertDetail detail : inventoryCheck.alertDetails()) {
                String estado = detail.outOfStock() ? "SIN STOCK" : (detail.belowMinimumStock() ? "BAJO MÍNIMO" : "OK");

                detallesAlerta.append("<tr>")
                        .append("<td>").append(detail.supplyName()).append("</td>")
                        .append("<td>").append(detail.requiredQuantity()).append("</td>")
                        .append("<td>").append(detail.availableQuantity()).append("</td>")
                        .append("<td>").append(estado).append("</td>")
                        .append("</tr>");
            }

            // Reemplazar placeholders en el template
            String body = EmailTemplateConfig.bodyAlertaInventario
                    .replace("[Destinatario]", "Administrador de Inventario")
                    .replace("[NumeroOrden]", order.getId().toString())
                    .replace("[MensajeAlerta]", inventoryCheck.alertMessage())
                    .replace("[DetallesAlerta]", detallesAlerta.toString())
                    .replace("[EstadoOrden]", order.getOrderStatus().toString());

            // Enviar correo
            emailService.enviarCorreo(new EmailDTO(
                    "Alerta de Inventario - Orden #" + order.getId(),
                    body,
                    "inventario@pasteleria.com" // Reemplazar con el correo real del administrador
            ));

        } catch (Exception e) {
            // Logear error pero no interrumpir el proceso
            System.err.println("Error al enviar alerta de inventario: " + e.getMessage());
        }
    }

    private int countOrderItems(Long orderId) {
        // Implementar según tu estructura de datos
        // Este método debería contar cuántos ítems hay en total en la orden
        return 0; // Placeholder
    }
}