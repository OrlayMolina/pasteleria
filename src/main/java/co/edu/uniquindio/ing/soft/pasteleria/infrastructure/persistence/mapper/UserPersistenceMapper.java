package co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.mapper;

import co.edu.uniquindio.ing.soft.pasteleria.application.dto.response.UserResponse;
import co.edu.uniquindio.ing.soft.pasteleria.domain.exception.DomainException;
import co.edu.uniquindio.ing.soft.pasteleria.domain.model.User;
import co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserPersistenceMapper {

    public UserEntity toEntity(User user) throws DomainException {
        if (user == null) {
            return null;
        }

        UserEntity entity = new UserEntity();
        entity.setId(user.getId());
        entity.setTypeDocument(user.getTypeDocument());
        entity.setPhone(user.getPhone());
        entity.setPosition(user.getPosition());
        entity.setSalary(user.getSalary());
        entity.setFirstName(user.getFirstName());
        entity.setSecondName(user.getSecondName());
        entity.setLastName(user.getLastName());
        entity.setSecondLastName(user.getSecondLastName());
        entity.setEmail(user.getEmail());
        entity.setPassword(user.getPassword());
        entity.setStatus(user.getStatus());
        entity.setIsAdmin(user.isAdmin());
        entity.setCreatedAt(user.getCreatedAt());
        entity.setUpdatedAt(user.getUpdatedAt());

        return entity;
    }

    public User toDomain(UserEntity entity) throws DomainException {
        if (entity == null) {
            return null;
        }

        return new User(
                entity.getTypeDocument(),
                entity.getDocumentNumber(),
                entity.getPhone(),
                entity.getPosition(),
                entity.getSalary(),
                entity.getFirstName(),
                entity.getSecondName(),
                entity.getLastName(),
                entity.getSecondLastName(),
                entity.getEmail(),
                entity.getPassword(),
                entity.getStatus(),
                entity.getIsAdmin(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public UserResponse toDomainResponse(UserEntity entity) throws DomainException {
        if (entity == null) {
            return null;
        }

        return new UserResponse(
                entity.getId(),
                entity.getTypeDocument(),
                entity.getDocumentNumber(),
                entity.getPhone(),
                entity.getPosition(),
                entity.getSalary(),
                entity.getFirstName(),
                entity.getSecondName(),
                entity.getLastName(),
                entity.getSecondLastName(),
                entity.getEmail(),
                entity.getPassword(),
                entity.getStatus(),
                entity.getIsAdmin(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
