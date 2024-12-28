package com.tienda.authController;

import com.tienda.administracion.adaptador.modelo.AdministradorPersistenceModel;
import com.tienda.authController.model.LoginDTO;
import com.tienda.exceptionHandler.excepciones.SearchItemNotFoundException;
import com.tienda.userSecurityService.aplicacion.puerto.salida.FindAdminByDoc;
import com.tienda.userSecurityService.aplicacion.puerto.salida.FindUsuarioByDoc;
import com.tienda.usuarios.adaptador.modelo.persistencia.UsuarioPersistenceModel;
import com.tienda.webConfigSecurity.JwtUtil;
import com.tienda.webConfigSecurity.totp.TotpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final FindUsuarioByDoc findUsuarioByDoc;
    private final FindAdminByDoc findAdminByDoc;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, FindUsuarioByDoc findUsuarioByDoc, FindAdminByDoc findAdminByDoc) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.findUsuarioByDoc = findUsuarioByDoc;
        this.findAdminByDoc = findAdminByDoc;
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginDTO dto) throws SearchItemNotFoundException {
        // 1. Autenticar usuario con documento y contraseña
        UsernamePasswordAuthenticationToken login=new UsernamePasswordAuthenticationToken(dto.getDocumento(),dto.getContrasena());
        Authentication authentication=this.authenticationManager.authenticate(login);

        // 2. Recuperar el usuario autenticado
        Optional<UsuarioPersistenceModel> usuario=findUsuarioByDoc.findByDoc(dto.getDocumento());
        Optional<AdministradorPersistenceModel> administrador= Optional.empty();
        if (usuario.isEmpty()){
            administrador=findAdminByDoc.findByDoc(dto.getDocumento());
            if (administrador.isEmpty()){
                System.out.println("no se encontro");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // Usuario no encontrado
            }
        }

        // 3. Validar el código TOTP
        boolean isTotpValid = false;
        if (usuario.isPresent()){
            isTotpValid=TotpUtils.validateTotp(usuario.get().getTotpSecret(), dto.getTotpCode());
        }
        if (administrador.isPresent()){
            isTotpValid=TotpUtils.validateTotp(administrador.get().getTotpSecret(), dto.getTotpCode());
        }
        System.out.println("linea 66 "+isTotpValid+" usuario:"+usuario.isPresent()+" admin:"+administrador.isPresent());
        if (!isTotpValid) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // TOTP inválido
        }

        // 4. Generar el JWT
        String jwt=this.jwtUtil.create(dto.getDocumento());
        System.out.println(jwt);

        // 5. Retornar el JWT en la cabecera
        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION,jwt).build();
    }
}
