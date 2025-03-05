package com.palaspadel.sb_palaspadel.services;

import com.palaspadel.sb_palaspadel.entities.Pal;
import com.palaspadel.sb_palaspadel.repositories.PalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description: Logica de negocio implementada de la entidad Pal
 * <p>
 * Created by Andres on 2025
 *
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class PalService {
    private final PalRepository palRepository;

    public List<Pal> findAll() {
        return palRepository.findAll();
    }

    public Pal agregarPala(Pal pala) {
        return palRepository.save(pala);
    }

}
