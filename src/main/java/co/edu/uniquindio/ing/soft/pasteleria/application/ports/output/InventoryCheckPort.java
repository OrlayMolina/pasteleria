package co.edu.uniquindio.ing.soft.pasteleria.application.ports.output;

import co.edu.uniquindio.ing.soft.pasteleria.domain.model.Supply;
import co.edu.uniquindio.ing.soft.pasteleria.domain.exception.DomainException;

public interface InventoryCheckPort {
    void reduceSupplyQuantity(Long supplyId, int quantity) throws DomainException;

    boolean hasEnoughQuantity(Long supplyId, double requiredQuantity) throws DomainException;

    Supply getSupplyDetails(Long supplyId) throws DomainException;
}