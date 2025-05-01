package co.edu.uniquindio.ing.soft.pasteleria.application.ports.input;

import co.edu.uniquindio.ing.soft.pasteleria.application.dto.LoginDTO;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.TokenDTO;
import co.edu.uniquindio.ing.soft.pasteleria.domain.exception.DomainException;

public interface ManageAuthUseCase {
    TokenDTO logIn(LoginDTO loginDTO) throws DomainException;

    String resetPassword(LoginDTO loginDTO) throws DomainException;

    String enviarCodigoRecuperacionPassword(String correo) throws CodigoValidacionNoEnviadoException;
}
