package co.edu.uniquindio.ing.soft.pasteleria.domain.exception;

import java.io.Serial;

public class ValidationCodeNotSentException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public ValidationCodeNotSentException() {
        super();
    }

    public ValidationCodeNotSentException(String message) {
        super(message);
    }
}
