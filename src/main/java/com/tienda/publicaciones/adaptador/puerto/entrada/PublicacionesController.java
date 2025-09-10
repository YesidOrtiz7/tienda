package com.tienda.publicaciones.adaptador.puerto.entrada;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tienda.multimedia.adaptador.puerto.entrada.MapperControllerToDomainImagen;
import com.tienda.multimedia.aplicacion.puerto.entrada.ImagenPortIn;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/productos")
public class PublicacionesController {
    private PublicacionPortIn service;
    private ImagenPortIn imagenService;
    private MapperControllerToDomainPublicacion mapper;
    private MapperControllerToDomainImagen mapperImagen;

    /*--------------------------------------------------------------------*/
    @Autowired
    public void setMapper(MapperControllerToDomainPublicacion mapper) {
        this.mapper = mapper;
    }

    @Autowired
    public void setImagenService(ImagenPortIn imagenService) {
        this.imagenService = imagenService;
    }

    @Autowired
    public void setService(PublicacionPortIn service) {
        this.service = service;
    }

    @Autowired
    public void setMapperImagen(MapperControllerToDomainImagen mapperImagen) {
        this.mapperImagen = mapperImagen;
    }

    /*--------------------------------------------------------------------*/
    @GetMapping("/")
    @Operation(summary = "Obtener todas las publicaciones de productos",
            description = "Obtiene todas las pubilcaciones almacenadas en la base de datos")
    @ApiResponse(responseCode = "200",description = "OK")
    public ResponseEntity<ArrayList<PublicacionControllerModel>> obtenerPublicaciones(){
        ArrayList<PublicacionControllerModel> response=new ArrayList<>();

        service.obtenerPublicaciones().forEach(
                (i)->{
                    if (i.isVisible()){
                        response.add(mapper.toControllerModel(i));
                    }
                }
        );
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    @PostMapping("/")
    @Operation(summary = "Busca una publicacion mediate el id de la publicacion", description = """
            Busca la publicacion del producto con base en el id de la publicacion,
            este metodo recibe un objeto en el cuerpo de la peticion el cual debe contener una propiedad llamada id, retorna un error 404 si la publicacion no existe""")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "OK"),
            @ApiResponse(responseCode = "404",description = "No se encontro la publicacion solicitada")
    })
    public ResponseEntity<PublicacionControllerModel> obtenerPublicacion(@RequestBody IdRequest idRequest) throws SearchItemNotFoundException{
        Publicacion p= service.obtenerPublicacion(idRequest.getId());
        PublicacionControllerModel publicacionControllerModel=mapper.toControllerModel(p);
        //publicacionControllerModel.setIdUsuario(p.getUsuario().getId());
        //publicacionControllerModel.setNombresUsuario(p.getUsuario().getNombres()+" "+p.getUsuario().getApellidos());
        imagenService.consultarImagenesPorPublicacion(idRequest.getId()).forEach(
                (i)->{publicacionControllerModel.getImagenes().add(
                        mapperImagen.toControllerModel(i)
                );}
        );
        return new ResponseEntity<>(
                publicacionControllerModel,
                HttpStatus.OK
        );
    }
    @PostMapping(
            value="/crear",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
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
    public ResponseEntity<Void> crearPublicacion(
            @RequestPart("publicacion") Publicacion publicacionJson,
            @RequestPart(value = "imagenes", required = false)List<MultipartFile> imagenes
    ) throws InvalidInputException,SearchItemNotFoundException, ItemAlreadyExistException {
        // convertir el JSON recibido a objeto Publicacion
        System.out.println(publicacionJson.getUsuario().getDocumento());
        Publicacion response;
        response=service.crearPublicacion(
                publicacionJson
        );
        if (response !=null){
            // Procesar las imagenes si llegaron
            if (imagenes !=null && !imagenes.isEmpty()){
                imagenService.guardarImagenes(imagenes, response.getId());
            }

            //enviar la respuesta
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
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
