package com.tienda.administracion.aplicacion;

import com.tienda.administracion.aplicacion.puerto.entrada.PuertoEntradaAdmin;
import com.tienda.exceptionHandler.excepciones.InvalidInputException;
import com.tienda.exceptionHandler.excepciones.ItemAlreadyExistException;
import com.tienda.exceptionHandler.excepciones.SearchItemNotFoundException;
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
public class AdminService implements PuertoEntradaAdmin {
    private final String regexContrasena = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[\\-/*@#$%^&+=!])\\S{8,}$";
    private final String regexNombre="^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$";
    private final String regexCorreo = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private final String regexTelefono="^(\\+\\d{1,3})?\\s?\\d{10}$";

    private PuertoSalidaUsuario repository;
    private UsuariosPagSort_portOut pagSortPortOut;

    @Autowired
    public AdminService(PuertoSalidaUsuario repository, UsuariosPagSort_portOut pagSortPortOut) {
        this.repository = repository;
        this.pagSortPortOut = pagSortPortOut;
    }

    public AdminService() {
    }

    @Autowired
    public void setRepository(PuertoSalidaUsuario repository) {
        this.repository = repository;
    }

    @Autowired
    public void setPagSortPortOut(UsuariosPagSort_portOut pagSortPortOut) {
        this.pagSortPortOut = pagSortPortOut;
    }

    @Override
    public boolean existePorId(int id) {
        return repository.existById(id);
    }

    @Override
    public Page<Usuario> obtenerAdmins(int page, int elements) {
        return pagSortPortOut.obtenerTodos(page, elements);
        //return null;
    }

    @Override
    public Usuario obtenerUsuarioPorId(int id) throws SearchItemNotFoundException {
        return repository.getById(id);
    }

    @Override
    public Usuario obtenerPorDocumento(String documento) throws SearchItemNotFoundException {
        return repository.getByDocument(documento);
    }

    @Override
    @Secured("ROLE_ADMIN")
    public String registrarAdmin(Usuario admin) throws ItemAlreadyExistException, InvalidInputException {
        if (admin.getNombres()==null||admin.getNombres().isBlank() || !admin.getNombres().matches(regexNombre)){
            throw new InvalidInputException("Solo se permiten letras en el campo nombres");
        }
        if (admin.getApellidos()==null||admin.getApellidos().isBlank() ||!admin.getApellidos().matches(regexNombre)){
            throw new InvalidInputException("Solo se permiten letras en el campo apellidos");
        }
        if (admin.getCorreo()==null||!admin.getCorreo().matches(regexCorreo)){
            throw new InvalidInputException("Direccion de correo electronico invalida");
        }
        if (admin.getTelefono()==null){
            throw new InvalidInputException("No hay ningun numero de telefono");
        }
        String telefono=admin.getTelefono().replaceAll("[.,\\s]", "");
        if (!telefono.matches(regexTelefono)){
            throw new InvalidInputException("telefono invalido, ingrese un telefono valido EJ: +52 1234567890");
        }
        if (admin.getTelefono()==null){
            throw new InvalidInputException("No hay documento de identidad");
        }
        String doc= admin.getDocumento().replaceAll("[.,\\s]", "");
        if (!doc.matches("^\\d+$")){
            throw new InvalidInputException("el campo documento debe ser llenado solo por numeros");
        }
        if (admin.getContrasena()==null){
            throw new InvalidInputException("No hay ninguna contraseña");
        }
        if (!admin.getContrasena().matches(regexContrasena)){
            throw new InvalidInputException("""
                    Contraseña invalida, ingrese una contraseña que como minimo: Contenga al menos 8 caracteres,\
                     No contenga espacios en blanco\
                     Contenga al menos una letra mayúscula.\
                     Contenga al menos una letra minúscula.\
                     Contenga al menos un dígito.\
                     Contenga al menos un carácter especial (como @, #, $, %, etc.).""");
        }
        admin.setBloqueado(true);

        //Generar el secreto TOTP
        String totpSecret = TotpUtils.generateSecret();
        admin.setTotpSecret(totpSecret);

        Usuario response=this.repository.crearUsuario_sinCuenta(admin,1);

        return QrCodeUtils.generateQrCodeUrl(
                response.getDocumento(),
                response.getTotpSecret(),
                "Tienda"
        );
    }

    @Override
    public Usuario actualizarAdmin(Usuario admin) throws SearchItemNotFoundException, InvalidInputException {
        return null;
    }

    @Override
    public boolean eliminarAdminPorId(int id) throws SearchItemNotFoundException {
        return repository.deleteById(id);
    }
}
