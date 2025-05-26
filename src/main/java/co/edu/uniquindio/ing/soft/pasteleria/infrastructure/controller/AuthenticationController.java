package co.edu.uniquindio.ing.soft.pasteleria.infrastructure.controller;

import co.edu.uniquindio.ing.soft.pasteleria.application.dto.CambiarPasswordDTO;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.LoginDTO;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.MensajeDTO;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.TokenDTO;
import co.edu.uniquindio.ing.soft.pasteleria.application.service.AuthService;
import co.edu.uniquindio.ing.soft.pasteleria.domain.exception.DomainException;
import co.edu.uniquindio.ing.soft.pasteleria.domain.exception.ValidationCodeNotSentException;
import co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.adapter.config.JWTUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthenticationController {

    private JWTUtils jwtUtils;
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<MensajeDTO<TokenDTO>> iniciarSesion(@Valid @RequestBody LoginDTO loginDTO) {
        try {
            TokenDTO tokenDTO = authService.logIn(loginDTO);
            return ResponseEntity.ok(new MensajeDTO<>(false, tokenDTO));
        } catch (DomainException e) {
            return ResponseEntity.status(NOT_FOUND).body(new MensajeDTO<>(true, new TokenDTO(e.getMessage())));
        }
    }

    @PostMapping("/recuperar-contrasena")
    public ResponseEntity<MensajeDTO<String>> resetPassword(@RequestBody CambiarPasswordDTO cambiarPasswordDTO) {
        try {
            String msj = authService.resetPassword(cambiarPasswordDTO);
            return ResponseEntity.ok(new MensajeDTO<>(false, msj));
        } catch (DomainException e) {
            return ResponseEntity.status(NOT_FOUND).body(new MensajeDTO<>(true, e.getMessage()));
        }
    }

    @PostMapping("/enviar-codigo")
    public ResponseEntity<MensajeDTO<String>> sendResetCode(@RequestBody CambiarPasswordDTO cambiarPasswordDTO) {
        try {
            String msj = authService.enviarCodigoRecuperacionPassword(cambiarPasswordDTO.email());
            return ResponseEntity.ok(new MensajeDTO<>(false, msj));
        } catch (ValidationCodeNotSentException e) {
            return ResponseEntity.status(NOT_FOUND).body(new MensajeDTO<>(true, e.getMessage()));
        }
    }

}

