package co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.adapter;

import co.edu.uniquindio.ing.soft.pasteleria.application.ports.output.ReviewPort;
import co.edu.uniquindio.ing.soft.pasteleria.domain.model.Review;
import co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.entity.ReviewEntity;
import co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.entity.SupplierEntity;
import co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.mapper.ReviewPersistenceMapper;
import co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.repository.ReviewJpaRepository;
import co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.repository.SupplierJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ReviewPersistenceAdapter implements ReviewPort {
    private final ReviewJpaRepository reviewJpaRepository;
    private final SupplierJpaRepository supplierJpaRepository;
    private final ReviewPersistenceMapper persistenceMapper;

    @Override
    public Review saveReview(Review review) {
        SupplierEntity supplierEntity = supplierJpaRepository.findById(review.getSupplierId())
                .orElseThrow(() -> new RuntimeException("Supplier not found"));

        ReviewEntity entity = persistenceMapper.toEntity(review, supplierEntity);
        ReviewEntity savedEntity = reviewJpaRepository.save(entity);
        return persistenceMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Review> findLatestReviewBySupplier(Long supplierId) {
        return reviewJpaRepository.findTopBySupplierIdOrderByOrderDateDesc(supplierId)
                .map(persistenceMapper::toDomain);
    }

    @Override
    public List<Review> findAllReviewsBySupplier(Long supplierId) {
        return reviewJpaRepository.findBySupplierIdOrderByOrderDateDesc(supplierId).stream()
                .map(persistenceMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteReviewById(Long id) {
        reviewJpaRepository.deleteById(id);
    }
}