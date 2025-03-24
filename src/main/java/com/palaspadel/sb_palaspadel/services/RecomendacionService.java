package com.palaspadel.sb_palaspadel.services;

import com.palaspadel.sb_palaspadel.entities.Pal;
import com.palaspadel.sb_palaspadel.repositories.PalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Description: Logica de negocio implementada de la entidad RecomendacionService
 * <p>
 * Created by Andres on 2025
 *
 * @version 1.0
 */
@Service
public class RecomendacionService {

    @Autowired
    private PalRepository palRepository;

    public List<Pal> recomendarPalasPorEstilo(String balance, String forma, String dureza, BigDecimal palpre) {
        return palRepository.findMatchingPals(
                Pal.BalancePala.valueOf(balance.toUpperCase()),  // Convierte String a Enum
                Pal.FormaPala.valueOf(forma.toUpperCase()),      // Convierte String a Enum
                Pal.DurezaPala.valueOf(dureza.toUpperCase()),    // Convierte String a Enum
                palpre
        );
    }
}
