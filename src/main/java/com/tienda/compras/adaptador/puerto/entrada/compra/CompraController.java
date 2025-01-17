package com.tienda.compras.adaptador.puerto.entrada.compra;

import com.tienda.compras.adaptador.modelo.controlador.CompraBasicData;
import com.tienda.compras.adaptador.modelo.controlador.OnlyId;
import com.tienda.compras.aplicacion.puerto.entrada.compra.PuertoEntradaCompra;
import com.tienda.compras.dominio.Compra;
import com.tienda.exceptionHandler.excepciones.InvalidInputException;
import com.tienda.exceptionHandler.excepciones.ItemAlreadyExistException;
import com.tienda.exceptionHandler.excepciones.SearchItemNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
            @ApiResponse(responseCode = "201",description = "se registro la compra",content = {
                    @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Compra.class))
                    )
            } )
    })
    public ResponseEntity<Void> registrarCompra(@RequestBody CompraBasicData compra) throws InvalidInputException, ItemAlreadyExistException, SearchItemNotFoundException {
        this.service.registrarCompra(compra.getUsuario(),compra.getOrdenes());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @GetMapping("/id")
    @Operation(summary = "Obtiene el registro de una compra en especifico, con solo enviar el ID",
    description = "Obtiene el registro de la compra con el ID especificado")
    @ApiResponses({
            @ApiResponse(responseCode = "201",description = "se registro la compra",content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Compra.class)
                    )
            } )
    })
    public ResponseEntity<Compra> obtenerCompraPorId(@RequestBody OnlyId id) throws SearchItemNotFoundException {
        Compra response=this.service.obtenerCompraPorId(id.getId());
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    @GetMapping("/usuario/id")
    @Operation(summary = "Obtiene todos los registros de las compras pertenecientes a un usuario",
    description = "Obtiene todos los registros de las compras pertenecientes a un usuario con pasar el id del usuario")
    @ApiResponses({
            @ApiResponse(responseCode = "201",description = "se registro la compra",content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Compra.class)
                    )
            } )
    })
    public ResponseEntity<List<Compra>> obtenerCompraPorIdUsuario(@RequestBody OnlyId id) throws SearchItemNotFoundException {
        List<Compra> response=this.service.obtenerComprasPorIdUsuario(id.getId());
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
