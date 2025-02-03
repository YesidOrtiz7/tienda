package com.tienda.usuarios.aplicacion.servicio;

import com.tienda.exceptionHandler.excepciones.InvalidInputException;
import com.tienda.exceptionHandler.excepciones.ItemAlreadyExistException;
import com.tienda.exceptionHandler.excepciones.SearchItemNotFoundException;
import com.tienda.usuarios.aplicacion.puerto.entrada.UsuarioPortIn;
import com.tienda.usuarios.aplicacion.puerto.salida.InhabilitarUsuarioQuery_portOut;
import com.tienda.usuarios.aplicacion.puerto.salida.PuertoSalidaUsuario;
import com.tienda.usuarios.aplicacion.puerto.salida.UsuariosPagSort_portOut;
import com.tienda.usuarios.dominio.Usuario;
import com.tienda.webConfigSecurity.totp.QrCodeUtils;
import com.tienda.webConfigSecurity.totp.TotpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

@Service
public class ServicioUsuario implements UsuarioPortIn {
    private final PuertoSalidaUsuario repository;
    private final InhabilitarUsuarioQuery_portOut inhabilitarUsuarioQuery;
    private final UsuariosPagSort_portOut pagSortPortOut;
    private final String regexContrasena = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[\\-/*@#$%^&+=!])\\S{8,}$";
    private final String regexNombre="^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$";
    private final String regexCorreo = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private final String regexTelefono="^(\\+\\d{1,3})?\\s?\\d{10}$";

    @Autowired
    public ServicioUsuario(PuertoSalidaUsuario repository, InhabilitarUsuarioQuery_portOut inhabilitarUsuarioQuery, UsuariosPagSort_portOut pagSortPortOut) {
        this.repository = repository;
        this.inhabilitarUsuarioQuery = inhabilitarUsuarioQuery;
        this.pagSortPortOut = pagSortPortOut;
    }

    @Override
    public String crearUsuario(Usuario usuario) throws InvalidInputException, SearchItemNotFoundException, ItemAlreadyExistException {
        if (usuario.getNombres()==null||usuario.getNombres().isBlank() || !usuario.getNombres().matches(regexNombre)){
            throw new InvalidInputException("Solo se permiten letras en el campo nombres");
        }
        if (usuario.getApellidos()==null||usuario.getApellidos().isBlank() ||!usuario.getApellidos().matches(regexNombre)){
            throw new InvalidInputException("Solo se permiten letras en el campo apellidos");
        }
        if (usuario.getCorreo()==null||!usuario.getCorreo().matches(regexCorreo)){
            throw new InvalidInputException("Direccion de correo electronico invalida");
        }
        if (usuario.getTelefono()==null){
            throw new InvalidInputException("No hay ningun numero de telefono");
        }
        String telefono=usuario.getTelefono().replaceAll("[.,\\s]", "");
        if (!telefono.matches(regexTelefono)){
            throw new InvalidInputException("telefono invalido, ingrese un telefono valido EJ: +52 1234567890");
        }
        if (usuario.getTelefono()==null){
            throw new InvalidInputException("No hay documento de identidad");
        }
        String doc= usuario.getDocumento().replaceAll("[.,\\s]", "");
        if (!doc.matches("^\\d+$")){
            throw new InvalidInputException("el campo documento debe ser llenado solo por numeros");
        }
        if (usuario.getContrasena()==null){
            throw new InvalidInputException("No hay ninguna contraseña");
        }
        if (!usuario.getContrasena().matches(regexContrasena)){
            throw new InvalidInputException("""
                    Contraseña invalida, ingrese una contraseña que como minimo: Contenga al menos 8 caracteres,\
                     No contenga espacios en blanco\
                     Contenga al menos una letra mayúscula.\
                     Contenga al menos una letra minúscula.\
                     Contenga al menos un dígito.\
                     Contenga al menos un carácter especial (como @, #, $, %, etc.).""");
        }
        usuario.setBloqueado(true);

        //Generar el secreto TOTP
        String totpSecret = TotpUtils.generateSecret();
        usuario.setTotpSecret(totpSecret);

        Usuario response=this.repository.crearUsuario(usuario,2);

        return QrCodeUtils.generateQrCodeUrl(
                response.getDocumento(),
                response.getTotpSecret(),
                "Tienda"
        );
    }

    /**bloquea al usuario que contenga el id especificado, cuando el segundo valor
     *  es false el usuario es bloqueado, cuando es true  el usuario es desbloqueado*/
    @Override
    @Secured("ROLE_ADMIN")
    public void bloquear(int id, boolean habilitado) throws SearchItemNotFoundException {
        if (!this.repository.existById(id)){
            throw new SearchItemNotFoundException("El usuario no existe");
        }
        if (habilitado){
            this.inhabilitarUsuarioQuery.desbloquear(id);
        }else {
            this.inhabilitarUsuarioQuery.bloquear(id);
        }
    }

    @Override
    public Usuario obtenerPorDocumento(String documento) throws SearchItemNotFoundException {
        return this.repository.getByDocument(documento);
    }

    @Override
    public Usuario obtenerPorId(int id) throws SearchItemNotFoundException {
        return this.repository.getById(id);
    }

    @Override
    public boolean existePorId(int id) {
        return this.repository.existById(id);
    }

    @Override
    public boolean existByDocumento(String documento) {
        return this.repository.existByDocumento(documento);
    }

    @Override
    public boolean eliminarPorId(int id) throws SearchItemNotFoundException {
        return this.repository.deleteById(id);
    }

    @Override
    public Page<Usuario> obtenerTodos(int page, int elements) {
        return pagSortPortOut.obtenerTodos(page,elements);
    }
}
