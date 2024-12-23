package com.tienda.exceptionHandler.models;

public class ModelResponseForREST {
    private String errorMessage;

    public ModelResponseForREST(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public ModelResponseForREST() {
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
