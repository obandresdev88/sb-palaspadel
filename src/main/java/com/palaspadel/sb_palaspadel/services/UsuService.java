package com.palaspadel.sb_palaspadel.services;

import com.palaspadel.sb_palaspadel.entities.Usu;
import com.palaspadel.sb_palaspadel.repositories.UsuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Description: Logica de negocio implementada de la entidad Usu
 *
 * Created by Andres on 2025
 * @version 1.0
 */


@Service
@RequiredArgsConstructor // Lombok genera un constructor para
public class UsuService {

    private final UsuRepository usuRepository;

    public List<Usu> findAll() {
        return usuRepository.findAll();
    }

    public Optional<Usu>findByEmail(String email) {
        return usuRepository.findByUsuema(email);
    }

    public Usu save(Usu usu) {
        return usuRepository.save(usu);
    }

    public Usu findById(Integer id) {
        return usuRepository.findById(id).orElse(null);
    }

}
