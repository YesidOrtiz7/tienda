package com.tienda.exceptionHandler.excepciones;

public class ItemAlreadyExistException extends Exception{
    public ItemAlreadyExistException() {
        super();
    }

    public ItemAlreadyExistException(String message) {
        super(message);
    }

    public ItemAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public ItemAlreadyExistException(Throwable cause) {
        super(cause);
    }
}
