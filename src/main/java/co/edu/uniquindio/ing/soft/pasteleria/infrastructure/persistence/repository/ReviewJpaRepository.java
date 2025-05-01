package co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.repository;

import co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewJpaRepository extends JpaRepository<ReviewEntity, Long> {
    Optional<ReviewEntity> findTopBySupplierIdOrderByOrderDateDesc(Long supplierId);

    List<ReviewEntity> findBySupplierIdOrderByOrderDateDesc(Long supplierId);
}
