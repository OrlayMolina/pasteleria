package co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.repository;

import co.edu.uniquindio.ing.soft.pasteleria.domain.enums.OrderStatus;
import co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.entity.CakeOrderDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CakeOrderDetailRepository extends JpaRepository<CakeOrderDetailEntity, Long> {

    List<CakeOrderDetailEntity> findByCakeOrderId(Long cakeOrderId);

    List<CakeOrderDetailEntity> findByRecipeId(Long recipeId);

    @Query("SELECT cod FROM CakeOrderDetailEntity cod JOIN cod.cakeOrder co WHERE co.orderStatus = :status")
    List<CakeOrderDetailEntity> findByOrderStatus(@Param("status") OrderStatus status);
}