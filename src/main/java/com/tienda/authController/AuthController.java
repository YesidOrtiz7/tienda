package com.tienda.authController;

import com.tienda.authController.model.LoginDTO;
import com.tienda.exceptionHandler.excepciones.InvalidInputException;
import com.tienda.exceptionHandler.excepciones.NotAuthorizedException;
import com.tienda.exceptionHandler.excepciones.SearchItemNotFoundException;
import com.tienda.userSecurityService.aplicacion.puerto.salida.FindUsuarioByDoc;
import com.tienda.usuarios.adaptador.modelo.persistencia.UsuarioNombreApellidoDTO;
import com.tienda.usuarios.adaptador.modelo.persistencia.UsuarioPersistenceModel;
import com.tienda.usuarios.aplicacion.puerto.salida.ObtenerNombreUsuarioQuery_portOut;
import com.tienda.usuarios.aplicacion.puerto.salida.PuertoSalidaUsuario;
import com.tienda.webConfigSecurity.JwtUtil;
import com.tienda.webConfigSecurity.totp.TotpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final FindUsuarioByDoc findUsuarioByDoc;
    private final ObtenerNombreUsuarioQuery_portOut obtenerNombreUsuarioQuery;
    private final PuertoSalidaUsuario usuarioRepository;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, FindUsuarioByDoc findUsuarioByDoc, ObtenerNombreUsuarioQuery_portOut obtenerNombreUsuarioQuery, PuertoSalidaUsuario usuarioRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.findUsuarioByDoc = findUsuarioByDoc;
        this.obtenerNombreUsuarioQuery = obtenerNombreUsuarioQuery;
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginDTO dto) throws SearchItemNotFoundException, InvalidInputException, NotAuthorizedException {
        // 1. Autenticar usuario con documento y contraseña
        UsernamePasswordAuthenticationToken login=new UsernamePasswordAuthenticationToken(dto.getDocumento(),dto.getContrasena());
        Authentication authentication=this.authenticationManager.authenticate(login);

        // 2. Recuperar el usuario autenticado
        UsuarioPersistenceModel usuario=findUsuarioByDoc.findByDoc(dto.getDocumento())
                .orElseThrow(()->new NotAuthorizedException());// Usuario no encontrado

        // 3. Validar el código TOTP
        boolean isTotpValid = false;
        if (dto.getTotpCode()==null||dto.getTotpCode().isBlank()){
            throw new InvalidInputException("No se ha ingresado el codigo TOTP");
        }
        isTotpValid=TotpUtils.validateTotp(usuario.getTotpSecret(), dto.getTotpCode());

        System.out.println("AuthController, totp valido: "+isTotpValid+" usuario: "+usuario.getUserName());
        if (!isTotpValid) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // TOTP inválido
        }

        // 4. Generar el JWT
        String jwt=this.jwtUtil.create(dto.getDocumento());
        //System.out.println(jwt);

        // 5. Retornar el JWT en la cabecera
        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION,jwt).build();
    }
    @GetMapping("/validate")
    public ResponseEntity<Void> validCredentials(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization){
        String jwt;
        if (authorization!=null && authorization.startsWith("Bearer ")){
            jwt = authorization.substring(7);
            System.out.println("AuthController: token encontrado");
            if(jwtUtil.isValid(jwt)){
                System.out.println("AuthController: token valido");
                System.out.println(jwtUtil.isValid(jwt));
                return ResponseEntity.ok().build();
            }
        }
        System.out.println("AuthController: token no encontrado");
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
    }
    @GetMapping("/get-username")
    public ResponseEntity<String> getUserName(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) throws SearchItemNotFoundException {
        String jwt;
        if (authorization!=null && authorization.startsWith("Bearer ")){
            jwt = authorization.substring(7);
            System.out.println("AuthController: token encontrado");
            String documento=jwtUtil.getUserName(jwt);
            UsuarioNombreApellidoDTO usuarioNombreApellidoDTO=obtenerNombreUsuarioQuery.findNombresAndApellidosByDocumento(documento)
                    .orElseThrow(()->new SearchItemNotFoundException("no se encontro el usuario"));
            return ResponseEntity.ok(usuarioNombreApellidoDTO.getNombres()+" "+usuarioNombreApellidoDTO.getApellidos());
        }
        System.out.println("AuthController: token no encontrado");
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
    }
    @GetMapping("/get-roles")
    public ResponseEntity<List<String>> getRoles(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization){
        String jwt;
        if (authorization!=null && authorization.startsWith("Bearer ")){
            jwt = authorization.substring(7);
            System.out.println("AuthController: token encontrado");
            String documento=jwtUtil.getUserName(jwt);
            List<String> roles=usuarioRepository.getRolesByDocument(documento);
            return ResponseEntity.ok(roles);
        }
        System.out.println("AuthController: token no encontrado");
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
    }
}
