package com.tienda.publicaciones.adaptador.puerto.entrada;

import com.tienda.publicaciones.adaptador.modelo.IdRequest;
import com.tienda.exceptionHandler.excepciones.InvalidInputException;
import com.tienda.exceptionHandler.excepciones.ItemAlreadyExistException;
import com.tienda.exceptionHandler.excepciones.SearchItemNotFoundException;
import com.tienda.publicaciones.adaptador.modelo.PublicacionControllerModel;
import com.tienda.publicaciones.aplicacion.puerto.entrada.PublicacionPortIn;
import com.tienda.publicaciones.dominio.Publicacion;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
@RestController
@RequestMapping("/productos")
public class PublicacionesController {
    private PublicacionPortIn service;
    private MapperControllerToDomainPublicacion mapper;

    @Autowired
    public void setMapper(MapperControllerToDomainPublicacion mapper) {
        this.mapper = mapper;
    }

    @Autowired
    public void setService(PublicacionPortIn service) {
        this.service = service;
    }
    @GetMapping("/")
    @Operation(summary = "Obtener todas las publicaciones de productos",
            description = "Obtiene todas las pubilcaciones almacenadas en la base de datos")
    @ApiResponse(responseCode = "200",description = "OK")
    public ResponseEntity<ArrayList<Publicacion>> obtenerPublicaciones(){
        return new ResponseEntity<>(service.obtenerPublicaciones(),HttpStatus.OK);
    }
    @PostMapping("/")
    @Operation(summary = "Busca una publicacion mediate el id de la publicacion", description = """
            Busca la publicacion del producto con base en el id de la publicacion,
            este metodo recibe un objeto en el cuerpo de la peticion el cual debe contener una propiedad llamada id, retorna un error 404 si la publicacion no existe""")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "OK"),
            @ApiResponse(responseCode = "404",description = "No se encontro la publicacion solicitada")
    })
    public ResponseEntity<Publicacion> obtenerPublicacion(@RequestBody IdRequest idRequest) throws SearchItemNotFoundException{
        return new ResponseEntity<>(service.obtenerPublicacion(idRequest.getId()),HttpStatus.OK);
    }
    @PostMapping("/crear")
    @Operation(summary = "Crea una nueva publicacion de venta de producto", description = """
            Crea una nueva publicacion.
            arrojara un codigo de error bajo las siguientes circunstancias:
              *  La categoria del producto no existe
              *  La cantidad de unidades disponibles es menor que 0
              *  El precio del producto es menor que 0
              *  La descripcion del producto esta en blanco
              *  El titulo de la publicacion esta en blanco o no cumple con los criterios para ser valido
            \nEn todos estos casos la aplicacion lanzara como respuesta un codigo de error acompañado de un mensaje\
             que indicara el error que se esta presentando""")
    @ApiResponses({
            @ApiResponse(responseCode = "201",description = "Se creo la publicacion"),
            @ApiResponse(responseCode = "404",description = "La categoria que se le esta tratando de asignar a la publicacion no existe"),
            @ApiResponse(responseCode = "400",description = "No se pudo publicar debido a algun error en la peticion recibida")
    })
    public ResponseEntity<Publicacion> crearPublicacion(@RequestBody Publicacion publicacion) throws InvalidInputException,SearchItemNotFoundException, ItemAlreadyExistException {
        Publicacion response=service.crearPublicacion(publicacion);
        if (response !=null){
            return new ResponseEntity<>(response,HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    @PutMapping("/actualizar")
    @Operation(summary = "Actualiza una publicacion de venta de producto", description = """
            Actualiza una publicacion.
            arrojara un codigo de error bajo las siguientes circunstancias:
              *  La publicacion que se esta tratando de actualizar no existe
              *  La categoria del producto no existe
              *  La cantidad de unidades disponibles es menor que 0
              *  El precio del producto es menor que 0
              *  La descripcion del producto esta en blanco
              *  El titulo de la publicacion esta en blanco o no cumple con los criterios para ser valido
            \nEn todos estos casos la aplicacion lanzara como respuesta un codigo de error acompañado de un mensaje\
             que indicara en mayor detalle el error que se esta presentando""")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "OK"),
            @ApiResponse(responseCode = "404",description = "La categoria que se le esta tratando de asignar a la publicacion no existe"),
            @ApiResponse(responseCode = "400",description = "No se pudo actualizar la publicacion debido a algun error en la peticion recibida")
    })
    public ResponseEntity<Publicacion> actualizarPublicacion(@RequestBody Publicacion publicacion) throws SearchItemNotFoundException,InvalidInputException{
        return new ResponseEntity<>(service.actualizarPublicacion(publicacion),HttpStatus.OK);
    }
    @DeleteMapping("/eliminar")
    @Operation(summary = "Elimina una publicacion con base en el id de la publicacion", description = """
            Elimina la publicacion que contiene el id que es pasado como parte del cuerpo de la peticion
            este metodo recibe un objeto en el cuerpo de la peticion con una propiedad llamada id, retorna un error 404 si la publicacion no existe""")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "OK"),
            @ApiResponse(responseCode = "404",description = "La publicacion que se esta tratando de eliminar no existe")
    })
    public ResponseEntity<Publicacion> eliminarPublicacion(@RequestBody IdRequest idRequest) throws SearchItemNotFoundException{
        if (service.eliminarPublicacion(idRequest.getId())){
            return new ResponseEntity<>(HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
