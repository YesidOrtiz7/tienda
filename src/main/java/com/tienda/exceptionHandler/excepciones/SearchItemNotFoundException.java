package com.tienda.exceptionHandler.excepciones;

public class SearchItemNotFoundException extends Exception{
    public SearchItemNotFoundException() {
        super();
    }

    public SearchItemNotFoundException(String message) {
        super(message);
    }

    public SearchItemNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public SearchItemNotFoundException(Throwable cause) {
        super(cause);
    }
}
