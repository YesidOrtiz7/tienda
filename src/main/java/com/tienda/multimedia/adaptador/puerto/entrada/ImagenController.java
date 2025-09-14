package com.tienda.multimedia.adaptador.puerto.entrada;

import com.tienda.exceptionHandler.excepciones.SearchItemNotFoundException;
import com.tienda.multimedia.adaptador.modelo.controller.ImagenProductoControllerModel;
import com.tienda.multimedia.aplicacion.puerto.entrada.ImagenPortIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
    @GetMapping("/publicacion/{idPublicacion}")
    public ResponseEntity<List<ImagenProductoControllerModel>> obtenerImagenesPorPublicacion(@PathVariable int idPublicacion){
        List<ImagenProductoControllerModel> response=new ArrayList<>();
        service.consultarImagenesPorPublicacion(idPublicacion).forEach(
                (i)->response.add(
                        mapper.toControllerModel(i)
                )
        );
        return ResponseEntity.ok(response);
    }
    @GetMapping("/id/{id}")
    public ResponseEntity<Resource> obtenerImagen(@PathVariable int id) throws SearchItemNotFoundException {
        if (!service.imagenExiste(id)){
            throw new SearchItemNotFoundException("No existe esta imagen");
        }
        // Recuperamos la imagen como recurso
        Resource recurso = service.consultarImagenPorId(id);

        // Determinar el tipo de respuesta que recibira el navegador (MIME) automáticamente
        String contentType = "application/octet-stream"; // fallback
        try {
            contentType = Files.probeContentType(Path.of(recurso.getFile().getAbsolutePath()));
        } catch (IOException ex) {
            throw new SearchItemNotFoundException("No se pudo recuperar la imagen");
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(recurso);
    }
}
