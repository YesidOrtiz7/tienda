package com.tienda.multimedia.adaptador.puerto.entrada;

import com.tienda.exceptionHandler.excepciones.SearchItemNotFoundException;
import com.tienda.multimedia.adaptador.modelo.controller.ImagenProductoControllerModel;
import com.tienda.multimedia.aplicacion.puerto.entrada.ImagenPortIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/imagenes")
public class ImagenController {
    private ImagenPortIn service;
    private MapperControllerToDomainImagen mapper;

    /*-----------------------------------------------------------*/
    @Autowired
    public void setService(ImagenPortIn service) {
        this.service = service;
    }

    @Autowired
    public void setMapper(MapperControllerToDomainImagen mapper) {
        this.mapper = mapper;
    }

    /*-----------------------------------------------------------*/
    @GetMapping("/{publicacion}")
    public ResponseEntity<List<ImagenProductoControllerModel>> obtenerImagenesPorPublicacion(@PathVariable int idPublicacion){
        List<ImagenProductoControllerModel> response=new ArrayList<>();
        service.consultarImagenesPorPublicacion(idPublicacion).forEach(
                (i)->response.add(
                        mapper.toControllerModel(i)
                )
        );
        return ResponseEntity.ok(response);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Resource> obtenerImagen(@PathVariable int id) throws SearchItemNotFoundException {
        if (service.imagenExiste(id)){
            throw new SearchItemNotFoundException("No existe esta imagen");
        }
        return ResponseEntity.ok(service.consultarImagenPorId(id));
    }
}
