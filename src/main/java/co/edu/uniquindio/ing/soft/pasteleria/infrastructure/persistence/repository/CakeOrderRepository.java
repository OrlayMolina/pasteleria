package co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.repository;

import co.edu.uniquindio.ing.soft.pasteleria.domain.enums.OrderStatus;
import co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.entity.CakeOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CakeOrderRepository extends JpaRepository<CakeOrderEntity, Long> {

    List<CakeOrderEntity> findByOrderStatus(OrderStatus orderStatus);

    List<CakeOrderEntity> findByCustomerNameContainingIgnoreCase(String customerName);

    List<CakeOrderEntity> findByOrderDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    List<CakeOrderEntity> findByDeliveryDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT co FROM CakeOrderEntity co WHERE co.hasInventoryAlert = true")
    List<CakeOrderEntity> findAllWithInventoryAlerts();
}