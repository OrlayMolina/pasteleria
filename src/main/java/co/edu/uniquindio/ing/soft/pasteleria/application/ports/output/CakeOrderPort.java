package co.edu.uniquindio.ing.soft.pasteleria.application.ports.output;

import co.edu.uniquindio.ing.soft.pasteleria.domain.exception.DomainException;
import co.edu.uniquindio.ing.soft.pasteleria.domain.model.CakeOrder;
import co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.entity.CakeOrderEntity;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface CakeOrderPort {
    CakeOrder saveCakeOrder(CakeOrder cakeOrder) throws DomainException;

    CakeOrder updateCakeOrder(CakeOrder cakeOrder) throws DomainException;

    Optional<CakeOrder> findCakeOrderById(Long id);

    void deleteCakeOrderById(Long id);

    List<CakeOrder> findAllCakeOrders();

    Page<CakeOrder> findCakeOrdersWithPaginationAndSorting(int page, int size, String sortField, String sortDirection, String searchTerm);

    boolean existsCakeOrderById(Long id);
}