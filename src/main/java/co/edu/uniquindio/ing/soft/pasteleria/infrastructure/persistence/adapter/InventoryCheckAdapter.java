package co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.adapter;

import co.edu.uniquindio.ing.soft.pasteleria.application.ports.output.InventoryCheckPort;
import co.edu.uniquindio.ing.soft.pasteleria.domain.exception.DomainException;
import co.edu.uniquindio.ing.soft.pasteleria.domain.model.Supply;
import co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.entity.SupplyEntity;
import co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.mapper.SupplyPersistenceMapper;
import co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.repository.SupplyJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class InventoryCheckAdapter implements InventoryCheckPort {

    private final SupplyJpaRepository supplyJpaRepository;
    private final SupplyPersistenceMapper supplyPersistenceMapper;

    @Override
    @Transactional
    public void reduceSupplyQuantity(Long supplyId, int quantity) throws DomainException {
        SupplyEntity supplyEntity = supplyJpaRepository.findById(supplyId)
                .orElseThrow(() -> new DomainException("Insumo no encontrado con ID: " + supplyId));

        int newQuantity = supplyEntity.getQuantity() - quantity;
        if (newQuantity < 0) {
            throw new DomainException("No hay suficiente cantidad de " + supplyEntity.getName() + " en el inventario.");
        }

        supplyEntity.setQuantity(newQuantity);
        supplyJpaRepository.save(supplyEntity);
    }

    @Override
    public boolean hasEnoughQuantity(Long supplyId, double requiredQuantity) throws DomainException {
        SupplyEntity supplyEntity = supplyJpaRepository.findById(supplyId)
                .orElseThrow(() -> new DomainException("Insumo no encontrado con ID: " + supplyId));

        return supplyEntity.getQuantity() >= requiredQuantity;
    }

    @Override
    public Supply getSupplyDetails(Long supplyId) throws DomainException {
        SupplyEntity supplyEntity = supplyJpaRepository.findById(supplyId)
                .orElseThrow(() -> new DomainException("Insumo no encontrado con ID: " + supplyId));

        return supplyPersistenceMapper.toDomain(supplyEntity);
    }
}