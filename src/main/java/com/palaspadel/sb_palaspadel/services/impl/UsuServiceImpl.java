package com.palaspadel.sb_palaspadel.services.impl;

import com.palaspadel.sb_palaspadel.dto.AutenticacionResponseDto;
import com.palaspadel.sb_palaspadel.dto.RegistroRequestDto;
import com.palaspadel.sb_palaspadel.entities.Usg;
import com.palaspadel.sb_palaspadel.entities.Usu;
import com.palaspadel.sb_palaspadel.entities.UsuUsg;

import com.palaspadel.sb_palaspadel.entities.UsuUsgId;
import com.palaspadel.sb_palaspadel.exceptions.RecursoYaExisteException;
import com.palaspadel.sb_palaspadel.repositories.UsgRepository;
import com.palaspadel.sb_palaspadel.repositories.UsuRepository;
import com.palaspadel.sb_palaspadel.repositories.UsuUsgRepository;
import com.palaspadel.sb_palaspadel.security.JwtUtil;
import com.palaspadel.sb_palaspadel.services.UsuService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuServiceImpl implements UsuService {
    private final UsuRepository usuRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final UsgRepository usgRepository;
    private final UsuUsgRepository usuUsgRepository;

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

        // Guardar usuario en la BD
        Usu usuarioGuardado = usuRepository.save(usuario);

        // Asignar rol por defecto (USER)
        Optional<Usg> rolUser = usgRepository.findByUsgnom("USER");
        if (rolUser.isEmpty()) {
            throw new IllegalStateException("Rol USER no existe en la base de datos");
        }

        // Guardar relación usuario-rol
        UsuUsgId usuUsgId = new UsuUsgId();
        usuUsgId.setUsuusuid(usuarioGuardado.getId());
        usuUsgId.setUsuusgid(rolUser.get().getId());

        UsuUsg usuUsg = new UsuUsg();
        usuUsg.setId(usuUsgId);
        usuUsg.setUsuario(usuarioGuardado);
        usuUsg.setPermiso(rolUser.get());


        usuUsgRepository.save(usuUsg);

        // Generar token con el rol asignado
        String token = jwtUtil.generateToken(
                usuarioGuardado.getId().longValue(),
                usuarioGuardado.getUsuema(),
                List.of("USER"),
                request.isPermaneceLogged()
        );

        return new AutenticacionResponseDto(
                token,
                usuarioGuardado.getUsuema(),
                usuarioGuardado.getId()
        );
    }

    @Override
    public List<Usu> findAll() {
        return usuRepository.findAll();
    }

    @Override
    public Optional<Usu> obtenerUsuarioPorEmail(String usuema) {
        return usuRepository.findByUsuema(usuema);
    }
}