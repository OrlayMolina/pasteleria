package co.edu.uniquindio.ing.soft.pasteleria.application.ports.input;

import co.edu.uniquindio.ing.soft.pasteleria.application.dto.EmailDTO;
import org.springframework.scheduling.annotation.Async;

public interface ManageEmailUseCase {
    @Async
    void enviarCorreo(EmailDTO emailDTO) throws Exception;
}
