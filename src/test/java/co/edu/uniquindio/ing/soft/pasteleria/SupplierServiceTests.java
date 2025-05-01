package co.edu.uniquindio.ing.soft.pasteleria;

import co.edu.uniquindio.ing.soft.pasteleria.application.dto.MensajeDTO;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.request.CreateSupplierCommand;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.request.CreateUserCommand;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.response.SupplierResponse;
import co.edu.uniquindio.ing.soft.pasteleria.application.ports.input.ManageSupplierUseCase;
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
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static co.edu.uniquindio.ing.soft.pasteleria.utils.RandomUtil.generateRandomNumericId;
import static co.edu.uniquindio.ing.soft.pasteleria.utils.RandomUtil.getRandomElement;

@SpringBootTest
public class SupplierServiceTests {

    @Autowired
    private ManageSupplierUseCase manageSupplierUseCase;
    @Autowired
    private ManageUserUseCase manageUserUseCase;
    @Autowired
    private UserJpaRepository jpaRepository;

    @Test
    public void createSupplierTest() throws DomainException {
        String randomSupplierID = generateRandomNumericId(9);
        List<UserEntity> users = jpaRepository.findAll();
        UserEntity user = new UserEntity();
        if (!users.isEmpty()) {
            user = getRandomElement(users);
        }

        if (user == null) {
            String randomUserDocument = generateRandomNumericId(10);

            CreateUserCommand commandUser = new CreateUserCommand(
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
                    true,
                    LocalDateTime.of(2024, 9, 14, 15, 30, 0),
                    LocalDateTime.of(2024, 9, 14, 15, 30, 0),
                    null
            );
            // Asumiendo que ManageUserUseCase también se ha actualizado para devolver MensajeDTO
            MensajeDTO<String> userResponseDTO = manageUserUseCase.createUser(commandUser);
            Assertions.assertNotNull(userResponseDTO);
            Assertions.assertFalse(userResponseDTO.error());
            Assertions.assertNotNull(userResponseDTO.respuesta());

            Optional<UserEntity> usersGenerate = jpaRepository.findByDocumentNumber(commandUser.documentNumber());
            UserEntity userGenerate = getRandomElement(Collections.singletonList(usersGenerate.get()));

            CreateSupplierCommand command = new CreateSupplierCommand(
                    "Harina as de oros",
                    TypeDocument.NIT,
                    randomSupplierID,
                    "Huila",
                    "+577425689",
                    "asdeoros@gmail.com",
                    "Veronica Garcia",
                    null,
                    null,
                    null,
                    null,
                    null,
                    Status.ACTIVO,
                    LocalDateTime.of(2023, 9, 14, 15, 30, 0),
                    LocalDateTime.of(2023, 9, 14, 15, 30, 0),
                    userGenerate.getId()
            );

            MensajeDTO<SupplierResponse> responseDTO = manageSupplierUseCase.createSupplier(command);
            Assertions.assertNotNull(responseDTO);
            Assertions.assertFalse(responseDTO.error());
            Assertions.assertNotNull(responseDTO.respuesta());
        } else {
            CreateSupplierCommand command = new CreateSupplierCommand(
                    "Harina as de oros",
                    TypeDocument.NIT,
                    randomSupplierID,
                    "Huila",
                    "+577425689",
                    "asdeoros@gmail.com",
                    "Sofia Guerra",
                    null,
                    null,
                    null,
                    null,
                    null,
                    Status.ACTIVO,
                    LocalDateTime.of(2023, 9, 14, 15, 30, 0),
                    LocalDateTime.of(2023, 9, 14, 15, 30, 0),
                    user.getId()
            );

            MensajeDTO<SupplierResponse> responseDTO = manageSupplierUseCase.createSupplier(command);
            Assertions.assertNotNull(responseDTO);
            Assertions.assertFalse(responseDTO.error());
            Assertions.assertNotNull(responseDTO.respuesta());
        }
    }
}