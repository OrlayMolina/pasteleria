package co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.adapter;

import co.edu.uniquindio.ing.soft.pasteleria.application.ports.output.SupplyPort;
import co.edu.uniquindio.ing.soft.pasteleria.domain.exception.DomainException;
import co.edu.uniquindio.ing.soft.pasteleria.domain.model.Supply;
import co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.entity.SupplyEntity;
import co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.entity.UserEntity;
import co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.mapper.SupplyPersistenceMapper;
import co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.repository.SupplierJpaRepository;
import co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.repository.SupplyJpaRepository;
import jakarta.persistence.criteria.Predicate;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class SupplyPersistenceAdapter implements SupplyPort {
    private final SupplyJpaRepository supplyJpaRepository;
    private final SupplierJpaRepository supplierJpaRepository;
    private final SupplyPersistenceMapper persistenceMapper;

    @Override
    public Supply saveSupply(Supply supply) throws DomainException {
        SupplyEntity entityToSave = persistenceMapper.toEntity(supply);
        SupplyEntity savedEntity = supplyJpaRepository.save(entityToSave);
        return persistenceMapper.toDomain(savedEntity);
    }

    @Override
    public Supply updateSupply(Supply supply) throws DomainException {
        SupplyEntity existingEntity = supplyJpaRepository.findById(supply.getId())
                .orElseThrow(() -> new DomainException("Insumo no encontrado"));

        existingEntity.setName(supply.getName());
        existingEntity.setPrice(supply.getPrice());
        existingEntity.setEntryDate(supply.getEntryDate());
        existingEntity.setExpirationDate(supply.getExpirationDate());
        existingEntity.setQuantity(supply.getQuantity());
        existingEntity.setMinimumStock(supply.getMinimumStock());
        existingEntity.setUpdatedAt(supply.getUpdatedAt());

        if (supply.getUserModify() != null) {
            UserEntity userEntity = new UserEntity();
            userEntity.setId(supply.getUserModify());
            existingEntity.setUserModify(userEntity);
        }

        SupplyEntity updatedEntity = supplyJpaRepository.save(existingEntity);
        return persistenceMapper.toDomain(updatedEntity);
    }

    @Override
    public Optional<Supply> findSupplyById(Long id) {
        return supplyJpaRepository.findById(id)
                .map(entity -> {
                    try {
                        return persistenceMapper.toDomain(entity);
                    } catch (DomainException e) {
                        return null;
                    }
                });
    }

    @Override
    public void deleteSupplyById(Long id) {
        supplyJpaRepository.deleteById(id);
    }

    @Override
    public List<Supply> findAllSupplies() {
        return supplyJpaRepository.findAll().stream()
                .map(entity -> {
                    try {
                        return persistenceMapper.toDomain(entity);
                    } catch (DomainException e) {
                        return null;
                    }
                })
                .filter(supply -> supply != null)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsSupplyById(Long id) {
        return supplyJpaRepository.existsById(id);
    }

    @Override
    public Optional<SupplyEntity> existsSupplyBySupplierDocument(String supplierDocument) {
        return supplyJpaRepository.findBySupplierDocument(supplierDocument);
    }

    @Override
    public Page<Supply> findSuppliesWithPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<SupplyEntity> suppliesPage = supplyJpaRepository.findAll(pageable);
        return suppliesPage.map(entity -> {
            try {
                return persistenceMapper.toDomain(entity);
            } catch (DomainException e) {
                throw new RuntimeException("Error mapping supply entity to domain", e);
            }
        });
    }

    @Override
    public Page<Supply> findSuppliesWithPaginationAndSorting(int page, int size, String sortField, String sortDirection, String searchTerm) {
        Sort sort = Sort.unsorted();

        if (sortField != null && !sortField.isEmpty()) {
            Sort.Direction direction = sortDirection.equalsIgnoreCase("asc") ?
                    Sort.Direction.ASC : Sort.Direction.DESC;

            String fieldName = mapSortField(sortField);
            sort = Sort.by(direction, fieldName);
        }

        Pageable pageable = PageRequest.of(page, size, sort);

        if (searchTerm != null && !searchTerm.isEmpty()) {
            Specification<SupplyEntity> spec = createSearchSpecification(searchTerm);
            return supplyJpaRepository.findAll(spec, pageable)
                    .map(entity -> {
                        try {
                            return persistenceMapper.toDomain(entity);
                        } catch (DomainException e) {
                            throw new RuntimeException("Error mapping supply entity to domain", e);
                        }
                    });
        }

        return supplyJpaRepository.findAll(pageable)
                .map(entity -> {
                    try {
                        return persistenceMapper.toDomain(entity);
                    } catch (DomainException e) {
                        throw new RuntimeException("Error mapping supply entity to domain", e);
                    }
                });
    }

    // Método auxiliar para mapear campos de ordenamiento desde la UI a la entidad
    private String mapSortField(String uiField) {
        return switch (uiField.toLowerCase()) {
            case "id" -> "id";
            case "name", "nombre" -> "name";
            case "price", "precio" -> "price";
            case "entrydate", "fechaentrada" -> "entryDate";
            case "expirydate", "expirationdate", "fechavencimiento" -> "expirationDate";
            case "quantity", "cantidad" -> "quantity";
            default -> "id";
        };
    }

    // Método para crear la especificación de búsqueda
    private Specification<SupplyEntity> createSearchSpecification(String searchTerm) {
        String term = "%" + searchTerm.toLowerCase() + "%";

        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), term));

            try {
                Long idValue = Long.parseLong(searchTerm.trim());
                predicates.add(criteriaBuilder.equal(root.get("id"), idValue));
            } catch (NumberFormatException e) {

            }

            try {
                BigDecimal priceValue = new BigDecimal(searchTerm.trim());
                predicates.add(criteriaBuilder.equal(root.get("price"), priceValue));
            } catch (NumberFormatException ignored) {

            }

            try {
                Integer quantityValue = Integer.parseInt(searchTerm.trim());
                predicates.add(criteriaBuilder.equal(root.get("quantity"), quantityValue));
            } catch (NumberFormatException e) {

            }

            predicates.add(criteriaBuilder.like(
                    criteriaBuilder.function("to_char", String.class, root.get("entryDate"),
                            criteriaBuilder.literal("YYYY-MM-DD")),
                    term
            ));

            predicates.add(criteriaBuilder.like(
                    criteriaBuilder.function("to_char", String.class, root.get("expirationDate"),
                            criteriaBuilder.literal("YYYY-MM-DD")),
                    term
            ));

            return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
        };
    }
}