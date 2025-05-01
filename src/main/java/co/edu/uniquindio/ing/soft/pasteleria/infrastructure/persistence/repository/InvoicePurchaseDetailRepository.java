package co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.repository;

import co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.entity.InvoicePurchaseDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface InvoicePurchaseDetailRepository extends JpaRepository<InvoicePurchaseDetailEntity, Long> {

    List<InvoicePurchaseDetailEntity> findByInvoicePurchaseId(Long invoicePurchaseId);

    List<InvoicePurchaseDetailEntity> findBySupplyId(Long supplyId);

    @Query("SELECT ipd FROM InvoicePurchaseDetailEntity ipd WHERE ipd.expirationDate < :date")
    List<InvoicePurchaseDetailEntity> findByExpirationDateBefore(@Param("date") LocalDate date);
}