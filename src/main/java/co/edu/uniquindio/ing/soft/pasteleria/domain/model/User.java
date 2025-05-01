package co.edu.uniquindio.ing.soft.pasteleria.domain.model;

import co.edu.uniquindio.ing.soft.pasteleria.domain.enums.Status;
import co.edu.uniquindio.ing.soft.pasteleria.domain.enums.TypeDocument;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class User {

    private Long id;
    private TypeDocument typeDocument;
    private String documentNumber;
    private String phone;
    private String position;
    private Float salary;
    private String firstName;
    private String secondName;
    private String lastName;
    private String secondLastName;
    private String email;
    private ValidationCode ValidationCodeRegister;
    private String password;
    private Status status;
    private boolean isAdmin;
    private ValidationCode validationCodePassword;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public User(TypeDocument typeDocument, String documentNumber, String phone, String position,
                Float salary, String firstName, String secondName, String lastName,
                String secondLastName, String email, String password, Status status,
                boolean isAdmin, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.typeDocument = typeDocument;
        this.documentNumber = documentNumber;
        this.phone = phone;
        this.position = position;
        this.salary = salary;
        this.firstName = firstName;
        this.secondName = secondName;
        this.lastName = lastName;
        this.secondLastName = secondLastName;
        this.email = email;
        this.password = password;
        this.status = status;
        this.isAdmin = isAdmin;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "User{" +
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
                '}';
    }
}
