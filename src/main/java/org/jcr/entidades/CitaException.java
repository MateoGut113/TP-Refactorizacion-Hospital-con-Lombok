package org.jcr.entidades;

//No recomendable agregar Lombok, debido a que no posee campos adicionales

public class CitaException extends Exception {

    public CitaException(String message) {
        super(message);
    }

    public CitaException(String message, Throwable cause) {
        super(message, cause);
    }

}

