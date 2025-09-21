package com.tienda.administracion.adaptador.puerto.entrada;

import com.tienda.administracion.adaptador.modelo.IdRequest;
import com.tienda.administracion.adaptador.modelo.QrCodeUrl;
import com.tienda.administracion.aplicacion.puerto.entrada.PuertoEntradaAdmin;
import com.tienda.exceptionHandler.excepciones.InvalidInputException;
import com.tienda.exceptionHandler.excepciones.ItemAlreadyExistException;
import com.tienda.exceptionHandler.excepciones.SearchItemNotFoundException;
import com.tienda.usuarios.adaptador.modelo.controller.CrearUsuario_ControllerModel;
import com.tienda.usuarios.adaptador.modelo.controller.UsuarioBasicData;
import com.tienda.usuarios.adaptador.puerto.entrada.MapperControllerToDomainUsuario_CrearUsuario;
import com.tienda.usuarios.adaptador.puerto.entrada.MapperDomainUsuarioToUsuarioBasicData;
import com.tienda.usuarios.dominio.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private PuertoEntradaAdmin service;
    private MapperDomainUsuarioToUsuarioBasicData mapper;
    private MapperControllerToDomainUsuario_CrearUsuario mapperCrearUsuario;

    @Autowired
    public AdminController(PuertoEntradaAdmin service, MapperDomainUsuarioToUsuarioBasicData mapper, MapperControllerToDomainUsuario_CrearUsuario mapperCrearUsuario) {
        this.service = service;
        this.mapper = mapper;
        this.mapperCrearUsuario = mapperCrearUsuario;
    }

    @GetMapping("/all")
    public ResponseEntity<Page<UsuarioBasicData>> obtenerAdministradores(@RequestParam int page,
                                                                         @RequestParam int elements){
        return ResponseEntity.ok(this.service.obtenerAdmins(page, elements)
                .map(mapper::toBasicDataModel));
    }
    @PostMapping("/id")
    public ResponseEntity<Usuario> obtenerAdminPorId(@RequestBody IdRequest idRequest) throws SearchItemNotFoundException {
        return ResponseEntity.ok(this.service.obtenerUsuarioPorId(idRequest.getId()));
    }
    @PostMapping("/registro")
    public ResponseEntity<QrCodeUrl> registrarNuevoAdministrador(@RequestBody CrearUsuario_ControllerModel admin)throws InvalidInputException, ItemAlreadyExistException {
        String qrCodeUrl=service.registrarAdmin(
                mapperCrearUsuario.toDomainModel(admin)
        );
        //se obtiene la url y se empaqueta en el JSON apropiado
        QrCodeUrl response=new QrCodeUrl(qrCodeUrl);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }
    /*@PutMapping("/actualizar")
    public ResponseEntity<AdministradorControllerModel> actualizarAdministrador(Administrador admin) throws InvalidInputException, SearchItemNotFoundException {
        AdministradorControllerModel response=mapper.toControllerModel(service.actualizarAdmin(admin));
        return new ResponseEntity<>(response,HttpStatus.OK);
    }*/
    @DeleteMapping("/eliminar")
    public ResponseEntity<?> eliminarAdministrador(@RequestBody IdRequest idRequest) throws SearchItemNotFoundException {
        service.eliminarAdminPorId(idRequest.getId());
        return ResponseEntity.ok().build();
    }
}
