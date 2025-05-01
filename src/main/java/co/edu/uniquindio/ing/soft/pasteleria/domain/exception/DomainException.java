package co.edu.uniquindio.ing.soft.pasteleria.domain.exception;

import java.io.Serial;

public class DomainException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    public DomainException() {
        super();
    }

    public DomainException(String message) {
        super(message);
    }
}
