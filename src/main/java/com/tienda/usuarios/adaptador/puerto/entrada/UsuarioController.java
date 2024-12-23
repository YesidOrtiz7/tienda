package com.tienda.usuarios.adaptador.puerto.entrada;

import com.tienda.exceptionHandler.excepciones.InvalidInputException;
import com.tienda.exceptionHandler.excepciones.SearchItemNotFoundException;
import com.tienda.usuarios.adaptador.modelo.CrearUsuario_ControllerModel;
import com.tienda.usuarios.adaptador.modelo.IdAndBooleanRequest;
//import com.tienda.usuarios.adaptador.modelo.IdRequest;
import com.tienda.usuarios.adaptador.modelo.QrCodeUrl;
import com.tienda.usuarios.adaptador.modelo.UsuarioBasicData;
import com.tienda.usuarios.aplicacion.puerto.entrada.CasoUsoCrearUsuario;
import com.tienda.usuarios.aplicacion.puerto.entrada.CasoUsoInhabilitarUsuario;
//import com.tienda.usuarios.dominio.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    private final CasoUsoCrearUsuario serviceCrearUsuario;
    private final MapperControllerToDomainUsuario_CrearUsuario mapper;
    private final MapperDomainUsuarioToUsuarioBasicData mapperBasicData;
    private final CasoUsoInhabilitarUsuario inhabilitarUsuarioService;

    @Autowired
    public UsuarioController(CasoUsoCrearUsuario serviceCrearUsuario, MapperControllerToDomainUsuario_CrearUsuario mapper,
                             MapperDomainUsuarioToUsuarioBasicData mapperBasicData, CasoUsoInhabilitarUsuario inhabilitarUsuarioService) {
        this.serviceCrearUsuario = serviceCrearUsuario;
        this.mapper=mapper;
        this.mapperBasicData=mapperBasicData;
        this.inhabilitarUsuarioService = inhabilitarUsuarioService;
    }
    @PostMapping("/nuevousuario")
    public ResponseEntity<QrCodeUrl> crearUsuario(CrearUsuario_ControllerModel user) throws InvalidInputException {
        String qrCodeUrl=serviceCrearUsuario.crearUsuario(mapper.toDomainModel(user));
        QrCodeUrl response=new QrCodeUrl();
        response.setQrCodeUrl(qrCodeUrl);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @PostMapping("/inhabilitar")
    public ResponseEntity<Void> inhabilitarUsuario(@RequestBody IdAndBooleanRequest idRequest) throws SearchItemNotFoundException {
        inhabilitarUsuarioService.bloquear(idRequest.getId(),idRequest.isHabilitado());
        return ResponseEntity.ok().build();
    }
}
