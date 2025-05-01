package co.edu.uniquindio.ing.soft.pasteleria.application.mapper;

import co.edu.uniquindio.ing.soft.pasteleria.application.dto.request.CreateUserCommand;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.response.UserResponse;
import co.edu.uniquindio.ing.soft.pasteleria.domain.exception.DomainException;
import co.edu.uniquindio.ing.soft.pasteleria.domain.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserDtoMapper {
    public User toModel(CreateUserCommand command) throws DomainException {
        return new User(
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
                command.password(),
                command.status(),
                command.isAdmin(),
                command.createdAt(),
                command.updatedAt()
        );
    }

    public UserResponse toResponse(User user) throws DomainException {
        return new UserResponse(
                user.getId(),
                user.getTypeDocument(),
                user.getDocumentNumber(),
                user.getPhone(),
                user.getPosition(),
                user.getSalary(),
                user.getFirstName(),
                user.getSecondName(),
                user.getLastName(),
                user.getSecondLastName(),
                user.getEmail(),
                user.getPassword(),
                user.getStatus(),
                user.isAdmin(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}
