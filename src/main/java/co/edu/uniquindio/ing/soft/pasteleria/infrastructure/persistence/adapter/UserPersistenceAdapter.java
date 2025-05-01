package co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.adapter;

import co.edu.uniquindio.ing.soft.pasteleria.application.dto.response.UserResponse;
import co.edu.uniquindio.ing.soft.pasteleria.application.ports.output.UserPort;
import co.edu.uniquindio.ing.soft.pasteleria.domain.exception.DomainException;
import co.edu.uniquindio.ing.soft.pasteleria.domain.model.User;
import co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.entity.UserEntity;
import co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.mapper.UserPersistenceMapper;
import co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.repository.UserJpaRepository;
import jakarta.persistence.criteria.Predicate;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class UserPersistenceAdapter implements UserPort {

    private final UserJpaRepository userJpaRepository;
    private final UserPersistenceMapper persistenceMapper;

    @Override
    public User saveUser(User user) throws DomainException {
        try {
            UserEntity userEntity = persistenceMapper.toEntity(user);
            System.out.println("UserEntity a guardar: " + userEntity);

            UserEntity savedEntity = userJpaRepository.save(userEntity);
            return persistenceMapper.toDomain(savedEntity);
        } catch (Exception e) {
            e.printStackTrace(); // importante para ver el error real
            throw new DomainException("Error al guardar usuario: " + e.getMessage());
        }
    }

    public User updateUser(User user) throws DomainException {
        UserEntity existingEntity = userJpaRepository.findById(user.getId())
                .orElseThrow(() -> new DomainException("Usuario no encontrado"));
        try {
            existingEntity.setTypeDocument(user.getTypeDocument());
            existingEntity.setDocumentNumber(user.getDocumentNumber());
            existingEntity.setFirstName(user.getFirstName());
            existingEntity.setSecondName(user.getSecondName());
            existingEntity.setLastName(user.getLastName());
            existingEntity.setSecondLastName(user.getSecondLastName());
            existingEntity.setEmail(user.getEmail());
            existingEntity.setPhone(user.getPhone());
            existingEntity.setStatus(user.getStatus());
            existingEntity.setUpdatedAt(user.getUpdatedAt());

            UserEntity updatedEntity = userJpaRepository.save(existingEntity);
            return persistenceMapper.toDomain(updatedEntity);
        } catch (Exception e) {
            e.printStackTrace(); // importante para ver el error real
            throw new DomainException("Error al guardar usuario: " + e.getMessage());
        }
    }

    @Override
    public Optional<User> findUserById(Long id) {
        Optional<UserEntity> optionalEntity = userJpaRepository.findById(id);
        if (optionalEntity.isEmpty()) {
            return Optional.empty();
        }

        try {
            return Optional.of(persistenceMapper.toDomain(optionalEntity.get()));
        } catch (DomainException e) {
            throw new RuntimeException("Error al convertir UserEntity a dominio User", e);
        }
    }


    @Override
    public Optional<UserResponse> findUserByIdAllData(Long id) {
        Optional<UserEntity> optionalEntity = userJpaRepository.findById(id);
        if (optionalEntity.isEmpty()) {
            return Optional.empty();
        }

        try {
            optionalEntity.get().setPassword("null");
            return Optional.of(persistenceMapper.toDomainResponse(optionalEntity.get()));
        } catch (DomainException e) {
            throw new RuntimeException("Error al convertir UserEntity a dominio User", e);
        }
    }

    @Override
    public void deleteUserById(Long id) {
        userJpaRepository.deleteById(id);
    }

    @Override
    public List<UserResponse> findAllUsers() {
        List<UserEntity> entities = userJpaRepository.findAll();
        return entities.stream()
                .map(entity -> {
                    try {
                        return persistenceMapper.toDomainResponse(entity);
                    } catch (DomainException e) {
                        throw new RuntimeException("Error al convertir lista de UserEntity a dominio User", e);
                    }
                })
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsUserById(Long id) {
        return userJpaRepository.existsById(id);
    }

    @Override
    public Page<User> findUsersWithPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<UserEntity> usersPage = userJpaRepository.findAll(pageable);
        return usersPage.map(entity -> {
            try {
                return persistenceMapper.toDomain(entity);
            } catch (DomainException e) {
                throw new RuntimeException("Error mapping user entity to domain", e);
            }
        });
    }

    @Override
    public Page<UserResponse> findUsersWithPaginationAndSorting(int page, int size, String sortField, String sortDirection, String searchTerm) {
        Sort sort = Sort.unsorted();

        if (sortField != null && !sortField.isEmpty()) {
            Sort.Direction direction = sortDirection.equalsIgnoreCase("asc") ?
                    Sort.Direction.ASC : Sort.Direction.DESC;

            String fieldName = mapSortField(sortField);
            sort = Sort.by(direction, fieldName);
        }

        Pageable pageable = PageRequest.of(page, size, sort);

        if (searchTerm != null && !searchTerm.isEmpty()) {
            Specification<UserEntity> spec = createSearchSpecification(searchTerm);
            return userJpaRepository.findAll(spec, pageable)
                    .map(entity -> {
                        try {
                            return persistenceMapper.toDomainResponse(entity);
                        } catch (DomainException e) {
                            throw new RuntimeException("Error mapping user entity to domain", e);
                        }
                    });
        }

        return userJpaRepository.findAll(pageable)
                .map(entity -> {
                    try {
                        return persistenceMapper.toDomainResponse(entity);
                    } catch (DomainException e) {
                        throw new RuntimeException("Error mapping user entity to domain", e);
                    }
                });
    }

    // Metodo auxiliar para mapear campos de ordenamiento desde la UI a la entidad
    private String mapSortField(String uiField) {
        return switch (uiField.toLowerCase()) {
            case "id" -> "id";
            case "name", "nombre" -> "name";
            default -> "id";
        };
    }

    private Specification<UserEntity> createSearchSpecification(String searchTerm) {
        String term = "%" + searchTerm.toLowerCase() + "%";

        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), term));

            try {
                Long idValue = Long.parseLong(searchTerm.trim());
                predicates.add(criteriaBuilder.equal(root.get("id"), idValue));
            } catch (NumberFormatException e) {

            }

            return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
        };
    }

}
