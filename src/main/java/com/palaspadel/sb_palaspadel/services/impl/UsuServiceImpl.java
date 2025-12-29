package com.palaspadel.sb_palaspadel.services.impl;

import com.palaspadel.sb_palaspadel.dto.AutenticacionResponseDto;
import com.palaspadel.sb_palaspadel.dto.RegistroRequestDto;
import com.palaspadel.sb_palaspadel.entities.Usu;
import com.palaspadel.sb_palaspadel.exceptions.RecursoYaExisteException;
import com.palaspadel.sb_palaspadel.repositories.UsuRepository;
import com.palaspadel.sb_palaspadel.security.JwtUtil;
import com.palaspadel.sb_palaspadel.services.UsuService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuServiceImpl implements UsuService {
    private final UsuRepository usuRepository;
    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;


    @Transactional
    public AutenticacionResponseDto registrar(RegistroRequestDto request) {
        // Validar email único
        if (usuRepository.existsByUsuema(request.getUsuema())) {
            throw new RecursoYaExisteException("usuario", "email", request.getUsuema());
        }

        // Crear usuario
        Usu usuario = new Usu();
        usuario.setUsunom(request.getUsunom());
        usuario.setUsuema(request.getUsuema());
        usuario.setUsupas(passwordEncoder.encode(request.getUsupas()));

        // Manejar nivel (fallback a INTERMEDIO)
        try {
            Usu.UsuNivel nivel = Usu.UsuNivel.valueOf(request.getUsuniv().toUpperCase());
            usuario.setUsuniv(nivel);
        } catch (Exception e) {
            usuario.setUsuniv(Usu.UsuNivel.INTERMEDIO);
        }

        // Guardar en BD
        Usu usuarioGuardado = usuRepository.save(usuario);

/* LO COMENTO PORQUE AHORA USAREMOS JWT PARA LA AUTENTICACION
        // Retornar respuesta (token mock por ahora)
        return new AutenticacionResponseDto(
                "TOKEN_MOCK",
                usuarioGuardado.getUsuema(),
                usuarioGuardado.getId()
        );*/
        String token = jwtUtil.generateToken(
                usuarioGuardado.getId().longValue(),
                usuarioGuardado.getUsuniv().name(),
                request.isPermaneceLogged());

        return new AutenticacionResponseDto(token, usuarioGuardado.getUsuema(), usuarioGuardado.getId());
    }

    @Override
    public List<Usu> findAll() {
        return List.of();
    }
}



