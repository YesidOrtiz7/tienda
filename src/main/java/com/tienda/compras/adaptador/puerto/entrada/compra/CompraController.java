package com.tienda.compras.adaptador.puerto.entrada.compra;

import com.tienda.compras.aplicacion.puerto.entrada.compra.PuertoEntradaCompra;
import com.tienda.compras.dominio.Compra;
import com.tienda.exceptionHandler.excepciones.InvalidInputException;
import com.tienda.exceptionHandler.excepciones.ItemAlreadyExistException;
import com.tienda.exceptionHandler.excepciones.SearchItemNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/compras")
public class CompraController {
    private PuertoEntradaCompra service;

    public CompraController() {
    }

    @Autowired
    public CompraController(PuertoEntradaCompra service) {
        this.service = service;
    }

    @PostMapping("/registrar")
    @Operation(summary = "registra las compras realizadas por los usuarios"
    ,description = "registra las compras del cliente, calculando automaticamente el total de la compra " +
            "con base en el precio y la cantidad de los articulos insertados")
    @ApiResponses({
            @ApiResponse(responseCode = "201",description = "se registro la compra")
    })
    public ResponseEntity<Compra> registrarCompra(Compra compra) throws InvalidInputException, ItemAlreadyExistException, SearchItemNotFoundException {
        Compra response=this.service.registrarCompra(compra);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }
    /*
    {
      "id_compra": 0,
      "usuario": {
        "id": 0,
        "documento": "string",
        "correo": "string",
        "nombres": "string",
        "apellidos": "string",
        "telefono": "string",
        "contrasena": "string",
        "totpSecret": "string",
        "bloqueado": true,
        "eliminado": true,
        "roles": [
          {
            "id": 0,
            "nombre": "string"
          }
        ],
        "cuenta": {
          "id_usuario": 0,
          "saldo": 0
        }
      },
      "ordenes": [
        {
          "id_orden": 0,
          "producto": {
            "id": 0,
            "tituloPublicacion": "string",
            "descripcion": "string",
            "precio": 0,
            "categoria": {
              "id": 0,
              "nombre": "string"
            },
            "cantidadDisponible": 0,
            "fechaPublicacion": "2025-01-08T17:23:51.573Z",
            "usuario": {
              "id": 0,
              "documento": "string",
              "correo": "string",
              "nombres": "string",
              "apellidos": "string",
              "telefono": "string",
              "contrasena": "string",
              "totpSecret": "string",
              "bloqueado": true,
              "eliminado": true,
              "roles": [
                {
                  "id": 0,
                  "nombre": "string"
                }
              ],
              "cuenta": {
                "id_usuario": 0,
                "saldo": 0
              }
            },
            "visible": true
          },
          "cantidad": 0,
          "compra": "string"
        }
      ],
      "total": 0,
      "fecha": "2025-01-08T17:23:51.573Z"
    }
    * */
}
