package co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.entity;

import co.edu.uniquindio.ing.soft.pasteleria.domain.enums.Status;
import co.edu.uniquindio.ing.soft.pasteleria.domain.enums.TypeDocument;
import co.edu.uniquindio.ing.soft.pasteleria.domain.model.ValidationCode;
import co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.converter.ValidationCodeConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_document", nullable = false)
    private TypeDocument typeDocument;

    @Column(name = "document_number")
    private String documentNumber;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "position")
    private String position;

    @Column(name = "salary")
    private Float salary;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "second_name")
    private String secondName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "second_last_name")
    private String secondLastName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Convert(converter = ValidationCodeConverter.class)
    private ValidationCode validationCodeRegister;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "status", nullable = false)
    private Status status;

    @Column(name = "is_admin", nullable = false)
    private Boolean isAdmin;

    @Convert(converter = ValidationCodeConverter.class)
    private ValidationCode validationCodePassword;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "modify_by")
    private UserEntity deletedBy;

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", typeDocument=" + typeDocument +
                ", documentNumber='" + documentNumber + '\'' +
                ", phone='" + phone + '\'' +
                ", position='" + position + '\'' +
                ", salary=" + salary +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", secondLastName='" + secondLastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", status=" + status +
                ", isAdmin=" + isAdmin +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", deletedBy=" + deletedBy +
                '}';
    }
}
