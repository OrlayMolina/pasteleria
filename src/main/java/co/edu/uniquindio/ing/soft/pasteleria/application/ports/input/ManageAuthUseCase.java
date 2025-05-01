package co.edu.uniquindio.ing.soft.pasteleria.application.ports.input;

import co.edu.uniquindio.ing.soft.pasteleria.application.dto.CambiarPasswordDTO;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.LoginDTO;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.TokenDTO;
import co.edu.uniquindio.ing.soft.pasteleria.domain.exception.DomainException;
import co.edu.uniquindio.ing.soft.pasteleria.domain.exception.ValidationCodeNotSentException;

public interface ManageAuthUseCase {
    TokenDTO logIn(LoginDTO loginDTO) throws DomainException;

    String resetPassword(CambiarPasswordDTO cambiarPasswordDTO) throws DomainException;

    String enviarCodigoRecuperacionPassword(String correo) throws ValidationCodeNotSentException;
}
