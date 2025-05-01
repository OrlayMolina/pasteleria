package co.edu.uniquindio.ing.soft.pasteleria.application.service;

import co.edu.uniquindio.ing.soft.pasteleria.application.dto.MensajeDTO;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.request.CreateUserCommand;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.request.UpdateUserCommand;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.response.PageResponse;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.response.UserResponse;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.response.UserSimplifyResponse;
import co.edu.uniquindio.ing.soft.pasteleria.application.mapper.UserDtoMapper;
import co.edu.uniquindio.ing.soft.pasteleria.application.ports.input.ManageUserUseCase;
import co.edu.uniquindio.ing.soft.pasteleria.application.ports.output.UserPort;
import co.edu.uniquindio.ing.soft.pasteleria.domain.exception.DomainException;
import co.edu.uniquindio.ing.soft.pasteleria.domain.model.User;
import co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.entity.UserEntity;
import co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.adapter.config.CryptoPassword.encriptarPassword;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements ManageUserUseCase {
    private final UserPort userPort;
    private final UserDtoMapper userDtoMapper;
    private final UserJpaRepository userJpaRepository;

    @Override
    public MensajeDTO<String> createUser(CreateUserCommand command) throws DomainException {
        try {
            Optional<UserEntity> userOptional = userJpaRepository.findByEmail(command.email());
            if (userOptional.isPresent()) {
                throw new DomainException("Ya existe un usuario con el mismo correo electrónico.");
            }

            User user = new User(
                    command.typeDocument(),
                    command.documentNumber(),
                    command.phone(),
                    command.position(),
                    command.salary(),
                    command.firstName(),
                    command.secondName(),
                    command.lastName(),
                    command.secondLastName(),
                    command.email(),
                    encriptarPassword(command.password()),
                    command.status(),
                    command.isAdmin(),
                    command.createdAt(),
                    command.updatedAt()
            );

            userPort.saveUser(user);
            return new MensajeDTO<>(false, "usuario creado con exito");
        } catch (Exception e) {
            return new MensajeDTO<>(true, "Error al crear el usuario: " + e.getMessage());
        }
    }

    @Override
    public MensajeDTO<String> updateUser(Long id, UpdateUserCommand command) {
        try {
            Optional<User> optionalUser = userPort.findUserById(id);
            if (optionalUser.isEmpty()) {
                return new MensajeDTO<>(true, "Usuario no encontrado");
            }

            User existingUser = optionalUser.get();
            existingUser.setId(command.id());
            existingUser.setTypeDocument(command.typeDocument());
            existingUser.setDocumentNumber(command.documentNumber());
            existingUser.setPhone(command.phone());
            existingUser.setPosition(command.position());
            existingUser.setSalary(command.salary());
            existingUser.setFirstName(command.firstName());
            existingUser.setSecondName(command.secondName());
            existingUser.setLastName(command.lastName());
            existingUser.setSecondLastName(command.secondLastName());
            existingUser.setEmail(command.email());

            if (command.password() != null && !command.password().isEmpty()) {
                existingUser.setPassword(command.password());
            }
            existingUser.setStatus(command.status());
            existingUser.setUpdatedAt(command.updatedAt());

            User updatedUser = userPort.updateUser(existingUser);
            UserResponse response = userDtoMapper.toResponse(updatedUser);

            return new MensajeDTO<>(false, "Usuario actualizado con exito");
        } catch (RuntimeException e) {
            throw e; // Importante: relanzar para que se haga rollback real
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar el usuario", e);
        }
    }

    @Override
    public MensajeDTO<Void> deleteUser(Long id) {
        try {
            userPort.deleteUserById(id);
            return new MensajeDTO<>(false, null);
        } catch (Exception e) {
            return new MensajeDTO<>(true, null);
        }
    }

    @Override
    public MensajeDTO<UserResponse> getUser(Long id) throws DomainException {
        try {
            Optional<UserResponse> optionalUser = userPort.findUserByIdAllData(id);
            if (optionalUser.isEmpty()) {
                return new MensajeDTO<>(true, null);
            }
            return new MensajeDTO<>(false, optionalUser.get());
        } catch (Exception e) {
            return new MensajeDTO<>(true, null);
        }
    }

    @Override
    public MensajeDTO<List<UserResponse>> searchUser() {
        try {
            List<UserResponse> users = userPort.findAllUsers();
//            List<UserResponse> responses = users.stream().map(user -> {
//                try {
//                    return userDtoMapper.toResponse(user);
//                } catch (DomainException e) {
//                    throw new RuntimeException("Error al mapear User a UserResponse", e);
//                }
//            }).toList();
            return new MensajeDTO<>(false, users);
        } catch (Exception e) {
            return new MensajeDTO<>(true, null);
        }
    }

    @Override
    public MensajeDTO<UserSimplifyResponse> getUserBasicInfo(Long id) throws DomainException {
        try {
            Optional<User> optionalUser = userPort.findUserById(id);
            if (optionalUser.isEmpty()) {
                return new MensajeDTO<>(true, null);
            }

            User user = optionalUser.get();
            UserSimplifyResponse response = new UserSimplifyResponse(
                    user.getTypeDocument(),
                    user.getDocumentNumber(),
                    user.getFirstName(),
                    user.getSecondName(),
                    user.getLastName(),
                    user.getSecondLastName()
            );

            return new MensajeDTO<>(false, response);
        } catch (Exception e) {
            return new MensajeDTO<>(true, null);
        }
    }

    @Override
    public MensajeDTO<PageResponse<UserResponse>> getPagedUsers(int page, int size, String sort, String direction, String search) {
        Page<UserResponse> usersPage = userPort.findUsersWithPaginationAndSorting(page, size, sort, direction, search);

        List<UserResponse> items = usersPage.getContent().stream().toList();

        PageResponse<UserResponse> pageResponse = new PageResponse<>(
                items,
                usersPage.getNumber(),
                usersPage.getSize(),
                usersPage.getTotalElements(),
                usersPage.getTotalPages(),
                usersPage.isLast()
        );

        return new MensajeDTO<>(false, pageResponse);
    }
}