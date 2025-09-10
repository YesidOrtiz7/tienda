package com.tienda.multimedia.aplicacion.servicio;

import com.tienda.exceptionHandler.excepciones.ItemAlreadyExistException;
import com.tienda.exceptionHandler.excepciones.SearchItemNotFoundException;
import com.tienda.multimedia.aplicacion.puerto.entrada.ImagenPortIn;
import com.tienda.multimedia.aplicacion.puerto.salida.ImagenPortOut;
import com.tienda.multimedia.domino.ImagenProductoDomainModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class ImagenService implements ImagenPortIn {
    private ImagenPortOut repository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    /*-----------------------------------------------------------------------*/
    @Autowired
    public void setRepository(ImagenPortOut repository) {
        this.repository = repository;
    }

    /*-----------------------------------------------------------------------*/

    @Override
    public List<ImagenProductoDomainModel> consultarImagenesPorPublicacion(int idPublicacion) {
        return repository.consultarImagenesPorPublicacion(idPublicacion);
    }

    @Override
    public Resource consultarImagenPorId(int idImagen) throws SearchItemNotFoundException {
        String nombreArchivo=repository.consultarImagenPorId(idImagen).getNombreArchivo();
        try {
            Path filePath=Paths.get(uploadDir).resolve(nombreArchivo);
            Resource resource=new UrlResource(filePath.toUri());
            if (resource.exists()){
                return resource;
            } else {
                return null;
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean guardarImagenes(List<MultipartFile> imagenes, int idPublicacion) throws ItemAlreadyExistException, SearchItemNotFoundException {

        int contador=1;
        for (MultipartFile imagen:imagenes){
            // verificando la validez de la imagen
            String contentType=imagen.getContentType();
            if (contentType==null || contentType.isBlank()){
                throw new IllegalArgumentException("No se encontro el archivo");
            }
            if (!contentType.equals("image/jpeg") && !contentType.equals("image/png")){
                throw new IllegalArgumentException("Solo se admiten imagenes en formato JPEG o PNG");
            }
            // guardando imagen
            Path destino= Paths.get(uploadDir, idPublicacion+"_"+contador);
            try {
                Files.copy(imagen.getInputStream(), destino, StandardCopyOption.REPLACE_EXISTING);
                repository.guardarImagen(imagen.getOriginalFilename(),idPublicacion);
            } catch (IOException e) {
                throw new RuntimeException("la imagen "+imagen.getOriginalFilename()+" no se pudo guardar");
            }
            contador++;
        }
        return true;
    }

    @Override
    public boolean eliminarImagenPorId(int idImagen) throws SearchItemNotFoundException {
        Path destino= Paths.get(uploadDir, repository.consultarImagenPorId(idImagen).getNombreArchivo());
        try {
            Files.deleteIfExists(destino);
        } catch (IOException e) {
            throw new SearchItemNotFoundException("la imagen con el id "+idImagen+" no se pudo eliminar");
        }
        return repository.eliminarImagenPorId(idImagen);
    }

    @Override
    public boolean imagenExiste(int idImagen) {
        return repository.imagenExiste(idImagen);
    }
}
