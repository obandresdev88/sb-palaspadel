package com.palaspadel.sb_palaspadel.services;

import com.palaspadel.sb_palaspadel.entities.Rev;
import com.palaspadel.sb_palaspadel.entities.Usu;
import com.palaspadel.sb_palaspadel.repositories.RevRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description: Logica de negocio implementada de la entidad Rev
 * <p>
 * Created by Andres on 2025
 *
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class RevService {
    private final RevRepository revRepository;

    public List<Rev> findAll() {
        return revRepository.findAll();
    }

    public List<Rev> obtenerReseñasPorUsuario(Usu usuario) {
        return revRepository.findByRevusu(usuario);
    }

    public Rev agregarReseña(Rev reseña) {
        return revRepository.save(reseña);
    }
}
