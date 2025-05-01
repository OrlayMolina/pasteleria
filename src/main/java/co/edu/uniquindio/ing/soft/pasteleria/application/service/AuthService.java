package co.edu.uniquindio.ing.soft.pasteleria.application.service;

import co.edu.uniquindio.ing.soft.pasteleria.application.dto.EmailDTO;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.LoginDTO;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.TokenDTO;
import co.edu.uniquindio.ing.soft.pasteleria.application.ports.input.ManageAuthUseCase;
import co.edu.uniquindio.ing.soft.pasteleria.domain.exception.DomainException;
import co.edu.uniquindio.ing.soft.pasteleria.domain.exception.ValidationCodeNotSentException;
import co.edu.uniquindio.ing.soft.pasteleria.domain.model.ValidationCode;
import co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.adapter.config.EmailTemplateConfig;
import co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.adapter.config.JWTUtils;
import co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.entity.UserEntity;
import co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService implements ManageAuthUseCase {

    private final JWTUtils jwtUtils;
    private final EmailService emailService;
    private final UserJpaRepository userRepository;
    private final EmailTemplateConfig emailTemplateConfig;
    private final UserJpaRepository userJpaRepository;

    @Override
    public TokenDTO logIn(LoginDTO loginDTO) throws DomainException {
        Optional<UserEntity> userEntity = userJpaRepository.findByEmail(loginDTO.email());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if (userEntity.isEmpty()) {
            throw new DomainException("Usuario no encontrado");
        }

        if (!passwordEncoder.matches(loginDTO.password(), userEntity.get().getPassword())) {
            throw new DomainException("La contraseña es incorrecta");
        }
        Map<String, Object> map = construirClaims(userEntity.get());
        return new TokenDTO(jwtUtils.generarToken(userEntity.get().getEmail(), map));
    }

    private Map<String, Object> construirClaims(UserEntity userEntity) {
        return Map.of(
                "user_id", userEntity.getId(),
                "email", userEntity.getEmail(),
                "isAdmin", userEntity.getIsAdmin()
        );
    }

    @Override
    public String resetPassword(LoginDTO loginDTO) throws DomainException {
        Optional<UserEntity> userEntity = userJpaRepository.findByEmail(loginDTO.email());

        if (userEntity.isEmpty()) {
            throw new DomainException("El correo no está registrado en el sistema.");
        }

        // Generar un código aleatorio de 6 dígitos
        String codigoRecuperacion = generarCodigoAleatorio();

        UserEntity userModificado = userEntity.get();


//        // Asignar el código de recuperación a la cuenta y establecer una expiración de 15 minutos
//        cuentaModificada.setCodigoValidacionPassword(new CodigoValidacion(
//                LocalDateTime.now(),
//                codigoRecuperacion
//        ));
//
//        // Guardar la cuenta actualizada en la base de datos
//        cuentaRepo.save(cuentaModificada);

        // Modificar el contenido del correo para el restablecimiento de contraseña
        String contenido = "Hemos recibido una solicitud para restablecer tu contraseña.\n\n" +
                "Tu código de verificación es: " + codigoRecuperacion + "\n" +
                "Este código es válido por 15 minutos. Si no solicitaste este cambio, puedes ignorar este mensaje.";

        // Enviar el correo
//        emailServicio.enviarCorreo(
//                new EmailDTO("Restablecimiento de contraseña - Pasteleria feliz", contenido, userModificado.getEmail())
//        );
//
//        Map<String, Object> map = construirClaims(userEntity.get());
        return "Correo enviado con exito";
    }

    @Override
    public String enviarCodigoRecuperacionPassword(String correo) throws ValidationCodeNotSentException {

        try{
            Optional<UserEntity>  cuentaOptional = userRepository.findByEmail(correo);

            if(cuentaOptional.isEmpty()){
                throw new ValidationCodeNotSentException("No se encontró una cuenta con ese email ");
            }

            UserEntity cuenta = cuentaOptional.get();
            String codigoValidacion = generarCodigoAleatorio();

            cuenta.setValidationCodePassword(new ValidationCode(
                    codigoValidacion,
                    LocalDateTime.now()
            ));


            String body = EmailTemplateConfig.bodyActualizarPassword.replace("[Codigo_Activacion]", codigoValidacion);

            emailService.enviarCorreo( new EmailDTO("Cambio de contraseña", body, correo) );
            userRepository.save(cuenta);

            return "Se ha enviado un correo con el código de validación";

        } catch (Exception e){
            throw new ValidationCodeNotSentException("Error al editar cuenta del profesional" + e.getMessage());
        }
    }


    private String generarCodigoAleatorio() {
        return String.format("%06d", (int) (Math.random() * 1000000));
    }
}
