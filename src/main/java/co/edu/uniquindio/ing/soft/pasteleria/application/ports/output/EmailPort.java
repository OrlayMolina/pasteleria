package co.edu.uniquindio.ing.soft.pasteleria.application.ports.output;

import co.edu.uniquindio.ing.soft.pasteleria.application.dto.EmailDTO;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.response.UserResponse;
import co.edu.uniquindio.ing.soft.pasteleria.domain.exception.DomainException;
import co.edu.uniquindio.ing.soft.pasteleria.domain.model.User;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface EmailPort {
    void enviarCorreo(EmailDTO emailDTO) throws Exception;

}
