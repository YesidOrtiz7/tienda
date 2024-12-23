package com.tienda.usuarios.aplicacion.servicio;

import com.tienda.exceptionHandler.excepciones.InvalidInputException;
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

    @Autowired
    public void setRepository(PuertoCrearUsuario repository) {
        this.repository = repository;
    }

    @Override
    public String crearUsuario(Usuario usuario) throws InvalidInputException {
        if (usuario.getPrimerNombre().isEmpty() || !usuario.getPrimerNombre().matches(regexNombre)){
            throw new InvalidInputException("Solo se permiten letras en el campo primer nombre");
        }
        if (usuario.getSegundoNombre().isBlank()){
            usuario.setSegundoNombre("");
        } else if (!usuario.getSegundoNombre().matches(regexNombre)){
            throw new InvalidInputException("Solo se permiten letras en el campo segundo nombre");
        }
        if (usuario.getPrimerApellido().isEmpty() ||!usuario.getPrimerApellido().matches(regexNombre)){
            throw new InvalidInputException("Solo se permiten letras en el campo primer apellido");
        }
        if (usuario.getSegundoApellido().isBlank()){
            usuario.setSegundoApellido("");
        }else if (!usuario.getSegundoApellido().matches(regexNombre)){
            throw new InvalidInputException("Solo se permiten letras en el campo segundo apellido");
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
        usuario.setHabilitado(true);
        usuario.setSaldoEnCuenta(0);

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
