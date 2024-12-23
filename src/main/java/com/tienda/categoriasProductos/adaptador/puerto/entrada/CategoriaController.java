package com.tienda.categoriasProductos.adaptador.puerto.entrada;

import com.tienda.categoriasProductos.adaptador.modelo.CategoriaControllerModel;
import com.tienda.categoriasProductos.adaptador.modelo.IdRequest;
import com.tienda.categoriasProductos.aplicacion.puerto.entrada.CategoriaPortIn;
import com.tienda.categoriasProductos.dominio.Categoria;
import com.tienda.exceptionHandler.excepciones.InvalidInputException;
import com.tienda.exceptionHandler.excepciones.ItemAlreadyExistException;
import com.tienda.exceptionHandler.excepciones.SearchItemNotFoundException;
import com.tienda.exceptionHandler.models.ModelResponseForREST;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {
    private CategoriaPortIn service;
    private MapperControllerToDomainCategoria mapper;

    @Autowired
    public void setMapper(MapperControllerToDomainCategoria mapper) {
        this.mapper = mapper;
    }

    @Autowired
    public void setService(CategoriaPortIn service) {
        this.service = service;
    }

    @GetMapping("/")
    @Operation(summary = "Obtiene todas las categorias", description = """
            Obtiene todas las categorias registradas en la base de datos""")
    @ApiResponse(responseCode = "200",description = "OK")
    public ResponseEntity<ArrayList<Categoria>> obtenerCategorias(){
        return new ResponseEntity<>(service.consultarCategorias(),HttpStatus.OK);
    }
    @PostMapping("/")
    @Operation(summary = "Busca la categoria mediante el id pasado en el cuerpo de la peticion",description = """
            Busca una categoria con base en el id de la categoria,
            este metodo recibe un objeto en el cuerpo de la peticion el cual debe contener una propiedad llamada id, retorna un error 404 si la categoria no existe""")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "OK"),
            @ApiResponse(responseCode = "404",description = "No se encontro la categoria solicitada")
    })
    public ResponseEntity<Categoria> obtenerCategoria(@RequestBody IdRequest idRequest) throws SearchItemNotFoundException{
        return new ResponseEntity<>(service.obtenerCategoria(idRequest.getId()),HttpStatus.OK);
    }

    @PostMapping("/crear")
    @Operation(summary = "Crea una nueva categoria",description = """
            Crea una nueva categoria.\n
            arrojara un codigo de error bajo las siguientes circuntancias:
            *  El nombre de la categoria esta en blanco o no cumple con los criterios para ser valido
            \nEn todos estos casos la aplicacion lanzara como respuesta un codigo de error acompañado de un mensaje\
             que indicara el error que se esta presentando""")
    @ApiResponses({
            @ApiResponse(responseCode = "201",description = "Se creo la categoria"),
            @ApiResponse(responseCode = "400",description = "No se pudo crear la categoria debido a algun error",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ModelResponseForREST.class))})
    })
    public ResponseEntity<?> crearCategoria(@RequestBody CategoriaControllerModel categoria) throws InvalidInputException, ItemAlreadyExistException {
        if (service.crearCategoria(mapper.toDomainModel(categoria))){
            return new ResponseEntity<>(HttpStatus.CREATED);
        }else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/actualizar")
    @Operation(summary = "Actualiza una categoria",description = """
            Actualiza una categoria.\n
            arrojara un codigo de error bajo las siguientes circuntancias:
            *  No existe la categoria que se quiere actualizar
            *  El nombre de la categoria esta en blanco o no cumple con los criterios para ser valido
            \nEn todos estos casos la aplicacion lanzara como respuesta un codigo de error acompañado de un mensaje\
             que indicara el error que se esta presentando""")
    @ApiResponses({
            @ApiResponse(responseCode = "201",description = "Se creo la categoria"),
            @ApiResponse(responseCode = "400",description = "No se pudo crear la categoria debido a algun error")
    })
    public ResponseEntity<Categoria> actualizarCategoria(@RequestBody Categoria categoria) throws SearchItemNotFoundException,InvalidInputException{
        return new ResponseEntity<>(service.actualizarCategoria(categoria),HttpStatus.OK);
    }
    @DeleteMapping("/eliminar")
    @Operation(summary = "Elimina una categoria con base en el id de la categoria", description = """
            Elimina la categoria que contiene el id que es pasado como parte del cuerpo de la peticion
            este metodo recibe un objeto en el cuerpo de la peticion con una propiedad llamada id, retorna un error 404 si la categoria no existe""")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "OK"),
            @ApiResponse(responseCode = "404",description = "La categoria que se esta tratando de eliminar no existe")
    })
    public ResponseEntity<Categoria> eliminarCategoria(@RequestBody IdRequest idRequest) throws SearchItemNotFoundException {
        if (service.eliminarCategoria(idRequest.getId())){
            return new ResponseEntity<>(HttpStatus.CREATED);
        }else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
