package co.edu.uniquindio.ing.soft.pasteleria.application.service;

import co.edu.uniquindio.ing.soft.pasteleria.application.dto.CambiarPasswordDTO;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.EmailDTO;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.LoginDTO;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.TokenDTO;
import co.edu.uniquindio.ing.soft.pasteleria.application.ports.input.ManageAuthUseCase;
import co.edu.uniquindio.ing.soft.pasteleria.application.ports.input.ManageEmailUseCase;
import co.edu.uniquindio.ing.soft.pasteleria.application.ports.output.UserPort;
import co.edu.uniquindio.ing.soft.pasteleria.domain.exception.DomainException;
import co.edu.uniquindio.ing.soft.pasteleria.domain.exception.ValidationCodeNotSentException;
import co.edu.uniquindio.ing.soft.pasteleria.domain.model.User;
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

import static co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.adapter.config.CryptoPassword.encriptarPassword;

@Service
@RequiredArgsConstructor
public class AuthService implements ManageAuthUseCase {

    private final JWTUtils jwtUtils;
    private final UserJpaRepository userRepository;
    private final UserPort userPort;
    private final EmailTemplateConfig emailTemplateConfig;
    private final ManageEmailUseCase emailService;
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
        boolean nuevo = userEntity.getValidationCodeRegister() != null;
        return Map.of(
                "user_id", userEntity.getId(),
                "email", userEntity.getEmail(),
                "isAdmin", userEntity.getIsAdmin(),
                "isNew", nuevo
        );
    }

    @Override
    public String resetPassword(CambiarPasswordDTO cambiarPasswordDTO) throws DomainException {

        try{
            Optional<UserEntity> userEntity = userJpaRepository.findByEmail(cambiarPasswordDTO.email());
            if (userEntity.isEmpty()) {
                throw new DomainException("El correo no está registrado en el sistema.");
            }

            UserEntity userEnt = userEntity.get();

            ValidationCode codigoValidacion = userEnt.getValidationCodePassword();

            if (codigoValidacion.getCode().
                    equals(cambiarPasswordDTO.codigoVerificacion()))
            {
                if(codigoValidacion.getCreatedAt().
                        plusMinutes(15).isAfter(LocalDateTime.now()))
                {

                    userEnt.setPassword( encriptarPassword(cambiarPasswordDTO.passwordNueva()));
                    User user = new User(
                            userEnt.getTypeDocument(),
                            userEnt.getDocumentNumber(),
                            userEnt.getPhone(),
                            userEnt.getPosition(),
                            userEnt.getSalary(),
                            userEnt.getFirstName(),
                            userEnt.getSecondName(),
                            userEnt.getLastName(),
                            userEnt.getSecondLastName(),
                            userEnt.getEmail(),
                            userEnt.getPassword(),
                            userEnt.getStatus(),
                            userEnt.getIsAdmin(),
                            userEnt.getCreatedAt(),
                            userEnt.getUpdatedAt()
                    );
                    userPort.saveUser(user);
                }
                else{
                    throw new DomainException("El código ya expiro");
                }
            }
            else{
                throw new DomainException("El código de validación es incorrecto");
            }

            return "Se ha cambiado su contraseña";

        } catch (Exception e){
            throw new DomainException("Error cambiar el password" + e.getMessage());
        }
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
