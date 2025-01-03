package com.tienda.usuarios.aplicacion.servicio;

import com.tienda.exceptionHandler.excepciones.InvalidInputException;
import com.tienda.exceptionHandler.excepciones.SearchItemNotFoundException;
import com.tienda.usuarios.aplicacion.puerto.entrada.CasoUsoCrearUsuario;
import com.tienda.usuarios.aplicacion.puerto.salida.PuertoCrearUsuario;
import com.tienda.usuarios.dominio.Usuario;
import com.tienda.webConfigSecurity.totp.QrCodeUtils;
import com.tienda.webConfigSecurity.totp.TotpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicioCrearUsuario implements CasoUsoCrearUsuario {
    private PuertoCrearUsuario repository;
    private final String regexContrasena = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[\\-/*@#$%^&+=!])\\S{8,}$";
    private final String regexNombre="^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$";
    private final String regexCorreo = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    @Autowired
    public void setRepository(PuertoCrearUsuario repository) {
        this.repository = repository;
    }

    @Override
    public String crearUsuario(Usuario usuario) throws InvalidInputException, SearchItemNotFoundException {
        if (usuario.getNombres().isBlank() || !usuario.getNombres().matches(regexNombre)){
            throw new InvalidInputException("Solo se permiten letras en el campo nombres");
        }
        if (usuario.getApellidos().isBlank() ||!usuario.getApellidos().matches(regexNombre)){
            throw new InvalidInputException("Solo se permiten letras en el campo apellidos");
        }
        if (!usuario.getCorreo().matches(regexCorreo)){
            throw new InvalidInputException("Direccion de correo electronico invalida");
        }
        String telefono=usuario.getTelefono().replaceAll("[.,\\s]", "");
        if (!telefono.matches("^(\\+\\d{1,3})?\\s?\\d{10}$")){
            throw new InvalidInputException("telefono invalido, ingrese un telefono valido EJ: +52 1234567890");
        }
        String doc= usuario.getDocumento().replaceAll("[.,\\s]", "");
        if (!doc.matches("^\\d+$")){
            throw new InvalidInputException("el campo documento debe ser llenado solo por numeros");
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

        Usuario response=repository.crearUsuario(usuario);

        return QrCodeUtils.generateQrCodeUrl(
                response.getDocumento(),
                response.getTotpSecret(),
                "Tienda"
        );
    }
}
