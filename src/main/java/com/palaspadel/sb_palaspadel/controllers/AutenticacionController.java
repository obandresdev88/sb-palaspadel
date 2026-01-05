package com.palaspadel.sb_palaspadel.controllers;

import com.palaspadel.sb_palaspadel.dto.AutenticacionResponseDto;
import com.palaspadel.sb_palaspadel.dto.LoginRequestDto;
import com.palaspadel.sb_palaspadel.dto.RegistroRequestDto;

import com.palaspadel.sb_palaspadel.entities.Usu;
import com.palaspadel.sb_palaspadel.entities.UsuUsg;

import com.palaspadel.sb_palaspadel.security.JwtUtil;
import com.palaspadel.sb_palaspadel.services.UsuService;
import com.palaspadel.sb_palaspadel.services.UsuUsgService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AutenticacionController {

    private final UsuService usuService;

    private final UsuUsgService usuUsgService;

    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @PostMapping("/registro")
    public ResponseEntity<AutenticacionResponseDto> registrar(@Valid @RequestBody RegistroRequestDto request) {
        AutenticacionResponseDto response = usuService.registrar(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid  @RequestBody LoginRequestDto request) {
        Optional<Usu> optUsu = usuService.obtenerUsuarioPorEmail(request.getUsuema());
        // Verificar si el email existe en la base de datos
        if (optUsu.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
        }
        Usu usu = optUsu.get();
        // Verificar contraseña con el metodo matches de PasswordEncoder que compara la contraseña en texto plano con la encriptada
        if (!passwordEncoder.matches(request.getUsupas(), usu.getUsupas())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
        }
        // Ver que rol o permisos tiene el usuario para incluirlo en el token JWT
        List<UsuUsg> usuUsgs  = usuUsgService.obtenerRolesPorUsuario(usu);
        List <String> roles = usuUsgs.stream()
                .map(uu -> uu.getPermiso().getUsgnom())
                .toList();

        String token = jwtUtil.generateToken(
                usu.getId().longValue(),
                usu.getUsuniv() != null ? usu.getUsuniv().name() : "INTERMEDIO",
                roles,
                request.isPermaneceLogged()
        );

        AutenticacionResponseDto resp = new AutenticacionResponseDto(token, usu.getUsuema(), usu.getUsunom(),usu.getId(), roles);
        return ResponseEntity.ok(resp);
    }
}
