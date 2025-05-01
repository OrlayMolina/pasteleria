package co.edu.uniquindio.ing.soft.pasteleria.domain.constant;

public class Constants {
    private Constants() {
        throw new AssertionError();
    }

    public static final String SUPPLIER_NAME_CAN_NOT_BE_EMPTY = "El nombre del proveedor no puede ser vacío";
    public static final String SUPPLIER_NAME_LENGTH = "El nombre del proveedor debe tener minimo 3 y máximo 100 caracteres";
    public static final String SUPPLIER_ADDRESS_CAN_NOT_BE_EMPTY = "La dirección del proveedor no puede ser vacía";
    static final int EXPIRATION_THRESHOLD_DAYS = 7;
}
