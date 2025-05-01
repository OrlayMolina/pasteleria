package co.edu.uniquindio.ing.soft.pasteleria.infrastructure.controller;

import co.edu.uniquindio.ing.soft.pasteleria.application.dto.MensajeDTO;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.request.CreateUserCommand;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.request.UpdateUserCommand;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.response.PageResponse;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.response.UserResponse;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.response.UserSimplifyResponse;
import co.edu.uniquindio.ing.soft.pasteleria.application.ports.input.ManageUserUseCase;
import co.edu.uniquindio.ing.soft.pasteleria.domain.exception.DomainException;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

    private final ManageUserUseCase manageUserUseCase;

    @PostMapping
    public ResponseEntity<MensajeDTO<String>> createUser(@Valid @RequestBody CreateUserCommand command) {
        try {
            MensajeDTO<String> response = manageUserUseCase.createUser(command);
            return ResponseEntity.ok(response);
        } catch (DomainException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MensajeDTO<>(true, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MensajeDTO<>(true, e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<MensajeDTO<String>> updateUser(@PathVariable Long id, @Valid @RequestBody UpdateUserCommand command) {
        try {
            MensajeDTO<String> response = manageUserUseCase.updateUser(id, command);
            if (response.error() || response.respuesta() == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            return ResponseEntity.ok(response);
        } catch (ConstraintViolationException e) {
            return ResponseEntity.badRequest().body(new MensajeDTO<>(true, "Datos inválidos: " + e.getMessage()));
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new MensajeDTO<>(true, "Conflicto de integridad de datos: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MensajeDTO<>(true, "Error al actualizar el usuario: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MensajeDTO<Void>> deleteUser(@PathVariable Long id) {
        try {
            MensajeDTO<Void> response = manageUserUseCase.deleteUser(id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MensajeDTO<>(true, null));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<MensajeDTO<UserResponse>> getUser(@PathVariable Long id) {
        try {
            MensajeDTO<UserResponse> response = manageUserUseCase.getUser(id);
            if (response.error() || response.respuesta() == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            return ResponseEntity.ok(response);
        } catch (DomainException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MensajeDTO<>(true, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MensajeDTO<>(true, null));
        }
    }

    @GetMapping
    public ResponseEntity<MensajeDTO<List<UserResponse>>> getAllUsers() {
        try {
            MensajeDTO<List<UserResponse>> response = manageUserUseCase.searchUser();
            if (response.error() || response.respuesta() == null || response.respuesta().isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MensajeDTO<>(true, null));
        }
    }

    @GetMapping("/{id}/basic-info")
    public ResponseEntity<MensajeDTO<UserSimplifyResponse>> getUserBasicInfo(@PathVariable Long id) {
        try {
            MensajeDTO<UserSimplifyResponse> response = manageUserUseCase.getUserBasicInfo(id);
            if (response.error() || response.respuesta() == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            return ResponseEntity.ok(response);
        } catch (DomainException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MensajeDTO<>(true, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MensajeDTO<>(true, null));
        }
    }

    @GetMapping("/paged")
    public ResponseEntity<MensajeDTO<PageResponse<UserResponse>>> getPagedUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sort,
            @RequestParam(defaultValue = "asc") String direction,
            @RequestParam(required = false) String search) {
        return ResponseEntity.ok(manageUserUseCase.getPagedUsers(page, size, sort, direction, search));
    }
}