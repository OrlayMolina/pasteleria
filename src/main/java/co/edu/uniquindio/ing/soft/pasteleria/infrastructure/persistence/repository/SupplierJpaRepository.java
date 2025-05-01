package co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.repository;

import co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.entity.SupplierEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SupplierJpaRepository extends JpaRepository<SupplierEntity, Long> {
    Optional<SupplierEntity> findSupplierEntitiesByName(String name);

    Optional<SupplierEntity> findBySupplierID(String supplierID);

    Page<SupplierEntity> findAll(Specification<SupplierEntity> spec, Pageable pageable);
}
