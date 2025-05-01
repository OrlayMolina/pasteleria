package co.edu.uniquindio.ing.soft.pasteleria;

import co.edu.uniquindio.ing.soft.pasteleria.application.dto.MensajeDTO;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.request.CreateUserCommand;
import co.edu.uniquindio.ing.soft.pasteleria.application.ports.input.ManageUserUseCase;
import co.edu.uniquindio.ing.soft.pasteleria.domain.enums.Status;
import co.edu.uniquindio.ing.soft.pasteleria.domain.enums.TypeDocument;
import co.edu.uniquindio.ing.soft.pasteleria.domain.exception.DomainException;
import co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.entity.UserEntity;
import co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.repository.UserJpaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static co.edu.uniquindio.ing.soft.pasteleria.utils.RandomUtil.generateRandomNumericId;
import static co.edu.uniquindio.ing.soft.pasteleria.utils.RandomUtil.getRandomElement;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private ManageUserUseCase manageUserUseCase;
    @Autowired
    private UserJpaRepository jpaRepository;

    @Test
    public void createUserAdminTest() throws DomainException {
        String randomUserDocument = generateRandomNumericId(10);
        CreateUserCommand command = new CreateUserCommand(
                TypeDocument.CC,
                randomUserDocument,
                "3127523694",
                "Administrador (a)",
                1500000F,
                "Lizeth",
                null,
                "Martinez",
                null,
                "lizmartinez@gmail.com",
                "123456",
                Status.ACTIVO,
                true,
                LocalDateTime.of(2024, 9, 14, 15, 30, 0),
                LocalDateTime.of(2024, 9, 14, 15, 30, 0),
                null
        );

        MensajeDTO<String> responseDTO = manageUserUseCase.createUser(command);
        Assertions.assertNotNull(responseDTO);
        Assertions.assertFalse(responseDTO.error());
        Assertions.assertNotNull(responseDTO.respuesta());
    }

    @Test
    public void createUserTest() throws DomainException {
        String randomUserDocument = generateRandomNumericId(10);
        List<UserEntity> users = jpaRepository.findAll();
        UserEntity user = new UserEntity();
        if (!users.isEmpty()) {
            user = getRandomElement(users);
        }

        CreateUserCommand command = new CreateUserCommand(
                TypeDocument.CC,
                randomUserDocument,
                "3104859632",
                "Auxiliar",
                1200000F,
                "Carolina",
                null,
                "Villanueva",
                null,
                "carolina@gmail.com",
                "123456",
                Status.ACTIVO,
                false,
                LocalDateTime.of(2024, 9, 14, 15, 30, 0),
                LocalDateTime.of(2024, 9, 14, 15, 30, 0),
                user.getId()
        );

        MensajeDTO<String> responseDTO = manageUserUseCase.createUser(command);
        Assertions.assertNotNull(responseDTO);
        Assertions.assertFalse(responseDTO.error());
        Assertions.assertNotNull(responseDTO.respuesta());
    }
}