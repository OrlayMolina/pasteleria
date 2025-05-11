package co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.adapter;

import co.edu.uniquindio.ing.soft.pasteleria.application.ports.output.CakeOrderPort;
import co.edu.uniquindio.ing.soft.pasteleria.domain.exception.DomainException;
import co.edu.uniquindio.ing.soft.pasteleria.domain.model.CakeOrder;
import co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.entity.CakeOrderEntity;
import co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.entity.UserEntity;
import co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.mapper.CakeOrderPersistenceMapper;
import co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.repository.CakeOrderRepository;
import jakarta.persistence.criteria.Predicate;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class CakeOrderPersistenceAdapter implements CakeOrderPort {

    private final CakeOrderRepository cakeOrderJpaRepository;
    private final CakeOrderPersistenceMapper persistenceMapper;

    @Override
    public CakeOrder saveCakeOrder(CakeOrder cakeOrder) throws DomainException {
        CakeOrderEntity entityToSave = persistenceMapper.toEntity(cakeOrder);
        CakeOrderEntity savedEntity = cakeOrderJpaRepository.save(entityToSave);
        return persistenceMapper.toDomain(savedEntity);
    }

    @Override
    public CakeOrder updateCakeOrder(CakeOrder cakeOrder) throws DomainException {
        CakeOrderEntity existingEntity = cakeOrderJpaRepository.findById(cakeOrder.getId())
                .orElseThrow(() -> new DomainException("Orden no encontrada"));

        existingEntity.setOrderStatus(cakeOrder.getOrderStatus());
        existingEntity.setHasInventoryAlert(cakeOrder.getHasInventoryAlert());
        existingEntity.setInventoryAlertDetails(cakeOrder.getInventoryAlertDetails());
        existingEntity.setUpdatedAt(cakeOrder.getUpdatedAt());

        if (cakeOrder.getModifiedById() != null) {
            UserEntity userEntity = new UserEntity();
            userEntity.setId(cakeOrder.getModifiedById());
            existingEntity.setModifiedBy(userEntity);
        }

        CakeOrderEntity updatedEntity = cakeOrderJpaRepository.save(existingEntity);
        return persistenceMapper.toDomain(updatedEntity);
    }

    @Override
    public Optional<CakeOrder> findCakeOrderById(Long id) {
        return cakeOrderJpaRepository.findById(id)
                .map(entity -> {
                    try {
                        return persistenceMapper.toDomain(entity);
                    } catch (DomainException e) {
                        return null;
                    }
                });
    }

    @Override
    public void deleteCakeOrderById(Long id) {
        cakeOrderJpaRepository.deleteById(id);
    }

    @Override
    public List<CakeOrder> findAllCakeOrders() {
        return cakeOrderJpaRepository.findAll().stream()
                .map(entity -> {
                    try {
                        return persistenceMapper.toDomain(entity);
                    } catch (DomainException e) {
                        return null;
                    }
                })
                .filter(order -> order != null)
                .collect(Collectors.toList());
    }

    @Override
    public Page<CakeOrder> findCakeOrdersWithPaginationAndSorting(int page, int size, String sortField, String sortDirection, String searchTerm) {
        Sort sort = Sort.unsorted();

        if (sortField != null && !sortField.isEmpty()) {
            Sort.Direction direction = sortDirection.equalsIgnoreCase("asc") ?
                    Sort.Direction.ASC : Sort.Direction.DESC;

            String fieldName = mapSortField(sortField);
            sort = Sort.by(direction, fieldName);
        }

        Pageable pageable = PageRequest.of(page, size, sort);

        if (searchTerm != null && !searchTerm.isEmpty()) {
            Specification<CakeOrderEntity> spec = createSearchSpecification(searchTerm);
            return cakeOrderJpaRepository.findAll(spec, pageable)
                    .map(entity -> {
                        try {
                            return persistenceMapper.toDomain(entity);
                        } catch (DomainException e) {
                            throw new RuntimeException("Error mapping cake order entity to domain", e);
                        }
                    });
        }

        return cakeOrderJpaRepository.findAll(pageable)
                .map(entity -> {
                    try {
                        return persistenceMapper.toDomain(entity);
                    } catch (DomainException e) {
                        throw new RuntimeException("Error mapping cake order entity to domain", e);
                    }
                });
    }

    @Override
    public boolean existsCakeOrderById(Long id) {
        return cakeOrderJpaRepository.existsById(id);
    }

    private String mapSortField(String uiField) {
        return switch (uiField.toLowerCase()) {
            case "id" -> "id";
            case "customerName", "cliente" -> "customerName";
            case "orderDate", "fechaorden" -> "orderDate";
            case "deliveryDate", "fechaentrega" -> "deliveryDate";
            case "orderStatus", "estado" -> "orderStatus";
            case "totalAmount", "total" -> "totalAmount";
            default -> "id";
        };
    }

    private Specification<CakeOrderEntity> createSearchSpecification(String searchTerm) {
        String term = "%" + searchTerm.toLowerCase() + "%";

        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Búsqueda por ID
            try {
                Long idValue = Long.parseLong(searchTerm.trim());
                predicates.add(criteriaBuilder.equal(root.get("id"), idValue));
            } catch (NumberFormatException ignored) {
            }

            // Búsqueda por nombre de cliente
            predicates.add(criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("customerName")), term
            ));

            // Búsqueda por teléfono
            predicates.add(criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("customerPhone")), term
            ));

            // Búsqueda por email
            predicates.add(criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("customerEmail")), term
            ));

            // Búsqueda por estado
            predicates.add(criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("orderStatus").as(String.class)), term
            ));

            // Búsqueda por fecha
            predicates.add(criteriaBuilder.like(
                    criteriaBuilder.function("to_char", String.class, root.get("orderDate"),
                            criteriaBuilder.literal("YYYY-MM-DD")),
                    term
            ));

            predicates.add(criteriaBuilder.like(
                    criteriaBuilder.function("to_char", String.class, root.get("deliveryDate"),
                            criteriaBuilder.literal("YYYY-MM-DD")),
                    term
            ));

            return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
        };
    }
}