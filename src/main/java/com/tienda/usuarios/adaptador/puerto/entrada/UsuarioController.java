package com.tienda.usuarios.adaptador.puerto.entrada;

import com.tienda.exceptionHandler.excepciones.InvalidInputException;
import com.tienda.exceptionHandler.excepciones.ItemAlreadyExistException;
import com.tienda.exceptionHandler.excepciones.SearchItemNotFoundException;
import com.tienda.usuarios.adaptador.modelo.controller.*;
//import com.tienda.usuarios.adaptador.modelo.controller.IdRequest;
import com.tienda.usuarios.aplicacion.puerto.entrada.CasoUsoInhabilitarUsuario;
//import com.tienda.usuarios.dominio.Usuario;
import com.tienda.usuarios.aplicacion.puerto.entrada.UsuarioPortIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    private final MapperControllerToDomainUsuario_CrearUsuario mapper;
    private final MapperDomainUsuarioToUsuarioBasicData mapperBasicData;
    private final UsuarioPortIn servicioUsuario;
    private final CasoUsoInhabilitarUsuario servicioInhabilitarUsuario;

    @Autowired
    public UsuarioController(MapperControllerToDomainUsuario_CrearUsuario mapper, MapperDomainUsuarioToUsuarioBasicData mapperBasicData, UsuarioPortIn servicioUsuario, CasoUsoInhabilitarUsuario servicioInhabilitarUsuario) {
        this.mapper = mapper;
        this.mapperBasicData = mapperBasicData;
        this.servicioUsuario = servicioUsuario;
        this.servicioInhabilitarUsuario = servicioInhabilitarUsuario;
    }

    @PostMapping("/nuevousuario")
    public ResponseEntity<QrCodeUrl> crearUsuario(@RequestBody CrearUsuario_ControllerModel user) throws InvalidInputException, SearchItemNotFoundException, ItemAlreadyExistException {
        String qrCodeUrl=servicioUsuario.crearUsuario(mapper.toDomainModel(user));
        //se obtiene la url y se empaqueta en el JSON apropiado
        QrCodeUrl response=new QrCodeUrl();
        response.setQrCodeUrl(qrCodeUrl);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @PostMapping("/inhabilitar")
    public ResponseEntity<Void> inhabilitarUsuario(@RequestBody IdAndBooleanRequest idRequest) throws SearchItemNotFoundException {
        servicioInhabilitarUsuario.bloquear(idRequest.getId(),idRequest.isHabilitado());
        return ResponseEntity.ok().build();
    }
    @PostMapping("/usuarioPorDocumento")
    public ResponseEntity<UsuarioBasicData> consultarUsuarioPorDocumento(@RequestBody DocumentRequest documentRequest) throws SearchItemNotFoundException {
        UsuarioBasicData response=mapperBasicData.toBasicDataModel(
                servicioUsuario.obtenerPorDocumento(documentRequest.getDocumento())
        );
        return ResponseEntity.ok(response);
    }
    @PostMapping("/usuarioPorId")
    public ResponseEntity<UsuarioBasicData> consultarUsuarioPorDocumento(@RequestBody IdRequest idRequest) throws SearchItemNotFoundException {
        UsuarioBasicData response=mapperBasicData.toBasicDataModel(
                servicioUsuario.obtenerPorId(idRequest.getId())
        );
        return ResponseEntity.ok(response);
    }
    public ResponseEntity<Page<UsuarioBasicData>> consultarTodos(@RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "10") int elements){
        return ResponseEntity.ok(this.servicioUsuario.obtenerTodos(page, elements).map(mapperBasicData::toBasicDataModel));
    }
}
