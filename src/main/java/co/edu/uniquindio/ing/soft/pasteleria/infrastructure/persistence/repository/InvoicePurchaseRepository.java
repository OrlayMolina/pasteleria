package co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.repository;

import co.edu.uniquindio.ing.soft.pasteleria.domain.enums.InvoiceStatus;
import co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.entity.InvoicePurchaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface InvoicePurchaseRepository extends JpaRepository<InvoicePurchaseEntity, Long> {

    List<InvoicePurchaseEntity> findByStatus(InvoiceStatus status);

    List<InvoicePurchaseEntity> findBySupplierId(Long supplierId);

    List<InvoicePurchaseEntity> findByPurchaseDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    Optional<InvoicePurchaseEntity> findByInvoiceNumber(String invoiceNumber);
}