package co.edu.uniquindio.ing.soft.pasteleria.application.ports.output;

import co.edu.uniquindio.ing.soft.pasteleria.application.dto.response.UserResponse;
import co.edu.uniquindio.ing.soft.pasteleria.domain.exception.DomainException;
import co.edu.uniquindio.ing.soft.pasteleria.domain.model.User;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface UserPort {
    User saveUser(User user) throws DomainException;

    User updateUser(User user) throws DomainException;

    Optional<User> findUserById(Long id);

    Optional<UserResponse> findUserByIdAllData(Long id);

    void deleteUserById(Long id);

    List<UserResponse> findAllUsers();

    boolean existsUserById(Long id);

    Page<User> findUsersWithPagination(int page, int size);

    Page<UserResponse> findUsersWithPaginationAndSorting(int page, int size, String sortField, String sortDirection, String searchTerm);
}
