package co.edu.uniquindio.ing.soft.pasteleria.application.ports.input;

import co.edu.uniquindio.ing.soft.pasteleria.application.dto.MensajeDTO;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.request.CreateSupplierCommand;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.request.UpdateSupplierCommand;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.response.PageResponse;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.response.SupplierResponse;
import co.edu.uniquindio.ing.soft.pasteleria.domain.exception.DomainException;

import java.util.List;

public interface ManageSupplierUseCase {
    MensajeDTO<SupplierResponse> createSupplier(CreateSupplierCommand command) throws DomainException;

    MensajeDTO<SupplierResponse> updateSupplier(Long id, UpdateSupplierCommand command);

    MensajeDTO<Void> deleteSupplier(Long id);

    MensajeDTO<SupplierResponse> getSupplier(Long id) throws DomainException;

    MensajeDTO<List<SupplierResponse>> searchSupplier();

    MensajeDTO<PageResponse<SupplierResponse>> getPagedSuppliersByPageAndSize(int page, int size);

    MensajeDTO<PageResponse<SupplierResponse>> getPagedSuppliers(int page, int size, String sort, String direction, String search);
}
