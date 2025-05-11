package co.edu.uniquindio.ing.soft.pasteleria.application.mapper;

import co.edu.uniquindio.ing.soft.pasteleria.application.dto.request.CreateCakeOrderCommand;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.request.CreateCakeOrderDetailCommand;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.response.CakeOrderDetailResponse;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.response.CakeOrderResponse;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.response.CakeOrderSummaryResponse;
import co.edu.uniquindio.ing.soft.pasteleria.application.ports.output.RecipePort;
import co.edu.uniquindio.ing.soft.pasteleria.domain.exception.DomainException;
import co.edu.uniquindio.ing.soft.pasteleria.domain.model.CakeOrder;
import co.edu.uniquindio.ing.soft.pasteleria.domain.model.CakeOrderDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CakeOrderDtoMapper {

    private final RecipePort recipePort;

    public CakeOrder toModel(CreateCakeOrderCommand command) {
        CakeOrder cakeOrder = new CakeOrder(
                LocalDateTime.now(),
                command.deliveryDate(),
                command.customerName(),
                command.customerPhone(),
                command.customerEmail(),
                command.orderStatus(),
                calculateTotalAmount(command.orderDetails()),
                false,
                null,
                LocalDateTime.now(),
                LocalDateTime.now(),
                command.createdById(),
                null
        );

        return cakeOrder;
    }

    public List<CakeOrderDetail> toOrderDetailModels(List<CreateCakeOrderDetailCommand> detailCommands, Long orderId) {
        return detailCommands.stream()
                .map(detail -> new CakeOrderDetail(
                        orderId,
                        detail.recipeId(),
                        detail.quantity(),
                        detail.unitPrice(),
                        detail.unitPrice().multiply(BigDecimal.valueOf(detail.quantity())),
                        detail.specialInstructions(),
                        LocalDateTime.now(),
                        LocalDateTime.now()
                ))
                .collect(Collectors.toList());
    }

    public CakeOrderResponse toResponse(CakeOrder cakeOrder, List<CakeOrderDetail> details) throws DomainException {
        List<CakeOrderDetailResponse> detailResponses = details.stream()
                .map(detail -> {
                    try {
                        String recipeName = recipePort.getRecipeNameById(detail.getRecipeId());
                        return new CakeOrderDetailResponse(
                                detail.getId(),
                                detail.getRecipeId(),
                                recipeName,
                                detail.getQuantity(),
                                detail.getUnitPrice(),
                                detail.getSubtotal(),
                                detail.getSpecialInstructions(),
                                detail.getCreatedAt(),
                                detail.getUpdatedAt()
                        );
                    } catch (DomainException e) {
                        throw new RuntimeException("Error al obtener el nombre de la receta", e);
                    }
                })
                .collect(Collectors.toList());

        return new CakeOrderResponse(
                cakeOrder.getId(),
                cakeOrder.getOrderDate(),
                cakeOrder.getDeliveryDate(),
                cakeOrder.getCustomerName(),
                cakeOrder.getCustomerPhone(),
                cakeOrder.getCustomerEmail(),
                cakeOrder.getOrderStatus(),
                cakeOrder.getTotalAmount(),
                cakeOrder.getHasInventoryAlert(),
                cakeOrder.getInventoryAlertDetails(),
                cakeOrder.getCreatedAt(),
                cakeOrder.getUpdatedAt(),
                cakeOrder.getCreatedById(),
                cakeOrder.getModifiedById(),
                detailResponses
        );
    }

    public CakeOrderSummaryResponse toSummaryResponse(CakeOrder cakeOrder, int totalItems) {
        return new CakeOrderSummaryResponse(
                cakeOrder.getId(),
                cakeOrder.getCustomerName(),
                cakeOrder.getOrderDate(),
                cakeOrder.getDeliveryDate(),
                cakeOrder.getOrderStatus(),
                cakeOrder.getTotalAmount(),
                cakeOrder.getHasInventoryAlert(),
                totalItems
        );
    }

    private BigDecimal calculateTotalAmount(List<CreateCakeOrderDetailCommand> details) {
        return details.stream()
                .map(detail -> detail.unitPrice().multiply(BigDecimal.valueOf(detail.quantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}