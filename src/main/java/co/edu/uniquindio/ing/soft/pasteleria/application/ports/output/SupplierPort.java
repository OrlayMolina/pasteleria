package co.edu.uniquindio.ing.soft.pasteleria.application.ports.output;

import co.edu.uniquindio.ing.soft.pasteleria.domain.exception.DomainException;
import co.edu.uniquindio.ing.soft.pasteleria.domain.model.Supplier;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface SupplierPort {
    Supplier saveSupplier(Supplier supplier) throws DomainException;

    Optional<Supplier> findSupplierById(Long id);

    void deleteSupplierById(Long id);

    List<Supplier> findAllSuppliers();

    boolean existsSupplierById(Long id);

    boolean existsSupplierBySupplierID(String supplierID);

    Long getSupplierIdBySupplierID(String supplierID);

    Page<Supplier> findSuppliersWithPagination(int page, int size);

    Page<Supplier> findSuppliersWithPaginationAndSorting(int page, int size, String sortField, String sortDirection, String searchTerm);

    String getSupplierNameById(Long supplierId);
}
