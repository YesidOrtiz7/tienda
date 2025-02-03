package com.tienda.usuarios.adaptador.modelo.controller;

public class DocumentRequest {
    private String documento;

    public DocumentRequest(String documento) {
        this.documento = documento;
    }

    public DocumentRequest() {
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }
}
