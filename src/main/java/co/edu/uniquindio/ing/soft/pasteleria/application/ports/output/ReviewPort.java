package co.edu.uniquindio.ing.soft.pasteleria.application.ports.output;

import co.edu.uniquindio.ing.soft.pasteleria.domain.model.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewPort {
    Review saveReview(Review review);

    Optional<Review> findLatestReviewBySupplier(Long supplierId);

    List<Review> findAllReviewsBySupplier(Long supplierId);

    void deleteReviewById(Long id);
}