package co.edu.uniquindio.ing.soft.pasteleria.application.ports.input;

import co.edu.uniquindio.ing.soft.pasteleria.application.dto.MensajeDTO;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.request.CreateSupplyCommand;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.request.UpdateSupplyCommand;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.response.PageResponse;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.response.SupplyInfoResponse;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.response.SupplyResponse;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.response.SupplyStockResponse;
import co.edu.uniquindio.ing.soft.pasteleria.domain.exception.DomainException;

import java.util.List;

public interface ManageSupplyUseCase {
    MensajeDTO<String> createSupply(CreateSupplyCommand command) throws DomainException;

    MensajeDTO<SupplyResponse> updateSupply(Long id, UpdateSupplyCommand command) throws DomainException;

    MensajeDTO<Void> deleteSupply(Long id);

    MensajeDTO<SupplyResponse> getSupply(Long id) throws DomainException;

    MensajeDTO<List<SupplyResponse>> searchSupply();

    MensajeDTO<PageResponse<SupplyResponse>> getPagedSupplies(int page, int size);

    MensajeDTO<PageResponse<SupplyResponse>> getPagedSupplies(int page, int size, String sort, String direction, String search);

    MensajeDTO<SupplyInfoResponse> getSupplyInfo(Long id) throws DomainException;

    MensajeDTO<SupplyStockResponse> getSupplyStock(Long id) throws DomainException;
}
