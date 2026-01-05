package com.palaspadel.sb_palaspadel.services;

import com.palaspadel.sb_palaspadel.dto.PalaResponseDto;
import com.palaspadel.sb_palaspadel.dto.RecomendadorDto;
import com.palaspadel.sb_palaspadel.entities.Pal;
import com.palaspadel.sb_palaspadel.repositories.PalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Description: Interfaz para la lógica de negocio del recomendador de palas
 * <p>
 * Created by Andres on 2026
 *
 * @version 1.0
 */
public interface RecomendadorService {
    List<PalaResponseDto> recomendar(RecomendadorDto request);
}
