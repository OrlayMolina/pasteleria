package co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.adapter;

import co.edu.uniquindio.ing.soft.pasteleria.application.ports.output.SupplierPort;
import co.edu.uniquindio.ing.soft.pasteleria.domain.exception.DomainException;
import co.edu.uniquindio.ing.soft.pasteleria.domain.model.Supplier;
import co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.entity.SupplierEntity;
import co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.mapper.SupplierPersistenceMapper;
import co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.repository.SupplierJpaRepository;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Component
@AllArgsConstructor
public class SupplierPersistenceAdapter implements SupplierPort {

    private final SupplierJpaRepository supplierJpaRepository;
    private final SupplierPersistenceMapper persistenceMapper;

    @Override
    public Supplier saveSupplier(Supplier supplier) throws DomainException {
        SupplierEntity entity = persistenceMapper.toEntity(supplier);
        SupplierEntity savedEntity = supplierJpaRepository.save(entity);
        return persistenceMapper.toDomain(savedEntity);
    }

    @SneakyThrows
    @Override
    public Optional<Supplier> findSupplierById(Long id) {
        return supplierJpaRepository.findById(id)
                .map(entity -> {
                    try {
                        return persistenceMapper.toDomain(entity);
                    } catch (DomainException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    @Override
    public void deleteSupplierById(Long id) {
        supplierJpaRepository.deleteById(id);
    }

    @SneakyThrows
    @Override
    public List<Supplier> findAllSuppliers() {
        return supplierJpaRepository.findAll().stream()
                .map(entity -> {
                    try {
                        return persistenceMapper.toDomain(entity);
                    } catch (DomainException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsSupplierById(Long id) {
        return supplierJpaRepository.existsById(id);
    }

    @Override
    public boolean existsSupplierBySupplierID(String supplierID) {
        return supplierJpaRepository.findBySupplierID(supplierID).isPresent();
    }

    @Override
    public Page<Supplier> findSuppliersWithPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<SupplierEntity> suppliersPage = supplierJpaRepository.findAll(pageable);
        return suppliersPage.map(entity -> {
            try {
                return persistenceMapper.toDomain(entity);
            } catch (DomainException e) {
                throw new RuntimeException("Error mapping supplier entity to domain", e);
            }
        });
    }

    @Override
    public Page<Supplier> findSuppliersWithPaginationAndSorting(int page, int size, String sortField, String sortDirection, String searchTerm) {
        Sort sort = Sort.unsorted();

        // Aplicar ordenamiento si se especifica un campo
        if (sortField != null && !sortField.isEmpty()) {
            Sort.Direction direction = sortDirection.equalsIgnoreCase("asc") ?
                    Sort.Direction.ASC : Sort.Direction.DESC;

            // Mapear los nombres de columna del frontend a los campos reales en la entidad
            String fieldName = mapSortField(sortField);
            sort = Sort.by(direction, fieldName);
        }

        Pageable pageable = PageRequest.of(page, size, sort);

        // Aplicar búsqueda si se proporciona un término
        if (searchTerm != null && !searchTerm.isEmpty()) {
            Specification<SupplierEntity> spec = createSearchSpecification(searchTerm);
            return supplierJpaRepository.findAll(spec, pageable)
                    .map(entity -> {
                        try {
                            return persistenceMapper.toDomain(entity);
                        } catch (DomainException e) {
                            throw new RuntimeException("Error mapping supplier entity to domain", e);
                        }
                    });
        }

        return supplierJpaRepository.findAll(pageable)
                .map(entity -> {
                    try {
                        return persistenceMapper.toDomain(entity);
                    } catch (DomainException e) {
                        throw new RuntimeException("Error mapping supplier entity to domain", e);
                    }
                });
    }

    @Override
    public Long getSupplierIdBySupplierID(String supplierID) {
        Optional<SupplierEntity> supplierOptional = supplierJpaRepository.findBySupplierID(supplierID);
        return supplierOptional.map(SupplierEntity::getId).orElse(null);
    }

    @Override
    public String getSupplierNameById(Long supplierId) {
        return supplierJpaRepository.findById(supplierId)
                .map(SupplierEntity::getName)
                .orElse("Proveedor no encontrado");
    }

    // Método auxiliar para mapear campos de ordenamiento desde la UI a la entidad
    private String mapSortField(String uiField) {
        return switch (uiField.toLowerCase()) {
            case "id" -> "id";
            case "name" -> "name";
            case "email" -> "email";
            case "phone" -> "phone";
            case "contactperson" -> "contactPerson";
            default -> "id"; // Ordenamiento por defecto
        };
    }

    // Método para crear la especificación de búsqueda
    private Specification<SupplierEntity> createSearchSpecification(String searchTerm) {
        String term = "%" + searchTerm.toLowerCase() + "%";

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), term),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), term),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("contactPerson")), term),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("phone")), term),
                        criteriaBuilder.like(root.get("id").as(String.class), term)
                );
    }
}
