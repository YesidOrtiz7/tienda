package com.tienda.administracion.aplicacion;

import com.tienda.administracion.aplicacion.puerto.entrada.PuertoEntradaAdmin;
import com.tienda.administracion.aplicacion.puerto.salida.PuertoSalidaAdmin;
import com.tienda.administracion.dominio.Administrador;
import com.tienda.exceptionHandler.excepciones.InvalidInputException;
import com.tienda.exceptionHandler.excepciones.ItemAlreadyExistException;
import com.tienda.exceptionHandler.excepciones.SearchItemNotFoundException;
import com.tienda.webConfigSecurity.totp.QrCodeUtils;
import com.tienda.webConfigSecurity.totp.TotpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AdminService implements PuertoEntradaAdmin {
    private PuertoSalidaAdmin repository;
    private final String regexContrasena = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[\\-/*@#$%^&+=!])\\S{8,}$";
    private final String regexNombre="^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$";

    @Autowired
    public void setRepository(PuertoSalidaAdmin repository) {
        this.repository = repository;
    }

    @Override
    public boolean existePorId(int id) {
        return repository.existById(id);
    }

    @Override
    public ArrayList<Administrador> obtenerAdmins() {
        return repository.obtenerAdmins();
    }

    @Override
    public Administrador obtenerAdminPorId(int id) throws SearchItemNotFoundException {
        return repository.obtenerAdminPorId(id);
    }

    @Override
    public Administrador obtenerAdminPorDocumento(String documento) throws SearchItemNotFoundException {
        return repository.findByDocument(documento);
    }

    @Override
    public String registrarAdmin(Administrador admin) throws ItemAlreadyExistException, InvalidInputException {
        if (admin.getNombres().isEmpty() || !admin.getNombres().matches(regexNombre)){
            throw new InvalidInputException("Solo se permiten letras en el campo nombre");
        }
        if (admin.getApellidos().isEmpty() || !admin.getApellidos().matches(regexNombre)){
            throw new InvalidInputException("Solo se permiten letras en el campo apellidos");
        }
        String doc= admin.getDocumento().replaceAll("[.,\\s]", "");
        if (!doc.matches("^\\d+$")){
            throw new InvalidInputException("el campo documento debe ser llenado solo por numeros");
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
        admin.setHabilitado(true);

        //Generar el secreto TOTP
        String totpSecret= TotpUtils.generateSecret();
        admin.setTotpSecret(totpSecret);

        Administrador response=repository.registrarAdmin(admin);

        return QrCodeUtils.generateQrCodeUrl(
                response.getDocumento(),
                response.getTotpSecret(),
                "Tienda"
        );
    }

    @Override
    public Administrador actualizarAdmin(Administrador admin) throws SearchItemNotFoundException, InvalidInputException {
        if(!repository.existById(admin.getId())){
            throw new SearchItemNotFoundException("No existe el administrador");
        }
        if (admin.getNombres().isEmpty() || !admin.getNombres().matches(regexNombre)){
            throw new InvalidInputException("Solo se permiten letras en el campo nombre");
        }
        if (admin.getApellidos().isEmpty() || !admin.getApellidos().matches(regexNombre)){
            throw new InvalidInputException("Solo se permiten letras en el campo nombre");
        }
        String doc= admin.getDocumento().replaceAll("[.,\\s]", "");
        if (!doc.matches("^\\d+$")){
            throw new InvalidInputException("el campo documento debe ser llenado solo por numeros");
        }
        if (!admin.getContrasena().matches(regexContrasena)){
            throw new InvalidInputException("""
                    Contraseña invalida, ingrese una contraseña que como minimo: Contenga al menos 8 caracteres,\
                     Contenga al menos una letra mayúscula.\
                     Contenga al menos una letra minúscula.\
                     Contenga al menos un dígito.\
                     Contenga al menos un carácter especial (como @, #, $, %, etc.).""");
        }
        return repository.actualizarAdmin(admin);
    }

    @Override
    public boolean eliminarAdminPorId(int id) throws SearchItemNotFoundException {
        return repository.eliminarAdminPorId(id);
    }
}
